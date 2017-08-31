package com.example.janken

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

class JankenController extends Controller {

  get("/") { _: Request =>
    "Hello"
  }

}
