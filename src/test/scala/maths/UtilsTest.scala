package maths

import maths.Utils.{factorial, isPrime}
import org.scalacheck.Properties
import org.scalacheck.Prop.forAll
import org.scalacheck.Gen

object UtilsTest extends Properties("Utils") {

  property("no even number is prime") = forAll{ n: Int => {
      if ((n>0) && (n % 2 == 0)) !isPrime(n) else true
    }
  }

  property("no even number is prime2") = forAll(Gen.choose(0,100000)){ n: Int => {
    if ((n>0) && (n % 2 == 0)) !isPrime(n) else true
  }
  }

  property("factorial 1") = forAll(Gen.choose(1,5)){ n: Int => {
      factorial(n)*(n+1) == factorial(n+1)
    }
  }

  property("factorial 2") = forAll(Gen.chooseNum[Int](1,5) ,
   Gen.chooseNum[Int](1,5)){ (n1:Int,  n2: Int) => {
      if ( n1 >= n2) factorial(n1) >= factorial(n2) else factorial(n2) > factorial(n1)
    }
  }

}
