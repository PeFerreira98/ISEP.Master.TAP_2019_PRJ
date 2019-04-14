package controller

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
    val outputFilePath = resourcesFolder + "test/schedule_test3.xml"

    processSchedule("", outputFilePath)
  }

}
