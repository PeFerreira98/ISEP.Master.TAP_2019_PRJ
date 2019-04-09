package io

import domain.{Agenda, Aircraft, Class1, Class2, Class3, Class4, Class5, Class6, Runway, Schedule}

import scala.xml.{Elem, XML}

object XmlAirportIO {

  // Load XML
  val loadAgenda : String => Agenda = loadAgendaPriv
  val loadAircrafts : Elem => Seq[Aircraft] = loadAircraftsPriv
  val loadRunways : Elem => Seq[Runway] = loadRunwaysPriv

  // Save XML
  val saveSchedule: (Seq[Schedule],  String) => Unit = saveSchedulePriv

  // Load XML
  def loadAgendaPriv(filePath: String) : Agenda = {
    val fileXml = XML.load(filePath)
    new Agenda(fileXml.\@("maximumDelayTime").toInt, loadAircrafts(fileXml), loadRunways(fileXml))
  }

  private def loadAircraftsPriv(fileXml: Elem) : Seq[Aircraft] = {
    (fileXml \ "aircrafts" \ "aircraft").map(node => Aircraft(node.\@("number").toInt,
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
          })
      )
    )
  }

  // Save XML
  private def saveSchedulePriv(schedule: Seq[Schedule], filePath: String) = {
    // TODO: do it!
    val node = ???
    XML.save(filePath, node)
  }
}
