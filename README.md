# ScaLambda

## Commands
Update protobuf code for elm: `protoc --elm_out=./src/main/elm/proto/ ./src/main/proto/*.proto`

Build zip file for aws sam: `bazel build :scalambda-zip`

Run elm locally: `elm reactor`

Run sam locally: `sam local start-api`

