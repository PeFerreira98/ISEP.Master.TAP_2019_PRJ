package controller

import domain._
import controller.PolarityDetectionController.detectPolarity
import controller.PolarityDetectionController.loadEntries
import org.scalacheck.{Gen, Properties, Test}
import org.scalacheck.Prop.forAll

object PolarityDetectionControllerPropertyTest extends Properties("PolarityDetectionController") {

  val gEntry: Gen[Entry] = for (
    w <- Gen.alphaStr.suchThat(i => !i.isEmpty);
    p <- Gen.choose[Double](0, 1);
    n <- Gen.choose[Double](0, 1)
  ) yield Entry(w,p,n)

  val glEntries: Gen[List[Entry]] = Gen.sized{
    s => Gen.listOfN(math.min(5, s), gEntry)
  }

  val gString: Gen[String] = Gen.alphaStr.suchThat(i => !i.isEmpty)

  property("detectPolarity") = forAll(gString, glEntries){ (n1: String, n2: List[Entry]) => {
    if (detectPolarity(n1, n2.toStream) == "Neutral")
      true
    else
      false
  }}

  property("negative statements don't affect polarity")
    = forAll(gString, glEntries){ (n1: String, n2: List[Entry]) => {
      detectPolarity(n1, n2.toStream) == detectPolarity("not " + n1, n2.toStream)
    }
  }
}
