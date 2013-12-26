package net.paploo.spacealertstats.mission

import scala.collection.mutable.ListBuffer

object Mission {
  def apply(seq: Event*): Mission = fromSeq(seq)

  def fromSeq(seq: Seq[Event]): Mission = new Mission(seq.toList)
}

class Mission private (val events: List[Event]) {

  def phaseEvents(phase: Integer): List[Event] = events.dropWhile {
    case BeginPhase(_, ph) if ph==phase => false
    case _ => true
  }.reverse.dropWhile {
    case EndPhase(_, ph) if ph==phase => false
    case _ => true
  }.reverse

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