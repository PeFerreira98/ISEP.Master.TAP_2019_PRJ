//import domain.{Aircraft, Class1}

// Prototype functions

val qwe = <agenda maximumDelayTime="900">
  <aircrafts>
    <aircraft number="1" target="0" class="5"/>
    <aircraft number="2" target="13" class="2"/>
    <aircraft number="3" target="104" class="3"/>
    <aircraft number="4" target="139" class="1" emergency="5"/>
    <aircraft number="5" target="151" class="2"/>
  </aircrafts>
</agenda>

val aircraftNodes = qwe \ "aircrafts" \ "aircraft"

/*
val aircrafts = for(node <- aircraftNodes)
  yield Aircraft(node.\@("number").toInt,
    node.\@("target").toInt,
    Class1)

 */