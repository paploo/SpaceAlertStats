package net.paploo.spacealertstats.stats

import scala.language.implicitConversions
import scala.collection.GenTraversableOnce

object SeqStats {

  object Implicits {
    implicit def seqStatsFromSeq[A](seq: Seq[A]) = new SeqStats(seq)
  }
}

class SeqStats[A](seq: Seq[A]) {

  def min(implicit ord: Ordering[A]): A = seq.min

  def max(implicit ord: Ordering[A]): A = seq.max

  def sum(implicit num: Numeric[A]): A = seq.sum

  def length: Int = seq.length

  def mean(implicit num: Numeric[A]): Double = num.toDouble(sum) / length.toDouble

  /**
   * Selects the median value.
   *
   * Since this must return a type of A, it does not attempt to average when
   * an even number of elements is present; instead it takes the left value.
   * @return The median element.
   */
  def median(implicit ord: Ordering[A]): A = {
    val sortedSeq = seq.sorted
    val len = sortedSeq.length
    val index = if (len%2 == 0) (len/2) - 1 else (len-1) / 2
    sortedSeq(index)
  }

  /**
   * Returns the median value as a double.
   *
   * The implementation sorts and selects the middle two elements if there are
   * an even number of elements, and averages them. If there are an odd number of
   * elements, then it returns the middle element as a double.
   * @return The median as a double.
   */
  def averagedMedian(implicit ord: Ordering[A], num: Numeric[A]): Double = {
    val sortedSeq = seq.sorted
    val len = sortedSeq.length
    if (len%2 ==0) {
      val elem1 = sortedSeq(len/2-1)
      val elem2 = sortedSeq(len/2)
      val elemSum = num.plus(elem1, elem2)
      num.toDouble(elemSum) / 2.0
    }
    else num.toDouble(sortedSeq((len-1)/2))
  }

  def variance(implicit num: Numeric[A]): Double = {
    val sqOfSums: Double = num.toDouble( num.times(sum, sum) )
    val sumOfSqs: Double = num.toDouble( seq.map(a => num.times(a,a)).sum )
    val numerator: Double = (sumOfSqs*length) - sqOfSums
    numerator / (length*length).toDouble
  }

  def toCSV: String = seq.mkString("\n")

  def toTable[B](implicit asTraversable: A => GenTraversableOnce[B]): Seq[Seq[Option[B]]] = {
    val lists = seq.map(asTraversable(_).toList)
    val maxLen = lists.map(_.length).max
    (0 until maxLen).map {i =>
      lists.map {l => if(l.isDefinedAt(i)) Some(l(i)) else None}
    }.toList
  }

  /**
   * Outpus the values of each
   * sequence in each column, comma delimited.  This allows importing the
   * sequence of sequences into plotting software, where each sub sequence is
   * a column in the plotting software.
   */
  def toOutputTable[B](width: Int)(implicit asTraversable: A => GenTraversableOnce[B]): String = {
    toTable.map {l =>
       l.map {
         case Some(i: Int) => s"%${width}d".format(i)
         case Some(f: Double) => s"%${width}.${width}f".format(f)
         case Some(s: String) => s"%${width}s".format(s)
         case Some(value) => s"%${width}s".format(value.toString)
         case None => " " * 10
       }.mkString(", ")
    }.mkString("\n")
  }

  def toOutputTable[B](implicit asTraversable: A => GenTraversableOnce[B]): String = toOutputTable(11)

  def toStats(implicit ord: Ordering[A], num: Numeric[A]): StatsBlock[A] =
    StatsBlock(length, (min,max), sum, median, mean, variance)

  def toSeq: Seq[A] = seq
}
