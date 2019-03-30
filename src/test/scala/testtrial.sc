import controller.PolarityDetectionController.loadEntries
import controller.PolarityDetectionController.detectPolarity
import controller.PolarityDetectionControllerPropertyTest.property
import domain.Entry
import org.scalacheck.Gen
import org.scalacheck.Prop.forAll

val absolutePath = "C:\\Users\\pefer\\Desktop\\TAP\\1140953_1140956_1141233_a_2019_tap_ncf\\resources"
val filePath = absolutePath + "\\SentiWordNet_3.0.0_20130122_test.txt"
val emptyFilePath = absolutePath + "\\SentiWordNet_3.0.0_20130122_test2.txt"

assert(!loadEntries(filePath).isEmpty)
assert(loadEntries(emptyFilePath).isEmpty)

println("test pass")

val stuff0 = loadEntries(filePath)
assert(detectPolarity("able", stuff0) == "Positive")
assert(detectPolarity("unable", stuff0) == "Negative")
detectPolarity("nice", stuff0)

println("test test pass")

val gEntry: Gen[Entry] = for (
  w <- Gen.alphaStr.suchThat(i => !i.isEmpty);
  p <- Gen.choose[Double](0, 1);
  n <- Gen.choose[Double](0, 1)
) yield Entry(w,p,n)

val glEntries: Gen[List[Entry]] = Gen.sized{
  s => Gen.listOfN(math.min(5, s), gEntry)
}

val gString: Gen[String] = Gen.alphaStr.suchThat(i => !i.isEmpty)

forAll(gString, glEntries){ (n1: String, n2: List[Entry]) => {
  if (detectPolarity(n1, n2.toStream) == "Neutral")
    true
  else
    false
}}