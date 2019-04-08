package controller

import domain.{Agenda, Aircraft, Runway, Schedule}

object AirportController {

  val calculateSchedule : Agenda => Set[Schedule] = calculateSchedulePriv

  def calculateSchedulePriv(agenda: Agenda) : Set[Schedule] = {
    // TODO: DO IT!
    ???
  }
}
