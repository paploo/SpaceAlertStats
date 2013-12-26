package net.paploo.spacealertstats.mission

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

}