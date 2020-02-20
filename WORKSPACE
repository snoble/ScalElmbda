load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

rules_scala_version="81238bba1b97f538a3fb30353829ff4d80550ff9" # update this as needed

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")
http_archive(
    name = "io_bazel_rules_scala",
    strip_prefix = "rules_scala-%s" % rules_scala_version,
    type = "zip",
    url = "https://github.com/bazelbuild/rules_scala/archive/%s.zip" % rules_scala_version,
    sha256 = "2104cab197ca601e13e0af64936e090ae74bc2c0e76eb4d0a1aa8180aef0959e",
)

load("@io_bazel_rules_scala//scala:toolchains.bzl", "scala_register_toolchains")
scala_register_toolchains()

load("@io_bazel_rules_scala//scala:scala.bzl", "scala_repositories")
scala_repositories()

protobuf_version="3.11.4"
protobuf_version_sha256="a79d19dcdf9139fa4b81206e318e33d245c4c9da1ffed21c87288ed4380426f9"


http_archive(
    name = "com_google_protobuf",
    url = "https://github.com/protocolbuffers/protobuf/archive/v%s.tar.gz" % protobuf_version,
    strip_prefix = "protobuf-%s" % protobuf_version,
    sha256 = protobuf_version_sha256,
)

# bazel-skylib 0.8.0 released 2019.03.20 (https://github.com/bazelbuild/bazel-skylib/releases/tag/0.8.0)
skylib_version = "0.8.0"
http_archive(
    name = "bazel_skylib",
    type = "tar.gz",
    url = "https://github.com/bazelbuild/bazel-skylib/releases/download/{}/bazel-skylib.{}.tar.gz".format (skylib_version, skylib_version),
    sha256 = "2ef429f5d7ce7111263289644d233707dba35e39696377ebab8b0bc701f7818e",
)

load("@io_bazel_rules_scala//scala_proto:toolchains.bzl", "scala_proto_register_toolchains")
scala_proto_register_toolchains()

load("@io_bazel_rules_scala//scala_proto:scala_proto.bzl", "scala_proto_repositories")
scala_proto_repositories()


load("@bazel_tools//tools/build_defs/repo:git.bzl", "git_repository")
# git_repository(
#    name = "rules_graal",
#    remote = "git@github.com:snoble/rules_graal.git",
#    commit = "e64fc3d122f38d2cb7ee55d54611957d93a70bb0",
# )

local_repository(
    name = "rules_graal",
    path = "/home/steven/Code/rules_graal",
)

load("@rules_graal//graal:graal_bindist.bzl", "graal_bindist_repository")

graal_bindist_repository(
    name = "graal",
    version = "19.3.1",
    java_version = "8"
)

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

http_archive(
    name = "rules_proto",
    sha256 = "602e7161d9195e50246177e7c55b2f39950a9cf7366f74ed5f22fd45750cd208",
    strip_prefix = "rules_proto-97d8af4dc474595af3900dd85cb3a29ad28cc313",
    urls = [
        "https://mirror.bazel.build/github.com/bazelbuild/rules_proto/archive/97d8af4dc474595af3900dd85cb3a29ad28cc313.tar.gz",
        "https://github.com/bazelbuild/rules_proto/archive/97d8af4dc474595af3900dd85cb3a29ad28cc313.tar.gz",
    ],
)
load("@rules_proto//proto:repositories.bzl", "rules_proto_dependencies", "rules_proto_toolchains")
rules_proto_dependencies()
rules_proto_toolchains()

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

RULES_JVM_EXTERNAL_TAG = "3.0"
RULES_JVM_EXTERNAL_SHA = "62133c125bf4109dfd9d2af64830208356ce4ef8b165a6ef15bbff7460b35c3a"

http_archive(
    name = "rules_jvm_external",
    strip_prefix = "rules_jvm_external-%s" % RULES_JVM_EXTERNAL_TAG,
    sha256 = RULES_JVM_EXTERNAL_SHA,
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/%s.zip" % RULES_JVM_EXTERNAL_TAG,
)

load("@rules_jvm_external//:defs.bzl", "maven_install")

maven_install(
    artifacts = [
	"org.scalaj:scalaj-http_2.12:2.4.2",
	"io.circe:circe-core_2.12:0.13.0",
	"io.circe:circe-numbers_2.12:0.13.0",
	"io.circe:circe-parser_2.12:0.13.0",
	"io.circe:circe-jawn_2.12:0.13.0",
	"org.typelevel:cats-core_2.12:2.1.0",
	"org.typelevel:cats-macros_2.12:2.1.0",
	"org.typelevel:cats-kernel_2.12:2.1.0",
	"org.typelevel:jawn-parser_2.12:1.0.0",
        "com.google.protobuf:protobuf-java:3.11.3",
        "com.google.api.grpc:proto-google-common-protos:1.17.0",
    ],
    repositories = [
        "https://repo1.maven.org/maven2/",
    ],
    fetch_sources = True,
)
