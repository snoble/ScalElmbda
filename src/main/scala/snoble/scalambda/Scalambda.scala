package snoble.scalambda

class ScalambdaShim {
  @native def start(): Unit
  @native def writeResponse(input: String): Unit
  @native def readRequest(): String
}

object Scalambda {

  def main(args: Array[String]): Unit = {
    println("Starting")
    System.loadLibrary("go-scalambda")
    val sl = new ScalambdaShim()
    sl.start()

    val request = sl.readRequest()
    sl.writeResponse("{\"hello\": \"world\"}")
  }
}
