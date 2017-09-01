package com.example.janken

import java.time.Instant
import java.util.concurrent.atomic.AtomicBoolean

import Arena.{Move, PlayerId, Round, Score}

import scala.collection.concurrent.TrieMap

class Arena {
  private var rounds: TrieMap[Long, Round] = TrieMap.empty

  def startNewRound(): Round = {
    val instant = Instant.now()
    val round = Round(instant)
    rounds.put(instant.getEpochSecond, round)
    round
  }

  def makeMove(instant: Instant, playerId: PlayerId, move: Move): Unit = {
    rounds.get(instant.getEpochSecond).foreach ( round =>
      if (round.open.get) {
        round.moves.getOrElseUpdate(playerId, move)
      }
    )
  }

  def getCurrentRound(): Round = {
    rounds.values.find(!_.isFinished).getOrElse(startNewRound())
  }

  def endRound(instant: Instant): Map[PlayerId, Score] = {
    rounds.get(instant.getEpochSecond).map { round =>
      println(round.moves)
      if (round.isFinished) {
        if (round.open.get()) {
          round.open.set(false)
          startNewRound()
        }
        val roundstate = round.moves.toMap

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
      } else {
        Map.empty[PlayerId, Score]
      }
    }.getOrElse(Map.empty[PlayerId, Score])
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
    def apply(s: String): Move = s.toUpperCase() match {
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

  case class Round(
    startTime: Instant,
    moves: TrieMap[PlayerId, Move] = TrieMap(),
    open: AtomicBoolean = new AtomicBoolean(true)
  ) {
    def isFinished: Boolean = {
      (Instant.now().getEpochSecond - startTime.getEpochSecond) >= 10
    }
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
