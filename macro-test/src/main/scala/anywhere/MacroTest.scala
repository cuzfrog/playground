package anywhere

import utest._

object MacroTest extends App{


  val tests = Tests{
    val resource = new Resource
    'test1 {
      println(s"use resource $resource")
    }
  }


}

class Resource extends AutoCloseable{
  override def close(): Unit = println("close resource.")
}