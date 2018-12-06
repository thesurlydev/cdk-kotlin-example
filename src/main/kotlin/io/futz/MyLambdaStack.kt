package io.futz


import software.amazon.awscdk.App
import software.amazon.awscdk.Stack
import software.amazon.awscdk.StackProps
import software.amazon.awscdk.services.iam.Role
import software.amazon.awscdk.services.iam.RoleProps
import software.amazon.awscdk.services.iam.ServicePrincipal
import software.amazon.awscdk.services.lambda.Code
import software.amazon.awscdk.services.lambda.Function
import software.amazon.awscdk.services.lambda.FunctionProps
import software.amazon.awscdk.services.lambda.Runtime.JAVA8
import software.amazon.awscdk.services.stepfunctions.StateMachine
import software.amazon.awscdk.services.stepfunctions.StateMachineProps
import software.amazon.awscdk.services.stepfunctions.Task
import software.amazon.awscdk.services.stepfunctions.TaskProps
import java.util.*

class MyLambdaStack @JvmOverloads constructor(
    parent: App,
    name: String,
    localCodePath: String,
    props: StackProps? = null
) : Stack(parent, name, props) {

    init {

        val namespace = "futz"
        val roleName = "$namespace-role"
        val funcName = "$namespace-func"
        val stateMachineName = "$namespace-sm"
        val taskName = "$namespace-hello-task"

        // TODO required?
        val lambdaRole = Role(this, roleName, RoleProps.builder()
                .withAssumedBy(ServicePrincipal("lambda.amazonaws.com"))
                .withManagedPolicyArns(Arrays.asList("arn:aws:iam::aws:policy/service-role/AWSLambdaBasicExecutionRole"))
                .build()
        )

        // "build/libs/app.zip"
        val code = Code.asset(localCodePath)

        val func = Function(
            this, funcName, FunctionProps.builder()
                .withFunctionName(funcName)
                .withCode(code)
                .withHandler("io.futz.MyFunctionKt::handle")
                .withMemorySize(256)
                .withTimeout(60)
                .withRole(lambdaRole)
                .withRuntime(JAVA8)
                .build()
        )

        val helloTask = Task(
            this, taskName, TaskProps.builder()
                .withResource(func)
                .build()
        )

        StateMachine(
            this, stateMachineName, StateMachineProps.builder()
                .withStateMachineName(stateMachineName)
                .withTimeoutSec(60)
                .withDefinition(helloTask)
                .build()
        )
    }
}
