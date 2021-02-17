package com.lambrospetrou.hello

import kotlinx.coroutines.*

class Hello {
  fun world() = "hello, world"

  fun world2() = "hello, world 2"

  fun launchAll() = runBlocking {
    println("boom!")
  }
}
