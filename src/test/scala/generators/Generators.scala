package generators

import domain.Entry
import org.scalacheck.Gen

object Generators {

  // String generators
  val gString: Gen[String] = Gen.alphaStr.suchThat(i => !i.isEmpty)
  val gDirtyString: Gen[String] = Gen.alphaStr.suchThat(i => !i.isEmpty)

  val gGoodString: Gen[String] = Gen.oneOf("able", "uncut", "absolute", "direct", "infinite", "good")
  val gBadString: Gen[String] = Gen.oneOf("bad", "unable", "relative")
  val gNeutralString: Gen[String] = Gen.oneOf("nice", "relational", "absorptive", "unquestioning", "neutral", "neutral")

  val gMixString: Gen[String] = Gen.oneOf(gGoodString, gBadString, gNeutralString)

  // Entries generators
  val gGoodEntry: Gen[Entry] = for (
    w <- gGoodString;
    p <- Gen.choose[Double](0, 1)
  ) yield Entry(w, p, 0)

  val gBadEntry: Gen[Entry] = for (
    w <- gBadString;
    n <- Gen.choose[Double](0, 1)
  ) yield Entry(w, 0, n)

  val gNeutralEntry: Gen[Entry] = for (
    w <- gNeutralString;
    p <- Gen.choose[Double](0, 1)
  ) yield Entry(w, p, p)

  val gEntry: Gen[Entry] = Gen.oneOf(gGoodEntry, gBadEntry, gNeutralEntry)

  // List Generators
  val glEntries: Gen[List[Entry]] = Gen.sized {
    s => Gen.listOfN(math.min(5000, s) ,gEntry)
  }
}
