package com.isuwang.dapeng.impl.plugins.netty;

import com.isuwang.dapeng.core.*;
import com.isuwang.dapeng.core.enums.CodecProtocol;
import com.isuwang.org.apache.thrift.TException;
import com.isuwang.org.apache.thrift.protocol.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lihuimin on 2017/12/8.
 */
public class SoaMessageProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(SoaMessageProcessor.class);

    private final byte STX = 0x02;
    private final byte ETX = 0x03;
    private final byte VERSION = 1;
    private final String OLD_VERSION = "1.0.0";

    private TProtocol headerProtocol;
    private TProtocol contentProtocol;

    private boolean oldVersion = false;


    public TSoaTransport transport;

    public boolean isOldVersion() {
        return oldVersion;
    }

    public void setOldVersion(boolean oldVersion) {
        this.oldVersion = oldVersion;
    }

    public TProtocol getHeaderProtocol() {
        return headerProtocol;
    }

    public void setHeaderProtocol(TProtocol headerProtocol) {
        this.headerProtocol = headerProtocol;
    }

    public TProtocol getContentProtocol() {
        return contentProtocol;
    }

    public void setContentProtocol(TProtocol contentProtocol) {
        this.contentProtocol = contentProtocol;
    }

    public TSoaTransport getTransport() {
        return transport;
    }

    public void setTransport(TSoaTransport transport) {
        this.transport = transport;
    }

    public SoaMessageProcessor(TSoaTransport transport) {
        this.transport = transport;
    }

    public void writeHeader(TransactionContext context) throws TException {

        headerProtocol = new TBinaryProtocol(transport);

        headerProtocol.writeByte(STX);
        if (isOldVersion()) {
            headerProtocol.writeString(OLD_VERSION);
        } else{
            headerProtocol.writeByte(VERSION);
        }
        headerProtocol.writeByte(context.getCodecProtocol().getCode());
        headerProtocol.writeI32(context.getSeqid());

        switch (context.getCodecProtocol()) {
            case Binary:
                contentProtocol = new TBinaryProtocol(transport);
                break;
            case CompressedBinary:
                contentProtocol = new TCompactProtocol(transport);
                break;
            case Json:
                contentProtocol = new TJSONProtocol(transport);
                break;
            case Xml:
                contentProtocol = null;
                break;
        }

        new SoaHeaderSerializer().write(context.getHeader(), headerProtocol);

        if (isOldVersion()) {
            contentProtocol.writeMessageBegin(new TMessage(context.getHeader().getMethodName(),TMessageType.CALL,context.getSeqid()));
        }
    }

    public <RESP>void writeBody(BeanSerializer<RESP> respSerializer, RESP result ) throws TException {
        respSerializer.write(result,contentProtocol);
    }

    public SoaHeader parseSoaMessage(TransactionContext context) throws TException{

        if (headerProtocol == null) {
            headerProtocol = new TBinaryProtocol(getTransport());
        }

        // length(int32) stx(int8) version(int8) protocol(int8) seqid(i32) header(struct) body(struct) etx(int8)

        byte stx = headerProtocol.readByte();
        if (stx != STX) {// 通讯协议不正确
            throw new TException("通讯协议不正确(起始符)");
        }

        // version
        byte version = headerProtocol.readByte();
        if (version != VERSION) {
            getTransport().flushBack(1);
            String oldVersion = headerProtocol.readString();
            if (!OLD_VERSION.equals(oldVersion)) {
                throw new TException("通讯协议不正确(协议版本号)");
            } else {
                this.setOldVersion(true);
            }
        }

        byte protocol = headerProtocol.readByte();
        context.setCodecProtocol(CodecProtocol.toCodecProtocol(protocol));
        switch (context.getCodecProtocol()) {
            case Binary:
                contentProtocol = new TBinaryProtocol(getTransport());
                break;
            case CompressedBinary:
                contentProtocol = new TCompactProtocol(getTransport());
                break;
            case Json:
                contentProtocol = new TJSONProtocol(getTransport());
                break;
            case Xml:
                //realContentProtocol = null;
                throw new TException("通讯协议不正确(包体协议)");
            default:
                throw new TException("通讯协议不正确(包体协议)");
        }

        context.setSeqid(headerProtocol.readI32());
        SoaHeader soaHeader =new SoaHeaderSerializer().read( headerProtocol);
        context.setHeader(soaHeader);

        if (isOldVersion()) {
            contentProtocol.readMessageBegin();
        }
        return soaHeader;

    }

    public void writeMessageEnd() throws TException {
        contentProtocol.writeMessageEnd();

        headerProtocol.writeByte(ETX);
    }


}
