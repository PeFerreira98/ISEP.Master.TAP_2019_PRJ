package domain
import utils.Utils

case class Agenda (maxDelayTime: Integer , aircraftList: Seq[Aircraft], runwayList: Seq[Runway]) {

  val maximumDelayTime: Integer = maxDelayTime
  val aircrafts: Seq[Aircraft] = aircraftList
  val runways: Seq[Runway] = runwayList

  def schedule: Option[Seq[Schedule]] = {
    createSchedule(aircrafts, List())
  }

  private def getBestRunwayMatch(list : List[Schedule], a : Aircraft) : Schedule = {
    val bestMatch =
      list.groupBy(_.runway)
        .filter(r => r._1.classes.contains(a.classe))
        .map(r => r._2.max(Ordering.by((s:Schedule) => s.time)))

    if(bestMatch == null || bestMatch.isEmpty)
    {
      null
    }
    else{
      val s = bestMatch.min(Ordering.by((s:Schedule) => s.time + Utils.getAircraftDelay(s.aircraft.classe, a.classe)))

      if(s.time + Utils.getAircraftDelay(s.aircraft.classe, a.classe) < a.target)
      {
        Schedule(a, a.target , s.runway)
      }
      else
      {
        Schedule(a, s.time + Utils.getAircraftDelay(s.aircraft.classe, a.classe) , s.runway)
      }
    }
  }

  private def getFreeSchedule(runways : Seq[Runway], list : List[Schedule],a : Aircraft) : Schedule = {
    val freeRunways =
      (list.groupBy(_.runway).map(s=> s._1).toList.diff(runways) ::: runways.diff(list.groupBy(_.runway).map(s=> s._1).toList).toList).filter(r => r.classes.contains(a.classe))

    if(freeRunways.isEmpty)
    {
      val res = getBestRunwayMatch(list, a)

      if(res == null){
        println("--> ERROR - Doesn't exist runways that support plane " + a.number + " class <--")
        null
      }
      else if(a.target + maximumDelayTime < res.time)
      {
        println("--> WARNING - Maximum delay time has been reached for plane " + a.number + " <--")
        null
      }
      else
      {
        res
      }
    }
    else
    {
      Schedule(a, a.target, freeRunways.headOption.get)
    }
  }

  private def createSchedule(aircrafts: Seq[Aircraft], scheduleList: List[Schedule]) : Option[Seq[Schedule]] = {
    val a = aircrafts.headOption.getOrElse(null)

    if(a == null){
      Option(scheduleList)
    }
    else
    {
      val tail = aircrafts.tail
      val schedule_item = getFreeSchedule(runways, scheduleList,a)

      if(schedule_item == null) None
      else
      {
        val lstAux : List[Schedule]= scheduleList ::: List(schedule_item)

        createSchedule(tail,lstAux)
      }
    }
  }
}
