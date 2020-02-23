package snoble.scalelmbda

import scalaj.http._
import scala.annotation.tailrec
import scala.util.Try
import io.circe.syntax._, io.circe.{Encoder, Json, Decoder}, io.circe.parser.decode
import scala.util.{Try, Success, Failure}

import snoble.scalelmbda.proto.simple.{Request, Response, HighLowRequest, ListOfStringsRequest, HighLowResponse, ListOfStringsResponse}

object ScalElmbdaSimple {
  val base64Decoder = java.util.Base64.getDecoder()
  val base64Encoder = java.util.Base64.getEncoder()

  case class AWSResponse(
    body: Response,
    headers: Map[String, String]
  )
  implicit val encodeResponse: Encoder[AWSResponse] = Encoder.instance[AWSResponse](r =>
    Json.obj(
      "body" -> base64Encoder.encodeToString(r.body.toByteArray).asJson,
      "headers" -> r.headers.asJson,
      "isBase64Encoded" -> true.asJson
    )
  )

  case class AWSRequest(
    body: Request,
    httpMethod: String,
    resource: String,
    path: String,
    isBase64Encoded: Boolean
  )
  implicit val decodeRequest: Decoder[AWSRequest] = Decoder.instance[AWSRequest]( c =>
      for {
        body <- c.downField("body").as[String]
        parsedBody = Request.parseFrom(base64Decoder.decode(body))
        httpMethod <- c.downField("httpMethod").as[String]
        resource <- c.downField("resource").as[String]
        path <- c.downField("path").as[String]
        isBase64Encoded <- c.downField("isBase64Encoded").as[Boolean]
      } yield AWSRequest(parsedBody, httpMethod, resource, path, isBase64Encoded)
  )

  def handleRequests(runtimeApi: String, handleRequest: AWSRequest => Try[AWSResponse]) = {
    val apiRequest: HttpResponse[String] = Http(s"http://$runtimeApi/2018-06-01/runtime/invocation/next").asString
      val requestId = apiRequest.header("Lambda-Runtime-Aws-Request-Id")
      val arn = apiRequest.header("Lambda-Runtime-Invoked-Function-Arn")
      val traceId = apiRequest.header("Lambda-Runtime-Trace-Id")
      println(s"requestId: $requestId")
      println(s"request body: ${apiRequest.body}")

      val response = decode[AWSRequest](apiRequest.body).map(handleRequest(_))

      (requestId, response) match {
        case (Some(id), Right(Success(r))) => Http(s"http://$runtimeApi/2018-06-01/runtime/invocation/$id/response")
          .postData(r.asJson.toString)
          .asString
        case (Some(id), Right(Failure(e))) => Http(s"http://$runtimeApi/2018-06-01/runtime/invocation/$id/error")
          .postData(Map("errorMessage" -> "fail", "errorType" -> "BadFail").asJson.toString)
          .asString
        case (Some(id), Left(parseError)) => Http(s"http://$runtimeApi/2018-06-01/runtime/invocation/$id/error")
          .postData(Map("errorMessage" -> "fail", "errorType" -> "BadFail").asJson.toString)
          .asString
        case (None, _) => Http(s"http://$runtimeApi/2018-06-01/runtime/invocation/init/error")
          .postData(Map("errorMessage" -> "no request id", "errorType" -> "MissingRequestIdHeader").asJson.toString)
          .asString
      }
  }

  def egRequestHandler(request: AWSRequest): Try[AWSResponse] = {
    Try {
      val headers = Map("Access-Control-Allow-Origin" -> "*", "Content-Type" -> "application/x-protobuf")
      val body = request.body.requestType match {
        case Request.RequestType.Hl(HighLowRequest(high, low)) => Success(Response(Response.ResponseType.Hl(HighLowResponse(low, high))))
        case Request.RequestType.Strings(ListOfStringsRequest(strings)) => Success(Response(Response.ResponseType.Strings(ListOfStringsResponse(strings.mkString(" "), strings.length))))
        case Request.RequestType.Empty => Failure(new Exception("Empty Request"))
      }
      body.map(AWSResponse(_, headers))
    }.flatten
  }

  def start(requestHandler: AWSRequest => Try[AWSResponse]) = {
    println("Starting")
    val runtimeApi = System.getenv("AWS_LAMBDA_RUNTIME_API")
    while(true) {
      handleRequests(runtimeApi, requestHandler)
    }
  }

  def main(args: Array[String]): Unit = {
    start(egRequestHandler(_))
  }
}
