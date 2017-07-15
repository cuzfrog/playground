package com.github.cuzfrog

import utest._

object TemplateTest extends TestSuite{
  val tests = this{
    'test1{
      eventually()
    }
  }
}