load("@io_bazel_rules_scala//scala:scala.bzl", "scala_binary", "scala_library")

scala_library(
    name = "lib_scalelmbda",
    srcs = ["ScalElmbda.scala"],
    visibility = ["//visibility:public"],
)

scala_library(
    name = "lib_scalelmbda_simple",
    srcs = ["ScalElmbdaSimple.scala"],
    deps = ["//:scalaj", "//:circe", "//src/main/proto:simple_scala_proto", "//:protobuf",],
    visibility = ["//visibility:public"],
)
