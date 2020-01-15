load("@io_bazel_rules_scala//scala:scala.bzl", "scala_binary")
scala_binary(
    name = "scala_scalambda",
    srcs = ["Scalambda.scala"],
    deps = ["//src/main/go:go_scalambda"],
    main_class = "snoble.scalambda.Scalambda"
)
