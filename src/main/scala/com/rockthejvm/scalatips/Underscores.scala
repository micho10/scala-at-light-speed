package com.rockthejvm.scalatips

/**
  * Underscores Everywhere
  * https://blog.rockthejvm.com/underscore-in-scala/
  */
object Underscores:

  // 1 - ignoring stuff

  // Defining variables
  val _ = 5 // not so useful
  // Anonymous functions
  val onlyFives = (1 to 10).map(_ => 5)
  // Self-types
  trait Singer
  trait Actor { self: Singer =>
    // implementation here
  }

  // Generic types
  def processList(list: List[Option[_]]): Int =  list.length


  // 2 - "everything" = wildcard

  // Pattern matching
  val meaningOfLife = 42
  meaningOfLife match
    case _ => "I'm fine with anything"


  // 3 - Default initializers

  var myVariable: String = _ // 0 for numeric types, false for boolean, null for reference types1


  // 4 - Lambda sugar

  List(1, 2, 3, 4).map(x => x * 5)
  List(1, 2, 3, 4).map(_ * 5) // identical

  val sumFunction: (Int, Int) => Int = _ + _ // (a, b) => a + b


  // 5 - HKT (Higher Kinded Types - generic types whose arguments are themselves generics)

  class MyHigherKindedJewel[M[_]]
  val myJewel = new MyHigherKindedJewel[List]


  // 6 - vararg methods

  def makeSentence(words: String*) = words.toSeq.mkString(" ")
  val words = List("I", "love", "Scala")
  val love = makeSentence(words: _*)
