class scheduleItem(
  plane : String,
  runway : String,
  time : Int
)
class schedule(
  order : Int,
  planeClass : Int,
  time : Int
)


def scheduleCreation(list : List[schedule], listRunways : List[Object]) : Unit = {
  //for (i <- list) yield scheduleItem(i.plane, checkRunway(listRunways, i), 0)

}

def circular[A](l: List[A]) : List[A] = {
  //l.tail ::: l.head :: Nil
  //l.tail :+ l.head
  l.headOption.map(h => l.tail :+ h).getOrElse(List())
}
val l = List(1,2,4,5,7,8)
circular(l)
