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

using System;
using System.Collections.Generic;

using com.vaticle.typedb.driver.Api;
using com.vaticle.typedb.driver.Common.Exception;
using InternalError = com.vaticle.typedb.driver.Common.Exception.Error.Internal;

namespace com.vaticle.typedb.driver.Api
{
    public interface ITypeDBTransaction : IDisposable
    {
        /**
         * Checks whether this transaction is open.
         *
         * <h3>Examples</h3>
         * <pre>
         * transaction.IsOpen();
         * </pre>
         */
        bool IsOpen();

        /**
         * The transaction’s type (READ or WRITE).
         */
        TransactionType Type();

        /**
         * The options for the transaction.
         */
        public TypeDBOptions Options();

        /**
         * The <code>ConceptManager</code> for this transaction, providing access to all Concept API methods.
         */
//        IConceptManager Concepts(); // TODO

        /**
         * The <code>LogicManager</code> for this Transaction, providing access to all Concept API - Logic methods.
         */
//        ILogicManager Logic(); // TODO

        /**
         * The<code></code>QueryManager<code></code> for this Transaction, from which any TypeQL query can be executed.
         */
//        IQueryManager Query(); // TODO

        /**
         * Registers a callback function which will be executed when this transaction is closed.
         *
         * <h3>Examples</h3>
         * <pre>
         * transaction.OnClose(function);
         * </pre>
         *
         * @param function The callback function.
         */
        void OnClose(Action<Exception> function);

        /**
         * Commits the changes made via this transaction to the TypeDB database.
         * Whether or not the transaction is commited successfully, it gets closed after the commit call.
         *
         * <h3>Examples</h3>
         * <pre>
         * transaction.Commit()
         * </pre>
         */
        void Commit();

        /**
         * Rolls back the uncommitted changes made via this transaction.
         *
         * <h3>Examples</h3>
         * <pre>
         * transaction.Rollback()
         * </pre>
         */
        void Rollback();

        /**
         * Closes the transaction.
         *
         * <h3>Examples</h3>
         * <pre>
         * transaction.Close()
         * </pre>
         */
        void Close();
    }

    /**
     * Used to specify the type of transaction.
     *
     * <h3>Examples</h3>
     * <pre>
     * session.Transaction(TransactionType.READ);
     * </pre>
     */
    public class TransactionType
    {
        public readonly int Id;
        public readonly pinvoke.TransactionType NativeObject;

        public static readonly TransactionType READ = new TransactionType(1, pinvoke.TransactionType.Read);
        public static readonly TransactionType WRITE = new TransactionType(2, pinvoke.TransactionType.Write);

        private TransactionType(int id, pinvoke.TransactionType nativeObject)
        {
            Id = id;
            NativeObject = nativeObject;
        }
    }
}
