load("@rules_java//java:defs.bzl", "java_library",)
load("@rules_proto//proto:defs.bzl", "proto_library",)
load("@io_bazel_rules_scala//scala_proto:scala_proto.bzl", "scala_proto_library",)
load("@io_bazel_rules_scala//scala_proto:scala_proto_toolchain.bzl", "scala_proto_toolchain",)


java_proto_library(
  name = "simple_java_proto",
  deps = [":simple_proto"],
  visibility = ["//visibility:public"],
)

scala_proto_library(
  name = "simple_scala_proto",
  deps = [":simple_proto"],
  visibility = ["//visibility:public"],
)

proto_library(
  name = "simple_proto",
  srcs = ["simple.proto"],
)


