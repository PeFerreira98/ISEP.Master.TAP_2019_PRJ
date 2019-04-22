//import domain.{Aircraft, Runway, Schedule}
//import domain._

sealed trait Class

case object Class1 extends Class
case object Class2 extends Class
case object Class3 extends Class
case object Class4 extends Class
case object Class5 extends Class
case object Class6 extends Class

case class Aircraft (number: Integer, target: Integer, classe: Class)
case class Runway(number: Integer, classes: Seq[Class])
case class Schedule (aircraft: Aircraft, time: Integer, runway: Runway)
object Utils {

  val textCleanupAndSplit : String => List[String] = textCleanupAndSplitPriv
  val getAircraftDelay: (Class, Class) => Integer = getAircraftDelayPriv

  // cleans the text from special characters and splits it into a list of words
  private def textCleanupAndSplitPriv(text: String) : List[String] = {
    text.replaceAll("[^a-zA-z_ ]", "")
      .replaceAll(" {2,}", " ")
      .split(" ").map(_.trim).toList
  }

  private def getAircraftDelayPriv(leading: Class, trailing: Class) : Integer = {
    (leading, trailing) match {
      case (Class1, Class1) => 82
      case (Class1, Class2) => 69
      case (Class1, Class3) => 60
      case (Class1, Class4) => 75
      case (Class1, Class5) => 75
      case (Class1, Class6) => 75

      case (Class2, Class1) => 131
      case (Class2, Class2) => 69
      case (Class2, Class3) => 60
      case (Class2, Class4) => 75
      case (Class2, Class5) => 75
      case (Class2, Class6) => 75

      case (Class3, Class1) => 196
      case (Class3, Class2) => 157
      case (Class3, Class3) => 96
      case (Class3, Class4) => 75
      case (Class3, Class5) => 75
      case (Class3, Class6) => 75

      case (Class4, Class1) => 60
      case (Class4, Class2) => 60
      case (Class4, Class3) => 60
      case (Class4, Class4) => 60
      case (Class4, Class5) => 60
      case (Class4, Class6) => 60

      case (Class5, Class1) => 60
      case (Class5, Class2) => 60
      case (Class5, Class3) => 60
      case (Class5, Class4) => 60
      case (Class5, Class5) => 60
      case (Class5, Class6) => 60

      case (Class6, Class1) => 60
      case (Class6, Class2) => 60
      case (Class6, Class3) => 60
      case (Class6, Class4) => 120
      case (Class6, Class5) => 120
      case (Class6, Class6) => 90
    }
  }
}


case class Agenda (maxDelayTime: Integer , aircraftList: Seq[Aircraft], runwayList: Seq[Runway]) {

  val maximumDelayTime: Integer = maxDelayTime
  val aircrafts: Seq[Aircraft] = aircraftList.sortBy(a => a.target)
  val runways: Seq[Runway] = runwayList

  def schedule: Option[Seq[Schedule]] = {
    createSchedule(aircrafts, List())
  }

  private def getBestRunwayMatch(list : List[Schedule], a : Aircraft) : Schedule = {
    //Getting a list grouped by runways and for each runway will get the time of the last aircraft.
    //This will be helpful to find what's was the latest flight for each runway
    /*val bestMatch =
    list.groupBy(_.runway)
      .filter(r => r._1.classes.contains(a.classe))
      .map(r => r._2.max(Ordering.by((s:Schedule) => s.time)))*/

    //Complete Separation
    //Getting a list grouped by runways and for each runway will get the correct available time for the aircraft @a
    val bestMatch_v2 = list.groupBy(s => s.runway)
      .filter(s => s._1.classes.contains(a.classe))
      .map(e => e._2.max(Ordering.by((s :Schedule) => Utils.getAircraftDelay(s.aircraft.classe, a.classe) + s.time)))

    //Checking if isn't runways that supports aircraft's class
    if(bestMatch_v2 == null || bestMatch_v2.isEmpty)
    {
      null
    }
    else{
      //It will find the best time of all latest runway's action, taking in consideration the delay for each one
      //val s = bestMatch.min(Ordering.by((s:Schedule) => s.time + Utils.getAircraftDelay(s.aircraft.classe, a.classe)))
      val s2 = bestMatch_v2.min(Ordering.by((s:Schedule) => s.time + Utils.getAircraftDelay(s.aircraft.classe, a.classe)))

      if(s2.time + Utils.getAircraftDelay(s2.aircraft.classe, a.classe) < a.target)
      {
        Schedule(a, a.target , s2.runway)
      }
      else
      {
        Schedule(a, s2.time + Utils.getAircraftDelay(s2.aircraft.classe, a.classe) , s2.runway)
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

val a1 = Aircraft(1,0, Class5)
val a2 = Aircraft(2,13, Class2)
val a3 = Aircraft(3,104, Class3)
val a4 = Aircraft(4,139, Class1)
val a5 = Aircraft(5,151, Class2)

//Test for Complete separation
val aa1 = Aircraft(1,0, Class3)
val aa2 = Aircraft(2,13, Class4)
val aa3 = Aircraft(3,104, Class1)

val r1 = Runway(1, List(Class1,Class2,Class3,Class4,Class5, Class6))
val r2 = Runway(2, List(Class1, Class2, Class4, Class5))
val r3 = Runway(3, List(Class2, Class3))

var lst_aircrafts = Seq(aa1,aa2,aa3)

var lst_runways =  Seq(r1)

val agenda = Agenda(900, lst_aircrafts, lst_runways).schedule
