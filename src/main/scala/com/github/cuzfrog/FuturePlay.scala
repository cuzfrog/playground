package com.github.cuzfrog

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
/**
  * Created by cuz on 17-6-3.
  */
object FuturePlay {
  val f1 = Future{
    new UnsupportedOperationException
  }

  f1.failed
}
