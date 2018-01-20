package de.htwg.mps.minesweeper.view.tui

import akka.actor.{Actor, ActorRef}
import de.htwg.mps.minesweeper.api.events._

case class ProcessTuiInput(input: String)

class TuiActor(controller: ActorRef, publisher: ActorRef) extends Actor {

  override def postStop(): Unit = publisher ! DeregisterObserver

  override def preStart(): Unit = publisher ! RegisterObserver

  sys.addShutdownHook(postStop())

  override def receive: Receive = {
    case ProcessTuiInput(input) => processInput(input)
    case FieldUpdate(_, _, _, grid) => println(GridTuiPrinter(grid).print())
    case GridUpdate(grid) => println(GridTuiPrinter(grid).print())
    case GameWon(game) => println(game.gameResult.getOrElse(""))
    case GameLost(game) => println(game.gameResult.getOrElse(""))
    case GameStart(game) =>
      println("\n==========================\nMinesweeper\n==========================")
      printTui(game.grid)
    case GameUpdate(game) =>
      println("\n==========================\nMinesweeper\n==========================")
      printTui(game.grid)
    case PlayerUpdate(player) => printTui(PlayerTuiPrinter(player).print())
  }

  private def printTui(grid: GridModel): Unit = {
    println(GridTuiPrinter(grid).print())
    println("You can choose following actions")
    println(" o <row> <col> - open a cell")
    println(" ! <row> <col> - toggle cell mark (#: flagged, ?: question marked)")
    println(" r <rows> <cols> <bombs> restart the game with new fields")
  }

  private def printTui(message: String): Unit = {
    println(message)
  }

  private def processInput(input: String): Unit = {
    input match {
      case "r" => controller ! StartGame(4, 5, 3)
      case _ =>
        input.split("\\s+").toList match {
          case "r" :: rows :: columns :: bombs :: Nil =>
            controller ! StartGame(rows.toInt, columns.toInt, bombs.toInt)
          case "o" :: row :: column :: Nil =>
            controller ! OpenField(row.toInt, column.toInt)
          case "!" :: row :: column :: Nil =>
            controller ! ToggleField(row.toInt, column.toInt)
          case _ => println("Unknown action")
        }
    }

  }

}
