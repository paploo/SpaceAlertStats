package net.paploo.spacealertstats.stats

import scala.language.implicitConversions

object SeqStats {
  implicit def seqStatsFromSeq[A](seq: Seq[A]) = new SeqStats(seq)
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
   * @return The
   */
  def median(implicit ord: Ordering[A]): A = {
    val sortedSeq = seq.sorted
    val len = sortedSeq.length
    val index = if (len%2 == 0) (len/2) - 1 else (len-1) / 2
    sortedSeq(index)
  }

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

  def toSeq: Seq[A] = seq
}
