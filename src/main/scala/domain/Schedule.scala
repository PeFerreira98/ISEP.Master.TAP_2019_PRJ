package domain

/**
  * Entity representing one schedule entry.
  * @param aircraft The aircraft scheduled.
  * @param time The real time of the operation.
  * @param runway The runway.
  * @param penalty The penalty cost for the operation.
  */
case class Schedule (aircraft: Aircraft, time: Integer, runway: Runway, penalty: Integer)
