/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.isuwang.org.apache.thrift;

import com.isuwang.dapeng.core.Context;
import com.isuwang.dapeng.core.SoaProcessFunction;
import com.isuwang.dapeng.core.TBeanSerializer;
import com.isuwang.org.apache.thrift.protocol.TProtocol;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * A processor is a generic object which operates upon an input stream and
 * writes to some output stream.
 */
public interface TProcessor<I> {

    I getIface();

    Class<I> getInterfaceClass();

    void setInterfaceClass(Class<I> interfaceClass);

    boolean process(TProtocol in, TProtocol out)
            throws TException;

    CompletableFuture<Context> processAsync(TProtocol in, TProtocol out) throws TException;

    public Map<String, SoaProcessFunction<I, ?, ?, ? extends TBeanSerializer<?>, ? extends TBeanSerializer<?>>> getProcessMapView();
}
