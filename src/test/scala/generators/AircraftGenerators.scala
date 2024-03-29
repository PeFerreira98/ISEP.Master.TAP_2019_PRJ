package generators

import domain._
import org.scalacheck.Gen
import org.scalacheck.Properties

object AircraftGenerators {

  /**
    * Generator for generating a single class (from Class1 to Class6)
    */
  val gClass : Gen[Class] = for (
    n <- Gen.oneOf(Class1, Class2, Class3, Class4, Class5, Class6)
  ) yield n

  /**
    * Generator for generating a sequence of unique classes
    * with a maximum of 6 elements, since only 6 classes exist
    * @param la The aircraft list
    * @return runway's class list
    */
  def gClassList(la : Seq[Aircraft]) : Gen[Seq[Class]] = for (
    n <- Gen.choose(1, if (la.isEmpty) 6 else la.map(_.classe).distinct.size) ;
    classes <- Gen.pick(n, if (la.isEmpty) Seq(Class1, Class2, Class3, Class4, Class5, Class6) else la.map(_.classe).distinct)
  ) yield classes.distinct

  /**
    * Generator for generating a single runway with specified ID
    * @param id The runway's ID (number)
    * @param la The aircraft list
    * @return The runway generator
    */
  def gRunway(id : Integer, la : Seq[Aircraft]) : Gen[Runway] = for (
    //id <- Gen.choose(1,6);
    classes <- gClassList(la)
  ) yield Runway(id, classes)

  /**
    * Generator for generating a sequence of runways with sequencial ID
    * @param la The aircraft list
    */
  def gRunwayList(la : Seq[Aircraft], compensation : Int) : Gen[Seq[Runway]] =
  Gen.choose(la.size/2, la.size+compensation).flatMap( n =>
    Gen.sequence[Seq[Runway], Runway]((0 until n).map(id => gRunway(id, la)))
  )

  /**
    * Generator for generating an emergency value
    */
  val gEmergency : Gen[Integer] = for (
    x <- Gen.choose(0,15)
  ) yield x

  /**
    * Generator for generating a single aircraft with specified ID
    * @param id The aircraft's ID (number)
    * @return The aircraft generator
    */
  def gAircraft(id : Integer, hasEmergency: Boolean) : Gen[Aircraft] = for (
    //id <- Gen.choose(0,1000);
    target <- Gen.choose(1, 50);
    classe <- gClass;
    emergency <- Gen.option(gEmergency)
  ) yield Aircraft(id, target ,classe, if(hasEmergency) emergency else None)

  /**
    * Generator for generating a sequence of aircrafts with sequencial IDs
    */
  def gAircraftList(nMax : Int, hasEmergency: Boolean): Gen[Seq[Aircraft]] =
    Gen.choose(2, nMax).flatMap( n =>
      Gen.sequence[Seq[Aircraft], Aircraft]((0 until n).map(id => gAircraft(id, hasEmergency)))
    )

  /**
    * Generator for generating a single agenda
    */
  val genValidAgenda : Gen[Agenda] = for (
    maxDelay <- Gen.choose(700,1000);
    aircrafts <- gAircraftList(50, false);
    runways <- gRunwayList(aircrafts, 0)
  ) yield Agenda(maxDelay, aircrafts, runways)

  /**
    * Generator for generating a single agenda without aircrafts
    */
  val genValidAgendaEmptyAircraft : Gen[Agenda] = for (
    maxDelay <- Gen.choose(700, 1000);
    runways <- gRunwayList(Seq(), 5)
  ) yield Agenda(maxDelay, Seq(), runways)

  /**
    * Generator for generating a single agenda with
    * at least an aircraft with no matching runway
    */
  val genInvalidAgendaNoAvailableRunway : Gen[Agenda] = for (
    maxDelay <- Gen.choose(700,1000);
    aircrafts <- gAircraftList(50, false)
  ) yield Agenda(maxDelay, aircrafts, Seq())

  /**
    * Generator for generating a single agenda with less
    * runways than aicrafts so that delay will always be exceeded
    */
  val genInvalidAgendaExceedMaxDelay : Gen[Agenda] = for (
    // delay + 50 range of aircraft target smaller than the smallest operation delay (60)
    maxDelay <- Gen.choose(1,5);
    aircrafts <- gAircraftList(50, true);
    // less runways than aircrafts to ensure the maximum delay will be exceeded
    runways <- gRunwayList(aircrafts, -1)
  ) yield Agenda(maxDelay, aircrafts, runways)

  /**
    * Generator for generating a single agenda with/without emergency values
    */
  val genValidAgendaEmergency : Gen[Agenda] = for (
    maxDelay <- Gen.choose(700,1000);
    aircrafts <- gAircraftList(50, false);
    runways <- gRunwayList(aircrafts, 0)
  ) yield Agenda(maxDelay, aircrafts, runways)
}
