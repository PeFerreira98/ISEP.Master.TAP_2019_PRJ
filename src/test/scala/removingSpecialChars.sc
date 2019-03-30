import domain.Entry

import scala.io.Source

def removeSpecialChars(input:String) : List[String] ={
  val res = input.replaceAll("[^a-zA-z ]", "").replaceAll(" {2,}", " ")

  res.split(" ").map(_.trim).toList
}

removeSpecialChars("Tap isn't  .  bad!!! but at least is not ALGAV :D")

// TODO: Houston we have a problem... -> ^ \ [ ] chars get through
val inputX = "aA09 <> ,;.:-_ ~^ºª !\"#$%&/\\|()=?' «»€@£§{[]}"
removeSpecialChars(inputX)