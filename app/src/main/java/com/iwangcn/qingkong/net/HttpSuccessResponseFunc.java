package com.iwangcn.qingkong.net;

import android.text.TextUtils;

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
        if (TextUtils.equals("200",response.getCode())) {
            try {
                return JSON.parseObject(response.getData().toString(), clazz);
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }

        } else {
            throw new ExceptionHandle.ServerException(response.getCode(), response.getMessage());
        }

    }
}
