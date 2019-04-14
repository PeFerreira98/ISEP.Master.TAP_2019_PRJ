package io

import domain.Class5
import org.scalatest.FunSuite
import io.XmlAirportIO._

class XmlAirportIOTest extends FunSuite {

  val resourcesFolder = "resources/aircraft/"

  test("Test XML Loading Agenda") {
    val inputFilePath = resourcesFolder + "agenda.xml"
    val agenda = loadAgenda(inputFilePath)

    assert(agenda.maximumDelayTime == 900)
    assert(agenda.aircrafts.size == 5)
    assert(agenda.runways.size == 2)

      <aircraft number="1" target="0" class="5"/>
    val firstAircraft = agenda.aircrafts.head
    assert(firstAircraft.number == 1)
    assert(firstAircraft.target == 0)
    assert(firstAircraft.classe == Class5)
  }

  test("Test XML Save Schedule") {
    val filePath = resourcesFolder + "test/test_save.xml"
    val schedule = Seq(
      domain.Schedule(
        domain.Aircraft(1,0,domain.Class5),
        0,
        domain.Runway(1, Seq(domain.Class5))),
      domain.Schedule(
        domain.Aircraft(2,10,domain.Class4),
        11,
        domain.Runway(1, Seq(domain.Class4)))
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
  }
}