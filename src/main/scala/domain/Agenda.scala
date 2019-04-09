package domain

class Agenda (maximumDelayTime: Integer , aircrafts: Seq[Aircraft], runways: Seq[Runway]) {

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
    //l.tail ::: l.head :: Nil
    //l.tail :+ l.head
    l.headOption.map(h =>  l.tail :+ h).getOrElse(List())
  }
}