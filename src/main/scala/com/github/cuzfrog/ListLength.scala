package com.github.cuzfrog

import org.github.jamm.MemoryMeter

/**
  * Created by cuz on 6/7/17.
  */
object ListLength extends App {

  val meter = new MemoryMeter()

  val list = (1 to 1000).toList

  val array = list.toArray

  val integralArray = array.map(i => new java.lang.Integer(i))

  val bitSet = new java.util.BitSet(1000)
  println("list:" + meter.measureDeep(list))
  println("array-primitive:" + meter.measureDeep(array))
  println("array-boxed:" + meter.measureDeep(integralArray))
  println("bitSet:" + meter.measureDeep(bitSet))
}
