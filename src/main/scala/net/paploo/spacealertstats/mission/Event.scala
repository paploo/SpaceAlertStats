package net.paploo.spacealertstats.mission

object Event {

  trait Zone

  case object White extends Zone

  case object Red extends Zone

  case object Blue extends Zone

  case object Internal extends Zone
}

trait Event {
  def time: Integer
}

case class IncomingData(time: Integer) extends Event

case class DataTransfer(time: Integer) extends Event

case class CommunicationsDown(time: Integer, duration: Integer) extends Event

case class Threat(time: Integer, tPlus: Integer, zone: Event.Zone, serious: Boolean = false, unconfirmed: Boolean = false) extends Event

case class EndPhase(time: Integer, phase: Integer) extends Event

case class UnknownEvent(eventType: String) extends Event {
  def time = 0
}