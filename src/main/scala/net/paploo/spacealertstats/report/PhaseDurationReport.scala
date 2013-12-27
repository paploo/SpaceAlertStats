package net.paploo.spacealertstats.report

import net.paploo.spacealertstats.mission.Mission
import net.paploo.spacealertstats.stats.Stats
import net.paploo.spacealertstats.stats.SeqStats.Implicits._

class PhaseDurationReport(val missions: Seq[Mission]) extends Report[Seq[Stats[Int]], Seq[Seq[Int]]] {

  override lazy val name = "Phase Duration"

  override lazy val result = durationLists.map(_.toStats)

  override lazy val backingData = Some(durationLists)

  lazy val durationLists = missions.map(_.phaseDurations).transpose

  override lazy val toResultString = result.mkString("List(\n\t", "\n\t", "\n)")

  override lazy val toBackingDataString = Some(durationLists.toOutputTable)
}

