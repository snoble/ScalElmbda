# ScaLambda

## Commands
Update protobuf code for elm: `protoc --elm_out=./src/main/elm/proto/ ./src/main/proto/*.proto`
Build zip file for same: `bazel build :scalambda-zip`
Run elm: `elm reactor`
Run sam: `sam local start-api`
