package net.paploo.spacealertstats.report

import net.paploo.spacealertstats.mission.Mission
import net.paploo.spacealertstats.stats.SeqStats.Implicits._
import net.paploo.spacealertstats.stats.Stats

class ThreatSpacingReport(val missions: Seq[Mission]) extends Report[List[Stats[Int]], Seq[Seq[Int]]] {

  override lazy val name = "Threat Spacing"

  override lazy val result = unzipAggregatedThreatSpacings2.map(_.toStats)

  override lazy val backingData = Some(unzipAggregatedThreatSpacings2)

  override lazy val toResultString = result.mkString("List(\n\t", "\n\t", "\n)")

  override lazy val toBackingDataString = Some(unzipAggregatedThreatSpacings2.toOutputTable)

  /**
   * Returns a list of spacings per phase per mission
   */
  lazy val phaseThreatSpacings = missions.map(_.threatSpacings)

  /**
   * Aggregates the spacings for the first two phases (as the third doesn't have
   * any threats ever in standard data, which is what I'm looking at right now).
   */
  lazy val aggregatedThreatSpacings2 =
    phaseThreatSpacings.reduce( (a,b) => List(a(0) ++ b(0), a(1) ++ b(1)) )

  /**
   * Threat spacings are pairs of (delta T+n, delta time) values. This unzips
   * a list
   */
  lazy val unzipAggregatedThreatSpacings2 = aggregatedThreatSpacings2.flatMap { l =>
    val pairLists = l.unzip
    List(pairLists._1, pairLists._2)
  }

}
