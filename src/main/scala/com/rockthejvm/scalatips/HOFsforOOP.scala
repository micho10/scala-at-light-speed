package com.rockthejvm.scalatips

/**
  * Higher-order Functions for OO Programmers
  * https://blog.rockthejvm.com/higher-order-functions-for-oop/
  */
object HOFsforOOP:

// You’re probably aware that the apply method is treated in a special way

  class Applicable:
    def apply(x: Int) = x + 1

  val applicable = new Applicable

  applicable.apply(2) // 3
  applicable(2) // still 3

// The apply method allows instances of classes to be “invoked” like functions. As such, objects with apply methods can
// behave like functions: they take arguments and return results. The Scala standard library actually has built-in
// types for function objects, which are nothing else but plain instances with apply methods:

  val incrementer = new Function1[Int, Int]:
    override def apply(x: Int) = x + 1

  incrementer.apply(4) // 5
  incrementer(4) // 5

  // syntax sugar
  val incrementerAlt = (x: Int) => x + 1 // new Function1[Int, Int] { apply = ... }
  incrementerAlt(4) // 5 of course

// The shorthand version is unwrapped by the compiler into the exact same Function1[Int, Int] construction which we saw
// earlier. The type of this function is Int => Int, which is also another sweet name for Function1[Int, Int].

//The HOF baffle

//Naturally, because these “functions” are nothing but objects with apply methods, they can be passed around as
// arguments or returned as results. The functions which take other functions as arguments and/or return other
// functions as results are called HOFs, or higher-order functions. This is usually easy to make sense of.

// Example: define a function which takes a function f and a number n, and returns another function whose
// implementation is f applied n times. In other words, write an implementation for

  //def nTimes(f: Int => Int, n: Int): Int => Int = ???

//    If we call g = nTimes(f, 30), then
//    g(x) = f(f(f(...f(x)))) 30 times

//  Here’s a possible implementation of this exercise:

  def nTimes(f: Int => Int, n: Int): Int => Int =
    if (n <= 0) (x: Int) => x // the "identity function"
    else (x: Int) => nTimes(f, n-1)(f(x))

//  _FAQ 1: How do you read this? _

// If n is zero or less, we return a function that given an argument returns the argument (the identity function).
// Otherwise, we return a function, that given an argument, applies the n-1-times function to f(x)..
// Look at this breakdown:

// nTimes(f, 4) = x => nTimes(f, 3)(f(x))
// nTimes(f, 3) = x => nTimes(f, 2)(f(x))
// nTimes(f, 2) = x => nTimes(f, 1)(f(x))
// nTimes(f, 1) = x => nTimes(f, 0)(f(x))
// nTimes(f, 0) = x => x

// nTimes(f, 1) = x => nTimes(f, 0)(f(x)) = f(x)
// nTimes(f, 2) = x => nTimes(f, 1)(f(x)) = f(f(x))
// nTimes(f, 3) = x => nTimes(f, 2)(f(x)) = f(f(f(x)))
// nTimes(f, 4) = x => nTimes(f, 3)(f(x)) = f(f(f(f(x))))

// FAQ 2: When are these functions created?

// If we read the code, we can see that all these intermediate functions are not created until we actually call the
// result function. For example, if we said

  val f = (x: Int) => x + 1
  val f4 = nTimes(f, 4) // x => f(f(f(f(x))))
  val f4Alt = (x: Int) => nTimes(f, 3)(f(x)) // new Function1[Int, Int] { apply = nTimes(f, 3)(f(x) }

// It's not creating the rest of the functions up to n = 0. If we invoke f4, then all the intermediate functions will be created.

// FAQ 3: I understand the mathematical definition. But to an OO programmer like me, how are these functions created in memory?

  def nTimesOriginal(f: Function1[Int, Int], n: Int): Function1[Int, Int] =
    if (n <= 0)
      new Function1[Int, Int] { override def apply(x: Int) = x } // JVM object
    else
      new Function1[Int, Int] { override def apply(x: Int) = nTimes(f, n-1).apply(f(x)) }

// This code will shed some light onto how the functions are getting created, because we’re now talking about plain
// JVM objects. If you track this down in the same style we saw above, you will see how the function objects are being
// spawned in memory. Being recursive, these objects are short lived, so even though they might be using more memory
// than necessary, they will be quickly freed by the JVM’s garbage collection.
