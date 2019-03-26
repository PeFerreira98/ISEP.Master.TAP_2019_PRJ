package maths

object Utils {

  def isPrime(n: Int): Boolean =
    (2 to math.sqrt(n).toInt).forall(d => n % d != 0)

  def factorial(n: Int): Int =
    (2 to n).product
}
