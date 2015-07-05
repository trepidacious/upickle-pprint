package upickle
import utest._
import upickle.old._
import acyclic.file
/**
* Created by haoyi on 4/22/14.
*/
object TestUtil{
  implicit class -->[T](x: T){
    def -->(y: T) = {
      val lhs = x
      val rhs = y
      assert(lhs == rhs)
    }
  }
  def rw[T: Reader: Writer](t: T, s: String*) = {
    rwk[T, T](t, s:_*)(t => t)
  }
  def rwk[T: Reader: Writer, V](t: T, sIn: String*)(k: T => V) = {
    val writtenT = write(t)

    val strings = sIn.map(_.trim)

    if (strings.length > 0) assert(strings.contains(writtenT))

    for (s <- strings) {
      val readS = read[T](s)
      assert(k(readS) == k(t))
    }

    assert(k(read[T](writtenT)) == k(t))
  }
}
