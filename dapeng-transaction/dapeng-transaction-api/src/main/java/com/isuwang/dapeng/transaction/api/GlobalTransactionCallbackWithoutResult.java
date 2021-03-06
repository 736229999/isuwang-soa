package com.isuwang.dapeng.transaction.api;

import com.isuwang.org.apache.thrift.TException;

/**
 * Soa Transactional ProcessCallback WithoutResult
 *
 * @author craneding
 * @date 16/4/11
 */
public abstract class GlobalTransactionCallbackWithoutResult implements GlobalTransactionCallback<Object> {

    public Object doInTransaction() throws TException {
        doInTransactionWithoutResult();

        return null;
    }

    protected abstract void doInTransactionWithoutResult() throws TException;

}
