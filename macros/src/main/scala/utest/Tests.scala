package utest

import utest.framework.{TestCallTree, Tree}

import scala.collection.mutable
import scala.collection.immutable
import scala.language.experimental.macros
import scala.reflect.macros.whitebox

case class Tests(nameTree: Tree[String], callTree: TestCallTree)
object Tests{
  def apply(expr: Unit): Tests = macro Builder.applyImpl


  object Builder {
    /**
      * Raise an exception if a test is nested badly within a `TestSuite{ ... }`
      * block.
      */
    def dieInAFire(testName: String): Nothing = {
      throw new IllegalArgumentException(s"Test nested badly: $testName")
    }

    def applyImpl(c: whitebox.Context)(expr: c.Expr[Unit]): c.Expr[Tests] = {
      import c.universe._

      def literalValue(t: c.Tree) = {
        t match{
          case q"scala.Symbol.apply(${Literal(Constant(foo))})" => foo.toString
          case Literal(Constant(foo)) => foo.toString
        }
      }

      def checkLhs(prefix: c.Tree) = prefix match{
        case q"utest.this.`package`.TestableString" => true
        case q"utest.`package`.TestableString" => true
        case q"utest.this.`package`.TestableSymbol" => true
        case q"utest.`package`.TestableSymbol" => true
        case _ => false
      }
      def matcher(i: Int): PartialFunction[c.Tree, (Option[String], c.Tree)] = {
        // Special case for *
        case q"""utest.this.`package`.*.-($body)""" => (None, body)
        case q"""utest.`package`.*.-($body)""" => (None, body)
        case q"""$p($value).apply($body)""" if checkLhs(p) => (Some(literalValue(value)), body)
        case q"""$p($value).-($body)""" if checkLhs(p) => (Some(literalValue(value)), body)
      }

      def extractAutoCloseable(normalExprs: immutable.List[c.Tree]): immutable.List[c.Tree] = {
        normalExprs.collect{
          case q"$mods val $name = utest.`package`.autoClose[$tpe]($body)" =>
            q"""try{$name.close()}catch{
               case e: Exception=> //scala.util.control.NonFatal should be used
               case e => throw e
            }"""
        }
      }

      def recurse(t: c.Tree, path: Seq[String],
                  clearHook: immutable.List[c.Tree] = List.empty): (c.Tree, Seq[c.Tree]) = {
        val b = t match{
          case b: Block => b
          case _t => Block(Nil, _t)
        }

        val (nested, normal0) = b.children.partition(matcher(0).isDefinedAt)

        val transformer = new Transformer{
          override def transform(t: c.Tree): c.Tree = {
            t match{
              case q"framework.this.TestPath.synthetic" =>
                c.typecheck(q"_root_.utest.framework.TestPath(_root_.scala.Array(..$path))")
              case _ => super.transform(t)
            }
          }
        }

        val normal = normal0.map(transformer.transform(_))

        val (normal2, last) =
          if (normal.isEmpty
            || normal.last.isInstanceOf[MemberDefApi]
            || nested.contains(b.children.last)) {
            (normal, q"()")
          }else{
            (normal.init, normal.last)
          }
        val normal2AutoCloseable = extractAutoCloseable(normal2)
        println("Normal2----")
        normal2.foreach(println)
        println("Last-----")
        println(last)
        println("AutoCloseable------")
        normal2AutoCloseable.foreach(t => println(t))

        val (names, bodies) = {
          var index = 0
          val names = mutable.Buffer.empty[String]
          val bodies = mutable.Buffer.empty[c.Tree]
          for(inner <- nested){
            val (nameOpt, tree2) = matcher(index)(inner)
            nameOpt match{
              case Some(name) => names.append(name)
              case None =>
                names.append(index.toString)
                index += 1
            }
            bodies.append(tree2)

          }
          (names, bodies)
        }

        val (childCallTrees, childNameTrees) =
          names.zip(bodies)
            .map{case (name, body) => recurse(body, path :+ name, normal2AutoCloseable)}
            .unzip
        println(s"childCallTree length: ${childCallTrees.length}")


        val nameTree = names.zip(childNameTrees).map{
          case (name, suite) => q"_root_.utest.framework.Tree($name, ..$suite)"
        }

        println("last raw:")
        println(showRaw(c.typecheck(last)))
        println(showRaw(c.Expr(q"println(resource)")))


        val callTree = c.untypecheck(q"""
        new _root_.utest.framework.TestCallTree({
          ..$normal2
          ${
          if (childCallTrees.isEmpty) q"_root_.scala.Left(( ()=>$last,()=>{..${clearHook}} ))"
          else q"$last; _root_.scala.Right(Array(..$childCallTrees))"
        }
        })""")
        println(showCode(callTree))
        (callTree, nameTree)
      }

      val (callTree, nameTree) = recurse(expr.tree, Vector())

      val res = q"""
      _root_.utest.Tests(
        _root_.utest.framework.Tree(
          "",
          ..$nameTree
        ),
        $callTree
      )"""

      c.Expr[Tests](res)
    }
  }

}
