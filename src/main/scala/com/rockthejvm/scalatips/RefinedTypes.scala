package com.rockthejvm.scalatips

/**
  * Refined Types
  * https://blog.rockthejvm.com/refined-types/
  */
object RefinedTypes:

//case class User(name: String, email: String)
//
//val daniel = User("Daniel", "daniel@rockthejvm.com")
//
//val noDaniel = User("daniel@rockthejvm.com", "Daniel")
//
//libraryDependencies += "eu.timepit" %% "refined" % "0.9.15"
//
//import eu.timepit.refined.api.Refined
//import eu.timepit.refined.auto._
//import eu.timepit.refined.numeric._
//
//val aPositiveInteger: Refined[Int, Positive] = 42
//
//val aNegativeInteger: Refined[Int, Positive] = -100
//
//val aNegative: Int Refined Negative = -100
//val nonNegative: Int Refined NonNegative = 0
//val anOdd: Int Refined Odd = 3
//val anEven: Int Refined Even = 68
//
//import eu.timepit.refined.W
//val smallEnough: Int Refined LessThan[W.`100`.T] = 45
//
//import eu.timepit.refined.string._
//
//val commandPrompt: String refined EndsWith[W.`"$"`.T] = "daniel@mbp $"
//
//val isRegex: String Refined Regex = "rege(x(es)?|xps?)"
//
//type Email = String Refined MatchesRegex[W.`"""[a-z0-9]+@[a-z0-9]+\\.[a-z0-9]{2,}"""`.T]
//
//type SimpleName = String Refined MatchesRegex[W.`"""[A-Z][a-z]+"""`.T]
//case class ProperUser(name: SimpleName, email: Email)
//
//val daniel = ProperUser("Daniel", "daniel@rockthejvm.com")
//// val noDaniel = ProperUser("daniel@rockthejvm.com", "Daniel") // doesn't compile
//
//import eu.timepit.refined.api.RefType
//
//val poorEmail = "daniel"
//val refineCheck = RefType.applyRef[Email](poorEmail)
