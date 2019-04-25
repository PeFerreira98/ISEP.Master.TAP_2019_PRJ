package utils

import domain.{Class1, Class2, Class3, Class4, Class5, Class6}
import org.scalatest.FunSuite

class UtilsTest extends FunSuite {

  test("Test Text Cleanup And Split") {
    val split = "Test split"
    assert(Utils.textCleanupAndSplit(split).size == 2)
    assert(Utils.textCleanupAndSplit(split).head.equals("Test"))
    assert(Utils.textCleanupAndSplit(split)(1).equals("split"))

    val cleanup = "Wor1d <> ,;.:- ~^ºª !\"#$%&/\\|()=?' «»€@£§{[]}"
    assert(Utils.textCleanupAndSplit(cleanup).head.equals("Word"))
  }

  test("Test get aircraft operation delay time") {
    assert(Utils.getAircraftDelay(Class1, Class1) == 82)
    assert(Utils.getAircraftDelay(Class1, Class4) == 75)
    assert(Utils.getAircraftDelay(Class1, Class5) == 75)
    assert(Utils.getAircraftDelay(Class4, Class1) == 60)
    assert(Utils.getAircraftDelay(Class4, Class4) == 60)
    assert(Utils.getAircraftDelay(Class4, Class5) == 60)
    assert(Utils.getAircraftDelay(Class5, Class1) == 60)
    assert(Utils.getAircraftDelay(Class5, Class4) == 60)
    assert(Utils.getAircraftDelay(Class5, Class5) == 60)
  }

  test("Test get delay penalty cost") {
    assert(Utils.getDelayPenaltyCost(Class1, 5) == 10)
    assert(Utils.getDelayPenaltyCost(Class2, 5) == 10)
    assert(Utils.getDelayPenaltyCost(Class3, 5) == 10)
    assert(Utils.getDelayPenaltyCost(Class4, 5) == 5)
    assert(Utils.getDelayPenaltyCost(Class5, 5) == 5)
    assert(Utils.getDelayPenaltyCost(Class6, 5) == 5)

    assert(Utils.getDelayPenaltyCost(Class2, -1) == 0)
    assert(Utils.getDelayPenaltyCost(Class3, 0) == 0)
    assert(Utils.getDelayPenaltyCost(Class4, -20) == 0)
  }
}
