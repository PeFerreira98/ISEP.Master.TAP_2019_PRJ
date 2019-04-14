package domain
import utils.Utils

case class Agenda (maxDelayTime: Integer , aircraftList: Seq[Aircraft], runwayList: Seq[Runway]) {

  val maximumDelayTime = maxDelayTime
  val aircrafts = aircraftList
  val runways = runwayList

  private def getBestRunwayMatch(list : List[Schedule], a : Aircraft) : Schedule = {
    val bestMatch =
      list.groupBy(_.runway)
        .filter(r => r._1.classes.contains(a.classe))
        .map(r => r._2.max(Ordering.by((s:Schedule) => s.time)))

    if(bestMatch == null || bestMatch.size == 0)
    {
      null
    }
    else{
      val s = bestMatch.min(Ordering.by((s:Schedule) => s.time + Utils.getAircraftDelay(s.aircraft.classe, a.classe)))

      if(s.time + Utils.getAircraftDelay(s.aircraft.classe, a.classe) < a.target)
      {
        new Schedule(a, a.target , s.runway)
      }
      else
      {
        new Schedule(a, s.time + Utils.getAircraftDelay(s.aircraft.classe, a.classe) , s.runway)
      }
    }
  }

  private def getFreeSchedule(runways : Seq[Runway], list : List[Schedule],a : Aircraft) : Schedule = {
    val freeRunways =
      (list.groupBy(_.runway).map(s=> s._1).toList.diff(runways) ::: runways.diff(list.groupBy(_.runway).map(s=> s._1).toList).toList).filter(r => r.classes.contains(a.classe))

    if(freeRunways.size == 0)
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
      new Schedule(a, a.target, freeRunways.headOption.get)
    }
  }

  def createSchedule(aircrafts: Seq[Aircraft], scheduleList: List[Schedule]) : Seq[Schedule] = {
    val a = aircrafts.headOption.getOrElse(null)

    if(a == null){
      scheduleList
    }
    else
    {
      val tail = aircrafts.tail
      val schedule_item = getFreeSchedule(runways, scheduleList,a)

      if(schedule_item == null)
      {
        List()
      }
      else
      {
        val lstAux : List[Schedule]= scheduleList ::: List(schedule_item)

        createSchedule(tail,lstAux)
      }
    }
  }
}
