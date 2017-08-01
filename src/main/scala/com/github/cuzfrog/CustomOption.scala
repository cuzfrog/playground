package com.github.cuzfrog


trait OptionParam[+T]
case class SomeParam[T](value:T) extends OptionParam[T]
case object NoneParam extends OptionParam[Nothing]
object OptionParam{
  implicit def enclose[T](v: T): OptionParam[T] = if(v == null) NoneParam else SomeParam(v)
}

object CustomOptionTest extends App{
  def consume[T](param:OptionParam[T] = NoneParam):Unit = println(param)

  consume("sth")
}