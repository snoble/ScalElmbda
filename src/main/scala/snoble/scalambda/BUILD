load("@io_bazel_rules_scala//scala:scala.bzl", "scala_binary")
scala_binary(
    name = "scala_scalambda",
    srcs = ["Scalambda.scala"],
    deps = ["//src/main/java/snoble/scalambda:java_scalambda"],
    main_class = "snoble.scalambda.Scalambda",
    jvm_flags = [ "-Djava.library.path=$(pwd)/../../../../../go/linux_amd64_stripped_c-shared" ],
)
