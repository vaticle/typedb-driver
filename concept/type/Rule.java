/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package grakn.client.concept.type;

import grakn.client.Grakn;
import graql.lang.pattern.Pattern;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public interface Rule extends Type {

    @Override
    Rule.Remote asRemote(Grakn.Transaction transaction);

    interface Local extends Type.Local, Rule {
    }

    interface Remote extends Type.Remote, Rule {

        @Nullable
        Pattern getWhen();

        @Nullable
        Pattern getThen();

        @Override
        Stream<? extends Rule.Local> getSupertypes();

        @Override
        Stream<? extends Rule.Local> getSubtypes();
    }
}
