package com.rockthejvm.scalatips

/**
  * Why are Type Classes useful?
  * https://blog.rockthejvm.com/why-are-typeclasses-useful/
  *
  * Type classes are “type system constructs that support ad hoc polymorphism”
  */
object WhyAreTypeClassesUseful:

  // The problem
  // Ever since generics were invented, you’ve surely come across the need for specialized implementations

  def processMyList[T](list: List[T]): T = ???
    // aggregate a list
    // for Ints        => sum = actual sum of the elements
    // for Strings     => sum = CONCATENATION of all the elements
    // for other types => ERROR

  /**
    * Type Class Pattern:
    *   - a trait with a generic type
    *   - one or more implicit instances implementations of the trait
    *
    * This structure allows to define specific implementations for certain types and not for others,
    * in the “ad hoc polymorphic” style
    */

  // implicits
  trait Summable[T]:
    def sumElements(list: List[T]): T

  implicit object IntSummable extends Summable[Int]:
    def sumElements(list: List[Int]): Int = list.sum

  implicit object StringSummable extends Summable[String]:
    def sumElements(list: List[String]): String = list.mkString("")

  // enhanced original method
  // when invoked, an implicit Summable of the right type must be available
  def processMyList[T](list: List[T])(implicit summable: Summable[T]): T = // ad-hoc polymorphism
    summable.sumElements(list) // <-- here

  //TODO: review to adapt to Scala 3 implicits
//  def main(args: Array[String]): Unit =
//    val intSum = processMyList(List(1, 2, 3)) // 6
//    val stringSum = processMyList(List("Scala ", "is ", "awesome")) // "Scala is awesome"

//    processMyList(List(true, true, false)) // ERROR at COMPILE TIME
