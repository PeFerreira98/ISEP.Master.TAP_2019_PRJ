package controller

import domain._
import controller.PolarityDetectionController.detectPolarity
import org.scalacheck.{Gen, Properties, Test}
import org.scalacheck.Prop.forAll
import generators.PolarityGenerators._

object PolarityDetectionControllerPropertyTest extends Properties("PolarityDetectionController") {

  property("Detect Good Polarity")
    = forAll(gGoodString, glEntries){ (text: String, entries: List[Entry]) => {
      val pol = detectPolarity(text, entries.toStream)
      pol == "Positive" || pol == "Neutral" //in case generator didn't pick the word
    }
  }

  property("Detect Bad Polarity")
    = forAll(gBadString, glEntries){ (text: String, entries: List[Entry]) => {
    val pol = detectPolarity(text, entries.toStream)
    pol == "Negative" || pol == "Neutral"
    }
  }

  property("Detect Neutral Polarity")
    = forAll(gNeutralString, glEntries){ (text: String, entries: List[Entry]) => {
      val pol = detectPolarity(text, entries.toStream)
      pol == "Neutral"
    }
  }

  //  (e.g. not <adjective>)
  property("negative statements don't affect polarity")
    = forAll(gMixString, glEntries){ (text: String, entries: List[Entry]) => {
      detectPolarity(text, entries.toStream) == detectPolarity("not " + text, entries.toStream)
    }
  }

  property("neutral words don't affect the phrase's polarity")
    = forAll(gMixString, gNeutralString, glEntries){ (text: String, neutral: String, entries: List[Entry]) => {
      detectPolarity(text, entries.toStream) == detectPolarity(neutral + " " + text, entries.toStream)
    }
  }
}
