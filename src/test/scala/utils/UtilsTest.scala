package utils

import domain.{Class1, Class4, Class5}
import org.scalatest.FunSuite

class UtilsTest extends FunSuite {

  test("Test Text Cleanup And Split") {
    val split = "Test split"
    assert(Utils.textCleanupAndSplit(split).size == 2)
    assert(Utils.textCleanupAndSplit(split)(0).equals("Test"))
    assert(Utils.textCleanupAndSplit(split)(1).equals("split"))

    val cleanup = "Wor1d <> ,;.:- ~^ºª !\"#$%&/\\|()=?' «»€@£§{[]}"
    assert(Utils.textCleanupAndSplit(cleanup)(0).equals("Word"))
  }

  test("Test get aircraft operation delay time") {
    assert(Utils.getAircraftDelay(Class1, Class1) == 82)
    assert(Utils.getAircraftDelay(Class4, Class5) == 60)
  }
}
