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
    body: String
  )
  implicit val decodeFoo: Decoder[AWSRequest] = Decoder.instanceTry[AWSRequest]( c =>
      for {
        body <- Try(c.downField("body").as[String].right.get)
      } yield AWSRequest(body)
  )

  def main(args: Array[String]): Unit = {
    println("Starting")
    val runtimeApi = System.getenv("AWS_LAMBDA_RUNTIME_API")
    
    @tailrec
    def handleRequest(): Unit = {
      val request: HttpResponse[String] = Http(s"http://$runtimeApi/2018-06-01/runtime/invocation/next").asString
      val requestId: Option[String] = request.header("Lambda-Runtime-Aws-Request-Id")
      println(s"requestId: $requestId")
      println(s"body: ${request.body}")
      println(s"body: ${decode[AWSRequest](request.body)}")

      val requestByteArray = java.util.Base64.getDecoder.decode(decode[AWSRequest](request.body).right.get.body)
      val simpleRequest = Request.parseFrom(requestByteArray)

      val simpleByteArray = Response(simpleRequest.low, simpleRequest.high).toByteArray
      val simpleEncoded = java.util.Base64.getEncoder.encodeToString(simpleByteArray)

      val headers = Map("Access-Control-Allow-Origin" -> "*", "Content-Type" -> "application/x-protobuf")
      val response = AWSResponse(simpleEncoded, headers).asJson

      println(s"response: $response")

      requestId.foreach( id =>
        Http(s"http://$runtimeApi/2018-06-01/runtime/invocation/$id/response")
          .postData(response.toString)
          .asString
          )
      handleRequest()
    }

    handleRequest()
  }
}
