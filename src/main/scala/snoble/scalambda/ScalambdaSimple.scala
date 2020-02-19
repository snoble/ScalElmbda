package snoble.scalambda

import scalaj.http._
import scala.annotation.tailrec
import io.circe.syntax._, io.circe.{Encoder, Json}

import snoble.scalambda.proto.simple.Simple

object ScalambdaSimple {

  case class Response(
    body: String,
    headers: Map[String, String]
  )
  implicit val encodeResponse: Encoder[Response] = Encoder.instance[Response](r =>
    Json.obj(
      "body" -> r.body.asJson,
      "headers" -> r.headers.asJson,
      "isBase64Encoded" -> true.asJson
    )
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

      val simpleByteArray = Simple("first", "last").toByteArray
      val simpleEncoded = java.util.Base64.getEncoder.encodeToString(simpleByteArray)

      // val body = Map("hello"-> "World").asJson.toString
      val body = simpleEncoded
      val headers = Map("Access-Control-Allow-Origin" -> "*", "Content-Type" -> "application/x-protobuf")
      val response = Response(body, headers).asJson

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
