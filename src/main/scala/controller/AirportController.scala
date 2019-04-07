package controller

import domain.{Aircraft, Runway, Schedule}

object AirportController {

  val calculateSchedule : (Set[Aircraft], Set[Runway]) => Set[Schedule] = calculateSchedulePriv

  def calculateSchedulePriv(aircrafts :Set[Aircraft], runways: Set[Runway]) : Set[Schedule] = {
    // TODO: DO IT!
    ???
    //throw NotImplementedException
  }
}
