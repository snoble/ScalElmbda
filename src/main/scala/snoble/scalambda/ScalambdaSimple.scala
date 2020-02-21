package snoble.scalambda

import scalaj.http._
import scala.annotation.tailrec
import scala.util.Try
import io.circe.syntax._, io.circe.{Encoder, Json, Decoder}, io.circe.parser.decode

import snoble.scalambda.proto.simple.{Request, Response}

object ScalambdaSimple {

  case class AWSResponse(
    body: String,
    headers: Map[String, String]
  )
  implicit val encodeResponse: Encoder[AWSResponse] = Encoder.instance[AWSResponse](r =>
    Json.obj(
      "body" -> r.body.asJson,
      "headers" -> r.headers.asJson,
      "isBase64Encoded" -> true.asJson
    )
  )

  case class AWSRequest(
    body: String,
    httpMethod: String,
    resource: String,
    path: String,
    isBase64Encoded: Boolean
  )
  implicit val decodeRequest: Decoder[AWSRequest] = Decoder.instance[AWSRequest]( c =>
      for {
        body <- c.downField("body").as[String]
        httpMethod <- c.downField("httpMethod").as[String]
        resource <- c.downField("resource").as[String]
        path <- c.downField("path").as[String]
        isBase64Encoded <- c.downField("isBase64Encoded").as[Boolean]
      } yield AWSRequest(body, httpMethod, resource, path, isBase64Encoded)
  )

  val base64Decoder = java.util.Base64.getDecoder()
  val base64Encoder = java.util.Base64.getEncoder()

  def main(args: Array[String]): Unit = {
    println("Starting")
    val runtimeApi = System.getenv("AWS_LAMBDA_RUNTIME_API")
    
    @tailrec
    def handleRequest(): Unit = {
      val request: HttpResponse[String] = Http(s"http://$runtimeApi/2018-06-01/runtime/invocation/next").asString
      val requestId = request.header("Lambda-Runtime-Aws-Request-Id")
      val arn = request.header("Lambda-Runtime-Invoked-Function-Arn")
      val traceId = request.header("Lambda-Runtime-Trace-Id")
      println(s"requestId: $requestId")
      println(s"request body: ${request.body}")

      val headers = Map("Access-Control-Allow-Origin" -> "*", "Content-Type" -> "application/x-protobuf")
      val response = for {
        body <- decode[AWSRequest](request.body)
        requestByteArray = base64Decoder.decode(body.body)
        simpleRequest = Request.parseFrom(requestByteArray)
        simpleByteArray = Response(simpleRequest.low, simpleRequest.high).toByteArray
        simpleEncoded = base64Encoder.encodeToString(simpleByteArray)
      } yield AWSResponse(simpleEncoded, headers).asJson.toString

      println(s"debug: ${response}")

      (requestId, response) match {
        case (Some(id), Right(r)) => Http(s"http://$runtimeApi/2018-06-01/runtime/invocation/$id/response")
          .postData(r)
          .asString
        case (Some(id), Left(_)) => Http(s"http://$runtimeApi/2018-06-01/runtime/invocation/$id/error")
          .postData(Map("errorMessage" -> "fail", "errorType" -> "BadFail").asJson.toString)
          .asString
        case (None, _) => Http(s"http://$runtimeApi/2018-06-01/runtime/invocation/init/error")
          .postData(Map("errorMessage" -> "no request id", "errorType" -> "MissingRequestIdHeader").asJson.toString)
          .asString
      }
      handleRequest()
    }

    handleRequest()
  }
}