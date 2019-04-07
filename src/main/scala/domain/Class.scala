package domain

/*
sealed trait Weight

case object Small extends Weight
case object Large extends Weight
case object Heavy extends Weight

sealed trait Operation

case object TakeOff extends Operation
case object Landing extends Operation

 trait ClassTrait {
  val number: String
  val operation: Operation
  val weight: Weight
}

class Class(val number: String, val operation: Operation, val weight: Weight) extends ClassTrait
*/

sealed trait Class

case object Class1 extends Class
case object Class2 extends Class
case object Class3 extends Class
case object Class4 extends Class
case object Class5 extends Class
case object Class6 extends Class


