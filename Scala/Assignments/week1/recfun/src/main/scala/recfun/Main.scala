package recfun
import common._

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
    def safePascal(c: Int, r: Int): Int = {
      if (c < 0 || c > r) 0
      else if (r == 0 || r == 1) 1
      else safePascal(c - 1, r - 1) + safePascal(c, r - 1)
    }

    if (c < 0 || r < 0) throw new IndexOutOfBoundsException("Column and row must be positive integers or 0.")
    else safePascal(c, r)
  }

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    def updateCount(c: Char, openCount: Int): Int = {
      if (c == '(') openCount + 1
      else if (c == ')') openCount - 1
      else openCount
    }

    def trackedBalance(chars: List[Char], openCount: Int): Boolean = {
      if (openCount < 0) false
      else if (chars.isEmpty) openCount == 0
      else trackedBalance(chars.tail, updateCount(chars.head, openCount))
    }

    trackedBalance(chars, 0)
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {
	def cc(money: Int, coins: List[Int]): Int = {
      if (money == 0) 1
      else if (money < 0 || coins.isEmpty) 0
      else cc(money - coins.head, coins) + cc(money, coins.tail)
    }
    
    if (money == 0) 0
    else cc(money, coins)
  }
}
