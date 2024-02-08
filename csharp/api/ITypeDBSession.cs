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

using com.vaticle.typedb.driver;
using com.vaticle.typedb.driver.Common.Exception;
using InternalError = com.vaticle.typedb.driver.Common.Exception.Error.Internal;

namespace com.vaticle.typedb.driver.Api
{
    public interface ITypeDBSession : IDisposable
    {

        /**
         * Checks whether this session is open.
         *
         * <h3>Examples</h3>
         * <pre>
         * session.IsOpen();
         * </pre>
         */
        bool IsOpen();

        /**
         * The current session’s type (SCHEMA or DATA).
         */
        SessionType Type();

        /**
         * Returns the name of the database of the session.
         *
         * <h3>Examples</h3>
         * <pre>
         * session.DatabaseName();
         * </pre>
         */
        string DatabaseName();

        /**
         * Gets the options for the session.
         */
        TypeDBOptions Options();

        /**
         * Opens a transaction on the database connected to the session with default options.
         *
         * @see ITypeDBSession#Transaction(TransactionType, TypeDBOptions)
         */
        ITypeDBTransaction Transaction(TransactionType type);

        /**
         * Opens a transaction to perform read or write queries on the database connected to the session.
         *
         * <h3>Examples</h3>
         * <pre>
         * session.Transaction(transactionType, options);
         * </pre>
         *
         * @param type The type of transaction to be created (READ or WRITE)
         * @param options Options for the session
         */
        ITypeDBTransaction Transaction(TransactionType type, TypeDBOptions options);

        /**
         * Registers a callback function which will be executed when this session is closed.
         *
         * <h3>Examples</h3>
         * <pre>
         * session.OnClose(function)
         * </pre>
         *
         * @param function The callback function.
         */
        void OnClose(Action function);

        /**
         * Registers a callback function which will be executed when this session is reopened.
         * A session may be closed if it times out, or loses the connection to the database.
         * In such situations, the session is reopened automatically when opening a new transaction.
         *
         * <h3>Examples</h3>
         * <pre>
         * session.OnReopen(function)
         * </pre>
         *
         * @param function The callback function.
         */
        void OnReopen(Action function);

        /**
         * Closes the session. Before opening a new session, the session currently open should first be closed.
         *
         * <h3>Examples</h3>
         * <pre>
         * session.Close();
         * </pre>
         */
        void Close();
        }

    /**
     * Used to specify the type of the session.
     *
     * <h3>Examples</h3>
     * <pre>
     * driver.Session(database, SessionType.SCHEMA);
     * </pre>
     */
    public class SessionType
    {
        public readonly int Id;
        public readonly pinvoke.SessionType NativeObject;

        public static readonly SessionType DATA = new SessionType(1, pinvoke.SessionType.Data);
        public static readonly SessionType SCHEMA = new SessionType(2, pinvoke.SessionType.Schema);

        private SessionType(int id, pinvoke.SessionType nativeObject)
        {
            Id = id;
            NativeObject = nativeObject;
        }
    }
}
