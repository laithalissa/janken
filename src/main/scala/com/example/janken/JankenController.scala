package com.example.janken

import java.util.UUID

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

class JankenController extends Controller {

  get("/") { _: Request =>
    "Hello"
  }

  post("/new-player") { _: Request =>
    UUID.randomUUID()
  }

}
