load("@io_bazel_rules_scala//scala:scala.bzl", "scala_binary", "scala_library")
scala_binary(
    name = "scala_scalambda",
    srcs = ["Scalambda.scala"],
    deps = ["//src/main/java/snoble/scalambda:java_scalambda"],
    main_class = "snoble.scalambda.Scalambda",
    jvm_flags = [ "-Djava.library.path=$(pwd)/../../../../../go/linux_amd64_stripped_c-shared" ],
)

scala_library(
    name = "lib_scalambda",
    srcs = ["Scalambda.scala"],
    deps = ["//src/main/java/snoble/scalambda:java_scalambda"],
    visibility = ["//visibility:public"],
)
