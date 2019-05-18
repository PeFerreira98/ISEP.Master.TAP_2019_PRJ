package domain
import utils.Utils

/**
  * Entity representing one Agenda
  * @param maxDelayTime The maximum delay time for each operation.
  * @param aircraftList The list of aircrafts to be scheduled.
  * @param runwayList The list of available runways.
  */

case class Agenda (maxDelayTime: Integer , aircraftList: Seq[Aircraft], runwayList: Seq[Runway]) {

  val maximumDelayTime: Integer = maxDelayTime
  val aircrafts: Seq[Aircraft] = aircraftList.sortBy(a => a.target)
  val runways: Seq[Runway] = runwayList

  /**
    * Schedules the aircrafts operations, respecting maximum delay time and emergency
    * @return [Optional] list of schedules.
    */
  def schedule: Option[Seq[Schedule]] = {
    createSchedule(aircrafts, List(), List())
  }

  private def getBestRunwayMatch(list : List[Schedule], a : Aircraft) : Schedule = {
    //Getting a list grouped by runways and for each runway will get the time of the last aircraft.
    //This will be helpful to find what's was the latest flight for each runway
    //Complete Separation
    //Getting a list grouped by runways and for each runway will get the correct available time for the aircraft @a
    val bestMatch_v2 = list.groupBy(s => s.runway)
      .filter(s => s._1.classes.contains(a.classe))
      .map(e => e._2.max(Ordering.by((s: Schedule) =>
        Utils.getAircraftDelay(s.aircraft.classe, a.classe) + s.time
      )))

    //Checking if isn't runways that supports aircraft's class
    if(bestMatch_v2 == null || bestMatch_v2.isEmpty) {
      null
    }
    else {
      //It will find the lowest penaly cost of all latest runway's action, taking in consideration the delay for each one
      val s2 = bestMatch_v2.min(Ordering.by((s: Schedule) =>
        Utils.getDelayPenaltyCost(a.classe, s.time + Utils.getAircraftDelay(s.aircraft.classe, a.classe) - a.target)
      ))

      if(s2.time + Utils.getAircraftDelay(s2.aircraft.classe, a.classe) <= a.target) {
        // no penalty, everything on time
        Schedule(a, a.target , s2.runway, 0)
      }
      else {
        // schedule with penalty
        val time = s2.time + Utils.getAircraftDelay(s2.aircraft.classe, a.classe)
        Schedule(a, time , s2.runway, Utils.getDelayPenaltyCost(a.classe, time - a.target))
      }
    }
  }

  private def getFreeSchedule(runways : Seq[Runway], list : List[Schedule], a: Aircraft) : Schedule = {
    //List with runways that never had aircrafts. This helps to add schedules to runways that hadn't have been used since the operations are always with used runways
    val freeRunways =
      (list.groupBy(_.runway).keys.toList.diff(runways) ::: runways.diff(list.groupBy(_.runway).keys.toList).toList).filter(r => r.classes.contains(a.classe))

    if(freeRunways.isEmpty) {
      //Getting the best runway
      getBestRunwayMatch(list, a) match {
        case null =>
          println("--> ERROR - Doesn't exist runways that support plane " + a.number + " class <--")
          null
        case res if a.target + maximumDelayTime < res.time =>
          println("--> WARNING - Maximum delay time has been reached for plane " + a.number + " <--")
          null
        case res => res
      }
    }
    else {
      //Adding a schedule with the first free runway since we know that exists free runways
      Schedule(a, a.target, freeRunways.headOption.get, 0)
    }
  }

  /**
    * Recursive method to create a schedule.
    * @param aircrafts Aircrafts list.
    * @param scheduleList It must be an empty list when starting as this will be the final result.
    * @return Sequence of schedules or None if maximum delay has been hit or if isn't runways that supports one of aircraft's classes
    */
  private def createSchedule(aircrafts: Seq[Aircraft], scheduleList: List[Schedule], lst_aircrafts_tmp : Seq[Aircraft]) : Option[Seq[Schedule]] = {
    //Getting the aircraft or null if the list is empty
    aircrafts.headOption.orNull match {
      //If can't get more aircrafts, it means it should stop because it hasn't more aircrafts to process
      case null => Option(scheduleList)
      case a: Aircraft =>
        //Calculating schedule item
        getFreeSchedule(runways, scheduleList, a) match {
          //If it's null that means an error occurred
          case null => None
          case scheduleItem =>
            if (scheduleItem.aircraft.emergency.isDefined && a.target + a.emergency.get < scheduleItem.time) {
              val scheduleItem_toRemove = scheduleList.filter(s => s.runway.number == scheduleItem.runway.number).max(Ordering.by((ss : Schedule) => ss.time))
              val new_lst_aircrafts_tmp = lst_aircrafts_tmp.toList ::: List(scheduleItem_toRemove.aircraft)
              val lstAux = scheduleList.filter(s => (s.runway.number != scheduleItem_toRemove.runway.number && s.time != scheduleItem_toRemove.time) || (s.runway.number == scheduleItem_toRemove.runway.number && s.time != scheduleItem_toRemove.time))
              createSchedule(aircrafts, lstAux, new_lst_aircrafts_tmp)
            }
            else {
              //We need to add the new element to the final list
              val lstAux : List[Schedule]= scheduleList ::: List(scheduleItem)
              //Recursive call without the processed aircraft
              val new_aircrafts = aircrafts.filter(a2 => a2.number != a.number).toList ::: lst_aircrafts_tmp.toList
              new_aircrafts.sortBy(a => a.target)
              createSchedule(new_aircrafts, lstAux, List())
            }
        }
    }
  }
}