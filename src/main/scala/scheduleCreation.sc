//import domain.{Aircraft, Runway, Schedule}
//import domain._

sealed trait Class

case object Class1 extends Class
case object Class2 extends Class
case object Class3 extends Class
case object Class4 extends Class
case object Class5 extends Class
case object Class6 extends Class

case class Aircraft (number: Integer, target: Integer, classe: Class, emergency: Option[Integer])
case class Runway(number: Integer, classes: Seq[Class])
case class Schedule (aircraft: Aircraft, time: Integer, runway: Runway, penalty: Integer)

object Utils {

  val textCleanupAndSplit : String => List[String] = textCleanupAndSplitPriv
  val getAircraftDelay: (Class, Class) => Integer = getAircraftDelayPriv
  val getDelayPenaltyCost: (Class, Integer) => Integer = getDelayPenaltyCostPriv

  /**
    * Cleans the text from special characters and splits it into a list of words
    * @param text The text to be cleaned
    * @return split text without special characters
    */
  private def textCleanupAndSplitPriv(text: String) : List[String] = {
    text.replaceAll("[^a-zA-z_ ]", "")
      .replaceAll(" {2,}", " ")
      .split(" ").map(_.trim).toList
  }

  /**
    * Gets the minimum delay time that must occur between operations according to
    * the problem's "The Separation of Operations" table.
    * @param leading The leading aircraft's class
    * @param trailing The trailing aircraft's class
    * @return The minimum delay time between leading and trailing operations.
    */
  private def getAircraftDelayPriv(leading: Class, trailing: Class) : Integer = {
    (leading, trailing) match {
      case (Class1, Class1) => 82
      case (Class1, Class2) => 69
      case (Class1, Class3) => 60
      case (Class1, _) => 75

      case (Class2, Class1) => 131
      case (Class2, Class2) => 69
      case (Class2, Class3) => 60
      case (Class2, _) => 75

      case (Class3, Class1) => 196
      case (Class3, Class2) => 157
      case (Class3, Class3) => 96
      case (Class3, _) => 75

      case (Class6, Class4) => 120
      case (Class6, Class5) => 120
      case (Class6, Class6) => 90
      case (_, _) => 60

    }
  }

  /**
    * Calculates the Penalty Cost according to the operation type and delay time
    * @param operation The operation type
    * @param delay The delay time
    * @return The calculated penalty cost
    */
  private def getDelayPenaltyCostPriv(operation: Class, delay: Integer): Integer = {
    delay match {
      // only positive delays have penalty cost
      case penalty if penalty > 0 =>
        operation match {
          // Landings 2 units/delay time
          case Class1 | Class2 | Class3 => penalty * 2
          // Take-offs 1 units/delay time
          case Class4 | Class5 | Class6 => penalty
        }
      case _ => 0
    }
  }
}
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
          //We don't want to check maximumDelay since if it is an emergency aircraft and can't land on time, since this planes can be swapped
        case res if (a.target + maximumDelayTime < res.time && a.emergency.isEmpty)
          || (a.target + maximumDelayTime < res.time && a.emergency.isDefined && res.time <= a.emergency.get + a.target) =>
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
              //Getting the last schedule of each runway
              //Since we don't want to remove the aircrafts that landed with emergency, it is necessary to remove them from the search
              val lst_Available_schedules =
                  scheduleList.groupBy(_.runway)
                    .map(e => e._2.max(Ordering.by((s: Schedule) =>s.time)))
                      .filter(s => !s.aircraft.emergency.isDefined)

              lst_Available_schedules.toList.length match {
                case 0 =>
                  println("Impossible to find a solution")
                  None
                case x if x > 0 =>
                  val scheduleItem_toRemove = lst_Available_schedules.max(Ordering.by((ss : Schedule) => ss.time))
                  val new_lst_aircrafts_tmp = lst_aircrafts_tmp.toList ::: List(scheduleItem_toRemove.aircraft)
                  val lstAux = scheduleList.filter(s => (s.runway.number != scheduleItem_toRemove.runway.number && s.time != scheduleItem_toRemove.time) || (s.runway.number == scheduleItem_toRemove.runway.number && s.time != scheduleItem_toRemove.time))
                  createSchedule(aircrafts, lstAux, new_lst_aircrafts_tmp)
              }
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

val a1 = Aircraft(1,0, Class5, None)
val a2 = Aircraft(2,13, Class2, None)
val a3 = Aircraft(3,104, Class3, None)
val a7 = Aircraft(7,130, Class3, None)
val a4 = Aircraft(4,139, Class1, None)
val a6 = Aircraft(6,151, Class2, Option(1))
val a5 = Aircraft(5,155, Class2, Option(1))

//Test for Complete separation
val aa1 = Aircraft(1,0, Class3, Option(0))
val aa2 = Aircraft(2,13, Class4,Option(0))
val aa3 = Aircraft(3,104, Class1,Option(0))

val r1 = Runway(1, List(Class1,Class2,Class3,Class4,Class5, Class6))
val r2 = Runway(2, List(Class1, Class2, Class4, Class5))

var lst_aircrafts = Seq(a1,a2,a7,a3,a4,a6,a5)

var lst_runways =  Seq(r1,r2)

val agenda = Agenda(900, lst_aircrafts, lst_runways).schedule
