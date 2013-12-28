package net.paploo.spacealertstats.mission

import scala.collection.mutable.ListBuffer
import net.paploo.spacealertstats.stats.SeqStats.Implicits._

object Mission {
  def apply(seq: Event*): Mission = fromSeq(seq)

  def fromSeq(seq: Seq[Event]): Mission = new Mission(seq.toList)
}

class Mission private (val events: List[Event]) {

  def phase(phaseNumber: Int): Phase = phases(phaseNumber-1)

  lazy val phases: List[Phase] =
    events.foldLeft(ListBuffer[ListBuffer[Event]]()) {
      (acc, event) => event match {
        case BeginPhase(_, _) => acc += ListBuffer(event)
        case _ =>
          acc.last += event
          acc
      }
    }.map(Phase.fromSeq(_)).toList


  lazy val phaseDurations = phases.map(_.duration)

  lazy val phaseBeginTimes = phases.map(_.beginTime)

  lazy val duration = events.last.time

  /**
   * For each threat, returns the T+n value of the threat, the overall time
   * of the threat, and the phase relative time of the threat
   */
  lazy val threatTimings: List[(Int, Int, Int)] = phases.flatMap { phase =>
    phase.events.flatMap {
      case event: Threat => List( (event.tPlus, event.time, phase.timeToRelative(event.time)) )
      case _ => Nil
    }
  }

  /**
   * For each phase, gives the spacing of all the phases from the last one.
   * The spacings are from the phase start.
   */
  lazy val threatSpacings: List[List[(Int, Int)]] = for {
    i <- (0 until phases.length).toList
    phase = phases(i)
  } yield i match {
      case 0 => phase.threatSpacings(0)
      case 1 => phase.threatSpacings(4)
      case _ => phase.threatSpacings(8)
    }

  override def toString = s"Mission(duration=$duration, eventCount=${events.length})"
}