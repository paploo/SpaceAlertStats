package net.paploo.spacealertstats.mission

import scala.collection.mutable.ListBuffer

object Phase {
  def apply(seq: Event*): Phase = fromSeq(seq)

  def fromSeq(seq: Seq[Event]): Phase = new Phase(seq.toList)
}

class Phase private (val events: List[Event]) {
  assert(events.head.isInstanceOf[BeginPhase])
  assert(events.last.isInstanceOf[EndPhase])

  lazy val duration = endTime - beginTime

  lazy val beginTime = events.head.time

  lazy val endTime = events.last.time

  def timeToRelative(t: Int) = t - beginTime

  def relativeToTime(relT: Int) = relT + beginTime

  def eventsOfType[T0: Manifest]: List[T0] = events.flatMap {
    case ev: T0 => List(ev)
    case _ => Nil
  }

  /**
   * Gives the spacing of threats. The first number is the threat spacing in T+n
   * value, and the second is the spacing in time. (The order is chosen to be
   * consistent with the threatTimings method.)
   *
   * The first threat is differenced with the start of the phase time, and with
   * the given tPlus offset (so that later phases can be adjusted to a given
   * starting offset based on either the last value of the previous phase or a
   * set starting value based off of phase patterns).
   */
  def threatSpacings(tPlusOffset: Int): List[(Int, Int)] = {
    var lastValues = (tPlusOffset, beginTime)
    eventsOfType[Threat].map { event =>
      val diffs = (event.tPlus - lastValues._1, event.time - lastValues._2)
      lastValues = (event.tPlus, event.time)
      diffs
    }
  }

}
