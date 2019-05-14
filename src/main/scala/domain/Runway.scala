package domain

/**
  * Entity representing one runway.
  * @param number The runway's number.
  * @param classes The runway's supported classes collection.
  */
case class Runway(number: Integer, classes: Seq[Class])
