load("@bazel_gazelle//:def.bzl", "gazelle")

gazelle(name = "gazelle")


load("@rules_graal//graal:graal.bzl", "graal_binary")
graal_binary(
    name = "scalambda-native",
    deps = ["//src/main/go:go-scalambda", "//src/main/scala/snoble/scalambda:lib_scalambda"],
    main_class = "snoble.scalambda.Scalambda",
    jni_configuration = "jniconfig",
)
