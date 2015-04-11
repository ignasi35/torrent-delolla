package com.marimon.torrent.delolla

import akka.actor.{ActorRef, Props, ActorSystem}
import akka.testkit.{ImplicitSender, TestKit}
import scala.concurrent.duration._
import org.scalatest.{FlatSpecLike, BeforeAndAfterAll, ShouldMatchers}


class TorrentParserActorSpec(_system: ActorSystem)
  extends TestKit(_system)
  with ImplicitSender
  with FlatSpecLike
  with ShouldMatchers
  with BeforeAndAfterAll {

  def this() = this(ActorSystem("MySpec"))

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  val subject = system.actorOf(Props[TorrentParserActor])

  behavior of "Torrent Parser Actor"

  it should "parse Strings" in {
    subject ! TorrentParserActor.Reset
    send (subject, "4asdf")
    expectMsg(500.milliseconds, TorrentParserActor.BencodeString("asdf"))
  }

  it should "parse Integers (using signed 64bit)" in {
    subject ! TorrentParserActor.Reset
    send(subject, "i123e")
    expectMsg(500.milliseconds, TorrentParserActor.Integer(123))
  }

  private def send (actor:ActorRef, msg:String) = {
    msg.toCharArray.foreach(actor ! _ )
  }
}
