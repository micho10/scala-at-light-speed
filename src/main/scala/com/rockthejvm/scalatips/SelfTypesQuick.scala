package com.rockthejvm.scalatips

/**
  * Self types, real quick
  * https://blog.rockthejvm.com/self-types/
  */
object SelfTypesQuick:

  trait Edible

  // person hierarchy
  trait Person:
    def hasAllergiesFrom(thing: Edible): Boolean
  trait Child extends Person
  trait Adult extends Person

//  // diet hierarchy
//  trait Diet:
//    def eat(thing: Edible): Boolean
//  trait Carnivore extends Diet
//  trait Vegetarian extends Diet

  // PROBLEM: Diet must be applicable to Persons only
  //   * 2 different class hierarchies that need to interact with each other without having a relationship
  // class VegetarianAthlete extends Vegetarian with Adult // Would like it enforced at compile time


// option #1 - Inheritance (enforce a subtype relationship)

//  trait Diet extends Person:
//    def eat(thing: Edible): Boolean =
//      if (this.hasAllergiesFrom(thing)) false
//      else 42 > 10
//  trait Carnivore extends Diet
//  trait Vegetarian extends Diet


// option #2 - Generics (add a type argument)

//  trait Diet[P <: Person](p: Person)
//    def eat(thing: Edible): Boolean
//  trait Carnivore[P <: Person] extends Diet[P]
//  trait Vegetarian[P <: Person] extends Diet[P]


// option #3 - Self types

//  trait Diet { self: Person => // self-type: whoever extends Diet MUST ALSO extend Person
//    def eat(thing: Edible): Boolean =
//      if (self.hasAllergiesFrom(thing)) false
//      else 45 > 32
//  }
//  trait Carnivore extends Diet with Person
//  trait Vegetarian extends Diet with Person
//
//  // Example:
//  class VegAthlete extends Vegetarian with Adult:
//    override def hasAllergiesFrom(thing: Edible): Boolean = false

  // Difference between Inheritance & Self Types
  trait Animal
  class Dog extends Animal      // A Dog IS an Animal
  trait Diet { self: Person =>  // A Diet REQUIRES a Person
  }
