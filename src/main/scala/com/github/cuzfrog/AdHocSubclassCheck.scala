package com.github.cuzfrog

object AdHocSubclassCheck extends App {
  trait SubClassGauge[A, B] {
    def A_isSubclassOf_B: Boolean
  }

  implicit class IsSubclassOps[A](a: A) {
    def isSubclassOf[B](implicit ev: SubClassGauge[A, B]): Boolean = ev.A_isSubclassOf_B
  }

  trait LowerLevelImplicits {
    implicit def defaultSubClassGauge[A, B] = new SubClassGauge[A, B] {
      override def A_isSubclassOf_B: Boolean = false
    }
  }

  object Implicits extends LowerLevelImplicits {
    implicit def subClassGauge[A <: B, B]: SubClassGauge[A, B] = new SubClassGauge[A, B] {
      override def A_isSubclassOf_B: Boolean = true
    }
  }

  trait Prime
  class NotSuper
  class Super extends Prime
  class Sub extends Super
  class NotSub

import Implicits._
(new Sub).isSubclassOf[NotSuper]
(new Sub).isSubclassOf[Super]
(new Sub).isSubclassOf[Prime]
(new Super).isSubclassOf[Prime]
(new Super).isSubclassOf[Sub]
(new NotSub).isSubclassOf[Super]

}
