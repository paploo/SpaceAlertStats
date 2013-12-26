package net.paploo.spacealertstats.mission

import scala.collection.mutable.ListBuffer

object Mission {
  def apply(seq: Event*): Mission = fromSeq(seq)

  def fromSeq(seq: Seq[Event]): Mission = new Mission(seq.toList)
}

class Mission private (val events: List[Event]) {

  def phase(phaseNumber: Integer): List[Event] = phases(phaseNumber-1)

  lazy val phases: List[List[Event]] =
    events.foldLeft(ListBuffer[ListBuffer[Event]]()) {
      (acc, event) => event match {
        case BeginPhase(_, _) => acc += ListBuffer(event)
        case _ =>
          acc.last += event
          acc
      }
    }.map(_.toList).toList


  lazy val phaseDurations = phases.map(phase => phase.last.time - phase.head.time)

  lazy val duration = events.last.time

  override def toString = s"Mission(duration=$duration, eventCount=${events.length})"
}