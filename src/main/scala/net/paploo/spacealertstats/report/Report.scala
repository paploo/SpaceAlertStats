package net.paploo.spacealertstats.report

import net.paploo.spacealertstats.mission.Mission
import net.paploo.spacealertstats.stats.SeqStats.Implicits._
import net.paploo.spacealertstats.stats.Stats

trait Report[T] {

  def missions: Seq[Mission]

  def name: String

  def result: T
}

class PhaseDurationReport(val missions: Seq[Mission]) extends Report[Seq[Stats[Int]]] {

  override def name = "Phase Duration"

  override def result = durationLists.map(_.toStats)

  def durationLists = missions.map(_.phaseDurations).transpose
}
