import domain.Entry
import controller.PolarityDetectionController
import controller.PolarityDetectionController.detectPolarity
import controller.PolarityDetectionController.loadEntries
import utils.Utils.textCleanupAndSplit

import scala.io.Source

// Prototype functions

def findWordPolarity(word: String, entries: List[Entry]): Double = {
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
    case i:Entry if (i.word == word) => i
  })
    .map(x => x.positiveScore - x.negativeScore)

  if(duplicatedEntryValues.isEmpty)
    0
  else
    duplicatedEntryValues.sum / duplicatedEntryValues.size
}

 def findPhrasePolarity(phrase: List[String], entries: List[Entry]): Double ={
  val polarities = for(word <- phrase)
    yield findWordPolarity(word, entries)

  polarities.sum / polarities.count(_ != 0)
}

// Parses the raw file lines
// returns: a list of the parsed entries
def parseEntries(lines:List[String]): List[Entry] = {
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


// cleans the text from special characters and splits it into a list of words
def textCleanupAndSplit(text: String) : List[String] = {
  text.replaceAll("[^a-zA-z_ ]", "")
    .replaceAll(" {2,}", " ")
    .split(" ").map(_.trim).toList
}

// Gets the lines from SentiWordNet file
def readFromFile(fileName: String): List[String] = {
  Source.fromFile(fileName).getLines.toList
}

// Loads and parses all the entries in the SentiWordNet file
// returns: a list of the parsed entries
 def loadEntries(): List[Entry] = {
  val fileName = "C:\\Users\\rmvieira\\Documents\\TAP\\1140953_1140956_1141233_a_2019_tap_ncf\\resources\\SentiWordNet_3.0.0_20130122.txt"
  //val fileName = "C:\\Users\\pefer\\Desktop\\TAP\\1140953_1140956_1141233_a_2019_tap_ncf\\resources\\SentiWordNet_3.0.0_20130122.txt"
  parseEntries(readFromFile(fileName))
}

def detectPolarity(phrase: String, entries: List[Entry]): String = {
  val polarity = findPhrasePolarity(textCleanupAndSplit(phrase), entries)

  if(polarity > 0) "Positive"
  else if (polarity < 0) "Negative"
  else "Neutral"
}

private def removeDuplicates (entries: List[Entry]) : List[Entry] = {
  // TODO: remove duplicated values when loading entries
  // TODO: calculate average
  /*(for(x <- entries)
    yield {
      entries collect {
        case i: Entry if (i.word == x.word) => i
      }
    }).map(findWordPolarity(_, entries))

   */null
}

// testing stuff

val allEntries = loadEntries().sortBy(_.word)


// find entries for polarity calculation
val unique = allEntries.find(_.word == "able")

/*
for(x <- allEntries)
  yield {
    allEntries collect {
      case i: Entry if (i.word == x.word) => i
    }
  }
*/

val duplicates = allEntries collect {
  case i: Entry if (i.word == "good") => i
}

val duplicateValues = duplicates.map(x => x.positiveScore - x.negativeScore)
val count = duplicates.size
val goodPolarity = duplicateValues.sum / duplicates.size


val input = "a_priori this abandoned stuff is good!"
val inputX = "aA09 <> ,;.:-_ ~^ºª !\"#$%&/\\|()=?' «»€@£§{[]}"

val inputXl = textCleanupAndSplit(inputX)

val exists = allEntries.find(_.word == "good").exists(_ != null)


val goodPol = findWordPolarity("good", allEntries)
val inputPol = findPhrasePolarity(textCleanupAndSplit(input), allEntries)


//val filepaths = "C:\\Users\\pefer\\Desktop\\TAP\\1140953_1140956_1141233_a_2019_tap_ncf\\resources\\SentiWordNet_3.0.0_20130122.txt"
//val stuff = PolarityDetectionController.loadEntries(filepaths)

//val stuff2 = PolarityDetectionController.detectPolarity(input, stuff)

textCleanupAndSplit("")
  .map(word => if (word.isEmpty) 0 else
    if(word.matches("[a-zA-Z_]+"))
      0 else 1
  ).sum
