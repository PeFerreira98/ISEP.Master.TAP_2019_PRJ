package domain

import org.scalatest.FunSuite

class AgendaTest extends FunSuite {

  test("testSchedule") {
    val aircrafts = Seq(
      domain.Aircraft(1,0,domain.Class5, None),
      domain.Aircraft(2,1,domain.Class5, None),
      domain.Aircraft(3,2,domain.Class5, None),
      domain.Aircraft(4,3,domain.Class5, None),
      domain.Aircraft(5,4,domain.Class5, None),
      domain.Aircraft(6,5,domain.Class5, None)
    )

    val runways = Seq(
      domain.Runway(1, Seq(domain.Class5)),
      domain.Runway(2, Seq(domain.Class5)),
      domain.Runway(3, Seq(domain.Class5)),
      domain.Runway(4, Seq(domain.Class5)),
      domain.Runway(5, Seq(domain.Class5)),
    )

    val aq = Agenda(200, aircrafts, runways).schedule

    assert(aq.isDefined)
    assert(aq.get.last.penalty > 0)
  }

  test("testAirplanesSameTimeSchedule") {
    val aircrafts = Seq(
      domain.Aircraft(1,0,domain.Class5, None),
      domain.Aircraft(2,0,domain.Class5, None),
      domain.Aircraft(3,0,domain.Class5, None),
      domain.Aircraft(4,0,domain.Class5, None),
      domain.Aircraft(5,0,domain.Class5, None),
      domain.Aircraft(6,0,domain.Class5, None)
    )

    val runways = Seq(
      domain.Runway(1, Seq(domain.Class5)),
      domain.Runway(2, Seq(domain.Class5)),
      domain.Runway(3, Seq(domain.Class5)),
      domain.Runway(4, Seq(domain.Class5)),
      domain.Runway(5, Seq(domain.Class5)),
    )

    val aq = Agenda(10, aircrafts, runways).schedule

    assert(aq.isEmpty)
  }

  test("testScheduleWithNoRunways") {
    val aircrafts = Seq(
      domain.Aircraft(1,0,domain.Class5, None),
      domain.Aircraft(2,0,domain.Class5, None),
      domain.Aircraft(3,0,domain.Class5, None),
      domain.Aircraft(4,0,domain.Class5, None),
      domain.Aircraft(5,0,domain.Class5, None),
      domain.Aircraft(6,0,domain.Class5, None)
    )

    val runways = Seq()

    val aq = Agenda(10, aircrafts, runways).schedule
    assert(aq.isEmpty)
  }

  test("testNoAircraftSchedule") {
    val aircrafts = Seq()

    val runways = Seq(
      domain.Runway(1, Seq(domain.Class5)),
      domain.Runway(2, Seq(domain.Class5)),
      domain.Runway(3, Seq(domain.Class5)),
      domain.Runway(4, Seq(domain.Class5)),
      domain.Runway(5, Seq(domain.Class5)),
    )

    val aq = Agenda(10, aircrafts, runways).schedule

    assert(aq === Some(List()))
  }

  test("testNoRunwayClassSchedule") {
    val aircrafts = Seq(
      domain.Aircraft(1,0,domain.Class5, None),
    )

    val runways = Seq(
      domain.Runway(1, Seq(domain.Class4)),
      domain.Runway(2, Seq(domain.Class4)),
      domain.Runway(3, Seq(domain.Class4))
    )

    val aq = Agenda(10, aircrafts, runways).schedule

    assert(aq.isEmpty)
  }

  test("testNoRunwayAndAircraftClassSchedule") {
    val aircrafts = Seq()

    val runways = Seq()

    val aq = Agenda(10, aircrafts, runways).schedule

    assert(aq === Some(List()))
  }

  test("testLotOfDelaySchedule") {
    val aircrafts = Seq(
      domain.Aircraft(1,0,domain.Class5, None),
      domain.Aircraft(2,1,domain.Class5, None),
      domain.Aircraft(3,2,domain.Class5, None),
      domain.Aircraft(4,3,domain.Class5, None),
      domain.Aircraft(5,4,domain.Class5, None),
      domain.Aircraft(6,5,domain.Class5, None)
    )

    val runways = Seq(
      domain.Runway(1, Seq(domain.Class5))
    )

    val aq = Agenda(9999, aircrafts, runways).schedule

    assert(aq.isDefined)
  }

  test("testNoDelaySchedule") {
    val aircrafts = Seq(
      domain.Aircraft(1,0,domain.Class5, None),
      domain.Aircraft(2,1,domain.Class5, None)
    )

    val runways = Seq(
      domain.Runway(1, Seq(domain.Class5))
    )

    val aq = Agenda(0, aircrafts, runways).schedule

    assert(aq.isEmpty)
  }

  test("testInitialBiggerTargetSchedule") {
    val aircrafts = Seq(
      domain.Aircraft(1,50,domain.Class5, None),
      domain.Aircraft(2,90,domain.Class1, None),
      domain.Aircraft(3,30,domain.Class3, None),
      domain.Aircraft(4,3,domain.Class1, None),
      domain.Aircraft(5,60,domain.Class5, None),
      domain.Aircraft(6,5,domain.Class5, None)
    )

    val runways = Seq(
      domain.Runway(1, Seq(domain.Class5)),
      domain.Runway(2, Seq(domain.Class1)),
      domain.Runway(3, Seq(domain.Class5)),
      domain.Runway(4, Seq(domain.Class3)),
      domain.Runway(5, Seq(domain.Class5)),
    )

    val aq = Agenda(999, aircrafts, runways).schedule

    assert(aq.isDefined)
  }

}
