#
# Copyright (C) 2021 Vaticle
#
# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#   http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied.  See the License for the
# specific language governing permissions and limitations
# under the License.
#

package(default_visibility = ["//visibility:public"])
exports_files(["VERSION"], visibility = ["//visibility:public"])

load("@vaticle_dependencies//tool/release:rules.bzl", "release_validate_deps")
load("@vaticle_dependencies//tool/checkstyle:rules.bzl", "checkstyle_test")
load("@vaticle_dependencies//distribution/maven:version.bzl", "version")
load("@vaticle_dependencies//library/maven:artifacts.bzl", artifacts_org = "artifacts")
load("//dependencies/maven:artifacts.bzl", artifacts_repo = "overrides")
load("@vaticle_bazel_distribution//maven:rules.bzl", "assemble_maven", "deploy_maven")
load("@vaticle_bazel_distribution//github:rules.bzl", "deploy_github")
load("@vaticle_dependencies//distribution:deployment.bzl", "deployment")
load("//:deployment.bzl", github_deployment = "deployment")

exports_files(["VERSION", "RELEASE_TEMPLATE.md", "deployment.bzl"])

java_library(
    name = "client-java",
    srcs = glob(["*.java"]),
    deps = [
        # Internal dependencies
        "//api:api",
        "//cluster:cluster",
        "//common:common",
        "//core:core",

        # External dependencies from @vaticle
        "@vaticle_typedb_common//:common",
    ],
    resources = ["LICENSE"],
    tags = ["maven_coordinates=com.vaticle.typedb:typedb-client:{pom_version}"],
)

checkstyle_test(
    name = "checkstyle",
    include = glob(["*", ".grabl/*"]),
    exclude = glob(["docs/*"]),
    license_type = "apache",
    size = "small",
)

assemble_maven(
    name = "assemble-maven",
    target = ":client-java",
    workspace_refs = "@vaticle_typedb_client_java_workspace_refs//:refs.json",
    version_overrides = version(artifacts_org, artifacts_repo),
    project_name = "TypeDB Client Java",
    project_description = "TypeDB Client API for Java",
    project_url = "https://github.com/vaticle/typedb-client-java",
    scm_url = "https://github.com/vaticle/typedb-client-java",
)

deploy_maven(
    name = "deploy-maven",
    target = ":assemble-maven",
    snapshot = deployment["maven.snapshot"],
    release = deployment["maven.release"],
)

deploy_github(
    name = "deploy-github",
    organisation = github_deployment["github.organisation"],
    repository = github_deployment["github.repository"],
    release_description = "//:RELEASE_TEMPLATE.md",
    title = "TypeDB Client Java",
    title_append_version = True,
    draft = False
)

release_validate_deps(
    name = "release-validate-deps",
    refs = "@vaticle_typedb_client_java_workspace_refs//:refs.json",
    tagged_deps = [
        "@vaticle_typedb_common",
        "@vaticle_typeql_lang_java",
        "@vaticle_typedb_protocol",
        "@vaticle_factory_tracing",
    ],
    tags = ["manual"]  # in order for bazel test //... to not fail
)

# CI targets that are not declared in any BUILD file, but are called externally
filegroup(
    name = "ci",
    data = [
        "@vaticle_dependencies//tool/checkstyle:test-coverage",
        "@vaticle_dependencies//tool/release:create-notes",
        "@vaticle_dependencies//tool/sonarcloud:code-analysis",
        "@vaticle_dependencies//tool/unuseddeps:unused-deps",
    ],
)
