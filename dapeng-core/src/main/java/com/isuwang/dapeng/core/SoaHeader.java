package com.isuwang.dapeng.core;

import java.util.Optional;

/**
 * Created by tangliu on 2016/1/11.
 */
public class SoaHeader {

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 版本号
     */
    private String versionName;

    /**
     * 调用源
     */
    private Optional<String> callerFrom = Optional.empty();

    /**
     * 返回码
     */
    private Optional<String> respCode = Optional.empty();

    /**
     * 返回信息
     */
    private Optional<String> respMessage = Optional.empty();

    /**
     * 调用源ip
     */
    private Optional<String> callerIp = Optional.empty();

    /**
     * 操作人编号
     */
    private Optional<Integer> operatorId = Optional.empty();

    /**
     * 操作人名称
     */
    private Optional<String> operatorName = Optional.empty();

    /**
     * 客户编号
     */
    private Optional<Integer> customerId = Optional.empty();

    /**
     * 客户名称
     */
    private Optional<String> customerName = Optional.empty();


    /**
     * 全局事务id
     */
    private Optional<Integer> transactionId = Optional.empty();

    /**
     * 当前过程所属序列号
     */
    private Optional<Integer> transactionSequence = Optional.empty();


    /**
     * 是否是异步请求
     */
    private boolean isAsyncCall = false;

    public String toString() {

        StringBuilder sb = new StringBuilder("{");

        sb.append("\"").append("serviceName").append("\":\"").append(this.serviceName).append("\",");
        sb.append("\"").append("methodName").append("\":\"").append(this.methodName).append("\",");
        sb.append("\"").append("versionName").append("\":\"").append(this.versionName).append("\",");
        sb.append("\"").append("transactionId").append("\":\"").append(this.transactionId.isPresent() ? this.transactionId.get() : null).append("\",");
        sb.append("\"").append("transactionSequence").append("\":\"").append(this.transactionSequence.isPresent() ? this.transactionSequence.get() : null).append("\",");
        sb.append("\"").append("callerFrom").append("\":\"").append(this.callerFrom.isPresent() ? this.callerFrom.get() : null).append("\",");
        sb.append("\"").append("callerIp").append("\":\"").append(this.callerIp.isPresent() ? this.callerIp.get() : null).append("\",");
        sb.append("\"").append("operatorId").append("\":").append(this.operatorId.isPresent() ? this.operatorId.get() : null).append(",");
        sb.append("\"").append("operatorName").append("\":\"").append(this.operatorName.isPresent() ? this.operatorName.get() : null).append("\",");
        sb.append("\"").append("customerId").append("\":").append(this.customerId.isPresent() ? this.customerId.get() : null).append(",");
        sb.append("\"").append("customerName").append("\":\"").append(this.customerName.isPresent() ? this.customerName.get() : null).append("\",");
        sb.append("\"").append("respCode").append("\":\"").append(this.respCode.isPresent() ? this.respCode.get() : null).append("\",");
        sb.append("\"").append("respMessage").append("\":\"").append(this.respMessage.isPresent() ? this.respMessage.get() : null).append("\",");

        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append("}");
        return sb.toString();
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public Optional<String> getCallerIp() {
        return callerIp;
    }

    public void setCallerIp(Optional<String> callerIp) {
        this.callerIp = callerIp;
    }

    public Optional<Integer> getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Optional<Integer> operatorId) {
        this.operatorId = operatorId;
    }

    public Optional<String> getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(Optional<String> operatorName) {
        this.operatorName = operatorName;
    }

    public Optional<Integer> getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Optional<Integer> customerId) {
        this.customerId = customerId;
    }

    public Optional<String> getCustomerName() {
        return customerName;
    }

    public void setCustomerName(Optional<String> customerName) {
        this.customerName = customerName;
    }

    public Optional<String> getRespCode() {
        return respCode;
    }

    public void setRespCode(Optional<String> respCode) {
        this.respCode = respCode;
    }

    public Optional<String> getRespMessage() {
        return respMessage;
    }

    public void setRespMessage(Optional<String> respMessage) {
        this.respMessage = respMessage;
    }

    public Optional<String> getCallerFrom() {
        return callerFrom;
    }

    public void setCallerFrom(Optional<String> callerFrom) {
        this.callerFrom = callerFrom;
    }

    public Optional<Integer> getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Optional<Integer> transactionId) {
        this.transactionId = transactionId;
    }

    public Optional<Integer> getTransactionSequence() {
        return transactionSequence;
    }

    public void setTransactionSequence(Optional<Integer> transactionSequence) {
        this.transactionSequence = transactionSequence;
    }

    public boolean isAsyncCall() {
        return isAsyncCall;
    }

    public void setAsyncCall(boolean asyncCall) {
        isAsyncCall = asyncCall;
    }
}
