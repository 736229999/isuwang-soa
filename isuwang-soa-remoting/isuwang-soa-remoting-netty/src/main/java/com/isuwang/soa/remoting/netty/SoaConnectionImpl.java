package com.isuwang.soa.remoting.netty;

import com.isuwang.soa.core.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import com.isuwang.org.apache.thrift.TApplicationException;
import com.isuwang.org.apache.thrift.TException;
import com.isuwang.org.apache.thrift.protocol.TMessage;
import com.isuwang.org.apache.thrift.protocol.TMessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class SoaConnectionImpl implements com.isuwang.soa.remoting.SoaConnection {

    private static final Logger LOGGER = LoggerFactory.getLogger(SoaConnectionImpl.class);

    private SoaClient soaClient;

    public SoaConnectionImpl(String host, int port) {
        try {
            soaClient = new SoaClient(host, port);
        } catch (SoaException e) {
            LOGGER.error("connect to {}:{} failed", host, port);
        }
    }

    public <REQ, RESP> RESP send(REQ request, RESP response, TBeanSerializer<REQ> requestSerializer, TBeanSerializer<RESP> responseSerializer) throws TException {
        InvocationContext context = InvocationContext.Factory.getCurrentInstance();
        SoaHeader soaHeader = context.getHeader();

        final ByteBuf requestBuf = Unpooled.directBuffer(8192);
        final TSoaTransport outputSoaTransport = new TSoaTransport(requestBuf);

        TSoaServiceProtocol outputProtocol;
        ByteBuf responseBuf = null;

        try {
            outputProtocol = new TSoaServiceProtocol(outputSoaTransport, true);
            outputProtocol.writeMessageBegin(new TMessage(soaHeader.getServiceName() + ":" + soaHeader.getMethodName(), TMessageType.CALL, context.getSeqid()));
            requestSerializer.write(request, outputProtocol);
            outputProtocol.writeMessageEnd();

            outputSoaTransport.flush();//在报文头部写入int,代表报文长度(不包括自己)
            if (soaClient == null) {
                throw new SoaException(SoaBaseCode.NotConnected);
            }
            responseBuf = soaClient.send(context.getSeqid(), requestBuf); //发送请求，返回结果

            if (responseBuf == null) {
                throw new SoaException(SoaBaseCode.TimeOut);
//                throw new TException("request time out.");
            } else {
                final TSoaTransport inputSoaTransport = new TSoaTransport(responseBuf);
                TSoaServiceProtocol inputProtocol = new TSoaServiceProtocol(inputSoaTransport, true);

                TMessage msg = inputProtocol.readMessageBegin();
                if (TMessageType.EXCEPTION == msg.type) {
                    TApplicationException x = TApplicationException.read(inputProtocol);
                    inputProtocol.readMessageEnd();
                    throw x;
                } else if (context.getSeqid() != msg.seqid) {
                    throw new TApplicationException(4, soaHeader.getMethodName() + " failed: out of sequence response");
                } else {
                    if ("0000".equals(soaHeader.getRespCode().get())) {
                        responseSerializer.read(response, inputProtocol);
                        inputProtocol.readMessageEnd();
                    } else {
                        throw new SoaException(soaHeader.getRespCode().get(), soaHeader.getRespMessage().get());
                    }

                    return response;
                }
            }
        } catch (SoaException e) {
            LOGGER.error(e.getMessage(), e);
            throw e;

        } catch (Throwable e) {
            LOGGER.error(e.getMessage(), e);

            throw new SoaException(SoaBaseCode.UnKnown);
        } finally {
            outputSoaTransport.close();

            if (requestBuf.refCnt() > 0)
                requestBuf.release();

            // to see SoaDecoder: ByteBuf msg = in.slice(readerIndex, length + Integer.BYTES).retain();
            if (responseBuf != null)
                responseBuf.release();
        }
    }


    public <REQ, RESP> Future<RESP> sendAsync(REQ request, RESP response, TBeanSerializer<REQ> requestSerializer, TBeanSerializer<RESP> responseSerializer) throws TException {

        InvocationContext context = InvocationContext.Factory.getCurrentInstance();
        SoaHeader soaHeader = context.getHeader();

        final ByteBuf requestBuf = Unpooled.directBuffer(8192);
        final TSoaTransport outputSoaTransport = new TSoaTransport(requestBuf);

        TSoaServiceProtocol outputProtocol;

        try {
            outputProtocol = new TSoaServiceProtocol(outputSoaTransport, true);
            outputProtocol.writeMessageBegin(new TMessage(soaHeader.getServiceName() + ":" + soaHeader.getMethodName(), TMessageType.CALL, context.getSeqid()));
            requestSerializer.write(request, outputProtocol);
            outputProtocol.writeMessageEnd();

            outputSoaTransport.flush();//在报文头部写入int,代表报文长度(不包括自己)
            if (soaClient == null) {
                throw new SoaException(SoaBaseCode.NotConnected);
            }

            CompletableFuture<ByteBuf> responseBufFuture = new CompletableFuture<>();
            soaClient.send(context.getSeqid(), requestBuf, responseBufFuture);

            Future<RESP> responseFuture = responseBufFuture.thenApply(responseBuf -> {

                final TSoaTransport inputSoaTransport = new TSoaTransport(responseBuf);
                TSoaServiceProtocol inputProtocol = new TSoaServiceProtocol(inputSoaTransport, true);
                InvocationContext.Factory.setCurrentInstance(context);

                try {

                    TMessage msg = inputProtocol.readMessageBegin();
                    if ("0000".equals(soaHeader.getRespCode().get())) {
                        responseSerializer.read(response, inputProtocol);
                        inputProtocol.readMessageEnd();
                    } else {
                        throw new SoaException(soaHeader.getRespCode().get(), soaHeader.getRespMessage().get());
                    }

                } catch (SoaException e) {
                    e.printStackTrace();
                } catch (Throwable e) {
                    e.printStackTrace();
                } finally {
                    // to see SoaDecoder: ByteBuf msg = in.slice(readerIndex, length + Integer.BYTES).retain();
                    if (responseBuf != null)
                        responseBuf.release();

                    if (requestBuf.refCnt() > 0)
                        requestBuf.release();

                    return response;
                }
            });
            return responseFuture;

        } catch (SoaException e) {
            LOGGER.error(e.getMessage(), e);
            throw e;

        } catch (Throwable e) {
            LOGGER.error(e.getMessage(), e);

            throw new SoaException(SoaBaseCode.UnKnown);
        } finally {
            outputSoaTransport.close();
        }
    }

}
