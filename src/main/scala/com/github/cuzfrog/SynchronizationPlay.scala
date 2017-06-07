package com.github.cuzfrog

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
/**
  * Created by cuz on 6/7/17.
  */
private[cuzfrog] object SynchronizationPlay extends App {

  import scala.concurrent.ExecutionContext.Implicits.global

  val sync = new SyncPlay
  val f1 = Future(sync.m1())
  val f2 = Future(sync.m2())

  Await.ready(Future.sequence(List(f1,f2)), 10 seconds)
}


private class SyncPlay {
  object Lock1

  def m1(): Unit = Lock1.synchronized {
    println("m1 begins..")
    Thread.sleep(3000)
    println("m1 ends..")
  }

  def m2(): Unit = Lock1.synchronized {
    println("m2 begins..")
    Thread.sleep(3000)
    println("m2 ends..")
  }
}