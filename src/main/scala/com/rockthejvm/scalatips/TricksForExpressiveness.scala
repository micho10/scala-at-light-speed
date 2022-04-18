package com.rockthejvm.scalatips

import java.net.URLEncoder

/**
  * 5 Nice Tricks for Concise and Expressive Code
  * https://blog.rockthejvm.com/scala-syntax-tricks-for-expressiveness/
  */
object TricksForExpressiveness extends App:

  // Trick #1 - the single abstract method pattern
  // Since Scala 2.12, abstract classes or traits with a single unimplemented method can be reduced to lambdas
  trait Action:
    def anImplementedMethod() = 43 // It also works if there are other implemented methods
    def act(x: Int): Int

  val myAction: Action = (x: Int) => x + 1
  /* The compiler will translate it like this
  val action: Action = new Action {
    override def act(x: Int) = x + 1
  }
  */

  // This pattern is often used to spawn JVM threads because they traditionally take a Runnable as argument, which is
  // an interface with a single abstract method
  new Thread(() => println("I run easy!")).start()


  // Trick #2 - right-associative methods
  val prependedElement = 2 :: List(3, 4)

  // methods with non-alphanumeric names which end in a colon (e.g. ::) are right-associative
  1 :: 2 :: 3 :: List()
  // equivalent
  1 :: (2 :: (3 :: List()))
  // the compiler re-writes it like this
  List().::(3).::(2).::(1)

  class MessageQueue[T]:
    // an enqueue method
    def -->:(value: T): MessageQueue[T] = new MessageQueue[T] // the implementation is not important

  val queue = 3 -->: 2 -->: 1 -->: new MessageQueue[Int]


// Trick #3 - baked-in “setters”

  class MutableIntWrapper:
    private var internalValue = 0
    // getter
    def value = internalValue
    // setter
    def value_=(newValue: Int) = { internalValue = value }

  val wrapper = new MutableIntWrapper
  wrapper.value = 43 // same as wrapper.value_=(43)


  // Trick 4 - multi-word members

  class Person(name: String):
    def `then said`(thing: String) = s"$name then said: $thing"

  val jim = new Person("Jim")
  jim `then said` "Scala is pretty awesome!"

  // real life example: Akka HTTP
//  val request = HttpRequest(
//    method = HttpMethods.POST,
//    uri = "http://markup.su/api/highlighter",
//    entity = HttpEntity(
//      ContentTypes.`application/x-www-form-urlencoded`, // <--- look here
//      s"source=${URLEncoder.encode(source, "UTF-8")}&language=Scala&theme=Sunburst"
//    )
//  )


  // Trick 5 - backtick pattern matching
  val meaningOfLife = 42
  val data: Any = 45

  // pattern matching against meaningOfLife
//  val pm = data match
//    case meaningOfLife => ... // WRONG. This meaningOfLife is just a value name being matched against data

//  val pm = data match
//    case m if m == meaningOfLife => ... // Correct way

//  val pm = data match
//    case `meaningOfLife` => ... // elegant way
