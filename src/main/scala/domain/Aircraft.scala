package domain

/**
  * Entity representing one aircraft.
  * @param number The aircraft's number.
  * @param target The aircraft's operation target time.
  * @param classe The aircraft's class.
  * @param emergency The [optional] emergency time limit.
  */
case class Aircraft (number: Integer, target: Integer, classe: Class, emergency: Option[Integer])