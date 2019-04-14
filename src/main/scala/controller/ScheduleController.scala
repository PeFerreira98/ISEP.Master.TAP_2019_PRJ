package controller

import io.XmlAirportIO.{loadAgenda, saveSchedule}

object ScheduleController {

  val processSchedule : (String, String) => Unit = calculateSchedule

  // Controller function to load XML file, calculate the schedule
  // and write the result to an XML file
  def calculateSchedule(inputFilePath: String, outputFilePath: String)  = {
    val schedule = loadAgenda(inputFilePath).schedule
     schedule match {
       case Some(_) => saveSchedule(schedule, outputFilePath)
       case None =>
    }
  }
}
