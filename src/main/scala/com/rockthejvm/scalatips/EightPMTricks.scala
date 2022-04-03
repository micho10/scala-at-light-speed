package com.rockthejvm.scalatips

import java.io.IOException


/**
  * 8 PM tricks you probably didn't know:
  * https://blog.rockthejvm.com/8-pm-tricks/
  */
object EightPMTricks {

  // 1 - Switch on Steroids
  val aNumber = 44
  val ordinal = aNumber match
    case 1 => "first"
    case 2 => "second"
    case 3 => "third"
    case _ => aNumber + "th" // ignore the English grammar will you please

  // 2 - case class deconstruction
  case class Person(name: String, age: Int, favoriteMovies: List[String])
  val bob = Person("Bob", 34, List("Inception", "The Departed"))

  val describeBob = bob match
    case Person(n, a, movies) => s"$n is $a years old and likes ${movies.mkString(",")}"
    case _ => "I don't know what you're talking about"

  // Trick #1 - list extractors
  val countingList = List(1, 2, 3, 42)
  val mustHaveThree = countingList match
    case List(_, _, 3, somethingElse) => s"A-HA! I've got a list with 3 as third element, I found $somethingElse after"

  // Trick #2 - Haskell-like prepending
  val startsWithOne = countingList match
    case 1 :: tail => s"This lists starts with one, the rest is $tail"

  def processList(numbers: List[Int]): String = numbers match
    case Nil => "List is empty"
    case head :: tail => s"List starts with $head, the rest is $tail"

  // Trick #3 - vararg pattern
  val dontCareAboutTheRest = countingList match
    case List(_, 2, _*) => "I only care that this list has 2 as second element"

  // Trick #4 - other infix patterns
  val mustEndWithMeaningOfLife = countingList match
    case List(1, 2, _) :+ 42 => "found the meaning of life!"

  val mustEndWithMeaningOfLife2 = countingList match
    case List(1, _*) :+ 42 => "I really don't care what comes before the meaning of life"

  // Trick #5 - Type specifiers
  def gimmeAValue(): Any = 45 // { ... }

  // This pattern is based on reflection and can have a performance hit
  val gimmeTheType = gimmeAValue() match
    case _: String => "I have a string"
    case _: Int => "I have a number"
    case _ => "I have something else"

  try
    " ... "
  catch
    case _: IOException => "IO failed!"
    case _: Exception => "We could have prevented that!"
    case _: RuntimeException => "Something else crashed!"

  // Trick #6 - name binding
  def requestMoreInfo(p: Person): String = s"The person ${p.name} is a good lad"

//  val bob = Person("Bob", 34, List("Inception", "The Departed"))

  val bobsInfo = bob match
    case Person(name, age, movies) => s"$name's info: ${requestMoreInfo(Person(name, age, movies))}"

  val bobsInfo2 = bob match
    case p @ Person(name, _, _) => s"$name's info: ${requestMoreInfo(p)}"

  val bobsInception = bob match
    case Person(name, _, movies @ List("Inception", _*)) => s"$name REALLY likes Inception, some other movies too: $movies"

  // Trick #7 - conditional guards
  val ordinal2 = aNumber match
    case 1 => "first"
    case 2 => "second"
    case 3 => "third"
    case n if n % 10 == 1 => n + "st"
    case n if n % 10 == 2 => n + "nd"
    case n if n % 10 == 3 => n + "rd"
    case n => n + "th"

  // Trick #8 - alternative patterns
  val myOptimalList = countingList match
    case List(1, _, _) => "I like this list"
    case List(43, _*) => "I like this list"
    case _ => "I don't like it"

  val myOptimalList2 = countingList match
    case List(1, _, _) | List (43, _*) => "I like this list"
    case _ => "I don't like it"


  def main(args: Array[String]): Unit = {

  }

}
