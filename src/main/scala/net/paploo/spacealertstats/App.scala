package net.paploo.spacealertstats

import net.paploo.spacealertstats.parser.Parser
import net.paploo.spacealertstats.report._
import net.paploo.spacealertstats.mission.Mission
import net.paploo.spacealertstats.stats.SeqStats.Implicits._

object App {

  def main(args: Array[String]): Unit = {
    for(report <- reports) {
      println(report.toFullString)
      print("\n")
    }
  }

  lazy val missions: List[Mission] = {
    val missionData = Parser.parseAll
    missionData.filter {
      case Some(m: Mission) => true
      case _ => false
    }.map(_.get).toList
  }

  lazy val reports: List[Report[Any,Any]] = List(
    new PhaseDurationReport(missions)
  )
}

