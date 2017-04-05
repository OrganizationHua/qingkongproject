package com.iwangcn.qingkong.net;

import com.alibaba.fastjson.JSON;

import rx.functions.Func1;

/**
 * Created by fjg on 2016/12/13.
 * 接口响应成功
 */

public class HttpSuccessResponseFunc<T> implements Func1<NetResponse<T>, T> {
    private Class<T> clazz;

    public HttpSuccessResponseFunc(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public T call(NetResponse<T> response) {
        if (response.getResult().isStatusOk() && response.getResult().isCodeOk()) {

            return JSON.parseObject(response.getResult().getData().toString(), clazz);

        } else {
            throw new ExceptionHandle.ServerException(response.getStatus(), response.getMsg(),
                    response.getResult().getCode(), response.getResult().getErrObject().getErrorMessage());
        }

    }
}
