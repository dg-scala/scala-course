package funsets

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  test("string take") {
    val message = "hello, world"
    assert(message.take(5) == "hello")
  }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  test("adding ints") {
    assert(1 + 2 === 3)
  }

  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s0 = singletonSet(0)
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton {1} should contain 1")
      assert(contains(s2, 2), "Singleton {2} should contain 2")
      assert(contains(s3, 3), "Singleton {3} should contain 3")
      assert(!contains(s1, 3), "Singleton {1} should not contain 3")
    }
  }

  test("union contains all elements") {
    new TestSets {
      val s = union(s1, s2)
      val comm = union(s2, s1)

      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")

      // Unions are commutative
      assert(contains(comm, 1), "Union 1")
      assert(contains(comm, 2), "Union 2")
      assert(!contains(comm, 3), "Union 3")

      // Unions are associative
      val u12_3 = union(union(s1, s2), s3)
      val u1_23 = union(s1, union(s2, s3))
      assert(contains(u12_3, 1) && contains(u1_23, 1))
      assert(contains(u12_3, 2)
        && contains(u1_23, 2))
      assert(contains(u12_3, 3)
        && contains(u1_23, 3))
    }
  }

  test("intersection contains common elements") {
    new TestSets {
      val s = union(s1, s2)
      val i1 = intersect(s, s1)
      val i2 = intersect(s, s2)
      val i3 = intersect(s, s3)

      assert(contains(i1, 1), "Intersection {1, 2} and {1} should contain 1")
      assert(!contains(i1, 2), "Inersection of {1, 2} and {1} should not contain 2")
      assert(contains(i2, 2), "Intersect {1, 2} and {2} should contain 2")
      assert(!contains(i2, 1), "Inersection of {1, 2} and {2} should not contain 1")
      assert(!contains(i3, 1)
        && !contains(i3, 2)
        && !contains(i3, 3), "Intersection of {1, 2} and {3} should be empty")

      // Intersections are commutative
      val c1 = intersect(s1, s)
      assert(contains(c1, 1))
      assert(!contains(c1, 2))

      // Intersections are associative
      val u = union(union(s1, s2), s3)
      val us_1 = intersect(intersect(u, s), s1)
      val u_s1 = intersect(u, intersect(s, s1))
      assert(contains(us_1, 1)
        && contains(u_s1, 1))
      assert(!contains(us_1, 2)
        && !contains(u_s1, 2))
      assert(!contains(us_1, 3)
        && !contains(u_s1, 3))
    }
  }

  test("difference contains non-common elements") {
    new TestSets {
      val s = union(s1, s2)
      val d1 = diff(s, s1)
      val d2 = diff(s, s2)
      val d3 = diff(s, s3)

      assert(contains(d1, 2), "Difference {1, 2} and {1} should contain 2")
      assert(!contains(d1, 1), "Difference of {1, 2} and {1} should not contain 1")
      assert(contains(d2, 1), "Difference {1, 2} and {2} should contain 1")
      assert(!contains(d2, 2), "Difference of {1, 2} and {2} should not contain 2")

      // Difference is not commutative
      val d1s = diff(s1, s)
      assert(!contains(d1s, 2))
      assert(!contains(d1s, 1))

      // Difference is not associative
      val mega: Set = x => 0 < x && x < 4
      val ms_1 = diff(diff(mega, s), s1)
      val m_s1 = diff(mega, diff(s, s1))
      assert(!contains(ms_1, 1) 
          && contains(ms_1, 3))
      assert(contains(m_s1, 1) 
          && contains(m_s1, 3))
    }
  }

  test("filter") {
    new TestSets {
      val mega: Set = x => 0 <= x && x < 4
      val positive = filter(mega, x => x > 0)
      val negative = filter(mega, x => x < 0)
      val zero = filter(mega, x => x == 0)

      assert(!contains(positive, 0))
      assert(contains(positive, 1)
        && contains(positive, 2)
        && contains(positive, 3))
      assert(!contains(negative, 0)
        && !contains(negative, 1)
        && !contains(negative, 2)
        && !contains(negative, 3))
      assert(contains(zero, 0)
          && !contains(zero, 1)
          && !contains(zero, 2)
          && !contains(zero, 3))
    }
  }
  
  test("forall") {
    // bounded set {x | -1000 <= x <= 1000}
    val boundedSet: Set = x => -1000 <= x && x <= 1000
    
    // predicates
    val lessThanUpperBound = (x: Int) => x < 1001
    val greaterThanLowerBound = (x: Int) => x > -1001
    val negative = (x: Int) => x < 0
    val positive = (x: Int) => x > 0
    val zero = (x: Int) => x == 0
    
    // expectations
    assert(forall(boundedSet, greaterThanLowerBound))
    assert(forall(boundedSet, lessThanUpperBound))
    assert(!forall(boundedSet, negative))
    assert(!forall(boundedSet, zero))
    assert(!forall(boundedSet, positive))
    assert(forall(boundedSet, x => x < 0 || x == 0 || x > 0))
  }
  
  test("exists") {
    // bounded set {x | -1000 <= x <= 1000}
    val boundedSet: Set = x => -1000 <= x && x <= 1000
    val evens: Set = x => x > 0 && x % 2 == 0
    val odds: Set = x => x > 0 && x % 2 == 1
    
    // predicates, i.e. subsets
    val lessThanUpperBound: Set = x => x < 1001
    val greaterThanLowerBound: Set = x => x > -1001
    val negatives: Set = x => x < 0
    val positives: Set = x => x > 0
    val zero: Set = x => x == 0
    
    // expectations
    assert(exists(boundedSet, greaterThanLowerBound))
    assert(exists(boundedSet, lessThanUpperBound))
    assert(!exists(boundedSet, x => x < -1000))
    assert(!exists(boundedSet, x => x > 1000))
    assert(exists(boundedSet, negatives))
    assert(exists(boundedSet, zero))
    assert(exists(boundedSet, positives))
    assert(!exists(boundedSet, x => x < 0 && x == 0 && x > 0))
    assert(exists(boundedSet, evens))
    assert(exists(boundedSet, odds))
  }
  
  test("map") {
    val decades: Set = x => x % 10 == 0
    val double = (x: Int) => x * 2
    assert(forall(map(decades, double), x => contains(decades, x / 2)))
    assert(contains(map(decades, double), -2000))
    assert(contains(map(decades, double), 2000))
  }
}
