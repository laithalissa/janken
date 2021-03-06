package com.example.janken

import java.time.Instant
import java.util.UUID

import com.example.janken.Arena.Move
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.finatra.request.RouteParam

class JankenController extends Controller {

  val arena = new Arena()

  options("/") { _: Request => response.ok }

  get("/") { _: Request =>
    response.ok.file("html/index.html")
  }

  get("/js") { _: Request =>
    response.ok.file("js/janken-widget.js")
  }

  get("/current") { _: Request =>
    arena.getCurrentRound().startTime.getEpochSecond
  }

  options("/new-player") { _: Request => response.ok }
  post("/new-player") { _: Request =>
    UUID.randomUUID()
  }

  options("/make-move") { _: Request => response.ok }
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

  get("/score/:timestamp/:uuid") { request: ScoreRequest =>
    arena.endRound(Instant.ofEpochSecond(request.timestamp))
      .getOrElse(request.uuid, Map())
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
