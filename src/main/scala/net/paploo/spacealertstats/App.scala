package net.paploo.spacealertstats

import net.paploo.spacealertstats.parser.Parser
import net.paploo.spacealertstats.mission._
import net.paploo.spacealertstats.stats.SeqStats._

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

    val l = List(10,24,50,48,31)
    println(l.min)
    println(l.max)
    println(l.length)
    println(l.sum)
    println(l.mean)
    println(l.median)
    println(l.averagedMedian)
  }
}
