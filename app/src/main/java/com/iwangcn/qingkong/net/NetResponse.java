package com.iwangcn.qingkong.net;

import java.io.Serializable;
import java.util.List;

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
    private T dataObject;
    private List<T> dataList;

    public T getDataObject() {
        return dataObject;
    }

    public void setDataObject(T dataObject) {
        this.dataObject = dataObject;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

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
