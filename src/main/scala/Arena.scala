import java.util.concurrent.atomic.AtomicBoolean

import Arena.{Move, Score}

import scala.collection.concurrent.TrieMap

class Arena {
  private val round: TrieMap[Int, Option[Move]] = TrieMap()
  private val open: AtomicBoolean = new AtomicBoolean(true)

  def makeMove(playerId: Int, move: Move): Unit = {
    if (open.get) {
      round.getOrElseUpdate(playerId, Some(move))
    }
  }

  def endRound(): Map[Int, Score] = ???

  def newPlayerId(): Int = ???
}

object Arena {
  sealed trait Move {
    val beats: Move
    val beatenBy: Move
    val id: Int
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
