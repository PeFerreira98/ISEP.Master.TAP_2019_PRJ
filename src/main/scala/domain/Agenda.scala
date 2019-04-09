package domain

class Agenda (maximumDelayTime: Integer , aircrafts: Seq[Aircraft], runways: Seq[Runway]) {

  def schedule: Option[Seq[Schedule]] = {
    ???
  }
}