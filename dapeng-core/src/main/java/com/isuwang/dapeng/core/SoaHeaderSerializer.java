package com.isuwang.dapeng.core;

import com.isuwang.org.apache.thrift.TException;
import com.isuwang.org.apache.thrift.protocol.*;

import java.util.Optional;

/**
 * Created by tangliu on 2016/1/11.
 * SoaHeader序列化和反序列化
 */
public class SoaHeaderSerializer implements TBeanSerializer<SoaHeader> {

    /**
     * 反序列化
     *
     * @throws TException
     */
    @Override
    public void read(SoaHeader bean, TProtocol iprot) throws TException {
        TField schemeField;
        iprot.readStructBegin();
        while (true) {
            schemeField = iprot.readFieldBegin();
            if (schemeField.type == TType.STOP) {
                break;
            }
            switch (schemeField.id) {
                case 1:
                    if (schemeField.type == TType.STRING) {
                        bean.setServiceName(iprot.readString());
                    } else {
                        TProtocolUtil.skip(iprot, schemeField.type);
                    }
                    break;
                case 2:
                    if (schemeField.type == TType.STRING) {
                        bean.setMethodName(iprot.readString());
                    } else {
                        TProtocolUtil.skip(iprot, schemeField.type);
                    }
                    break;
                case 3:
                    if (schemeField.type == TType.STRING) {
                        bean.setVersionName(iprot.readString());
                    } else {
                        TProtocolUtil.skip(iprot, schemeField.type);
                    }
                    break;
                case 4:
                    if (schemeField.type == TType.STRING) {
                        bean.setCallerFrom(Optional.of(iprot.readString()));
                    } else {
                        TProtocolUtil.skip(iprot, schemeField.type);
                    }
                    break;
                case 5:
                    if (schemeField.type == TType.STRING) {
                        bean.setCallerIp(Optional.of(iprot.readString()));
                    } else {
                        TProtocolUtil.skip(iprot, schemeField.type);
                    }
                    break;
                case 11:
                    if (schemeField.type == TType.STRING) {
                        bean.setRespCode(Optional.of(iprot.readString()));
                    } else {
                        TProtocolUtil.skip(iprot, schemeField.type);
                    }
                    break;
                case 12:
                    if (schemeField.type == TType.STRING) {
                        bean.setRespMessage(Optional.of(iprot.readString()));
                    } else {
                        TProtocolUtil.skip(iprot, schemeField.type);
                    }
                    break;
                case 15:
                    if (schemeField.type == TType.I32) {
                        bean.setOperatorId(Optional.of(iprot.readI32()));
                    } else {
                        TProtocolUtil.skip(iprot, schemeField.type);
                    }
                    break;
                case 16:
                    if (schemeField.type == TType.STRING) {
                        bean.setOperatorName(Optional.of(iprot.readString()));
                    } else {
                        TProtocolUtil.skip(iprot, schemeField.type);
                    }
                    break;
                case 17:
                    if (schemeField.type == TType.I32) {
                        bean.setCustomerId(Optional.of(iprot.readI32()));
                    } else {
                        TProtocolUtil.skip(iprot, schemeField.type);
                    }
                    break;
                case 18:
                    if (schemeField.type == TType.STRING) {
                        bean.setCustomerName(Optional.of(iprot.readString()));
                    } else {
                        TProtocolUtil.skip(iprot, schemeField.type);
                    }
                    break;
                case 19:
                    if (schemeField.type == TType.I32) {
                        bean.setTransactionId(Optional.of(iprot.readI32()));
                    } else {
                        TProtocolUtil.skip(iprot, schemeField.type);
                    }
                    break;
                case 20:
                    if (schemeField.type == TType.I32) {
                        bean.setTransactionSequence(Optional.of(iprot.readI32()));
                    } else {
                        TProtocolUtil.skip(iprot, schemeField.type);
                    }
                    break;
                case 21:
                    if (schemeField.type == TType.BOOL) {
                        bean.setAsyncCall(iprot.readBool());
                    } else {
                        TProtocolUtil.skip(iprot, schemeField.type);
                    }
                    break;

                default:
                    TProtocolUtil.skip(iprot, schemeField.type);
            }
            iprot.readFieldEnd();
        }
        iprot.readStructEnd();
    }


    /**
     * 序列化
     */
    @Override
    public void write(SoaHeader bean, TProtocol oprot) throws TException {

        validate(bean);
        oprot.writeStructBegin(new TStruct("soaheader"));

        if (null != bean.getServiceName()) {
            oprot.writeFieldBegin(new TField("serviceName", TType.STRING, (short) 1));
            oprot.writeString(bean.getServiceName());
            oprot.writeFieldEnd();
        }
        if (null != bean.getMethodName()) {
            oprot.writeFieldBegin(new TField("methodName", TType.STRING, (short) 2));
            oprot.writeString(bean.getMethodName());
            oprot.writeFieldEnd();
        }
        if (null != bean.getVersionName()) {
            oprot.writeFieldBegin(new TField("versionName", TType.STRING, (short) 3));
            oprot.writeString(bean.getVersionName());
            oprot.writeFieldEnd();
        }
        if (bean.getCallerFrom().isPresent()) {
            oprot.writeFieldBegin(new TField("callerFrom", TType.STRING, (short) 4));
            oprot.writeString(bean.getCallerFrom().get());
            oprot.writeFieldEnd();
        }
        if (bean.getCallerIp().isPresent()) {
            oprot.writeFieldBegin(new TField("callerIP", TType.STRING, (short) 5));
            oprot.writeString(bean.getCallerIp().get());
            oprot.writeFieldEnd();
        }
        if (bean.getRespCode().isPresent()) {
            oprot.writeFieldBegin(new TField("respCode", TType.STRING, (short) 11));
            oprot.writeString(bean.getRespCode().get());
            oprot.writeFieldEnd();
        }
        if (bean.getRespMessage().isPresent()) {
            oprot.writeFieldBegin(new TField("respMessage", TType.STRING, (short) 12));
            oprot.writeString(bean.getRespMessage().get());
            oprot.writeFieldEnd();
        }
        if (bean.getOperatorId().isPresent()) {
            oprot.writeFieldBegin(new TField("operatorId", TType.I32, (short) 15));
            oprot.writeI32(bean.getOperatorId().get());
            oprot.writeFieldEnd();
        }
        if (bean.getOperatorName().isPresent()) {
            oprot.writeFieldBegin(new TField("operatorName", TType.STRING, (short) 16));
            oprot.writeString(bean.getOperatorName().get());
            oprot.writeFieldEnd();
        }
        if (bean.getCustomerId().isPresent()) {
            oprot.writeFieldBegin(new TField("customerId", TType.I32, (short) 17));
            oprot.writeI32(bean.getCustomerId().get());
            oprot.writeFieldEnd();
        }
        if (bean.getCustomerName().isPresent()) {
            oprot.writeFieldBegin(new TField("customerName", TType.STRING, (short) 18));
            oprot.writeString(bean.getCustomerName().get());
            oprot.writeFieldEnd();
        }
        if (bean.getTransactionId().isPresent()) {
            oprot.writeFieldBegin(new TField("transactionId", TType.I32, (short) 19));
            oprot.writeI32(bean.getTransactionId().get());
            oprot.writeFieldEnd();
        }
        if (bean.getTransactionSequence().isPresent()) {
            oprot.writeFieldBegin(new TField("transactionSequence", TType.I32, (short) 20));
            oprot.writeI32(bean.getTransactionSequence().get());
            oprot.writeFieldEnd();
        }

        oprot.writeFieldBegin(new TField("isAsyncCall", TType.BOOL, (short) 21));
        oprot.writeBool(bean.isAsyncCall());
        oprot.writeFieldEnd();

        oprot.writeFieldStop();
        oprot.writeStructEnd();

        //oprot.getTransport().flush();
    }

    /**
     * SoaHeader验证
     */
    @Override
    public void validate(SoaHeader bean) throws TException {
        if (bean.getServiceName() == null)
            throw new SoaException(SoaBaseCode.NotNull, "serviceName字段不允许为空");
        if (bean.getMethodName() == null)
            throw new SoaException(SoaBaseCode.NotNull, "methodName字段不允许为空");
        if (bean.getVersionName() == null)
            throw new SoaException(SoaBaseCode.NotNull, "versionName字段不允许为空");
    }

    @Override
    public String toString(SoaHeader bean) {
        return bean == null ? "null" : bean.toString();
    }

}
