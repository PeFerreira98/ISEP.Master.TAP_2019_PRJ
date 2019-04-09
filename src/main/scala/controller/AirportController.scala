package controller

import io.XmlAirportIO.{loadAgenda, saveSchedule}

object AirportController {

  val processSchedule : (String, String) => Unit = calculateSchedulePriv

  def calculateSchedulePriv(inputFilePath: String, outputFilePath: String)  = {
    saveSchedule(loadAgenda(inputFilePath).schedule, outputFilePath)
  }
}
