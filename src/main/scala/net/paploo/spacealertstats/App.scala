package net.paploo.spacealertstats

import net.paploo.spacealertstats.parser.Parser
import net.paploo.spacealertstats.mission._
import scala.collection.mutable.ListBuffer

object App {

  def main(args: Array[String]): Unit = {
    val parsed = Parser.parse(1)
    println(parsed)
    val mission = parsed.getOrElse(Mission())
    val events = mission.events
    println(events)


    def splitAtPhase(events: List[Event]): (List[Event], List[Event]) = {
      val phaseEndIndex = events.indexWhere {
        case EndPhase(_, _) => true
        case _ => false
      }
      events.splitAt(phaseEndIndex+1)
    }

    def phases(events: List[Event], acc: List[List[Event]] = Nil): List[List[Event]] = {
      if (events.isEmpty) acc.reverse
      else {
        val split = splitAtPhase(events)
        phases(split._2, split._1 :: acc)
      }
    }

    def phases2(event: List[Event]): List[List[Event]] = {
      val buf = ListBuffer[ListBuffer[Event]]()
      event.foldLeft(buf){
        (acc, event) => event match {
          case BeginPhase(_,_) => acc += ListBuffer(event)
          case _ =>
            acc.last += event
            acc
        }
      }
      buf.map(_.toList).toList
    }

    println(phases(events))
    println(phases2(events))
  }
}
