package com.example.janken

import java.time.Instant
import java.util.concurrent.atomic.AtomicBoolean

import Arena.{Move, PlayerId, Score}

import scala.collection.concurrent.TrieMap

class Arena {
  private val start = Instant.now
  private val round: TrieMap[PlayerId, Move] = TrieMap()
  private val open: AtomicBoolean = new AtomicBoolean(true)

  def makeMove(playerId: PlayerId, move: Move): Unit = {
    if (open.get) {
      round.getOrElseUpdate(playerId, move)
    }
  }

  def endRound(): Map[PlayerId, Score] = {
    open.set(false)
    val roundstate = round.toMap

    val totalPlayersByMove: Map[Move, Int] = roundstate.groupBy(_._2).mapValues(_.toList.length)

    val scoresByMove: Map[Move, Score] = totalPlayersByMove map {
      case (move, _) => {
        val beats = totalPlayersByMove.getOrElse(move.beats, 0)
        val beatenBy = totalPlayersByMove.getOrElse(move.beatenBy, 0)
        move -> Score(beats, beatenBy, totalPlayersByMove.getOrElse(move, 0))
      }
    }

    roundstate map {
      case (playerId, move) => playerId -> scoresByMove.getOrElse(move, Score.empty)
    }
  }
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
    losses: Int,
    draws: Int
  ) {
    val score: Int = wins - losses
  }

  object Score {
    def empty: Score = Score(0, 0, 0)
  }
}
