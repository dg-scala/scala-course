package objsets

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TweetSetSuite extends FunSuite {
  trait TestSets {
    val set1 = new Empty
    val a = new Tweet("a", "a body", 20)
    val b = new Tweet("b", "b body", 20)
    val set2 = set1.incl(a)
    val set3 = set2.incl(b)
    val c = new Tweet("c", "c body", 7)
    val d = new Tweet("d", "d body", 9)
    val set4c = set3.incl(c)
    val set4d = set3.incl(d)
    val set5 = set4c.incl(d)
  }

  def asSet(tweets: TweetSet): Set[Tweet] = {
    var res = Set[Tweet]()
    tweets.foreach(res += _)
    res
  }

  def size(set: TweetSet): Int = asSet(set).size

  test("filter: on empty set") {
    new TestSets {
      assert(size(set1.filter(tw => tw.user == "a")) === 0)
    }
  }

  test("filter: a on set5") {
    new TestSets {
      assert(size(set5.filter(tw => tw.user == "a")) === 1)
    }
  }  
  
  test("filter: less than 20 on set5") {
    new TestSets {
      val result = set5.filter(tw => tw.retweets < 20)
      assert(size(result) === 2)
      assert(result.contains(c) && result.contains(d))
    }
  }

  test("filter: 20 on set5") {
    new TestSets {
      val result = set5.filter(tw => tw.retweets == 20)
      assert(size(result) === 2)
      assert(result.contains(a) && result.contains(b))
    }
  }

  test("filter: greater 20 on set5") {
    new TestSets {
      val result = set5.filter(tw => tw.retweets > 20)
      assert(size(result) === 0)
      assert(result.isEmpty)
    }
  }
  
  test("union: set4c and set4d") {
    new TestSets {
      assert(size(set4c.union(set4d)) === 4)
    }
  }

  test("union: with empty set (1)") {
    new TestSets {
      assert(size(set5.union(set1)) === 4)
    }
  }

  test("union: with empty set (2)") {
    new TestSets {
      assert(size(set1.union(set5)) === 4)
    }
  }

  test("descending: set5") {
    new TestSets {
      val trends = set5.descendingByRetweet
      assert(!trends.isEmpty)
      assert(trends.head.user == "a" || trends.head.user == "b")
      assert(trends.tail.head.user == "a" || trends.tail.head.user == "b")
      assert(trends.tail.tail.head.user == "d")
      assert(trends.tail.tail.tail.tail == Nil)
    }
  }
  
  test("descending: Empty") {
    val trends = (new Empty).descendingByRetweet
    assert(Nil == trends)
  }
  
  test("descending: Singleton") {
    new TestSets {
      val trends = (new Empty).incl(c).descendingByRetweet
      assert(trends.head.user == "c")
      assert(trends.tail == Nil)
    }
  }
  
  test("descending: Two tweets") {
    new TestSets {
      val cd = (new Empty).incl(c).incl(d)
      val dc = (new Empty).incl(d).incl(c)
      val t1 = cd.descendingByRetweet
      val t2 = dc.descendingByRetweet
      assert(t1.head.user == t2.head.user)
      assert(t1.tail.head.user == t2.tail.head.user)
      assert(t1.tail.tail == Nil)
      assert(t2.tail.tail == Nil)
      assert(t1.head.user == "d")
      assert(t2.head.user == "d")
      assert(t1.tail.head.user == "c")
      assert(t2.tail.head.user == "c")
    }
  }
}
