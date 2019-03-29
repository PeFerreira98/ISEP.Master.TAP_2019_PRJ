package controller

import scala.io.Source
import domain.{Entry}

object PolarityDetectionController {

  // the polarity detector
  val detectPolarity: (String, Stream[Entry]) => Boolean = detectPolarityStuff

  //val detectPolarity: String => Boolean = detectPolarityStuff
  private def detectPolarityStuff(phrase: String, entries: Stream[Entry]): Boolean = {
    // TODO: add functionality here!!!
    true
  }

  // Loads and parses all the entries in the SentiWordNet file
  // returns: a list of the parsed entries
  def loadEntries(): Stream[Entry] = {
    parseEntries(readFromFile())
  }

  // Gets the lines from SentiWordNet file
  private def readFromFile(): Stream[String] = {
    val fileName = "C:\\Users\\rmvieira\\Documents\\TAP\\1140953_1140956_1141233_a_2019_tap_ncf\\SentiWordNet_3.0.0_20130122.txt"
    Source.fromFile(fileName).getLines.toStream
  }

  // Parses the raw file lines
  // returns: a list of the parsed entries
  private def parseEntries(lines:Stream[String]): Stream[Entry] = {
    for (line
           <-
           lines.filter(_.startsWith("a"))
             .map(_.split("\t")
               .map(_.trim)))
      yield Entry(
        line(4).substring(0, line(4).indexOf("#")),
        line(2).toDouble,
        line(3).toDouble)
  }
}
