package net.paploo.spacealertstats.report

import net.paploo.spacealertstats.mission.Mission
import net.paploo.spacealertstats.stats.SeqStats.Implicits._
import net.paploo.spacealertstats.stats.Stats

trait Report[+A,+B] {

  def missions: Seq[Mission]

  def name: String

  def result: A

  def backingData: Option[B] = None

  def toResultString: String = result.toString

  def toBackingDataString: Option[String] = backingData match {
    case Some(value) => Some(value.toString)
    case None => None
  }

  def toFullString: String = {
    val prefix = s"@BEGIN $name\n\n"
    val suffix = s"\n\n@END $name"
    val separator = "\n\n"
    val paragraphs = List(Some(toResultString), toBackingDataString).filter(_.isDefined).map(_.get)
    paragraphs.mkString(prefix, separator, suffix)
  }

  override def toString: String = name
}

class PhaseDurationReport(val missions: Seq[Mission]) extends Report[Seq[Stats[Int]], Seq[Seq[Int]]] {

  override lazy val name = "Phase Duration"

  override lazy val result = durationLists.map(_.toStats)

  override lazy val backingData = Some(durationLists)

  lazy val durationLists = missions.map(_.phaseDurations).transpose

  override lazy val toResultString = result.mkString("List(\n\t", "\n\t", "\n)")

  override lazy val toBackingDataString = Some(durationLists.toOutputTable)
}

class TestReport(val missions: Seq[Mission]) extends Report[Int, Int] {
  override lazy val name = "Test Report"
  override lazy val result = 88
}
