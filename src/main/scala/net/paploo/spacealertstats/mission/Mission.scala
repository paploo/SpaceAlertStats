package net.paploo.spacealertstats.mission

object Mission {
  def apply(seq: Event*): Mission = new Mission(Vector(seq: _*))
}

class Mission private (val events: Seq[Event]) {
}
