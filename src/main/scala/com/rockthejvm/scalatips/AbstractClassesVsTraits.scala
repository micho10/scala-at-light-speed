package com.rockthejvm.scalatips

object AbstractClassesVsTraits:

  class Animal
  class Dog extends Animal

  // abstract classes
  abstract class Person:
    // the abstract keyword is not necessary in the field/method definition
    val canFly: Boolean = false
    val canDrive: Boolean
    def discussWith(another: Person): String

//  val bob = new Person // error - not all members implemented

  // traits
  trait PersonTrait:
    val canFly: Boolean = false
    val canDrive: Boolean
    def discussWith(another: Person): String

  /*
    Traits and abstract classes share most of the same capabilities:
      - they can’t be instantiated by themselves
      - they may have abstract (unimplemented) fields or methods
      - (more importantly) they may have non-abstract (implemented) fields and methods as well
  */

  class Adult(val name: String, hasDrivingLicence: Boolean) extends Person: // could also extend PersonTrait and it would make no difference
    override def toString: String = name
    override val canDrive: Boolean = hasDrivingLicence
    override def discussWith(another: Person): String = "Indeed, ${other}, Kant was indeed revolutionary for his time..."

  // if you're extending a SINGLE type, abstract classes or traits make no difference

  /**
    * Difference #1:
    *   - can inherit from a SINGLE abstract class
    *   - can inherit from a MULTIPLE traits
    */

  /**
    * Difference #2:
    *   Represent:
    *     - "things" as classes
    *     - "behaviours" as traits
    */
