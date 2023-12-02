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

#include "typedb/common/native.hpp"
#include "typedb/concept/conceptmanager.hpp"
#include "typedb/connection/options.hpp"
#include "typedb/logic/logicmanager.hpp"
#include "typedb/query/querymanager.hpp"

namespace TypeDB {

class Transaction;

// forward declaration for friendship
class Session;
class Concept;

class Transaction {
   private:
    NativePointer<_native::Transaction> transactionNative;
    TypeDB::TransactionType txnType;

   public:
    const QueryManager query;
    const ConceptManager concepts;
    const LogicManager logic;

    Transaction();
    Transaction(const Transaction&) = delete;
    Transaction(Transaction&&);
    ~Transaction() = default;

    Transaction& operator=(const Transaction&) = delete;
    Transaction& operator=(Transaction&&);

    TypeDB::TransactionType type() const;

    bool isOpen() const;

    void close();

    void forceClose();

    void commit();

    void rollback();

   private:
    Transaction(_native::Transaction*, TypeDB::TransactionType);
    _native::Transaction* getNative();

    friend class TypeDB::Session;
    friend class TypeDB::QueryManager;
    friend class TypeDB::ConceptManager;
    friend class TypeDB::LogicManager;
    friend class TypeDB::ConceptFactory;
};

}  // namespace TypeDB