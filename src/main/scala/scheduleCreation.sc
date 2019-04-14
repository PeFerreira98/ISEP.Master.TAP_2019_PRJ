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

  val maximumDelayTime = maxDelayTime
  val aircrafts = aircraftList
  val runways = runwayList

  private def getBestRunwayMatch(list : List[Schedule], a : Aircraft) : Schedule = {
    val bestMatch =
      list.groupBy(_.runway)
        .filter(r => r._1.classes.contains(a.classe))
        .map(r => r._2.max(Ordering.by((s:Schedule) => s.time)))

    //Check if isn't runways with aircraft class
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
        println("--> ERROR - Doesn't exist none runways that support plane " + a.number + " class <--")
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

val a1 = Aircraft(1,0, Class5)
val a2 = Aircraft(2,13, Class2)
val a3 = Aircraft(3,104, Class3)
val a4 = Aircraft(4,139, Class1)
val a5 = Aircraft(5,151, Class2)

val r1 = Runway(1, List(Class1,Class2,Class3,Class4,Class5, Class6))
val r2 = Runway(2, List(Class1, Class2, Class4, Class5))
val r3 = Runway(3, List(Class2, Class3))

var lst_aircrafts = Seq(a1,a2,a3,a4,a5)
var lst_runways = Seq()

val lst_test = List(1,2,4)
//lst_runways.filter((r => r.classes.contains(a1.classe)))

val agenda = Agenda(900, lst_aircrafts, lst_runways).createSchedule(lst_aircrafts, List())