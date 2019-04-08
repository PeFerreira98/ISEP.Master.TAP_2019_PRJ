//import domain.{Class1, Class2, Class3, Class4, Class5, Class6, Runway}
//import domain.{Aircraft, Class1}
import domain._

// Prototype functions

val qwe = <agenda maximumDelayTime="900" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:noNamespaceSchemaLocation="agenda.xsd">
  <aircrafts>
    <aircraft number="1" target="0" class="5"/>
    <aircraft number="2" target="13" class="2"/>
    <aircraft number="3" target="104" class="3"/>
    <aircraft number="4" target="139" class="1" emergency="5"/>
    <aircraft number="5" target="151" class="2"/>
  </aircrafts>
  <runways>
    <runway number="1">
      <class type="1"/>
      <class type="2"/>
      <class type="3"/>
      <class type="4"/>
      <class type="5"/>
      <class type="6"/>
    </runway>
    <runway number="2">
      <class type="1"/>
      <class type="2"/>
      <class type="4"/>
      <class type="5"/>
    </runway>
  </runways>
</agenda>

val aircraftNodes = qwe \ "aircrafts" \ "aircraft"

val runways = qwe \ "runways" \ "runway"

/*
runways.map(node =>
  Runway(node.\@("number").toInt,
    (node \ "class")
      .map(classNode => classNode.\@("type") match {
        case "1" => Class1
        case "2" => Class2
        case "3" => Class3
        case "4" => Class4
        case "5" => Class5
        case "6" => Class6
      }
      ).toSet
  )
)

val aircrafts = for(node <- aircraftNodes)
  yield Aircraft(node.\@("number").toInt,
    node.\@("target").toInt,
    Class1)

 */


def circular[A](l: List[A]) : List[A] = {
  //l.tail ::: l.head :: Nil
  //l.tail :+ l.head
  l.headOption.map(h => l.tail :+ h).getOrElse(List())
}