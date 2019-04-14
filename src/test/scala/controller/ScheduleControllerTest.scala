package controller

import java.io.FileNotFoundException

import org.scalatest.FunSuite
import controller.ScheduleController._

class ScheduleControllerTest extends FunSuite {

  val resourcesFolder = "resources/aircraft/"

  test("testProcessSchedule") {
    val inputFilePath = resourcesFolder + "agenda.xml"
    val outputFilePath = resourcesFolder + "test/schedule_test1.xml"

    processSchedule(inputFilePath, outputFilePath)
  }

  test("testEmptyInputProcessSchedule") {
    val inputFilePath = resourcesFolder + "test/empty_agenda.xml"
    val outputFilePath = resourcesFolder + "test/schedule_test2.xml"

    processSchedule(inputFilePath, outputFilePath)
  }

  test("testNullInputProcessSchedule") {
    try
    {
      val inputFilePath = ""
      val outputFilePath = resourcesFolder + "test/schedule_test3.xml"

      processSchedule(inputFilePath, outputFilePath)
    }
    catch
    {
      case _: Throwable =>
    }
  }

  test("testNullOutputProcessSchedule") {
    try
    {
      val inputFilePath = resourcesFolder + "agenda.xml"
      val outputFilePath = ""

      processSchedule(inputFilePath, outputFilePath)
    }
    catch
    {
      case _: Throwable =>
    }
  }
}
