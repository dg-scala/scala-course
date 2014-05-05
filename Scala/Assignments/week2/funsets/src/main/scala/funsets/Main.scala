package funsets

object Main extends App {
  import FunSets._
  println(contains(singletonSet(1), 1))
  
  val decades: Set = x => x % 10 == 0
  val double = (x: Int) => x * 2
  val dubdecs: Set = map(decades, double) 
  println("decades = ")
  printSet(decades)
  println("dubdecs = ")
  printSet(dubdecs)
}
