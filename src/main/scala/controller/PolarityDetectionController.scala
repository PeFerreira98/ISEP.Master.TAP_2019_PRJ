package controller

import scala.io.Source
import domain.Entry
import sun.reflect.generics.reflectiveObjects.NotImplementedException
import utils.Utils.textCleanupAndSplit

object PolarityDetectionController {

  // the polarity detector
  val detectPolarity: (String, Stream[Entry]) => String = detectPolarityPriv

  // the entries loader
  val loadEntries : (String) => Stream[Entry] = loadEntriesPriv


  private def detectPolarityPriv(phrase: String, entries: Stream[Entry]): String = {
    val polarity = findPhrasePolarity(textCleanupAndSplit(phrase), entries)

    if(polarity > 0) "Positive"
    else if (polarity < 0) "Negative"
    else "Neutral"
  }

  private def findPhrasePolarity(phrase: List[String], entries: Stream[Entry]): Double ={
    val polarities = for(word <- phrase)
      yield findWordPolarity(word, entries)

    polarities.sum / polarities.count(_ != 0)
  }

  private def findWordPolarity(word: String, entries: Stream[Entry]): Double = {
    // TODO: change this if parsing behaviour changes
    /*// assuming there are no duplicates and average has already been performed
    val entry = entries.find(_.word == word).get
    if(entry == null)
      0
    else
      entry.positiveScore - entry.negativeScore
    */

    // Processing all entries and calculating average
    val duplicatedEntryValues = (entries collect {
      case i:Entry if (i.word.equalsIgnoreCase(word)) => i
    })
      .map(x => x.positiveScore - x.negativeScore)

    if(duplicatedEntryValues.isEmpty)
      0
    else
      duplicatedEntryValues.sum / duplicatedEntryValues.size
  }

  // Loads and parses all the entries in the SentiWordNet file
  // returns: a list of the parsed entries
  private def loadEntriesPriv(filePath: String): Stream[Entry] = {
    parseEntries(readFromFile(filePath))
  }

  // Gets the lines from SentiWordNet file
  private def readFromFile(filePath : String): Stream[String] = {
    Source.fromFile(filePath).getLines.toStream
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

  // Removes the duplicated entries and calculates and average for affected entries
  // returns: a list of entries without duplicated entries
  private def removeDuplicates (entries: Stream[Entry]) : Stream[Entry] = {
    // TODO: remove duplicated values when loading entries
    // TODO: calculate average
    null
  }
}
