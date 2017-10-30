package anywhere

import utest._
import utest.framework.TestCallTree

import scala.concurrent.ExecutionContext.Implicits.global

object MacroTest extends App {

  GenCode.printValResult{
    val a = 3
    val b = "4"
  }


//  val tests = Tests {
//    val resource = autoClose(Resource.create("r1"))
//    var x = 0
//    'test1 {
//      println(resource)
//      'test11{
//        x += 11
//        println(x)
//      }
//      'test12{
//        x += 12
//        println(x)
//      }
//    }
//    'test2 {
//      throw new AssertionError("")
//    }
//  }

//  tests.callTree.run(List(0,0))
//  tests.callTree.run(List(0,1))
//  tests.callTree.run(List(1))

  Thread.sleep(500)

}

class Resource(name: String) extends AutoCloseable {
  override def close(): Unit = println(s"close resource $name.")
}

object Resource {
  def create(name: String): Resource = {
    println(s"New Resource $name created.")
    new Resource(name)
  }
}