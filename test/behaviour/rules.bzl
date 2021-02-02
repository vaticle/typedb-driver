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

load("@graknlabs_common//test/server:rules.bzl", "grakn_java_test")

def grakn_behaviour_java_test(
        name,
        connection_steps_core,
        connection_steps_cluster,
        steps,
        grakn_core_artifact_mac,
        grakn_core_artifact_linux,
        grakn_core_artifact_windows,
        grakn_cluster_artifact_mac,
        grakn_cluster_artifact_linux,
        grakn_cluster_artifact_windows,
        runtime_deps = [],
        **kwargs):

    grakn_java_test(
        name = name + "-core",
        mac_artifact = grakn_core_artifact_mac,
        linux_artifact = grakn_core_artifact_linux,
        windows_artifact = grakn_core_artifact_windows,
        runtime_deps = runtime_deps + [connection_steps_core] + steps,
        **kwargs,
    )

    grakn_java_test(
        name = name + "-cluster",
        mac_artifact = grakn_cluster_artifact_mac,
        linux_artifact = grakn_cluster_artifact_linux,
        windows_artifact = grakn_cluster_artifact_windows,
        runtime_deps = runtime_deps + [connection_steps_cluster] + steps,
        **kwargs,
    )
