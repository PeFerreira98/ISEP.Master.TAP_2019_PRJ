package io

import domain.{Agenda, Aircraft, Class1, Class2, Class3, Class4, Class5, Class6, Runway, Schedule}
import scala.xml.{Attribute, Elem, Node, Null, Text, XML}
import javax.xml.transform.stream.StreamSource
import javax.xml.validation.SchemaFactory
import org.xml.sax.SAXException

object XmlAirportIO {

  // Load XML
  val loadAgenda : String => Option[Agenda] = loadAgendaPriv

  // Save XML
  val saveSchedule: (Option[Seq[Schedule]], String) => Unit = saveSchedulePriv

  // Validate Xml file with Schema
  val validateXml: (String,  String) => Boolean = validate

  // Load XML
  /**
    * Loads the Agenda from the XML file
    * @param filePath The input Xml file path
    * @return The Agenda
    */
  private def loadAgendaPriv(filePath: String) : Option[Agenda] = {
    val agendaSchema = "resources/aircraft/agenda.xsd"
    // validate schema
    if(validate(filePath, agendaSchema))
    {
      // Load XML
      val fileXml = XML.load(filePath)
      // Parse XML
      Some(Agenda(fileXml.\@("maximumDelayTime").toInt, loadAircraftsPriv(fileXml), loadRunwaysPriv(fileXml)))
    }
    else None
  }

  /**
    * Validate Xml file with Schema
    * @param xmlFile The Xml file to be validated
    * @param xsdFile The Xsd file used to validate the Xml file
    * @return true if file is according to schema definition, false otherwise
    */
  private def validate(xmlFile: String, xsdFile: String): Boolean = {
    try {
      val schemaLang = "http://www.w3.org/2001/XMLSchema"
      val factory = SchemaFactory.newInstance(schemaLang)
      val schema = factory.newSchema(new StreamSource(xsdFile))
      val validator = schema.newValidator()
      validator.validate(new StreamSource(xmlFile))
      true
    } catch {
      case ex: SAXException => println("--> ERROR - Schema validation failed: " + ex.getMessage + " <--"); false
    }
  }

  /**
    * Parses the Aircraft list from the Xml root element
    * @param fileXml the Xml root Element
    * @return Sequence with the parsed aircrafts
    */
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
      },
      if(node.\@("emergency").nonEmpty)
        Some(node.\@("emergency").toInt)
      else
        None
      ))
  }

  /**
    * Parses the Runway list from the Xml root element
    * @param fileXml The Xml root Element
    * @return Sequence with the parsed runways
    */
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

  /**
    * Saves the Schedule list to an XML file
    * @param scheduleOpt The list with all schedules
    * @param filePath The output XML file path
    */
  private def saveSchedulePriv(scheduleOpt: Option[Seq[Schedule]], filePath: String) : Unit = {
    scheduleOpt match {
      case Some(schedule) =>
        val xmlContent =
          <schedule xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:noNamespaceSchemaLocation="aircraftSchedule.xsd">
            { schedule.map(s =>
            <aircraft />% {Attribute(None, "runway", Text(s.runway.number.toString), Null)}
            % {Attribute(None, "time", Text(s.time.toString), Null)}
            % {Attribute(None, "number", Text(s.aircraft.number.toString), Null)}
            //% {Attribute(None, "penalty", Text(s.penalty.toString), Null)}
          )}
        </schedule>
        XML.save(filePath, xmlContent, "UTF-8", true)
      case _ =>
    }
  }
}
