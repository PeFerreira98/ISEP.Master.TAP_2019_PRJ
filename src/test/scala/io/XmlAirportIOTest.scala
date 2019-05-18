package io

import java.nio.file.{Files, Paths}

import domain.Class5
import org.scalatest.FunSuite
import io.XmlAirportIO._

class XmlAirportIOTest extends FunSuite {

  val resourcesFolder = "resources/aircraft/"

  // XML Load

  test("Test XML Loading Agenda") {
    val inputFilePath = resourcesFolder + "agenda.xml"
    val agenda = loadAgenda(inputFilePath).get

    assert(agenda.maximumDelayTime == 900)
    assert(agenda.aircrafts.size == 5)
    assert(agenda.runways.size == 2)

    //  <aircraft number="1" target="0" class="5"/>
    val firstAircraft = agenda.aircrafts.head
    assert(firstAircraft.number == 1)
    assert(firstAircraft.target == 0)
    assert(firstAircraft.classe == Class5)
  }

  test("Test XML Loading Invalid schema") {
    val inputFilePath = resourcesFolder + "agenda_invalid.xml"
    val agenda = loadAgenda(inputFilePath)

    assert(agenda.isEmpty)
  }

  test("Test XML Loading Empty schema") {
    val inputFilePath = resourcesFolder + "agenda_empty.xml"
    val agenda = loadAgenda(inputFilePath)

    assert(agenda.isEmpty)
  }

  test("Test XML Loading MS1") {
    val files = List( resourcesFolder + "ms1_test01.xml",
      resourcesFolder + "ms1_test02.xml",
      resourcesFolder + "ms1_test03.xml",
      resourcesFolder + "ms1_test04.xml",
      resourcesFolder + "ms1_test05.xml",
      resourcesFolder + "ms1_test06.xml")

    files.foreach(file => assert(loadAgenda(file).isDefined))
  }

  // XML Save

  test("Test XML Save Schedule") {
    val filePath = resourcesFolder + "test/test_save.xml"
    val schedule = Seq(
      domain.Schedule(
        domain.Aircraft(1,0,domain.Class5, None),
        0,
        domain.Runway(1, Seq(domain.Class5)),
        0),
      domain.Schedule(
        domain.Aircraft(2,10,domain.Class4, None),
        11,
        domain.Runway(1, Seq(domain.Class4)),
        6)
    )

    saveSchedule(Option(schedule), filePath)
  }

  test("Test XML Save Empty Schedule") {
    val filePath = resourcesFolder + "test/test_save_empty.xml"
    val schedule = Seq()

    saveSchedule(Option(schedule), filePath)
  }

  test("Test XML Save Non Existing Schedule") {
    val filePath = resourcesFolder + "test/test_save_null.xml"
    saveSchedule(None, filePath)

    assert(!Files.exists(Paths.get(filePath)))
  }
}