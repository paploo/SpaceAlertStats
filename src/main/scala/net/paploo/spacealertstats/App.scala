package net.paploo.spacealertstats

import net.paploo.spacealertstats.parser.Parser
import net.paploo.spacealertstats.mission.Mission

object App {

  def main(args: Array[String]): Unit = {
    val parsed = Parser.parse(1)
    println(parsed)
    println(parsed.getOrElse(Mission()).events)
  }

}
