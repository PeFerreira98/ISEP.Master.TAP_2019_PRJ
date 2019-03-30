package controller

import scala.io.Source
import domain.{Entry}

object PolarityDetectionController {

  // the polarity detector
  val detectPolarity: (String, Stream[Entry]) => String = detectPolarityPriv
  // the entries loader
  val loadEntries : () => Stream[Entry] = loadEntriesPriv


  private def detectPolarityPriv(phrase: String, entries: Stream[Entry]): String = {
    val polarity = findPolarity(textCleanupAndSplit(phrase), entries)

    if(polarity > 0) "Positive"
    else if (polarity < 0) "Negative"
    else "Neutral"
  }

  private def findPolarity(input: List[String], entries: Stream[Entry]): Double ={
    // TODO: add functionality here!!!
    0
  }

  // cleans the text from special characters and splits it into a list of words
  private def textCleanupAndSplit(text: String) : List[String] = {
    text.replaceAll("[^a-zA-z]", " ")
      .replaceAll(" {2,}", " ")
      .split(" ").map(_.trim).toList
  }

  // Loads and parses all the entries in the SentiWordNet file
  // returns: a list of the parsed entries
  private def loadEntriesPriv(): Stream[Entry] = {
    parseEntries(readFromFile())
  }

  // Gets the lines from SentiWordNet file
  private def readFromFile(): Stream[String] = {
    val fileName = "C:\\Users\\rmvieira\\Documents\\TAP\\1140953_1140956_1141233_a_2019_tap_ncf\\SentiWordNet_3.0.0_20130122.txt"
    Source.fromFile(fileName)
      .getLines.toStream
  }

  // Parses the raw file lines
  // returns: a list of the parsed entries
  private def parseEntries(lines:Stream[String]): Stream[Entry] = {
    ( for (line
           <-
           lines.filter(_.startsWith("a"))
             .map(_.split("\t")
               .map(_.trim)))
      yield Entry(
        line(4).substring(0, line(4).indexOf("#")),
        line(2).toDouble,
        line(3).toDouble) )
        // sort alphabetically just for OCD satisfaction purposes
    .sortBy(_.word)
  }
}
