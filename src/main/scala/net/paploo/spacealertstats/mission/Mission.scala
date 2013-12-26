package net.paploo.spacealertstats.mission

import scala.collection.mutable.ListBuffer

object Mission {
  def apply(seq: Event*): Mission = fromSeq(seq)

  def fromSeq(seq: Seq[Event]): Mission = new Mission(seq.toList)
}

class Mission private (val events: List[Event]) {

  def phase(phase: Integer): List[Event] = phases(phase-1)

  lazy val phases: List[List[Event]] =
    events.foldLeft(ListBuffer[ListBuffer[Event]]()) {
      (acc, event) => event match {
        case BeginPhase(_, _) => acc += ListBuffer(event)
        case _ =>
          acc.last += event
          acc
      }
    }.map(_.toList).toList

}