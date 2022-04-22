package com.rockthejvm.scalatips

/**
  * Eta-expansion and Partially Applied Functions
  * https://blog.rockthejvm.com/eta-expansion-and-paf/
  */
object PartiallyAppliedFunctions:

  def incrementMethod(x: Int): Int = x + 1

  // invoking the previous method (can only be done from the class or an object of it). It depends on the class
  PartiallyAppliedFunctions.incrementMethod(3) // 4
  incrementMethod(3) // this.incrementMethod(3)

  // A function value (lambda) is a piece of code which can be invoked independently of any class or object
  val incrementFunction = (x: Int) => x + 1
  val three = incrementFunction(2)

  // what the compiler does
  val incrementFunctionExplicit = new Function1[Int, Int]:
    override def apply(x: Int): Int = x + 1
    // ^^ identical
  val four = incrementFunction.apply(3) // de-sugared from incrementFunction(2)


  // eta-expansion
  // its purpose is to turn a method into a function value

  val incrementF = incrementMethod // eta-expansion done automatically.
  // Syntax: "val incrementF = incrementMethod _" no longer needed

  // what the compiler does
  val incrementFExplicit = (x: Int) => incrementMethod(x) // "equivalent"
  // ^^ equivalent
  val incrementF2: Int => Int = incrementMethod
  List(1, 2, 3).map(incrementF) // eta-expansion is done automatically


  // partially-applied functions

  def multiArgAdder(x: Int)(y: Int) = x + y
  val add2 = multiArgAdder(2) _ // y => 4 + y
  // later
  val five = add2(3)

  List(1, 2, 3).map(multiArgAdder(3)) // eta-expansion is done automatically

  // interesting question #1
  def add(x: Int, y: Int) = x + y
  val addF = add // (x, y) => x + y

  // interesting question #2
  def threeArgAdder(x: Int)(y: Int)(z: Int) = x + y + z
  val twoArgsRemaining = threeArgAdder(2) // (curried function) y => z => 2 + y + z
  val ten = twoArgsRemaining(3)(5)
  val oneArgRemaining = threeArgAdder(2)(3) // z => 2 + 3 + z
