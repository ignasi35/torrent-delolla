package com.marimon.torrent.delolla

import akka.actor.{Props, ActorSystem}
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
    subject ! '4'
    subject ! 'a'
    subject ! 's'
    subject ! 'd'
    subject ! 'f'

    expectMsg(500.milliseconds, TorrentParserActor.String("asdf"))

  }
}
