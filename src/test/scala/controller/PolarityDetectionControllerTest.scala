package controller

import domain.Entry
import controller.PolarityDetectionController._
import java.io.File

import org.scalatest.FunSuite

import scala.io.Source

class PolarityDetectionControllerTest extends FunSuite {

  //val absolutePath = "C:\\Users\\rmvieira\\Documents\\TAP\\1140953_1140956_1141233_a_2019_tap_ncf"
  val absolutePath = "C:\\Users\\pefer\\Desktop\\TAP\\1140953_1140956_1141233_a_2019_tap_ncf"
  val sentiword = absolutePath + "\\resources\\SentiWordNet_3.0.0_20130122.txt"
  val filePath = absolutePath + "\\resources\\SentiWordNet_3.0.0_20130122_test.txt"
  val emptyFilePath = absolutePath + "\\resources\\SentiWordNet_3.0.0_20130122_test2.txt"

  private val testEntries = Stream(
    // Positive
    Entry("able", 0.5, 0.4),
    Entry("uncut", 0.5, 0),
    Entry("absolute", 0.75, 0),
    Entry("direct", 1, 0.5),
    Entry("infinite", 0.85, 0),
    Entry("good", 1, 0),

    // negative
    Entry("bad", 0, 1),
    Entry("unable", 0, 1),
    Entry("relative", 0, 1),

    // neutral
    Entry("nice", 0, 0),
    Entry("relational", 0.5, 0.5),
    Entry("absorptive", 0.3, 0.3),
    Entry("unquestioning", 1, 1),
    Entry("neutral", 1, 0),
    Entry("neutral", 0, 1)
  )

  test("Test Load Provided Entries") {
    val entries = loadEntries(filePath)

    assert(!entries.isEmpty)
    assert(detectPolarity("good", entries) == "Positive")
    assert(detectPolarity("bad", entries) == "Negative")
    assert(detectPolarity("This is good", entries) == "Positive")
  }

  test("Test Load Empty Entries File") {
    val entries = loadEntries(emptyFilePath)

    assert(entries.isEmpty)
    assert(detectPolarity("Everything will be neutral since no entries are loaded", entries) == "Neutral")
  }

  test("Test Detect Words Polarity") {
    val entries = testEntries

    //positive
    assert(detectPolarity("able", entries) == "Positive")
    assert(detectPolarity("uncut", entries) == "Positive")
    assert(detectPolarity("absolute", entries) == "Positive")
    assert(detectPolarity("direct", entries) == "Positive")
    assert(detectPolarity("infinite", entries) == "Positive")

    //negative
    assert(detectPolarity("unable", entries) == "Negative")
    assert(detectPolarity("relative", entries) == "Negative")

    //neutral
    assert(detectPolarity("nice", entries) == "Neutral")
    assert(detectPolarity("relational", entries) == "Neutral")
    assert(detectPolarity("absorptive", entries) == "Neutral")
    assert(detectPolarity("unquestioning", entries) == "Neutral")
  }

  test("Test Detect Phrases Polarity") {
    val entries = testEntries

    assert(detectPolarity("This is neither good nor bad", entries) == "Neutral")
    assert(detectPolarity("This is good", entries) == "Positive")
    assert(detectPolarity("This is bad", entries) == "Negative")

    // Negative statements are not taken into account
    assert(detectPolarity("This is not good", entries) == "Positive")
    assert(detectPolarity("This is not bad", entries) == "Negative")
  }

  test("Test Positive Reviews polarity") {


    val projectPath = "C:\\Users\\rmvieira\\Documents\\TAP\\1140953_1140956_1141233_a_2019_tap_ncf"
    //val projectPath = "C:\\Users\\pefer\\Desktop\\TAP\\1140953_1140956_1141233_a_2019_tap_ncf"
    val folderPath = projectPath + "\\resources\\review_polarity\\txt_sentoken\\pos\\"

    val entries = loadEntries(sentiword)

    for(file <- listFiles(folderPath)){
      filePolarity(file, entries)
    }
  }

  private def listFiles(dir:String ) : List[File] = {
    val file = new File(dir)
    file.listFiles.filter(_.isFile)
      .filter(_.getName.endsWith(".txt")).toList
  }

  private def filePolarity(file: File, entries: Stream[Entry]): String ={
    val source = Source.fromFile(file)
    val text = source.getLines().toStream.mkString(" ")
    val pol = detectPolarity(text, entries)

    source.close()
    pol
  }

}
