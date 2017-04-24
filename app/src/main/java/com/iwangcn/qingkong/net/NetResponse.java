package com.iwangcn.qingkong.net;

import java.io.Serializable;

/**
 * Created by RF on 2017/1/11.
 */

/**
 * 说明:
 * "data": "",  -------------------------接口数据
 * "code": "",--------------------------接口响应码 0 成功  其它错误码
 * "msg": "",  ---------------------------通讯层提示信息
 */

public class NetResponse<T> implements Serializable {

    private String code;

    private String message;

    private String data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
