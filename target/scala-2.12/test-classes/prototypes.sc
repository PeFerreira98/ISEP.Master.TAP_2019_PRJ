import domain.Entry

import scala.io.Source

// testing stuff
val source = Source.fromFile("C:\\Users\\rmvieira\\Documents\\TAP\\1140953_1140956_1141233_a_2019_tap_ncf\\SentiWordNet_3.0.0_20130122.txt")
val contentList = source.getLines.toList


val result = contentList.filter(_.startsWith("a")).map(_.split("\t").map(_.trim))


val word = result(0)(4).substring(0, result(0)(4).indexOf("#"))
val entry0 = Entry(word, result(0)(2).toDouble, result(0)(3).toDouble)

val resultFinal = for (line <- result) yield Entry(line(4).substring(0, line(4).indexOf("#")), line(2).toDouble, line(3).toDouble)

source.close

def loadEntries(): Stream[Entry] = {
  for (line
         <-
         Source.fromFile("C:\\Users\\rmvieira\\Documents\\TAP\\1140953_1140956_1141233_a_2019_tap_ncf\\SentiWordNet_3.0.0_20130122.txt")
           .getLines.toStream
           .filter(_.startsWith("a"))
           .map(_.split("\t")
             .map(_.trim)))
    yield Entry(
      line(4).substring(0, line(4).indexOf("#")),
      line(2).toDouble,
      line(3).toDouble)
}

val total = loadEntries()

