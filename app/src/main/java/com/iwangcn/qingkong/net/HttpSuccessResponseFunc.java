package com.iwangcn.qingkong.net;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import rx.functions.Func1;

/**
 * Created by fjg on 2016/12/13.
 * 接口响应成功
 */

public class HttpSuccessResponseFunc<T> implements Func1<NetResponse, NetResponse> {
    private Class<T> clazz;

    public HttpSuccessResponseFunc(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public NetResponse<T> call(NetResponse response) {
        if (response.getCode() == null) {
            response.setCode("-1");
        }
        if (response.getMessage() == null)
            response.setMessage("数据异常");
        if (response.getData() == null)
            response.setData("");
        if (TextUtils.equals("200", response.getCode())) {
            try {
                response.setDataObject(JSON.parseObject(response.getData().toString(), clazz));
            } catch (Exception e) {
                try {
                    response.setDataList(JSON.parseArray(response.getData().toString(), clazz));
                } catch (Exception e1) {
                    throw new ExceptionHandle.ServerException(response.getCode(), response.getMessage());
                }
            }
            return response;
        } else {
            throw new ExceptionHandle.ServerException(response.getCode(), response.getMessage());
        }

    }
}
