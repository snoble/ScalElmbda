package snoble.scalambda

object Scalambda {

  def main(args: Array[String]): Unit = {
    println("Starting")
    val sl = new ScalambdaShim()
    sl.start()

    val request = sl.readRequest()
    sl.writeResponse("{\"hello\": \"world\"}")
  }
}
