package net.paploo.spacealertstats.parser

import scala.util.parsing.json.JSON
import net.paploo.spacealertstats.mission._

object Parser {
  def parse(missionNumber: Int): Option[Mission] = {
    val input = read(pathToMission(missionNumber))
    val data = JSON.parseFull(input)
    data match {
      //case Some(seq: Seq[Map[String, Any]]) => Some( Mission(seq.map(eventFromMap): _*) )
      case Some(seq: Seq[_]) =>
        val dataSeq = seq.asInstanceOf[Seq[Map[String, Any]]]
        val convertedEvents = dataSeq.flatMap(eventsFromMap)
        val events = if(convertedEvents.isEmpty) Nil else padPhaseEvents(convertedEvents)
        Some( Mission.fromSeq(events) )
      case _ => None
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

  protected def padPhaseEvents(events: Seq[Event]): Seq[Event] = {
    // Add the start of phase one, and trim the last start phase that was inserted.
    List(BeginPhase(0,1)) ++ events.dropRight(1)
  }

  protected def eventsFromMap(eventMap: Map[String, Any]): List[Event] = eventMap.getOrElse("event", "no-event-defined") match {
    case "incoming_data" => incomingDataEventFromMap(eventMap)
    case "data_transfer" => dataTransferEventFromMap(eventMap)
    case "communications_down" => communicationsEventDownFromMap(eventMap)
    case "threat" => threatEventFromMap(eventMap)
    case "end_phase" => endPhaseEventFromMap(eventMap)
    case eventType => List(UnknownEvent(eventType.toString))
  }

  protected def incomingDataEventFromMap(eventMap: Map[String, Any]): List[Event] = {
    val time = extractTime(eventMap)
    List( IncomingData(time))
  }

  protected def dataTransferEventFromMap(eventMap: Map[String, Any]): List[Event] = {
    val time = extractTime(eventMap)
    List(DataTransfer(time))
  }

  protected def communicationsEventDownFromMap(eventMap: Map[String, Any]): List[Event] = {
    val time = extractTime(eventMap)
    val duration = extractInt(eventMap, "duration").getOrElse(0)
    List(CommunicationsDown(time, duration))
  }

  protected def threatEventFromMap(eventMap: Map[String, Any]): List[Event] = {
    val time = extractTime(eventMap)
    val tPlus = extractInt(eventMap, "t").get
    val zone = extractZone(eventMap, "zone").get
    val serious = extractBoolean(eventMap, "serious").getOrElse(false)
    val unconfirmed = extractBoolean(eventMap, "unconfirmed").getOrElse(false)
    List(Threat(time, tPlus, zone, serious, unconfirmed))
  }

  protected def endPhaseEventFromMap(eventMap: Map[String, Any]): List[Event] = {
    val time = extractTime(eventMap)
    val phase = extractInt(eventMap, "phase").get
    List(EndPhase(time, phase), BeginPhase(time, phase+1))
  }

  protected def extractTime(eventMap: Map[String, Any]): Int = extractInt(eventMap, "time").get

  protected def extractInt(eventMap: Map[String, Any], key: String): Option[Int] = eventMap.get(key) match {
    case Some(x: Double) => Some(x.toInt)
    case Some(n: Int) => Some(n)
    case _ => None
  }

  protected def extractString(eventMap: Map[String, Any], key: String): Option[String] = eventMap.get(key) match {
    case Some(value) => Some(value.toString)
    case None => None
  }

  protected def extractBoolean(eventMap: Map[String, Any], key: String): Option[Boolean] = eventMap.get(key) match {
    case Some(flag: Boolean) => Some(flag)
    case _ => None
  }

  protected def extractZone(eventMap: Map[String, Any], key: String): Option[Event.Zone] = eventMap.get(key) match {
    case Some("white") => Some(Event.White)
    case Some("red") => Some(Event.Red)
    case Some("blue") => Some(Event.Blue)
    case Some("internal") => Some(Event.Internal)
    case _ => None
  }
}
