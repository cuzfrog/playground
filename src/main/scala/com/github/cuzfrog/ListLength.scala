package com.github.cuzfrog

import org.github.jamm.MemoryMeter

/**
  * Created by cuz on 6/7/17.
  */
object ListLength extends App {

  val meter = new MemoryMeter()

  val list = (1 to 100).toList

  val array = list.toArray

  val integralArray = array.map(i => new java.lang.Integer(i))
  println(meter.measureDeep(list))
  println(meter.measureDeep(array))
  println(meter.measureDeep(integralArray))
}
