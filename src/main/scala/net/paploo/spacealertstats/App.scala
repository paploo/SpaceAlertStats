package net.paploo.spacealertstats

import net.paploo.spacealertstats.mission._

object App {

  def main(args: Array[String]): Unit = {
    println("Running...")

    val e1 = IncomingData(10)
    val e2 = Threat(20, 1, Event.White, unconfirmed = true)
    println(e1)
    println(e1.time)
    println(e2)
    println(e2.zone)


    val m = Mission(e1, e2)
    println(m.events)
  }

}
