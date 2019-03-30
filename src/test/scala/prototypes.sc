import domain.Entry

import io.Source

// Prototype functions

def loadEntries(): List[Entry] = {
  val fileName = "C:\\Users\\rmvieira\\Documents\\TAP\\1140953_1140956_1141233_a_2019_tap_ncf\\resources\\SentiWordNet_3.0.0_20130122.txt"
  //val fileName = "..\\resources\\SentiWordNet_3.0.0_20130122.txt"

  for (line
       <-
       Source.fromFile(fileName).getLines().toList
         .filter(_.startsWith("a"))
         .map(_.split("\t")
           .map(_.trim)))
    yield Entry(
      line(4).substring(0, line(4).indexOf("#")),
      line(2).toDouble,
      line(3).toDouble)
}

def removeSpecialChars(input:String) : List[String] ={
  input.replaceAll("[^a-zA-z ]", "")
    .replaceAll(" +", " ")
    .split(" ")
    .map(_.trim)
    .toList
}

private def findPhrasePolarity(input: List[String], entries: Stream[Entry]): Double ={
  // TODO: implement feature here!!!
  //input.map(findWordPolarity(_, entries)).sum / input.size
  0
}

private def findWordPolarity(word: String, entries: Stream[Entry]): Double = {
  0
}



// testing stuff

val total = loadEntries().sortBy(_.word)


// find entries for polarity calculation
val unique = total.find(_.word == "able")

val duplicates = total collect {
  case i:Entry if (i.word == "good") => i
}

val duplicateValues = duplicates.map(x => x.positiveScore - x.negativeScore)
val count = duplicates.size
val goodPolarity = duplicateValues.sum / duplicates.size


val input = "a_priori this is good!"
val inputX = "aA09 <> ,;.:-_ ~^ºª !\"#$%&/\\|()=?' «»€@£§{[]}"

val inputXl = removeSpecialChars(input)

val exists = total.find(_.word == "good").exists(_ != null)


// TODO: THIS ACCTUALLY WORDKS!
val matchValues = (for (word <- inputXl if total.find(_.word == word).exists(_ != null))
  yield {
    val entry = total.find(_.word == word).get
    entry.positiveScore - entry.negativeScore
  })



val matchValues2 = for (word <- inputXl if total.find(_.word == word).exists(_ != null))
  yield {
    val entries = (total collect {
      case i:Entry if (i.word == word) => i
    }).map(x => x.positiveScore - x.negativeScore)
  }

val sum1 = matchValues.sum / matchValues.size

