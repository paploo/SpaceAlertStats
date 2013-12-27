package net.paploo.spacealertstats.stats

object Stats {
  def apply[A](length: Int, bounds: (A,A), sum: A, median: A, mean: Double, variance: Double): Stats[A] =
    StatsBlock(length, bounds, sum, median, mean, variance)

  def unapply[A](stats: Stats[A]) = Some((stats.length, stats.bounds, stats.sum, stats.median, stats.mean, stats.variance))

  def unapply[A](stats: StatsBlock[A]) = StatsBlock.unapply(stats)
}

trait Stats[A] {

  def bounds: (A,A)

  def min: A = bounds._1

  def max: A = bounds._2

  def sum: A

  def length: Int

  def median: A

  def mean: Double

  def variance: Double

  def stdDeviation: Double = Math.sqrt(variance)

  def toMap = Map(
    "length" -> length,
    "bounds" -> ((min, max)),
    "sum" -> sum,
    "median" -> median,
    "mean" -> mean,
    "variance" -> variance,
    "stdDeviation" -> stdDeviation
  )

  override def toString = s"Stats(length = $length, bounds = $bounds, sum = $sum, median = $median, mean = $mean, variance = $variance, stdDeviation = $stdDeviation)"

}

case class StatsBlock[A](length: Int,
                         bounds: (A,A),
                         sum: A,
                         median: A,
                         mean: Double,
                         variance: Double) extends Stats[A]
