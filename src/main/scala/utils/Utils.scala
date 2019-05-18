package utils

import domain.{Class, Class1, Class2, Class3, Class4, Class5, Class6}

object Utils {

  val textCleanupAndSplit : String => List[String] = textCleanupAndSplitPriv
  val getAircraftDelay: (Class, Class) => Integer = getAircraftDelayPriv
  val getDelayPenaltyCost: (Class, Integer) => Integer = getDelayPenaltyCostPriv

  /**
    * Cleans the text from special characters and splits it into a list of words
    * @param text The text to be cleaned
    * @return split text without special characters
    */
  private def textCleanupAndSplitPriv(text: String) : List[String] = {
    text.replaceAll("[^a-zA-z_ ]", "")
      .replaceAll(" {2,}", " ")
      .split(" ").map(_.trim).toList
  }

  /**
    * Gets the minimum delay time that must occur between operations according to
    * the problem's "The Separation of Operations" table.
    * @param leading The leading aircraft's class
    * @param trailing The trailing aircraft's class
    * @return The minimum delay time between leading and trailing operations.
    */
  private def getAircraftDelayPriv(leading: Class, trailing: Class) : Integer = {
    (leading, trailing) match {
      case (Class1, Class1) => 82
      case (Class1, Class2) => 69
      case (Class1, Class3) => 60
      case (Class1, _) => 75

      case (Class2, Class1) => 131
      case (Class2, Class2) => 69
      case (Class2, Class3) => 60
      case (Class2, _) => 75

      case (Class3, Class1) => 196
      case (Class3, Class2) => 157
      case (Class3, Class3) => 96
      case (Class3, _) => 75

      case (Class6, Class4) => 120
      case (Class6, Class5) => 120
      case (Class6, Class6) => 90
      case (_, _) => 60

    }
  }

  /**
    * Calculates the Penalty Cost according to the operation type and delay time
    * @param operation The operation type
    * @param delay The delay time
    * @return The calculated penalty cost
    */
  private def getDelayPenaltyCostPriv(operation: Class, delay: Integer): Integer = {
    delay match {
      // only positive delays have penalty cost
      case penalty if penalty > 0 =>
        operation match {
          // Landings 2 units/delay time
          case Class1 | Class2 | Class3 => penalty * 2
          // Take-offs 1 units/delay time
          case Class4 | Class5 | Class6 => penalty
        }
      case _ => 0
    }
  }
}
