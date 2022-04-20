package com.rockthejvm.scalatips

/**
  *The Difference Between:
  *   - Null
  *   - Nothing
  *   - Nil
  *   - None
  *   - Unit
  * https://blog.rockthejvm.com/null-nothing-none-nil-unit/
  */
object NothingnessDifferences:

  // 1 - The null reference
  // Used to represent an absent value (rarely used in Scala)
  /**
    * null has:
    *   - no fields
    *   - no methods
    */
  val anAbsentString: String = null
  anAbsentString.length() // triggers the famous NullPointerException


  // 2 - The Null type
  // the type of the null reference
  val theNullReference: Null = null
  /**
    * The Null type:
    *   - has no methods
    *   - has no fields
    *   - cannot be extended or instantiated
    *   - the only possible values is: null
    *   - extends all REFERENCE types
    */

  class Person
  val noString: String = theNullReference
  val noPerson: Person = theNullReference
  val noList: List[Int] = theNullReference

  // Reference type hierarchy
  // AnyRef -> all reference types -> Null
  // Null receives a special treatment by the compiler


  // 3 - Nil
  // it represents an empty list
  /**
    * Nil:
    *   - has methods
    *   - has fields
    */

  val anEmptyList: List[Int] = Nil
  val emptyListLength: Int = Nil.length

  def processList(list: List[Int]): Int = list.length
  // later:
  processList(Nil)


  // 4 - None
  // another way of expressing an absent value
  // it's a subtype of Option
  /**
    * None:
    *   - has methods
    *   - has fields
    */
  val anAbsentInt: Option[Int] = None
  val aPresentInt: Option[Int] = Some(42)
  println(anAbsentInt.isEmpty)

//  val anAbsentValue: Option[Int] = Option(null) // this returns None


  // 5 - Unit
  // equivalent to void in Java
  // represents absence of a meaningful value
  // expressions returning Unit are side effects (display something on screen, write something to a file,
  // print something to a console, etc)
  def aUnitReturningFunction(): Unit = println("Starting to get the difference!")
  val theUnit: Unit = () // the only value of Unit


  // 6 - Nothing
  // represents no value at all
  val theNothing = throw new RuntimeException("Nothing to see here")
  /**
    * Nothing:
    *   - has no fields
    *   - has no methods
    *   - cannot be extended or instantiated
    *   - has no value at all
    *   - the only expressions that can return Nothing is throwing exceptions
    *   - extends ALL types
    */

  val nothingInt: Int = throw new RuntimeException("No int") // returns Nothing
  val nothingString: String = throw new RuntimeException("No string") // returns Nothing

  // Nothing receives a special treatment by the compiler
