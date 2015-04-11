package com.marimon.torrent.delolla

import akka.actor.Actor

object TorrentParserActor {

  object Reset

  case class String(value: scala.Predef.String)

}

class TorrentParserActor extends Actor {

  override def receive: Receive = reset

  private def default: Receive = {
    case i: Char if (i >= '0' && i <= '9') =>
      val pending = (i - 48).toInt
      context.become(stringParse(pending, "") orElse reset)
    case x => println(x)
  }

  private def stringParse(pending: Int, current: String): Receive = {
    case x: Char if pending == 1 =>
      sender ! TorrentParserActor.String(current + x)
      context.become(default)
    case x: Char => context.become(stringParse(pending - 1, current + x))
  }

  private def reset: Receive = {
    case TorrentParserActor.Reset => context.become(default)
    case x => println(x)
  }

}
