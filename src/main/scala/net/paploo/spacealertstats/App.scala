package net.paploo.spacealertstats

import net.paploo.spacealertstats.parser.Parser
import net.paploo.spacealertstats.mission._
import scala.collection.mutable.ListBuffer

object App {

  def main(args: Array[String]): Unit = {
    val parsed = Parser.parse(2)
    println(parsed)
    val mission = parsed.getOrElse(Mission())
    val events = mission.events
    println(events)


    println(mission.phases)

    println(mission.phase(2))
    println(mission.phaseDurations)

    println(Parser.parseAll)
  }
}
