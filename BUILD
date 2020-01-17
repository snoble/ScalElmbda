load("@bazel_gazelle//:def.bzl", "gazelle")

# gazelle:prefix github.com/example/project
gazelle(name = "gazelle")


load("@rules_graal//graal:graal.bzl", "graal_binary")
graal_binary(
    name = "scalambda-native",
    deps = ["//src/main/scala/snoble/scalambda:lib_scalambda"],
    main_class = "snoble.scalambda.Scalambda",
)
