package com.rockthejvm.scalatips

import scala.concurrent.Future
import scala.util.Try

/**
  * 3 Tricks with Call-by-Name
  * https://blog.rockthejvm.com/3-tricks-for-CBN/
  */
object NiceThingsCBN:

  // By value parameter passing
  def byValueFunction(x: Int) = x + 5 // implementation is not important
  byValueFunction(2 + 3) // 2 + 3 = 5, and so you call byValueFunction(5)

  // By NAME parameter passing
  def byNameFunction(x: => Int) = x + 5 // implementation is not important
  byNameFunction(2 + 3) // the expression 2 + 3 is passed LITERALLY

  // Trick #1 - Reevaluation

  def byValuePrint(x: Long): Unit =
    println(x)
    println(x)

  def byNamePrint(x: => Long): Unit =
    println(x)
    println(x)

  byValuePrint(System.nanoTime())
  //180615587360888
  //180615587360888

  byNamePrint(System.nanoTime())
  //180615853269641
  //180615853294140

  // Trick 2 - manageable infinity (call by need)

  abstract class LazyList[+T]:
    def head: T
    def tail: LazyList[T]

  case object Empty extends LazyList[Nothing]:
    override def head = throw new NoSuchElementException
    override def tail = throw new NoSuchElementException

  // Neither the arguments nor the fields are evaluated on construction
  // They're all evaluated only once when called and then the same value is reused again
  // Useful for infinite collections
  class NonEmptyList[+T](h: => T, t: => LazyList[T]) extends LazyList[T]:
    override lazy val head = h
    override lazy val tail = t

  // Trick #3 - hold the door

  // It prevents the computation of the argument so that the expression can be handled in some other way
  val anAttempt: Try[Int] = Try(throw new NullPointerException)

  // Try.apply method takes BY NAME parameters
  val anotherAttempt: Try[Int] = Try { // seems like part of the language
    // something that can blow up
    throw new NullPointerException
  }

  import scala.concurrent.ExecutionContext.Implicits.global
  // Future.apply method takes BY NAME parameters
  val aFuture = Future
    // some nasty computation
    42
