package controller

import controller.PolarityDetectionController.detectPolarity
import controller.PolarityDetectionController.loadEntries
import org.scalatest.FunSuite

class PolarityDetectionControllerTest extends FunSuite {

  //val absolutePath = "C:\\Users\\rmvieira\\Documents\\TAP\\1140953_1140956_1141233_a_2019_tap_ncf"
  val absolutePath = "C:\\Users\\pefer\\Desktop\\TAP\\1140953_1140956_1141233_a_2019_tap_ncf"
  val filePath = absolutePath + "\\resources\\SentiWordNet_3.0.0_20130122_test.txt"
  val emptyFilePath = absolutePath + "\\resources\\SentiWordNet_3.0.0_20130122_test2.txt"

  test("testLoadEntries") {
    assert(!loadEntries(filePath).isEmpty)
    assert(loadEntries(emptyFilePath).isEmpty)
  }

  test("testDetectPolarity") {
    val entries = loadEntries(filePath)

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

}
