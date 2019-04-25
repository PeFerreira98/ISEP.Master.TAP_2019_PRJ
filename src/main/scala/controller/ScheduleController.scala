package controller

import io.XmlAirportIO.{loadAgenda, saveSchedule}

object ScheduleController {

  val processSchedule : (String, String) => Unit = calculateSchedule

  // Controller function to load XML file, calculate the schedule
  // and write the result to an XML file
  def calculateSchedule(inputFilePath: String, outputFilePath: String) : Unit  = {
    loadAgenda(inputFilePath) match {
      case Some(agenda) =>
        val schedule = agenda.schedule
        schedule match {
          case Some(_) => saveSchedule(schedule, outputFilePath)
          case _ =>
        }
      case _ =>
    }
  }
}
