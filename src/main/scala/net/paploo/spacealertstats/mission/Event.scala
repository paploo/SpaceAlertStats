package net.paploo.spacealertstats.mission

object Event {

  trait Zone

  case object White extends Zone

  case object Red extends Zone

  case object Blue extends Zone

  case object Internal extends Zone
}

trait Event {
  def time: Int
}

case class IncomingData(time: Int) extends Event

case class DataTransfer(time: Int) extends Event

case class CommunicationsDown(time: Int, duration: Int) extends Event

case class Threat(time: Int, tPlus: Int, zone: Event.Zone, serious: Boolean = false, unconfirmed: Boolean = false) extends Event

case class BeginPhase(time: Int, phase: Int) extends Event

case class EndPhase(time: Int, phase: Int) extends Event

case class UnknownEvent(eventType: String) extends Event {
  def time = -1
}