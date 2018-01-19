package de.htwg.mps.minesweeper.view.utils

trait NumberToStringUtils {

  def getCharStringAtOrElse(input: Int, position: Int, orElse: String): String = {
    if (stringLength(input) <= position || position < 0) {
      orElse
    } else {
      input.toString.substring(position, position + 1)
    }
  }

  def stringLength(x: Int): Int = x.toString.length

}

object NumberToStringUtils extends NumberToStringUtils

