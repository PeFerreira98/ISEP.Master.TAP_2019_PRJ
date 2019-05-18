package controller

import java.nio.file.{Paths, Files}

import org.scalatest.FunSuite
import controller.ScheduleController._

class ScheduleControllerTest extends FunSuite {

  val resourcesFolder = "resources/aircraft/"
  val outputFolder = resourcesFolder + "test/"

  test("testProcessSchedule") {
    val inputFilePath = resourcesFolder + "agenda.xml"
    val outputFilePath = outputFolder + "schedule_test1.xml"

    processSchedule(inputFilePath, outputFilePath)
    assert(Files.exists(Paths.get(outputFilePath)))
  }

  test("testEmptyInputProcessSchedule") {
    val inputFilePath = resourcesFolder + "agenda_empty.xml"
    val outputFilePath = outputFolder + "schedule_test2.xml"

    processSchedule(inputFilePath, outputFilePath)
    assert(!Files.exists(Paths.get(outputFilePath)))
  }

  test("testNullInputProcessSchedule") {
    val inputFilePath = ""
    val outputFilePath = outputFolder + "schedule_test3.xml"

    processSchedule(inputFilePath, outputFilePath)

    assert(!Files.exists(Paths.get(outputFilePath)))
  }

  test("testNullOutputProcessSchedule") {
    val inputFilePath = resourcesFolder + "agenda.xml"
    val outputFilePath = ""

    assertThrows[Exception] {
      processSchedule(inputFilePath, outputFilePath)
    }
  }

  test("testInputMS1") {
    val files = List( "ms1_test01.xml",
      "ms1_test02.xml",
      "ms1_test03.xml",
      "ms1_test04.xml",
      "ms1_test05.xml",
      "ms1_test06.xml")

    files.foreach(file =>
      processSchedule(resourcesFolder + file, outputFolder + file))

    assert(!Files.exists(Paths.get(outputFolder + "tests/ms1_test01.xml")))
    assert(!Files.exists(Paths.get(outputFolder + "tests/ms1_test02.xml")))
  }
}
