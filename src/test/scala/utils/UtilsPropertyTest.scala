package utils

import utils.Utils._
import generators.PolarityGenerators._
import org.scalacheck.Prop.forAll
import org.scalacheck.Properties

object UtilsPropertyTest extends Properties("Utils") {

  property("Text can't contain special characters")
    = forAll(gDirtyString){ text: String => {
    textCleanupAndSplit(text)
      .map(word =>
        if (word.isEmpty) 0
        else if(word.matches("[a-zA-Z_]+")) 0
        else 1
      ).sum == 0
    }
  }
}
