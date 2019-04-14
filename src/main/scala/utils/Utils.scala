package utils

import domain.{Class, Class1, Class2, Class3, Class4, Class5, Class6}

object Utils {

  val textCleanupAndSplit : String => List[String] = textCleanupAndSplitPriv
  val getAircraftDelay: (Class, Class) => Integer = getAircraftDelayPriv

  // cleans the text from special characters and splits it into a list of words
  private def textCleanupAndSplitPriv(text: String) : List[String] = {
    text.replaceAll("[^a-zA-z_ ]", "")
      .replaceAll(" {2,}", " ")
      .split(" ").map(_.trim).toList
  }

  // Gets the delay time between operations according to
  // the problem's "The Separation of Operations" table.
  private def getAircraftDelayPriv(leading: Class, trailing: Class) : Integer = {
    (leading, trailing) match {
      case (Class1, Class1) => 82
      case (Class1, Class2) => 69
      case (Class1, Class3) => 60
      case (Class1, Class4) => 75
      case (Class1, Class5) => 75
      case (Class1, Class6) => 75

      case (Class2, Class1) => 131
      case (Class2, Class2) => 69
      case (Class2, Class3) => 60
      case (Class2, Class4) => 75
      case (Class2, Class5) => 75
      case (Class2, Class6) => 75

      case (Class3, Class1) => 196
      case (Class3, Class2) => 157
      case (Class3, Class3) => 96
      case (Class3, Class4) => 75
      case (Class3, Class5) => 75
      case (Class3, Class6) => 75

      case (Class4, Class1) => 60
      case (Class4, Class2) => 60
      case (Class4, Class3) => 60
      case (Class4, Class4) => 60
      case (Class4, Class5) => 60
      case (Class4, Class6) => 60

      case (Class5, Class1) => 60
      case (Class5, Class2) => 60
      case (Class5, Class3) => 60
      case (Class5, Class4) => 60
      case (Class5, Class5) => 60
      case (Class5, Class6) => 60

      case (Class6, Class1) => 60
      case (Class6, Class2) => 60
      case (Class6, Class3) => 60
      case (Class6, Class4) => 120
      case (Class6, Class5) => 120
      case (Class6, Class6) => 90
    }
  }
}
