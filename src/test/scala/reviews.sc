import domain.Entry
import controller.PolarityDetectionController._

import scala.io.Source
import java.io.File

val projectPath = "C:\\Users\\rmvieira\\Documents\\TAP\\1140953_1140956_1141233_a_2019_tap_ncf\\"
//val projectPath = "C:\\Users\\pefer\\Desktop\\TAP\\1140953_1140956_1141233_a_2019_tap_ncf\\"
val sentiword = projectPath + "resources\\SentiWordNet_3.0.0_20130122.txt"
val folderPath = projectPath + "resources\\review_polarity\\txt_sentoken\\pos\\"


def listFiles(dir:String ) : List[String] = {
  val file = new File(dir)
  file.listFiles.filter(_.isFile)
    .filter(_.getName.endsWith(".txt"))
    .map(_.getName).toList
}

listFiles(folderPath)

val entries = loadEntries(sentiword)

val x = for(file <- listFiles(folderPath)) {
  val source = Source.fromFile(folderPath + file)

  val text = source.getLines().toStream.mkString(" ")
  val pol = detectPolarity(text, entries)

  println(pol)

  source.close()
}