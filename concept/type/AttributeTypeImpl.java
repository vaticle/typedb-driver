/*
 * Copyright (C) 2022 Vaticle
 *
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

package com.vaticle.typedb.client.concept.type;

import com.vaticle.typedb.client.api.TypeDBTransaction;
import com.vaticle.typedb.client.api.concept.Value;
import com.vaticle.typedb.client.api.concept.thing.Attribute;
import com.vaticle.typedb.client.api.concept.type.AttributeType;
import com.vaticle.typedb.client.concept.ConceptManagerImpl;
import com.vaticle.typedb.client.concept.thing.AttributeImpl;
import com.vaticle.typedb.client.jni.Transitivity;
import com.vaticle.typeql.lang.common.TypeQLToken;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.stream.Stream;

import static com.vaticle.typedb.client.jni.typedb_client_jni.attribute_type_get;
import static com.vaticle.typedb.client.jni.typedb_client_jni.attribute_type_get_instances;
import static com.vaticle.typedb.client.jni.typedb_client_jni.attribute_type_get_owners;
import static com.vaticle.typedb.client.jni.typedb_client_jni.attribute_type_get_regex;
import static com.vaticle.typedb.client.jni.typedb_client_jni.attribute_type_get_subtypes;
import static com.vaticle.typedb.client.jni.typedb_client_jni.attribute_type_get_subtypes_with_value_type;
import static com.vaticle.typedb.client.jni.typedb_client_jni.attribute_type_get_supertype;
import static com.vaticle.typedb.client.jni.typedb_client_jni.attribute_type_get_supertypes;
import static com.vaticle.typedb.client.jni.typedb_client_jni.attribute_type_get_value_type;
import static com.vaticle.typedb.client.jni.typedb_client_jni.attribute_type_put;
import static com.vaticle.typedb.client.jni.typedb_client_jni.attribute_type_set_regex;
import static com.vaticle.typedb.client.jni.typedb_client_jni.attribute_type_set_supertype;
import static com.vaticle.typedb.client.jni.typedb_client_jni.attribute_type_unset_regex;
import static java.util.Collections.emptySet;

public class AttributeTypeImpl extends ThingTypeImpl implements AttributeType {
    public AttributeTypeImpl(com.vaticle.typedb.client.jni.Concept concept) {
        super(concept);
    }

    @Override
    public ValueType getValueType() {
        return ValueType.of(attribute_type_get_value_type(concept));
    }

    @Override
    public final void setSupertype(TypeDBTransaction transaction, AttributeType attributeType) {
        attribute_type_set_supertype(((ConceptManagerImpl) transaction.concepts()).transaction, concept, ((AttributeTypeImpl) attributeType).concept);
    }

    @Nullable
    @Override
    public AttributeTypeImpl getSupertype(TypeDBTransaction transaction) {
        com.vaticle.typedb.client.jni.Concept res = attribute_type_get_supertype(((ConceptManagerImpl) transaction.concepts()).transaction, concept);
        if (res != null) return new AttributeTypeImpl(res);
        else return null;
    }

    @Override
    public final Stream<AttributeTypeImpl> getSupertypes(TypeDBTransaction transaction) {
        return attribute_type_get_supertypes(((ConceptManagerImpl) transaction.concepts()).transaction, concept).stream().map(AttributeTypeImpl::new);
    }

    @Override
    public final Stream<AttributeTypeImpl> getSubtypes(TypeDBTransaction transaction) {
        return attribute_type_get_subtypes(((ConceptManagerImpl) transaction.concepts()).transaction, concept, Transitivity.Transitive).stream().map(AttributeTypeImpl::new);
    }

    @Override
    public final Stream<AttributeTypeImpl> getSubtypes(TypeDBTransaction transaction, ValueType valueType) {
        return attribute_type_get_subtypes_with_value_type(
                ((ConceptManagerImpl) transaction.concepts()).transaction, concept,
                valueType.asJNI(), Transitivity.Transitive
        ).stream().map(AttributeTypeImpl::new);
    }

    @Override
    public final Stream<AttributeTypeImpl> getSubtypesExplicit(TypeDBTransaction transaction) {
        return attribute_type_get_subtypes(((ConceptManagerImpl) transaction.concepts()).transaction, concept, Transitivity.Explicit).stream().map(AttributeTypeImpl::new);
    }

    @Override
    public final Stream<AttributeImpl> getInstances(TypeDBTransaction transaction) {
        return attribute_type_get_instances(((ConceptManagerImpl) transaction.concepts()).transaction, concept, Transitivity.Transitive).stream().map(AttributeImpl::new);
    }

    @Override
    public final Stream<AttributeImpl> getInstancesExplicit(TypeDBTransaction transaction) {
        return attribute_type_get_instances(((ConceptManagerImpl) transaction.concepts()).transaction, concept, Transitivity.Explicit).stream().map(AttributeImpl::new);
    }

    @Override
    public Stream<ThingTypeImpl> getOwners(TypeDBTransaction transaction) {
        return getOwners(transaction, emptySet());
    }

    @Override
    public Stream<ThingTypeImpl> getOwners(TypeDBTransaction transaction, Set<TypeQLToken.Annotation> annotations) {
        return attribute_type_get_owners(((ConceptManagerImpl) transaction.concepts()).transaction, concept, Transitivity.Transitive, annotations.stream().map(TypeQLToken.Annotation::toString).toArray(String[]::new)).stream().map(ThingTypeImpl::of);
    }

    @Override
    public Stream<ThingTypeImpl> getOwnersExplicit(TypeDBTransaction transaction) {
        return getOwnersExplicit(transaction, emptySet());
    }

    @Override
    public Stream<ThingTypeImpl> getOwnersExplicit(TypeDBTransaction transaction, Set<TypeQLToken.Annotation> annotations) {
        return attribute_type_get_owners(((ConceptManagerImpl) transaction.concepts()).transaction, concept, Transitivity.Explicit, annotations.stream().map(TypeQLToken.Annotation::toString).toArray(String[]::new)).stream().map(ThingTypeImpl::of);
    }

    @Override
    public final Attribute put(TypeDBTransaction transaction, Value value) {
        return new AttributeImpl(attribute_type_put(((ConceptManagerImpl) transaction.concepts()).transaction, concept, value.value));
    }

    @Nullable
    @Override
    public final Attribute get(TypeDBTransaction transaction, Value value) {
        com.vaticle.typedb.client.jni.Concept res = attribute_type_get(((ConceptManagerImpl) transaction.concepts()).transaction, concept, value.value);
        if (res != null) return new AttributeImpl(res);
        else return null;
    }

    @Override
    public String getRegex(TypeDBTransaction transaction) {
        return attribute_type_get_regex(((ConceptManagerImpl) transaction.concepts()).transaction, concept);
    }

    @Override
    public void setRegex(TypeDBTransaction transaction, String regex) {
        attribute_type_set_regex(((ConceptManagerImpl) transaction.concepts()).transaction, concept, regex);
    }

    @Override
    public void unsetRegex(TypeDBTransaction transaction) {
        attribute_type_unset_regex(((ConceptManagerImpl) transaction.concepts()).transaction, concept);
    }
}
