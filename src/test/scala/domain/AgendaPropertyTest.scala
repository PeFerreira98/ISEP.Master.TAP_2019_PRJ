package domain

import org.scalacheck.Properties
import org.scalacheck.Prop.forAll
import generators.AircraftGenerators._

object AgendaPropertyTest extends Properties("Agenda Schedule") {

  property("Test Valid Schedule") =
    forAll(genValidAgenda) {
      agenda => agenda match {
        // runways available for all kinds of aircrafts
        case _: Agenda
          if agenda.aircrafts.map(_.classe).distinct.intersect(agenda.runways.flatMap(_.classes).distinct).size
          == agenda.aircrafts.map(_.classe).distinct.size
          => val s = agenda.schedule; s.isDefined && s.get.nonEmpty
        // runways unavailable for some kinds of aircrafts
        case _: Agenda => agenda.schedule.isEmpty
      }
    }

  property("Test Valid empty Schedule") =
    forAll(genValidAgendaEmptyAircraft) { agenda =>
      val s = agenda.schedule
      s.isDefined && s.get.isEmpty
    }

  property("Test invalid Schedule no available runway") =
    forAll(genInvalidAgendaNoAvailableRunway) {
      _.schedule.isEmpty
    }

  property("Test invalid Schedule exceed max delay") =
    forAll(genInvalidAgendaExceedMaxDelay) {
      _.schedule.isEmpty
    }

  property("Test Schedule with emergency") =
    forAll(genValidAgendaEmergency) {
      agenda => agenda match {
        // runways available for all kinds of aircrafts
        case _: Agenda
          if agenda.aircrafts.map(_.classe).distinct.intersect(agenda.runways.flatMap(_.classes).distinct).size
            == agenda.aircrafts.map(_.classe).distinct.size
        => val s = agenda.schedule; s.isDefined && s.get.nonEmpty
        // runways unavailable for some kinds of aircrafts
        case _: Agenda => agenda.schedule.isEmpty
      }
    }
}
