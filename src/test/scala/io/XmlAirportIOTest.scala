package io

import org.scalatest.FunSuite
import io.XmlAirportIO

class Test extends FunSuite {

  val projectFolder = "C:\\Users\\rmvieira\\Documents\\TAP\\1140953_1140956_1141233_a_2019_tap_ncf\\"
  //val projectFolder = "C:\\Users\\pefer\\Desktop\\TAP\\1140953_1140956_1141233_a_2019_tap_ncf\\"
  val resourcesFolder = projectFolder + "resources\\aircraft\\"

  test("Test XML Loading") {
    val inputFilePath = resourcesFolder + "agenda.xml"
    val agenda = XmlAirportIO.loadAgenda(inputFilePath)

    assert(agenda.)
  }
}