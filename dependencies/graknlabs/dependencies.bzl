#
# GRAKN.AI - THE KNOWLEDGE GRAPH
# Copyright (C) 2018 Grakn Labs Ltd
#
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU Affero General Public License as
# published by the Free Software Foundation, either version 3 of the
# License, or (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU Affero General Public License for more details.
#
# You should have received a copy of the GNU Affero General Public License
# along with this program.  If not, see <https://www.gnu.org/licenses/>.
#

load("@bazel_tools//tools/build_defs/repo:git.bzl", "git_repository")

def graknlabs_grakn_core():
    git_repository(
        name = "graknlabs_grakn_core",
        remote = "https://github.com/lolski/grakn",
        commit = "9bd69fc53c7c13856f7bd578041cea145bea4ac0" # sync-marker: do not remove this comment, this is used for sync-dependencies by @graknlabs_grakn_core
    )

# TODO: revert to graknlabs/build-tools and remove local_repository before merging the PR to master
def graknlabs_build_tools():
#    native.local_repository(
#            name = "graknlabs_build_tools",
#            path = "/Users/lolski/grakn.ai/build-tools"
#        )
    git_repository(
        name = "graknlabs_build_tools",
        remote = "https://github.com/lolski/build-tools",
        commit = "83efaaa2d2a539bc2c101c6704b619a77ccc6e30", # sync-marker: do not remove this comment, this is used for sync-dependencies by @graknlabs_build_tools
    )