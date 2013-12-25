package net.paploo.spacealertstats.mission

object Mission {
  def apply(seq: Event*): Mission = fromSeq(seq)

  def fromSeq(seq: Seq[Event]): Mission = new Mission(seq.toList)
}

class Mission private (val events: List[Event]) {
}
