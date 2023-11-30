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
#pragma once

#include "typedb/answer/conceptmap.hpp"
#include "typedb/common/iterator.hpp"
#include "typedb/common/native.hpp"
#include "typedb/concept/concept.hpp"

namespace TypeDB {

class ConceptMapGroup {
   public:
    ConceptMapGroup(_native::ConceptMapGroup*);
    ConceptMapGroup(const ConceptMapGroup&) = delete;
    ConceptMapGroup(ConceptMapGroup&&) = default;

    ConceptMapGroup& operator=(const ConceptMapGroup&) = delete;
    ConceptMapGroup& operator=(ConceptMapGroup&&) = default;
    ~ConceptMapGroup() = default;

    std::unique_ptr<Concept> owner();
    ConceptMapIterable conceptMaps();

   private:
    NativePointer<_native::ConceptMapGroup> conceptMapGroupNative;

    friend class TypeDBIteratorHelper<_native::ConceptMapGroupIterator, _native::ConceptMapGroup, TypeDB::ConceptMapGroup>;
};

// For ConceptMapGroup
using ConceptMapGroupIterable = TypeDBIterable<_native::ConceptMapGroupIterator, _native::ConceptMapGroup, TypeDB::ConceptMapGroup>;
using ConceptMapGroupIterator = TypeDBIterator<_native::ConceptMapGroupIterator, _native::ConceptMapGroup, TypeDB::ConceptMapGroup>;

}  // namespace TypeDB
