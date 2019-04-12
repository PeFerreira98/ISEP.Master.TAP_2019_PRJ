package io

import domain.Class5
import org.scalatest.FunSuite
import io.XmlAirportIO

class Test extends FunSuite {

  val resourcesFolder = "resources/aircraft/"

  test("Test XML Loading Agenda") {
    val inputFilePath = resourcesFolder +"agenda.xml"
    val agenda = XmlAirportIO.loadAgenda(inputFilePath)

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
    XmlAirportIO.saveSchedule(Option(
      Seq(domain.Schedule(
        domain.Aircraft(1,0,domain.Class5),
        0,
        domain.Runway(1, Seq(domain.Class5))),
        domain.Schedule(
          domain.Aircraft(2,10,domain.Class4),
          11,
          domain.Runway(1, Seq(domain.Class4)))
    )), "resources/aircraft/test/output.xml")
  }
}