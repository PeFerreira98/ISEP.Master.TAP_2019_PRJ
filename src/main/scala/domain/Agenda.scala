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
    //Getting a list grouped by runways and for each runway will get the time of the last aircraft.
    //This will be helpful to find what's was the latest flight for each runway
    val bestMatch =
      list.groupBy(_.runway)
        .filter(r => r._1.classes.contains(a.classe))
        .map(r => r._2.max(Ordering.by((s:Schedule) => s.time)))

    //Checking if isn't runways that supports aircraft's class
    if(bestMatch == null || bestMatch.isEmpty)
    {
      null
    }
    else{
      //It will find the best time of all latest runway's action, taking in consideration the delay for each one
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
    //List with runways that never had aircrafts. This helps to add schedules to runways that hadn't have been used since the operations are always with used runways
    val freeRunways =
      (list.groupBy(_.runway).map(s=> s._1).toList.diff(runways) ::: runways.diff(list.groupBy(_.runway).map(s=> s._1).toList).toList).filter(r => r.classes.contains(a.classe))

    if(freeRunways.isEmpty)
    {
      //Getting the best runway
      val res = getBestRunwayMatch(list, a)

      if(res == null){
        println("--> ERROR - Doesn't exist runways that support plane " + a.number + " class <--")
        null
      }
      //Checking if the maximum delay time has been hitted
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
      //Adding a schedule with the first free runway since we know that exists free runways
      Schedule(a, a.target, freeRunways.headOption.get)
    }
  }

  /**
    * Recursive method to create a schedule.
    * @param aircrafts Aircraft's list.
    * @param scheduleList It must be an empty list. This will be the final result
    * @return Sequence of schedules or None if maximum delay has been hit or if isn't runways that supports one of aircraft's classes
    */
  private def createSchedule(aircrafts: Seq[Aircraft], scheduleList: List[Schedule]) : Option[Seq[Schedule]] = {
    //Getting the aircraft or null if the list is empty
    val a = aircrafts.headOption.getOrElse(null)

    //If can't get more aircrafts, it means it should stop because it hasn't more aircrafts to process
    if(a == null){
      Option(scheduleList)
    }
    else
    {
      //Create a list without the processed aircraft to get passed to the next recursive call
      val tail = aircrafts.tail
      //Calculating schedule item
      val schedule_item = getFreeSchedule(runways, scheduleList,a)

      //If it's null that means an error occurred
      if(schedule_item == null) None
      else
      {
        //We need to add the new element to the final list
        val lstAux : List[Schedule]= scheduleList ::: List(schedule_item)

        //Recursive call
        createSchedule(tail,lstAux)
      }
    }
  }
}
