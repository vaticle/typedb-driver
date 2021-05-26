/*
 * Copyright (C) 2021 Vaticle
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

package com.vaticle.typedb.client.common.rpc;

import com.vaticle.typedb.client.common.exception.TypeDBClientException;
import io.grpc.ManagedChannel;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NettyChannelBuilder;
import io.netty.handler.ssl.SslContext;

import javax.annotation.Nullable;
import javax.net.ssl.SSLException;
import java.nio.file.Path;

public abstract class TypeDBConnectionFactory {

    public abstract ManagedChannel newManagedChannel(String address);
    public abstract TypeDBStub.Core newTypeDBStub(ManagedChannel channel);

    ManagedChannel plainTextChannel(String address) {
        return NettyChannelBuilder.forTarget(address)
                .usePlaintext()
                .build();
    }

    public static class Core extends TypeDBConnectionFactory {

        @Override
        public ManagedChannel newManagedChannel(String address) {
            return plainTextChannel(address);
        }

        @Override
        public TypeDBStub.Core newTypeDBStub(ManagedChannel channel) {
            return TypeDBStub.core(channel);
        }
    }

    public static class ClusterNode extends TypeDBConnectionFactory {

        private final String username;
        private final String password;
        private final boolean tlsEnabled;
        @Nullable
        private final Path tlsRootCA;

        public ClusterNode(String username, String password, boolean tlsEnabled, @Nullable Path tlsRootCA) {
            this.username = username;
            this.password = password;
            this.tlsEnabled = tlsEnabled;
            this.tlsRootCA = tlsRootCA;
        }

        @Override
        public ManagedChannel newManagedChannel(String address) {
            if (!tlsEnabled) {
                return plainTextChannel(address);
            } else {
                return TLSChannel(address);
            }
        }

        private ManagedChannel TLSChannel(String address) {
            try {
                SslContext sslContext;
                if (tlsRootCA != null) {
                    sslContext = GrpcSslContexts.forClient().trustManager(tlsRootCA.toFile()).build();
                } else {
                    sslContext = GrpcSslContexts.forClient().build();
                }
                return NettyChannelBuilder.forTarget(address).useTransportSecurity().sslContext(sslContext).build();
            } catch (SSLException e) {
                throw new TypeDBClientException(e.getMessage(), e);
            }
        }

        @Override
        public TypeDBStub.Core newTypeDBStub(ManagedChannel channel) {
            return TypeDBStub.clusterNode(username, password, channel);
        }
    }
}
