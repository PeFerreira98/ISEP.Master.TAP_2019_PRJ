package controller

import domain.{Agenda, Schedule}

object AirportController {

  val calculateSchedule : Agenda => Option[Seq[Schedule]] = calculateSchedulePriv

  def calculateSchedulePriv(agenda: Agenda) : Option[Seq[Schedule]] = {
    agenda.schedule
  }
}
