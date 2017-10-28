package object utest {
  /**
   * Extension methods to allow you to create tests via the "omg"-{ ... }
   * syntax.
   */
  @reflect.internal.annotations.compileTimeOnly("String#- method should only be used directly inside a TestSuite{} macro")
  implicit class TestableString(s: String){
    /**
     * Used to demarcate tests with the `TestSuite{ ... }` block. Has no
     * meaning outside that block
     */
    @reflect.internal.annotations.compileTimeOnly("String#- method should only be used directly inside a TestSuite{} macro")
    def -(x: => Any) = ()
  }

  @reflect.internal.annotations.compileTimeOnly("String#- method should only be used directly inside a TestSuite{} macro")
  implicit class TestableSymbol(s: Symbol){
    /**
     * Used to demarcate tests with the `TestSuite{ ... }` block. Has no
     * meaning outside that block
     */
    @deprecated("Use the 'foo - {...} syntax instead")
    @reflect.internal.annotations.compileTimeOnly("Symbol#apply method should only be used directly inside a TestSuite{} macro")
    def apply(x: => Any): Unit = ()
    /**
     * Used to demarcate tests with the `TestSuite{ ... }` block. Has no
     * meaning outside that block
     */
    @reflect.internal.annotations.compileTimeOnly("Symbol#- method should only be used directly inside a TestSuite{} macro")
    def -(x: => Any): Unit = ()
  }

  final def autoClose[T<:{def close():Unit}](t:T):T = t
}
