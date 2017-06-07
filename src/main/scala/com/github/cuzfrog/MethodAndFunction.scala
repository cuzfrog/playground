package com.github.cuzfrog

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by cuz on 6/6/17.
  */
private[cuzfrog] object MethodAndFunction extends App {

  def m(in: Long): Long = in + 1

  val f = m _

  Future {
    val time1 = System.nanoTime()
    (1l to 10000000L).foreach(m)
    val time2 = System.nanoTime()
    println(time2 - time1 + "ns")
  }

  Future {
    val time1 = System.nanoTime()
    (1l to 10000000L).foreach(f)
    val time2 = System.nanoTime()
    println(time2 - time1 + "ns")
  }

  Numeric
}
