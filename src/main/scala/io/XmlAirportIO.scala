package io

import domain.{Agenda, Aircraft, Class1, Class2, Class3, Class4, Class5, Class6, Runway, Schedule}
import scala.xml.{Attribute, Elem, Node, Null, Text, XML}

object XmlAirportIO {

  // Load XML
  val loadAgenda : String => Agenda = loadAgendaPriv

  // Save XML
  val saveSchedule: (Option[Seq[Schedule]], String) => Unit = saveSchedulePriv

  // Load XML
  private def loadAgendaPriv(filePath: String) : Agenda = {
    val fileXml = XML.load(filePath)
    Agenda(fileXml.\@("maximumDelayTime").toInt, loadAircraftsPriv(fileXml), loadRunwaysPriv(fileXml))
  }

  private def loadAircraftsPriv(fileXml: Elem) : Seq[Aircraft] = {
    (fileXml \\ "agenda" \ "aircrafts" \ "aircraft").map(node => Aircraft(node.\@("number").toInt,
      node.\@("target").toInt,
      node.\@("class") match {
        case "1" => Class1
        case "2" => Class2
        case "3" => Class3
        case "4" => Class4
        case "5" => Class5
        case "6" => Class6
      }))
  }

  private def loadRunwaysPriv(fileXml: Elem) : Seq[Runway] = {
    (fileXml \\ "agenda" \ "runways" \ "runway").map(runwayNode =>
      Runway(runwayNode.\@("number").toInt,
        (runwayNode \ "class")
          .map(classNode => classNode.\@("type") match {
            case "1" => Class1
            case "2" => Class2
            case "3" => Class3
            case "4" => Class4
            case "5" => Class5
            case "6" => Class6
          })
      )
    )
  }

  // Save XML
  private def saveSchedulePriv(schedule: Option[Seq[Schedule]], filePath: String) = {
    schedule match {
      case Some(_) => {
        val xmlContent =
          <schedule xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:noNamespaceSchemaLocation="aircraftSchedule.xsd">
            { schedule.getOrElse(Seq()).map(s =>
            <aircraft />% {Attribute(None, "runway", Text(s.runway.number.toString), Null)}
            % {Attribute(None, "time", Text(s.time.toString), Null)}
            % {Attribute(None, "number", Text(s.aircraft.number.toString), Null)}
          )}
        </schedule>
        XML.save(filePath, xmlContent, "UTF-8", true)
      }
      case None =>
    }
  }
}
