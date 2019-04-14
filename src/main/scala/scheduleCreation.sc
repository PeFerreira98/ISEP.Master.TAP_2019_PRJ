//import domain.{Aircraft, Runway, Schedule}
//import utils.Utils

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

//TODO: Onde tem " + 10" nos tempos, necessário trocar pelo o valor da classe de cada avião
case class Agenda (maxDelayTime: Integer , aircraftList: Seq[Aircraft], runwayList: Seq[Runway]) {

  val maximumDelayTime = maxDelayTime
  val aircrafts = aircraftList
  val runways = runwayList

  private def getBestRunwayMatch(list : List[Schedule], a : Aircraft) : Schedule = {
    val bestMatch =
      list.groupBy(_.runway)
        .map(r => r._2.max(Ordering.by((s:Schedule) => s.time + 10)))
        .min(Ordering.by((s:Schedule) => s.time + 10))

    new Schedule(a, bestMatch.time + /*Utils.getAircraftDelay(bestMatch.aircraft.classe, a.classe)*/ 10, bestMatch.runway)
  }

  private def getFreeSchedule(runways : Seq[Runway], list : List[Schedule],a : Aircraft) : Schedule = {
    val freeRunways = list.groupBy(_.runway).map(s=> s._1).toList.diff(runways) ::: runways.diff(list.groupBy(_.runway).map(s=> s._1).toList).toList

    if(freeRunways.size == 0)
    {
      val res = getBestRunwayMatch(list, a)

      if(a.target + maximumDelayTime < res.time)
      {
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
        println("--> WARNING - Maximum delay time has been reached for plane " + a.number  + " <--")
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

val a1 = new Aircraft(1,1,null)
val a2 = new Aircraft(2,8,null)
val a3 = new Aircraft(3,10,null)
val a4 = new Aircraft(4,17,null)
val a5 = new Aircraft(5,12,null)

val r1 = Runway(1, List())
val r2 = Runway(2, List())
val r3 = Runway(3, List())

var lst_aircrafts = Seq(a1,a2,a3,a4,a5)
var lst_runways = Seq(r1,r2)

val agenda = Agenda(10, lst_aircrafts, lst_runways).createSchedule(lst_aircrafts, List())

//Testes
val lstR = Seq(r1,r2)
val lst = List(Schedule(Aircraft(1,1,null),1,Runway(1,List())), Schedule(Aircraft(2,8,null),8,Runway(2,List())), Schedule(Aircraft(3,10,null),11,Runway(1,List())))
lst.groupBy(_.runway)
  .map(r => r._2.max(Ordering.by((s:Schedule) => s.time)))
  .min(Ordering.by((s:Schedule) => s.time))
val freeRunways = lst.groupBy(_.runway).map(s=> s._1).toList.diff(lstR) ::: lstR.diff(lst.groupBy(_.runway).map(s=> s._1).toList).toList
//bestRunwayMatch(lst,a4)
