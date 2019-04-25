package generators

import domain._
import org.scalacheck.Gen

object AircraftGenerators {
/*
  val gClass : Gen[Class] = for(
    n <- Gen.choose(1,6);
  ) yield n

  val gAircraft : Gen[Aircraft] = for(
    id <- Gen.choose(0,1000);
    target <- Gen.choose(0,1000);
    classe <- gClass;
    emergency <- gEmergency
  ) yield Aircraft(id, target ,classe, emergency)

 */

  val genAircraftList: Gen[Seq[Aircraft]] = ???
    
}
