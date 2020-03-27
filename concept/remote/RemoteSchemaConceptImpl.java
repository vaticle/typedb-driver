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

package grakn.client.concept.remote;

import grakn.client.GraknClient;
import grakn.client.concept.ConceptId;
import grakn.client.concept.Label;
import grakn.client.concept.SchemaConcept;
import grakn.client.exception.GraknClientException;
import grakn.client.rpc.RequestBuilder;
import grakn.protocol.session.ConceptProto;

import javax.annotation.Nullable;
import java.util.stream.Stream;

/**
 * Client implementation of SchemaConcept
 */
public abstract class RemoteSchemaConceptImpl<
        SomeRemoteSchemaConceptType extends RemoteSchemaConcept<SomeRemoteSchemaConceptType, SomeSchemaConceptType>,
        SomeSchemaConceptType extends SchemaConcept<SomeSchemaConceptType>>
        extends RemoteConceptImpl<SomeRemoteSchemaConceptType, SomeSchemaConceptType>
        implements RemoteSchemaConcept<SomeRemoteSchemaConceptType, SomeSchemaConceptType> {

    RemoteSchemaConceptImpl(GraknClient.Transaction tx, ConceptId id) {
        super(tx, id);
    }

    public final SomeRemoteSchemaConceptType sup(SomeSchemaConceptType type) {
        ConceptProto.Method.Req method = ConceptProto.Method.Req.newBuilder()
                .setSchemaConceptSetSupReq(ConceptProto.SchemaConcept.SetSup.Req.newBuilder()
                                                   .setSchemaConcept(RequestBuilder.ConceptMessage.from(type))).build();

        runMethod(method);
        return asCurrentBaseType(this);
    }

    @Override
    public final Label label() {
        ConceptProto.Method.Req method = ConceptProto.Method.Req.newBuilder()
                .setSchemaConceptGetLabelReq(ConceptProto.SchemaConcept.GetLabel.Req.getDefaultInstance()).build();

        return Label.of(runMethod(method).getSchemaConceptGetLabelRes().getLabel());
    }

    @Override
    public final Boolean isImplicit() {
        ConceptProto.Method.Req method = ConceptProto.Method.Req.newBuilder()
                .setSchemaConceptIsImplicitReq(ConceptProto.SchemaConcept.IsImplicit.Req.getDefaultInstance()).build();

        return runMethod(method).getSchemaConceptIsImplicitRes().getImplicit();
    }

    @Override
    public final SomeRemoteSchemaConceptType label(Label label) {
        ConceptProto.Method.Req method = ConceptProto.Method.Req.newBuilder()
                .setSchemaConceptSetLabelReq(ConceptProto.SchemaConcept.SetLabel.Req.newBuilder()
                                                     .setLabel(label.getValue())).build();

        runMethod(method);
        return asCurrentBaseType(this);
    }

    @Nullable
    public final SomeRemoteSchemaConceptType sup() {
        ConceptProto.Method.Req method = ConceptProto.Method.Req.newBuilder()
                .setSchemaConceptGetSupReq(ConceptProto.SchemaConcept.GetSup.Req.getDefaultInstance()).build();

        ConceptProto.SchemaConcept.GetSup.Res response = runMethod(method).getSchemaConceptGetSupRes();

        switch (response.getResCase()) {
            case NULL:
                return null;
            case SCHEMACONCEPT:
                return RemoteConcept.of(response.getSchemaConcept(), tx());
            default:
                throw GraknClientException.unreachableStatement("Unexpected response " + response);
        }

    }

    @Override
    public final Stream<SomeRemoteSchemaConceptType> sups() {
        return tx().sups(this).filter(this::equalsCurrentBaseType).map(this::asCurrentBaseType);
    }

    @Override
    public final Stream<SomeRemoteSchemaConceptType> subs() {
        ConceptProto.Method.Req method = ConceptProto.Method.Req.newBuilder()
                .setSchemaConceptSubsReq(ConceptProto.SchemaConcept.Subs.Req.getDefaultInstance()).build();

        int iteratorId = runMethod(method).getSchemaConceptSubsIter().getId();
        return conceptStream(iteratorId, res -> res.getSchemaConceptSubsIterRes().getSchemaConcept()).map(this::asCurrentBaseType);
    }

    abstract boolean equalsCurrentBaseType(RemoteConcept<SomeRemoteSchemaConceptType, SomeSchemaConceptType> other);
}
