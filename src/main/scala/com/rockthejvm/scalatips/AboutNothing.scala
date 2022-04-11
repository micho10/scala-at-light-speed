package com.rockthejvm.scalatips

/**
  * About Nothing
  * https://blog.rockthejvm.com/nothing/
  */
object AboutNothing:

  class MyPrecious // extends AnyRef

  // nothing
  def gimmeNumber(): Int = throw new NoSuchElementException
  def gimmeString(): String = throw new NoSuchElementException
  def gimmePrecious(): MyPrecious = throw new NoSuchElementException

  // throw expressions return NOTHING
  // Nothing != Unit != Null != anything at all
  // Nothing is the type of "nothingness"

  // can you use Nothing?
  def aFunctionAboutNothing(a: Nothing): Int = 56
  // It doesn't compile because the argument needs to be evaluated before calling the function
//  aFunctionAboutNothing(throw new NullPointerException)

  // functions returning Nothing
  def aFunctionReturningNothing(): Nothing = throw new RuntimeException

  // Nothing is useful in GENERICS, especially in COVARIANT GENERICS
  abstract class MyList[+T] // if Dog extends Animal, then List[Dog] <: List[Animal]
  class NonEmptyList[+T](head: T, tail: MyList[T])
  object EmptyList extends MyList[Nothing]

  // Because Nothing can be a replacement for any type
  val listOfStrings: MyList[String] = EmptyList
  val listOfIntegers: MyList[Int] = EmptyList
  val preciousList: MyList[MyPrecious] = EmptyList

  // ???
  def someUnimplementedMethod(): String = ??? // def ???: Nothing = throw new NotImplementedError
