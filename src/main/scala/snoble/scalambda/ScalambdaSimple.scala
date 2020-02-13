package snoble.scalambda

import scalaj.http._
import scala.annotation.tailrec


object ScalambdaSimple {

  def main(args: Array[String]): Unit = {
    println("Starting")
    val runtimeApi = System.getenv("AWS_LAMBDA_RUNTIME_API")
    
    @tailrec
    def handleRequest(): Unit = {
      val request: HttpResponse[String] = Http(s"http://$runtimeApi/2018-06-01/runtime/invocation/next").asString
      val requestId: Option[String] = request.header("Lambda-Runtime-Aws-Request-Id")
      println(s"requestId: $requestId")
      println(s"body: ${request.body}")
      requestId.foreach( id =>
        Http(s"http://$runtimeApi/2018-06-01/runtime/invocation/$id/response")
          .postData("{\"body\": \"[1]\"}")
          .asString
          )
      handleRequest()
    }

    handleRequest()
  }
}
