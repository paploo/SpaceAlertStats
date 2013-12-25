package net.paploo.spacealertstats.parser

import scala.util.parsing.json.JSON
import net.paploo.spacealertstats.mission._

object Parser {
  def parse(missionNumber: Int): Option[Mission] = {
    val input = read(pathToMission(missionNumber))
    val data = JSON.parseFull(input)
    data match {
      case Some(seq: Seq[Map[String, Any]]) => Some( Mission(seq.map(eventFromMap): _*) )
      case Some(_) => None
      case None => None
    }
  }

  def parseAll: Seq[Option[Mission]] = (1 to 8).map(parse)

  protected def pathToMission(missionNumber: Int): String = s"data/mission_$missionNumber.json"

  protected def read(filePath: String) = {
    val source = io.Source.fromFile(filePath)
    val input = source.mkString
    source.close()
    input
  }

  protected def eventFromMap(eventMap: Map[String, Any]): Event = {
    println()
    IncomingData(0)
  }
}
