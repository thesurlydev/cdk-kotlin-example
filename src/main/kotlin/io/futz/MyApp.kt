package io.futz

import software.amazon.awscdk.App
import software.amazon.awscdk.Environment
import software.amazon.awscdk.StackProps

fun main(args: Array<String>) {
    val app = App()

    MyLambdaStack(
        app, "MyApp", "build/libs/app.zip", StackProps.builder()
            .withEnv(
                Environment.builder()
                    .withAccount("515292396565")
                    .withRegion("us-west-2")
                    .build()
            )
            .build()
    )

    app.run()
}