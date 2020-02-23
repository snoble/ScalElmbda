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
	"@maven//:io_circe_circe_parser_2_12_0_13_0",
	"@maven//:io_circe_circe_jawn_2_12_0_13_0",
	"@maven//:org_typelevel_cats_core_2_12_2_1_0",
	"@maven//:org_typelevel_cats_kernel_2_12_2_1_0",
	"@maven//:org_typelevel_cats_macros_2_12_2_1_0",
	"@maven//:org_typelevel_jawn_parser_2_12_1_0_0",
    ],
    visibility = ["//visibility:public"],
)

java_library(
    name = "protobuf",
    exports = [
        "@maven//:com_google_protobuf_protobuf_java",
    ],
    visibility = ["//visibility:public"],
)

load("@rules_graal//graal:graal.bzl", "graal_binary")
graal_binary(
    name = "scalelmbda-simple-native",
    deps = ["//src/main/scala/snoble/scalelmbda:lib_scalelmbda_simple"],
    main_class = "snoble.scalelmbda.ScalElmbdaSimple",
    reflection_configuration = "reflectconfig",
    graal_extra_args = ["--enable-http"],
)

load("@io_bazel_rules_scala//scala:scala.bzl", "scala_binary", "scala_library")
scala_binary(
    name = "scalelmbda-simple-jvm",
    deps = ["//src/main/scala/snoble/scalelmbda:lib_scalelmbda_simple"],
    main_class = "snoble.scalelmbda.ScalElmbdaSimple",
)

genrule(
    name = "scalelmbda-zip",
    srcs = ["bootstrap", ":scalelmbda-simple-native"],
    tools = ["@bazel_tools//tools/zip:zipper"],
    outs = ["scalelmbda.zip"],
    cmd = "$(location @bazel_tools//tools/zip:zipper) c $@ bootstrap $(locations :scalelmbda-simple-native)",
)
