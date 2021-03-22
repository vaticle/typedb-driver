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

import grakn.client.api.GraknTransaction;
import grakn.client.api.concept.type.EntityType;
import grakn.client.common.Label;
import grakn.client.concept.thing.EntityImpl;
import grakn.client.concept.thing.ThingImpl;
import grakn.protocol.ConceptProto;

import java.util.stream.Stream;

import static grakn.client.common.rpc.RequestBuilder.Type.EntityType.createReq;

public class EntityTypeImpl extends ThingTypeImpl implements EntityType {

    EntityTypeImpl(Label label, boolean isRoot) {
        super(label, isRoot);
    }

    public static EntityTypeImpl of(ConceptProto.Type typeProto) {
        return new EntityTypeImpl(Label.of(typeProto.getLabel()), typeProto.getRoot());
    }

    @Override
    public EntityTypeImpl.Remote asRemote(GraknTransaction transaction) {
        return new EntityTypeImpl.Remote(transaction, getLabel(), isRoot());
    }

    @Override
    public EntityTypeImpl asEntityType() {
        return this;
    }

    public static class Remote extends ThingTypeImpl.Remote implements EntityType.Remote {

        Remote(GraknTransaction transaction, Label label, boolean isRoot) {
            super(transaction, label, isRoot);
        }

        @Override
        public EntityTypeImpl.Remote asRemote(GraknTransaction transaction) {
            return new EntityTypeImpl.Remote(transaction, getLabel(), isRoot());
        }

        @Override
        public final EntityImpl create() {
            return EntityImpl.of(execute(createReq(getLabel())).getEntityTypeCreateRes().getEntity());
        }

        @Override
        public final void setSupertype(EntityType entityType) {
            super.setSupertype(entityType);
        }

        @Override
        public final Stream<EntityTypeImpl> getSubtypes() {
            return super.getSubtypes().map(ThingTypeImpl::asEntityType);
        }

        @Override
        public final Stream<EntityImpl> getInstances() {
            return super.getInstances().map(ThingImpl::asEntity);
        }

        @Override
        public EntityTypeImpl.Remote asEntityType() {
            return this;
        }
    }
}
