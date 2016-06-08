package com.isuwang.dapeng.remoting;

import com.isuwang.org.apache.thrift.TException;
import com.isuwang.dapeng.core.TBeanSerializer;

import java.util.concurrent.Future;

/**
 * @author craneding
 * @date 16/3/1
 */
public interface SoaConnection {

    <REQ, RESP> RESP send(REQ request, RESP response, TBeanSerializer<REQ> requestSerializer, TBeanSerializer<RESP> responseSerializer) throws TException;

    <REQ, RESP> Future<RESP> sendAsync(REQ request, RESP response, TBeanSerializer<REQ> requestSerializer, TBeanSerializer<RESP> responseSerializer, long timeout) throws TException;

}
