package de.htwg.mps.minesweeper.view.tui


import akka.actor.{ActorRef, ActorSystem, Props}

import scala.io.StdIn

object TuiMinesweeperMain {

  implicit val actorSystem: ActorSystem = ActorResolver.createSystem()

  val actorResolver: ActorResolver = ActorResolver.resolver()

  val publisher: ActorRef = actorResolver.resolvePublisher()

  val gameController: ActorRef = actorResolver.resolveGameController()

  val tui: ActorRef = actorSystem.actorOf(Props(new TuiActor(gameController, publisher)), "test")

  def main(args: Array[String]): Unit = {
    while (true) {
      tui.tell(ProcessTuiInput(StdIn.readLine()), tui)
    }
  }
}
