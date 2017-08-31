package com.example.janken

import java.util.UUID

import com.example.janken.Arena.Move
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.finatra.request.RouteParam

class JankenController(arena: Arena) extends Controller {

  get("/") { _: Request =>
    "Hello"
  }

  post("/new-player") { _: Request =>
    UUID.randomUUID()
  }

  post("/make-move") { request: MakeMoveRequest =>
    arena.makeMove(request.uuid, Move(request.move))
  }

  get("/results") { _: Request =>
    arena.endRound()
  }

  get("/score/:uuid") { request: ScoreRequest =>
    arena.endRound().get(request.uuid)
  }

}

case class ScoreRequest(
  @RouteParam uuid: String
)


case class MakeMoveRequest(
  uuid: String,
  move: String
)
