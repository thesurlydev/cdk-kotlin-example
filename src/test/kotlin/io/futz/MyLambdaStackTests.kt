package io.futz

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.junit.Assert.assertEquals
import org.junit.Test
import software.amazon.awscdk.App
import java.nio.file.Paths

class MyLambdaStackTests {

  @Test
  fun init() {

    val objectMapper = ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, true)

    // CDK expects zip file to be present when synthesize is called
    val localCodePath = "build/libs/app.zip"
    val localCodeFile = Paths.get(localCodePath).toFile()
    localCodeFile.parentFile.mkdirs()
    localCodeFile.createNewFile()

    val app = App()
    val stack = MyLambdaStack(app, "test", localCodePath)

    val actual = objectMapper.valueToTree<JsonNode>(app.synthesizeStack(stack.name).template)
    val expected = objectMapper.readTree(MyLambdaStackTests::class.java.classLoader.getResource("expected.json"))

    assertEquals(expected, actual)
  }
}