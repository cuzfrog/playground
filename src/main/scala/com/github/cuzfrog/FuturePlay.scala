package com.github.cuzfrog

import scala.concurrent.Future

/**
  * Created by cuz on 17-6-3.
  */
object FuturePlay {
  val f1 = Future{
    new UnsupportedOperationException
  }

  f1.failed
}
