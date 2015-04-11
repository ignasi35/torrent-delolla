package com.marimon.torrent.delolla

import akka.actor.Actor

object TorrentParserActor {

  object Reset

  case class BencodeString(value: scala.Predef.String)

  case class Integer(value: scala.Long)

}

class TorrentParserActor extends Actor {

  override def receive: Receive = reset

  private def default: Receive = {
    case i: Char if isDigit(i) =>
      val pending = charDigitToInt(i)
      context.become(stringParse(pending, "") orElse reset)
    case 'i' =>
      context.become(integerParse(0L) orElse reset)
  }

  private def stringParse(pending: Int, current: String): Receive = {
    case x: Char if pending == 1 =>
      sender ! TorrentParserActor.BencodeString(current + x)
      context.become(default)
    case x: Char => context.become(stringParse(pending - 1, current + x))
  }

  private def integerParse(current: Long): Receive = {
    case 'e' =>
      sender ! TorrentParserActor.Integer(current)
      context.become(default)
    case x: Char if isDigit(x) => context.become(integerParse(current * 10 + charDigitToInt(x)))
  }

  private def reset: Receive = {
    case TorrentParserActor.Reset => context.become(default)
    case x => println(x)
  }


  private def isDigit(i:Char) = (i >= '0' && i <= '9')

  private def charDigitToInt(x:Char):Int = (x - 48).toInt
}
