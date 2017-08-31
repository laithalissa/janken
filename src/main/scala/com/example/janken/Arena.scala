package com.example.janken

import java.util.concurrent.atomic.AtomicBoolean

import Arena.{Move, PlayerId, Score}

import scala.collection.concurrent.TrieMap

class Arena {
  private val round: TrieMap[PlayerId, Move] = TrieMap()
  private val open: AtomicBoolean = new AtomicBoolean(true)

  def makeMove(playerId: PlayerId, move: Move): Unit = {
    if (open.get) {
      round.getOrElseUpdate(playerId, move)
    }
  }

  def endRound(): Map[PlayerId, Score] = ???

  def newPlayerId(): PlayerId = ???
}

object Arena {
  type PlayerId = String
  sealed trait Move {
    val beats: Move
    val beatenBy: Move
    val id: Int
  }

  object Move {
    def apply(s: String): Move = s match {
      case "R" => Rock
      case "P" => Paper
      case "S" => Scissors
      case _ => throw new RuntimeException("FUCK OFF")
    }
  }

  case object Rock extends Move {
    val beats = Scissors
    val beatenBy: Move = Paper
    val id = 1
  }
  case object Paper extends Move {
    val beats = Rock
    val beatenBy = Scissors
    val id = 2
  }
  case object Scissors extends Move {
    val beats = Paper
    val beatenBy = Rock
    val id = 3
  }

  case class Score(
    wins: Int,
    losses: Int
  ) {
    val score: Int = wins - losses
  }
}
