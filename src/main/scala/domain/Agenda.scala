package domain

class Agenda (maxDelayTime: Integer , aircraftList: Seq[Aircraft], runwayList: Seq[Runway]) {

  val maximumDelayTime = maxDelayTime
  val aircrafts = aircraftList
  val runways = runwayList

  def schedule/*: Option[Seq[Schedule]]*/ = {
    ???
    val lstres : Seq[Schedule] = Seq()
    aircrafts.sortBy(a => a.target)

    /*for (a <- aircrafts)
      runways.filter(r=> r.classes.contains(a.classe) && lstres.filter(s=>s.runway == r ).max() <= a.target)
*/
  }

  def circular[A](l: List[A]) : List[A] = {
    l.headOption.map(h =>  l.tail :+ h).getOrElse(List())
  }
}