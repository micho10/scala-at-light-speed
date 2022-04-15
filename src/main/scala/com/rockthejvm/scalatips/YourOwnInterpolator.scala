package com.rockthejvm.scalatips

/**
  * How to create your own String interpolator
  *
  * A custom interpolator needs 2 things
  *   - an implicit wrapper over a special class called StringContext
  *   - a method whose name is equal to the interpolator to be created
  */
object YourOwnInterpolator:

  // s-interpolator
  val lifeOfPi = 3.141592
  val sInterpolator = s"The value of PI is $lifeOfPi,\n the half of PI is ${lifeOfPi / 2}"

  // raw-interpolator
  val rawInterpolator = raw"The value of PI is $lifeOfPi\n <-- this is not a new line"

  // f-interpolator ~ printf
  val fInterpolator = f"The approximate value of PI is $lifeOfPi%4.2f"

  // Other interpolators
//  val spark = SparkSession.builder().getOrCreate()
//  import spark.implicits._
//  val aColumn = $"columName" // a Spark Column object

  // a custom interpolator
  case class Person(name: String, age: Int)

  // Problem: parsing pairs "name,age" -> Person

  // "normal" approach
  def fromStringToPerson(line: String): Person =
    val tokens = line.split(",")
    Person(tokens(0), tokens(1).toInt)

  // structure for custom interpolator
  implicit class PersonInterpolator(sc: StringContext):
    def person(args: Any*): Person =
      // logic inside
      val parts = sc.parts  // the things in between the args
      // s concatenates all the parts with all the args in the right order
      val totalString = sc.s(args: _*) // total expanded string
      val tokens = totalString.split(",")
      Person(tokens(0), tokens(1).toInt)

  val bob = person"Bob,34"  // an instance of Person

  val name = "Marley"
  val age = 43
  val marley = person"$name,$age"

  def main(args: Array[String]): Unit =
    println(sInterpolator)
    println(rawInterpolator)
    println(fInterpolator)
    println(bob)
    println(marley)
