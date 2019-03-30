import org.scalacheck.Gen
import scala.io.Source
import domain.Entry


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


val gEntry: Gen[Entry] = for (
  w <- Gen.alphaStr.suchThat(i => !i.isEmpty);
  p <- Gen.choose[Double](0, 1);
  n <- Gen.choose[Double](0, 1)
) yield Entry(w,p,n)

val glEntries: Gen[List[Entry]] = Gen.sized{
  s => Gen.listOfN(math.min(5, s), gEntry)
}

glEntries.sample