load("@bazel_gazelle//:def.bzl", "gazelle")

gazelle(name = "gazelle")

java_library(
    name = "scalaj",
    exports = [
	"@maven//:org_scalaj_scalaj_http_2_12",
    ],
    visibility = ["//visibility:public"],
)

java_library(
    name = "circe",
    exports = [
	"@maven//:io_circe_circe_core_2_12",
	"@maven//:io_circe_circe_numbers_2_12_0_13_0",
	"@maven//:org_typelevel_cats_core_2_12_2_1_0",
	"@maven//:org_typelevel_cats_kernel_2_12_2_1_0",
	"@maven//:org_typelevel_cats_macros_2_12_2_1_0",
    ],
    visibility = ["//visibility:public"],
)

load("@rules_graal//graal:graal.bzl", "graal_binary")
graal_binary(
    name = "scalambda-native",
    deps = ["//src/main/scala/snoble/scalambda:lib_scalambda"],
    data = ["//src/main/go:go-scalambda"],
    main_class = "snoble.scalambda.Scalambda",
    jni_configuration = "jniconfig",
)

graal_binary(
    name = "scalambda-simple-native",
    deps = ["//src/main/scala/snoble/scalambda:lib_scalambda_simple"],
    main_class = "snoble.scalambda.ScalambdaSimple",
    reflection_configuration = "reflectconfig"
)

load("@io_bazel_rules_scala//scala:scala.bzl", "scala_binary", "scala_library")
scala_binary(
    name = "scalambda-simple-jvm",
    deps = ["//src/main/scala/snoble/scalambda:lib_scalambda_simple"],
    main_class = "snoble.scalambda.ScalambdaSimple",
)
