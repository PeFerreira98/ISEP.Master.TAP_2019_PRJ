//import domain.{Aircraft, Runway, Schedule}
import util.control.Breaks._

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

val a1 = new Aircraft(1,1,null)
val a2 = new Aircraft(2,8,null)
val a3 = new Aircraft(3,10,null)
val a4 = new Aircraft(4,17,null)
val a5 = new Aircraft(5,12,null)

val r1 = Runway(1, List())
val r2 = Runway(2, List())
val r3 = Runway(3, List())

def circular[A](l: List[A]) : List[A] = {
  //l.tail ::: l.head :: Nil
  //l.tail :+ l.head
  l.headOption.map(h => l.tail :+ h).getOrElse(List())
}
val l = List(1,2,4,5,7,8)
circular(l)

//TODO: Onde tem " + 10" nos tempos, necessário trocar pelo o valor da classe de cada avião

def getOldestAction(list : List[Schedule]) : Schedule = {
  list.groupBy(_.runway)
    .map(s => s._2.max(Ordering.by((s:Schedule) => s.time)))
    .min(Ordering.by((s:Schedule) => s.time))
}

def bestRunwayMatch(list : List[Schedule], a : Aircraft) : Schedule = {
  val bestMatch =
    list.groupBy(_.runway)
    .map(r => r._2.max(Ordering.by((s:Schedule) => s.time)))
    .min(Ordering.by((s:Schedule) => s.time))
  ???
  new Schedule(a, bestMatch.time + 10, bestMatch.runway)
}

def getFreeSchedule(runways : Seq[Runway], list : List[Schedule],a : Aircraft) : Schedule = {
  val freeRunways = list.groupBy(_.runway).map(s=> s._1).toList.diff(runways) ::: runways.diff(list.groupBy(_.runway).map(s=> s._1).toList).toList

  if(freeRunways.size == 0)
    {
      val earliestAction = getOldestAction(list)
      ???
      if(earliestAction.time + 10 <= a.target)
      {
        earliestAction
      }
      else
      {
        bestRunwayMatch(list,a)
      }
      //list.filter(s => s.time + 10 <= a.target).headOption.getOrElse(bestRunwayMatch(list,a))
    }
  else
  {
    new Schedule(a, a.target, freeRunways.headOption.get)
  }
}

def schedule(aircrafts: Seq[Aircraft], runways: Seq[Runway], scheduleList: List[Schedule]) : Seq[Schedule] = {
  val a = aircrafts.headOption.getOrElse(null)

  if(a == null){
    scheduleList
  }
  else
    {
      val tail = aircrafts.tail
      val lstAux : List[Schedule]=
        scheduleList :::
        List[Schedule](getFreeSchedule(runways, scheduleList,a))
      //List[Schedule](new Schedule(a,scheduleList.filter(s => s.time + 10 <= a.target).headOption.getOrElse(new Schedule(a, -10, runways.headOption.get)).time + 10,scheduleList.filter(s=>s.time + 10 <= a.target).headOption.getOrElse(new Schedule(a, 10, runways.headOption.get)).runway))
      schedule(tail,runways,lstAux)
    }
}

schedule(Seq(a1,a2,a3,a4,a5), Seq(r1,r2), List[Schedule]())

//Testes
val lstR = Seq(r1,r2)
val lst = List(Schedule(Aircraft(1,1,null),1,Runway(1,List())), Schedule(Aircraft(2,8,null),8,Runway(2,List())), Schedule(Aircraft(3,10,null),11,Runway(1,List())))
lst.groupBy(_.runway)
  .map(r => r._2.max(Ordering.by((s:Schedule) => s.time)))
  .min(Ordering.by((s:Schedule) => s.time))
val freeRunways = lst.groupBy(_.runway).map(s=> s._1).toList.diff(lstR) ::: lstR.diff(lst.groupBy(_.runway).map(s=> s._1).toList).toList
bestRunwayMatch(lst,a4)
