import domain._
import org.scalacheck.Gen
import org.scalacheck.Properties


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
  n <- Gen.choose(1,6) ;
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
  Gen.choose(1, la.size+compensation).flatMap( n =>
    Gen.sequence[Seq[Runway], Runway]((1 until n+1).map(id => gRunway(id, la)))
  )

val gEmergency : Gen[Integer] = for (
  x <- Gen.choose(0,15)
) yield x

/**
  * Generator for generating a single aircraft with specified ID
  * @param id The aircraft's ID (number)
  * @return The aircraft generator
  */
def gAircraft(id : Integer) : Gen[Aircraft] = for (
  //id <- Gen.choose(0,1000);
  target <- Gen.choose(1, 50);
  classe <- gClass;
  emergency <- Gen.option(gEmergency)
) yield Aircraft(id, target ,classe, emergency)

/**
  * Generator for generating a sequence of aircrafts with sequencial IDs
  */
def gAircraftList(nMax : Int): Gen[Seq[Aircraft]] =
  Gen.choose(1, nMax).flatMap( n =>
    Gen.sequence[Seq[Aircraft], Aircraft]((1 until n+1).map(id => gAircraft(id)))
  )

/**
  * Generator for generating a single agenda
  */
val genValidAgenda : Gen[Agenda] = for (
  maxDelay <- Gen.choose(700,1000);
  aircrafts <- gAircraftList(15);
  runways <- gRunwayList(aircrafts, 0)
) yield Agenda(maxDelay, aircrafts, runways)

val genValidAgendaEmptyAircraft : Gen[Agenda] = for (
  maxDelay <- Gen.choose(700, 1000);
  runways <- gRunwayList(Seq(), 3)
) yield Agenda(maxDelay, Seq(), runways)

// An invalid nurse list and period list is generated with 1 to 10 nurses added to the maximum for each skill
val genInvalidAgendaNoAvailableRunway : Gen[Agenda] = for (
  maxDelay <- Gen.choose(700,1000);
  aircrafts <- gAircraftList(15);
  runways <- gRunwayList(aircrafts, 0)
) yield Agenda(maxDelay, aircrafts, runways)

val genInvalidAgendaExceedMaxDelay : Gen[Agenda] = for (
  maxDelay <- Gen.choose(1,50);
  aircrafts <- gAircraftList(15);
  runways <- gRunwayList(aircrafts, -1)
) yield Agenda(maxDelay, aircrafts, runways)

val a = gAircraftList(15).sample
val run = gRunwayList(a.get, 0).sample
