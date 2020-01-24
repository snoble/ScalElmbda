load("@io_bazel_rules_scala//scala:scala.bzl", "scala_binary", "scala_library")
scala_binary(
    name = "scala_scalambda",
    srcs = ["Scalambda.scala"],
    main_class = "snoble.scalambda.Scalambda",
    jvm_flags = [ "-Djava.library.path=./src/main/go/linux_amd64_stripped_c-shared" ],
    data = ["//src/main/go:go-scalambda"],
)

scala_library(
    name = "lib_scalambda",
    srcs = ["Scalambda.scala"],
    visibility = ["//visibility:public"],
)
