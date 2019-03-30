import domain.Entry

val input = "Scala é mau. O que nós gostamos é... .NET!"

/*Será que se matém o "'"? (isn't)*/
def removeSpecialChars(input:String) : Array[String] ={
  val res = input.replaceAll("[^a-zA-z]", " ").replaceAll(" {2,}", " ")

  res.split(" ").map(_.trim)
}

removeSpecialChars("Tap is    bad!!! but at least is not ALGAV :D")

/*
def findPolarity(arr_entry : Stream[Entry], arr_input : Array[String]) : Double = {
  val res: Double = 0
  for()
}
*/