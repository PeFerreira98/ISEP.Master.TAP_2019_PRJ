package utils

object Utils {

  val textCleanupAndSplit : String => List[String] = textCleanupAndSplitPriv

  // cleans the text from special characters and splits it into a list of words
  private def textCleanupAndSplitPriv(text: String) : List[String] = {
    text.replaceAll("[^a-zA-z_ ]", "")
      .replaceAll(" {2,}", " ")
      .split(" ").map(_.trim).toList
  }
}
