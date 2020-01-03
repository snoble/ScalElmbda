package snoble.scalambda

object Scalambda {
  @native def start(): Unit
  @native def writeResponse(input: String): Unit
  @native def readRequest(): String

  def main(args: Array[String]): Unit = {
    start()

    val request = readRequest()
    writeResponse("{\"hello\": \"world\"}")
  }
}
