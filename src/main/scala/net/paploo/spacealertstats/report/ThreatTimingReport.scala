package net.paploo.spacealertstats.report

import net.paploo.spacealertstats.mission.Mission
import net.paploo.spacealertstats.stats.SeqStats.Implicits._

class ThreatTimingReport(val missions: Seq[Mission]) extends Report[Option[Any], Seq[Seq[Int]]] {

  override lazy val name = "Threat Timing"

  override lazy val result = Option(missions.map(_.threatSpacings).transpose)

  override lazy val backingData = Some(threatTimings)

  override lazy val toBackingDataString = Some(threatTimingTable.toOutputTable)

  lazy val threatTimingTable = threatTimings.transpose

  lazy val threatTimings = missions.flatMap(_.threatTimings).map(timing => List(timing._1, timing._2, timing._3))

}
