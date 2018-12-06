
# cdk-kotlin-example

A simple CDK application written in Kotlin using the Gradle DSL.


## build

    ./gradlew clean build
    

## synthesize

    cdk synth
        

## deploy

    cdk deploy --profile [YOUR_AWS_PROFILE]
    
        
## todo

- Address [#1294](https://github.com/awslabs/aws-cdk/issues/1294) so `.jar` extension does not fail CDK asset validation.
This will negate the need to rename the final artifact from a jar to a zip.
- Split the CDK bits from the Lambda function bits so we're not packing all the CDK dependencies with the Lambda 
artifact. (This applies to cases where a local path is referenced for Lambda function code only)
     