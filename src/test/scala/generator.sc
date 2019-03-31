import org.scalacheck.Gen
import domain.Entry

val gEntry: Gen[Entry] = for (
  w <- Gen.alphaStr.suchThat(i => !i.isEmpty);
  p <- Gen.choose[Double](0, 1);
  n <- Gen.choose[Double](0, 1)
) yield Entry(w, p, n)

val gGoodEntry: Gen[Entry] = for (
  w <- Gen.alphaStr.suchThat(i => !i.isEmpty);
  p <- Gen.choose[Double](0, 1);
  n <- Gen.choose[Double](0, 1).suchThat(i => i < p)
) yield Entry(w, p, n)

val gBadEntry: Gen[Entry] = for (
  w <- Gen.oneOf("bad", "unable", "relative");
  n <- Gen.choose[Double](0, 1);
  p <- Gen.choose[Double](0, 1).suchThat(i => i < n)
) yield Entry(w, p, n)

val glEntries: Gen[List[Entry]] = Gen.sized {
  s => Gen.listOfN(math.min(5, s), gEntry)
}

val gString: Gen[String] = Gen.alphaStr.suchThat(i => !i.isEmpty)

glEntries.sample

gString.sample