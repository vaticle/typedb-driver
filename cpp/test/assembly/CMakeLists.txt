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

cmake_minimum_required(VERSION 3.10)
if(NOT DEFINED TYPEDB_ASSEMBLY)
    message(FATAL_ERROR "TYPEDB_ASSEMBLY is not set")
ENDIF()

project (TestAssembly)

set(CMAKE_CXX_STANDARD 17)
link_directories(${TYPEDB_ASSEMBLY}/lib)
include_directories(${TYPEDB_ASSEMBLY}/include)

add_executable(test_assembly test.cpp)
IF (WIN32)
    target_link_libraries(test_assembly typedb-driver-cpp.if.lib)
ELSEIF (APPLE)
    target_link_libraries(test_assembly typedb-driver-cpp "-framework CoreFoundation")
ELSE()
    target_link_libraries(test_assembly typedb-driver-cpp)
ENDIF()
