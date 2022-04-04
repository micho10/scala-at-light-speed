package com.rockthejvm.scalatips

/**
  * Why is contravariance so hard?
  * https://blog.rockthejvm.com/contravariance/
  *
  * <u>Rule of thumb:</u>
  *   - when your generic type “contains” or “produces” elements of type T, it should be covariant [+T].
  *   - when your generic type “acts on” or “consumes” elements of type T, it should be contravariant  [-T].
  *
  * <u>Examples:</u>
  *   - Covariant concepts: a cage, a garage, a factory, a list
  *   - Contravariant concepts: a vet, a mechanic, a garbage pit, a function (in terms of args type)
  */
object WhyIsContravarianceHard:

  // known
  // Generics allow to apply the same logic to collections independently of their element's type
  // abstract class List[+T]
  val list: List[Int] = List(1, 2, 3)

  class Animal
  class Dog(name: String) extends Animal

  // Question: if Dog <: Animal, does List[Dog] <: List[Animal]? - THE VARIANCE QUESTION

  // If the answer is YES, then the type is called COVARIANT
  val lassie = new Dog("Lassie")
  val hachi  = new Dog("Hachi")
  val laika  = new Dog("Laika")

  // Polymorphism
  val anAnimal: Animal = laika
  // Transferred polymorphism to the collection
  val myDogs: List[Animal] = List(lassie, hachi, laika) // A list of dogs is a list of animals

  // If the answer is NO, then the type is called INVARIANT
  class MyInvariantList[T]
  // This isn't possible since the subtype relationship is not transferred to the collection
//  val myDogs2: MyInvariantList[Animal] = new MyInvariantList[Dog]  // Will not compile
  val myDogs2: MyInvariantList[Animal] = new MyInvariantList[Animal]

  // If the answer is HELL NO!! Or, NO, quite the opposite. - CONTRAVARIANCE
  class MyContravariantList[-T]
  val myDogs3: MyContravariantList[Dog] = new MyContravariantList[Animal] // ??

  // A contravariance example
  trait Vet[-T]: // we can also insert an optional -T <: Animal here if we wanted to impose a type constraint
    def heal(animal: T): Boolean

  def gimmeAVet(): Vet[Dog] = (animal: Animal) =>
    // same as:
    // new Vet[Animal]
    //   override def heal(animal: T) = ???
    println("You'll be fine")
    true

  val myDog = new Dog("Buddy")
  val myVet: Vet[Dog] = gimmeAVet()
  myVet.heal(myDog) // Buddy is happy & healthy
