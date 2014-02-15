import org.scalatest._
import scala.collection.mutable

class TmpTest extends FeatureSpec with GivenWhenThen with Matchers {

  scenario("abc", AbcTestTag) {
    abc
    0 should be(0)
  }

  def abc {
    val ts = mutable.TreeSet[Int]()(new AbcOrdering)

    ts += 1
    ts += 2
    ts += 3

    println(ts.mkString(","))
  }
}

class AbcOrdering extends Ordering[Int] {
  override def compare(a: Int, b: Int) = {
    val result = if (a < b)
      -1
    else if (a > b)
      1
    else
      0

    println(s"Ordering $a $b result=$result")

    result
  }
}