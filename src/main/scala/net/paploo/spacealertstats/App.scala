package net.paploo.spacealertstats

import net.paploo.spacealertstats.parser.Parser
import net.paploo.spacealertstats.report._
import net.paploo.spacealertstats.mission.Mission
import net.paploo.spacealertstats.stats.SeqStats.Implicits._
import net.paploo.spacealertstats.stats.Stats

object App {

  def main(args: Array[String]): Unit = {
    val missionData = Parser.parseAll
    val missions = missionData.filter {
      case Some(m: Mission) => true
      case _ => false
    }.map(_.get).toList

    val report = new PhaseDurationReport(missions)
    println(report.name)
    println(report.result.map(_.toMap))
    println(report.durationLists)
    println(report.durationLists.toTable)
  }
}

