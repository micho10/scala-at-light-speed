package com.rockthejvm.scalatips

/**
  * Lenses, Prisms and Optics
  *
  * https://blog.rockthejvm.com/lens/
  *
  * We’re going to explore deeply nested data structures using the Monocle library and the concepts of “optics” in Scala.
  */
object Lenses {

  /*
    Monocle is a popular library for traversing, inspecting and editing deeply nested data structures.

    Monocle was invented because nested data structures are a pain to inspect and change. The pain increases with the
    depth of the data structures.

    Consider the following scenario: we’re designing an online web compendium of rock bands (Rock the JVM, right?).
    We’re thinking about the following data structure design:
   */
  case class Guitar(make: String, model: String)
  case class Guitarist(name: String, favoriteGuitar: Guitar)
  case class RockBand(name: String, yearFormed: Int, leadGuitarist: Guitarist)

  // Let’s assume now that we’ve created some bands for our database:
  val metallica = RockBand("Metallica", 1981, Guitarist("Kirk Hammett", Guitar("ESP", "M II")))

  /*
    Let’s also assume that we have a giant database of guitars, and we want to store them in a consistent format.
    To comply with that format, we’ll need to replace all spaces in a guitar’s model with a dash (don’t ask why).
    Normally, we’d have to go through the entire data structure and copy everything up to the guitar’s model:
  */
  val metallicaFixed = metallica.copy(
    leadGuitarist = metallica.leadGuitarist.copy(
      favoriteGuitar = metallica.leadGuitarist.favoriteGuitar.copy(
        model = metallica.leadGuitarist.favoriteGuitar.model.replace(" ", "-")
      )
    )
  )

  /*
    This is a pain. Imagine we’d have 10 places in our small app where we would have to do this. The code would be a mess.

    This is where Monocle comes in. Monocle gives us the capability to access a deeply nested field in a data structure,
    inspect it and/or change it, therefore creating a new data structure as a result.
  */

  val kirksFavGuitar = Guitar("ESP", "M II")
  import monocle.Lens
  import monocle.macros.GenLens

  // 1. Lenses

  val guitarModelLens: Lens[Guitar, String] = GenLens[Guitar](_.model)
  // inspecting
  val kirksGuitarModel = guitarModelLens.get(kirksFavGuitar) // "M II"
  // modifying
  val formattedGuitar = guitarModelLens.modify(_.replace(" ", "-"))(kirksFavGuitar) // Guitar("ESP", "M-II")

  // Composing lenses
  val leadGuitaristLens = GenLens[RockBand](_.leadGuitarist) // Lens[RockBand, Guitarist]
  val favGuitarLens = GenLens[Guitarist](_.favoriteGuitar)
  val composedLens: Lens[RockBand, String] = leadGuitaristLens.composeLens(favGuitarLens).composeLens(guitarModelLens)

  val kirksGuitarModel2 = composedLens.get(metallica)
  val metallicaFixed2 = composedLens.modify(_.replace(" ", "-"))(metallica)

  // Lens are reusable


  // 2. Prisms

  /*
    Here’s a scenario: we’re working on a visual design app and we have various built-in shapes in place. We’d like
    to be able to manipulate their features while still working against the main “interface”.
   */
  sealed trait Shape
  case class Circle(radius: Double) extends Shape
  case class Rectangle(w: Double, h: Double) extends Shape
  case class Triangle(a: Double, b: Double, c: Double) extends Shape

  val aCircle = Circle(20)
  val aRectangle = Rectangle(10, 20)
  val aTriangle = Triangle(3, 4, 5)

  val aShape: Shape = aCircle

  /*
    In this scenario, we’d like to be able to increase the radius of this shape if it’s a Circle, and leave it
    intact otherwise - all without having to resort to isInstanceOf. Of course, we can do pattern matching.

    But again, if we wanted to apply this transformation to many Shapes throughout various parts of our code,
    we’d have no choice but to repeat this pattern.
   */
  val newCircle: Shape = aShape match {
    case Circle(r) => Circle(r + 10)
    case x => x
  }

  import monocle.Prism
  /*
    A Prism takes two argument lists, each of which takes a function.

      - One is of type Shape => Option[Double], so a “getter” (we return an Option because the Shape might be
        something other than a Circle).

      - The other function is a “creator”, of type Double => Shape.

      In other words, a Prism is a wrapper over a back-and-forth transformation between a Double and a Shape. A prism
      allows us to investigate a Shape and get a double, or use a double and create a Shape.
   */
  val circlePrism = Prism[Shape, Double] {
    case Circle(r) => Some(r)
    case _ => None
  }(r => Circle(r))

  val circle = circlePrism(30) // returns a Shape (actually a Circle)
  val noRadius = circlePrism.getOption(aRectangle) // will return None because that shape is not a Circle
  val radius = circlePrism.getOption(aCircle) // returns Some(20)

  /*
    This seems complicated at first, but it clears a lot of boilerplate, for several reasons:

      - the prism’s apply method acts as a “smart constructor” which can create instances of Circle for us

      - we can safely inspect any shape’s radius even if it’s not a Circle - this saves us the need to repeat the
        earlier pattern matching.

    Both of the above can be used at any point inside our application, without the need to type-check or pattern match
    every time.
  */


  // 3. Composing optics

  // Combinations
  case class Icon(background: String, shape: Shape)
  case class Logo(color: String)
  case class BrandIdentity(logo: Logo, icon: Icon)

  val iconLens = GenLens[BrandIdentity](_.icon)
  val shapeLens = GenLens[Icon](_.shape)
  // compose all
  val brandCircleR = iconLens.composeLens(shapeLens).composePrism(circlePrism)

  val aBrand = BrandIdentity(Logo("red"), Icon("white", Circle(45)))
  val enlargeRadius = brandCircleR.modify(_ + 10)(aBrand)
  // ^^ a new brand whose icon circle's radius is now 55

  val aTriangleBrand = BrandIdentity(Logo("yellow"), Icon("black", Triangle(3, 4, 5)))
  brandCircleR.modify(_ + 10)(aTriangleBrand)
  // ^^ doesn't do anything because the shape isn't a triangle, but the code is 100% safe
}
