package net.paploo.spacealertstats.report

import net.paploo.spacealertstats.mission.Mission

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
