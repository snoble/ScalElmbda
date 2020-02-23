# ScalElmbda

## Commands
Update protobuf code for elm: `protoc --elm_out=./src/main/elm/proto/ ./src/main/proto/*.proto`

This command requires Protobuf to be installed as well as the elm extension.
You can install the latter with `npm install --global protoc-gen-elm`.

Build zip file for aws sam: `bazel build :scalambda-zip`

Run elm locally: `elm reactor`

Run sam locally: `sam local start-api`

