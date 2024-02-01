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

namespace com.vaticle.typedb.driver.Api
{
    using TransactionTypeNativeToOwn = Dictionary<pinvoke.TransactionType, ITypeDBTransaction.TransactionType>;

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
         * The transaction’s type (Read or Write).
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

        /**
         * Used to specify the type of transaction.
         *
         * <h3>Examples</h3>
         * <pre>
         * session.Transaction(TransactionType.Value.Read);
         * </pre>
         */
        public struct TransactionType
        {
            public enum Value : int
            {
                Read = 0,
                Write = 1
            }

            static TransactionType Of(pinvoke.TransactionType transactionType)
            {
                TransactionType resultType;
                if (s_transactionTypeNativeToOwnAll.TryGetValue(transactionType, out resultType))
                {
                    return resultType;
                }
                return resultType; // Temp
// TODO:
//                throw new TypeDBDriverException(UNEXPECTED_NATIVE_VALUE);
            }

            int Id() // TODO: Maybe rename to Value
            {
                return (int)_value;
            }

            bool IsRead()
            {
                return !_isWrite;
            }

            bool IsWrite()
            {
                return _isWrite;
            }

            private TransactionType(Value value, pinvoke.TransactionType nativeObject)
            {
                _value = value;
                NativeObject = nativeObject;
                _isWrite = NativeObject == pinvoke.TransactionType.Write;
            }

            private readonly Value _value;
            private readonly bool _isWrite;
            public readonly pinvoke.TransactionType NativeObject;

            private static TransactionTypeNativeToOwn s_transactionTypeNativeToOwnAll =
                new TransactionTypeNativeToOwn()
                {
                    {pinvoke.TransactionType.Read, new TransactionType(Value.Read, pinvoke.TransactionType.Read)},
                    {pinvoke.TransactionType.Write, new TransactionType(Value.Write, pinvoke.TransactionType.Write)}
                };
        }
    }
}
