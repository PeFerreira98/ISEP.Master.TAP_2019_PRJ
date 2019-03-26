import org.scalacheck.Gen

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


val tupleGen = for (
  n1 <- Gen.chooseNum[Int](1,5) ;
  n2 <- Gen.chooseNum[Int](1,5) ;
) yield (n1,n2)

tupleGen.sample