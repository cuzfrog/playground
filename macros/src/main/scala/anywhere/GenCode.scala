package anywhere

import scala.language.experimental.macros
import scala.reflect.macros.whitebox

object GenCode {
  def printValResult(expr: Unit): Unit = macro GenCodeImpl.printValResultImpl
}

private object GenCodeImpl {
  def printValResultImpl(c: whitebox.Context)(expr: c.Tree): c.Tree = {
    import c.universe._
    val splicer = new Splicer[c.type](c)

    val block = expr match {
      case b: Block => b
      case _t => Block(Nil, _t)
    }

    val clauses = block.children.collect {
      case valDef@q"$mods val $name = $body" =>
        c.untypecheck(q"$valDef;println($name)").children
    }.flatten

    val valDefs = clauses.collect{
      case valDef:ValDef => valDef
    }

    val prints = clauses.collect{
      case p@q"println($v)" => p
    }

    val tree = q"..${valDefs ++ prints}"
    println(tree)
    tree
  }
}

class Splicer[C <: reflect.macros.whitebox.Context with Singleton](val c: C) {
  /** Safely splice the tree `t` into the enclosing lexical context in which it will
   * be type checked. Rather than directly include `t` in the result, a layer of
   * indirection is used: a call to the macro `changeOwner`.
   *
   * This macro is provied with the symbol of the current enclosing context
   * (`c.enclosingOwner`), and the tree `t`.
   *
   * When it is typechecked, it will have access to another macro context with
   * a new enclosing owner. This is substituted from the old owner.
   *
   * This avoids the tedium of manually creating symbols for synthetic enclosing
   * owners when splicing macro arguments. And it avoids the bugs that still plaugue
   * `untypecheck` (e.g. https://issues.scala-lang.org/browse/SI-8500)
   *
   * This approach only works in cases when you are splicing the arguments into leaf
   * position in the synthetic tree. If you need to splice typed trees *above* untyped
   * trees, it will fail because the typecheck stops descending when it finds a typed
   * tree.
   */
  def apply(t: c.Tree): c.Tree = {
    import c.universe._
    // smuggle the symbol of the current enclosing owner through to the
    // `changeOwner` as the symbol of the tree of its first argument.
    // Tree attachments would be a more principled approach, but they aren't
    // part of the public API.
    val ownerIdent = c.internal.setType(Ident(c.internal.enclosingOwner), typeOf[Any])
    q"_root_.anywhere.Splicer.changeOwner($ownerIdent, $t)"
  }
}

object Splicer {

  def changeOwnerMacro[A](c: whitebox.Context)(ownerIdent: c.Tree, a: c.Tree): c.Tree = {
    import c.universe._
    val origOwner = ownerIdent.symbol
    val result = c.internal.changeOwner(a, c.internal.enclosingOwner, origOwner)
    println(result)
    result
  }

  def changeOwner[A](ownerIdent: Any, a: A): A = macro changeOwnerMacro[A]
}