package utils

import scala.io.Source

object Utils {

  def isPrime(n: Int): Boolean =
    (2 to math.sqrt(n).toInt).forall(d => n % d != 0)

  def factorial(n: Int): Int =
    (2 to n).product

  def loadFile() = Source.fromFile("SentiWordNet_3.0.0_20130122.txt").getLines.toArray
}
