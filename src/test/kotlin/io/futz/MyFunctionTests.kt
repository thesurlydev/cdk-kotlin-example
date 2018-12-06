package io.futz

import org.junit.Assert.assertEquals
import org.junit.Test

class MyFunctionTests {
  @Test
  fun handle() {
    val result = MyFunction().handle("Shane")
    assertEquals("Hello, Shane", result)
  }
}