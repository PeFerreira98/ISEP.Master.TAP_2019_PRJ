package controller

import io.XmlAirportIO.{loadAgenda, saveSchedule}

object ScheduleController {

  val processSchedule : (String, String) => Unit = calculateSchedule

  def calculateSchedule(inputFilePath: String, outputFilePath: String)  = {
    //saveSchedule(loadAgenda(inputFilePath).schedule, outputFilePath)
    val schedule = loadAgenda(inputFilePath).schedule
     schedule match {
      case Some(_) => saveSchedule(schedule, outputFilePath)
    }
  }
}
