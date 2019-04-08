package io

import domain.{Agenda, Aircraft, Class1, Class2, Class3, Class4, Class5, Class6, Runway, Schedule}

import scala.xml.{Elem, XML}

object XmlAirportIO {

  // Load XML
  val loadAgenda : String => Agenda = loadAgendaPriv
  val loadAircrafts : Elem => Set[Aircraft] = loadAircraftsPriv
  val loadRunways : Elem => Set[Runway] = loadRunwaysPriv

  // Save XML


  // Load XML
  def loadAgendaPriv(filePath: String) : Agenda = {
    val fileXml = XML.load(filePath)
    new Agenda(fileXml.\@("maximumDelayTime").toInt, loadAircrafts(fileXml), loadRunways(fileXml))
  }

  private def loadAircraftsPriv(fileXml: Elem) : Set[Aircraft] = {
    (fileXml \ "aircrafts" \ "aircraft").map(node => Aircraft(node.\@("number").toInt,
      node.\@("target").toInt,
      Class1)).toSet
  }

  private def loadRunwaysPriv(fileXml: Elem) : Set[Runway] = {
    (fileXml \ "runways" \ "runway").map(node =>
      Runway(node.\@("number").toInt,
        (node \ "class")
          .map(classNode => classNode.\@("type") match {
            case "1" => Class1
            case "2" => Class2
            case "3" => Class3
            case "4" => Class4
            case "5" => Class5
            case "6" => Class6
          }).toSet
      )
    ).toSet
  }

  // Save XML
  private def saveSchedule(schedule: Schedule) = {
    ???
  }
}
