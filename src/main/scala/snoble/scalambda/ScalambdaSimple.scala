package snoble.scalambda

import scalaj.http._
import scala.annotation.tailrec
import io.circe.syntax._, io.circe.{Encoder, JsonObject}

object ScalambdaSimple {

  case class Response(
    body: String,
    headers: Map[String, String]
  )
  implicit val encodeResponse: Encoder[Response] = Encoder.instance[Response](r =>
    Map("body" -> r.body.asJson,
      "headers" -> r.headers.asJson
    ).asJson
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

      val body = Map("hello"-> "World").asJson.toString
      val response = Response(body, Map("Access-Control-Allow-Origin" -> "*")).asJson
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
