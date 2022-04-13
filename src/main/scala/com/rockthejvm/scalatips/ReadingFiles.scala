package com.rockthejvm.scalatips

import java.io.File
import java.util.Scanner

import scala.io.Source

/**
  * 4 Nice Ways to Read Files
  * https://blog.rockthejvm.com/nice-ways-to-read-files-in-Scala/
  */
object ReadingFiles:

  // Sample file
  val filePath = "src/main/resources/L5R decks/CN-SA-SoD-BKx-KinoCroni.txt"
  val file = new File(filePath)

  // Version #1: the Java way
//  val reader = new Scanner(file)
//  while (reader.hasNextLine)
//    val line = reader.nextLine()
//    // do something with it
//    println(line)

  // Version #2: the Java way with cheats (Java-style, friendlier, with cheatsPermalink)
//  import org.apache.commons.io.FileUtils
//  val fileContents = FileUtils.readLines(file, "UTF-8") // a Java list of lines (Strings) that you can now process freely
//  fileContents.forEach(println)

  // Version #3: the Scala way
//  val scalaFileContents: Iterator[String] = Source.fromFile(file).getLines()
//  scalaFileContents.foreach(println)

  // Version #4: like a boss (the good API)

  // Python way: open("path").read
  def open(path: String) = new File(path)

  // implicit type enrichment
  implicit class RichFile(file: File) {
    def read() = Source.fromFile(file).getLines()
  }

  val readLikeABoss = open(filePath).read() // new RichFile(open(filepath)).read()
  readLikeABoss.foreach(println)
