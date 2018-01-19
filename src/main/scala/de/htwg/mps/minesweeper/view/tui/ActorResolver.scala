package de.htwg.mps.minesweeper.view.tui

import akka.actor.{ActorRef, ActorSelection, ActorSystem}
import com.typesafe.config.{Config, ConfigFactory}

import scala.concurrent.Await
import scala.concurrent.duration._

class ActorResolver(config: Config, actorSystem: ActorSystem) {
  private val controllerActorName = config.getString("akka.minesweeper.controllerActor")
  private val publisherActorName = config.getString("akka.minesweeper.publisherActor")

  def resolveGameController(): ActorRef = {
    val gameController: ActorSelection = actorSystem.actorSelection(controllerActorName)
    Await.result(gameController.resolveOne(5.seconds), 5.seconds)
  }

  def resolvePublisher(): ActorRef = {
    val publisher: ActorSelection = actorSystem.actorSelection(publisherActorName)
    Await.result(publisher.resolveOne(5.seconds), 5.seconds)
  }

}

object ActorResolver {
  private val config = ConfigFactory.load()
  private val systemNameKey = "akka.minesweeper.systemName"

  def createSystem(): ActorSystem = ActorSystem(config.getString(systemNameKey))

  def resolver()(implicit actorSystem: ActorSystem): ActorResolver = new ActorResolver(config, actorSystem)

}