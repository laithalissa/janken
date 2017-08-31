package com.example.janken

import java.time.Instant
import java.util.UUID

import com.example.janken.Arena.Move
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.finatra.request.RouteParam

class JankenController extends Controller {

  val arena = new Arena()

  get("/") { _: Request =>
    response.ok.file("../../../../../html/index.html")
  }

  get("/current") { _: Request =>
    arena.getCurrentRound().startTime
  }

  post("/new-player") { _: Request =>
    UUID.randomUUID()
  }

  post("/make-move") { request: MakeMoveRequest =>
    arena.makeMove(
      Instant.ofEpochSecond(request.timestamp),
      request.uuid,
      Move(request.move)
    )
  }

  get("/results/:timestamp") { request: ResultsRequest =>
    arena.endRound(Instant.ofEpochSecond(request.timestamp))
  }

  get("/score/:uuid") { request: ScoreRequest =>
    arena.endRound(Instant.ofEpochSecond(request.timestamp))
      .get(request.uuid)
  }

}

case class ScoreRequest(
  @RouteParam timestamp: Long,
  @RouteParam uuid: String
)

case class ResultsRequest(
  @RouteParam timestamp: Long
)

case class MakeMoveRequest(
  timestamp: Long,
  uuid: String,
  move: String
)
