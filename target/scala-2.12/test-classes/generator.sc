import org.scalacheck.Gen

import scala.io.Source

val g1 = Gen.oneOf( 1, 2, 3)
val g2 = Gen.alphaStr

g1.sample
g2.sample

case class Account(val clients: List[String], val balance: Double, val period: Int)

val genAccount = for (
  c <- Gen.nonEmptyContainerOf[List,String](Gen.alphaStr) ;
  b <- Gen.chooseNum[Double](-10000,10000) ;
  p <- Gen.choose[Int](1,5)
) yield Account(c,b,p)

genAccount.sample

// testing stuff
val source = Source.fromFile("C:\\Users\\rmvieira\\Documents\\TAP\\1140953_1140956_1141233_a_2019_tap_ncf\\SentiWordNet_3.0.0_20130122.txt").getLines
  val contentList = source.toList

