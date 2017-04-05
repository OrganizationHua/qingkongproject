package com.iwangcn.qingkong.net;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by RF on 2017/1/11.
 */

/**
 * 说明:
 * "result": {   ---------------------------业务数据
 * "id": "",   ----------------------------接口编号(暂时为空)
 * "method": "",-----------------------接口名称(暂时为空)
 * "data": "",  -------------------------接口数据
 * "code": "",--------------------------接口响应码 0 成功  其它错误码
 * "errObject": {-----------------------接口提示信息  非0时解析
 * "errorCode": "",
 * ("errorMessage": ""
 * }
 * },
 * "msg": "",  ---------------------------通讯层提示信息
 * "status": "0"   ------------------------通讯层错误码  0 成功  1失败
 */

public class NetResponse<T> implements Serializable {
    private ResultBean result;
    private String msg;
    private String status;


    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public class ResultBean {

        private String id;
        private String method;
        private T data;
        private String code;
        private ErrObjectBean errObject;

        public boolean isStatusOk() {
            return TextUtils.equals(status, NetConst.STATUS_SUCCESS);
        }

        public boolean isCodeOk() {
            return TextUtils.equals(code, NetConst.CODE_SUCCESS);
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public ErrObjectBean getErrObject() {
            return errObject;
        }

        public void setErrObject(ErrObjectBean errObject) {
            this.errObject = errObject;
        }

        public class ErrObjectBean {

            private String errorCode;
            private String errorMessage;

            public String getErrorCode() {
                return errorCode;
            }

            public void setErrorCode(String errorCode) {
                this.errorCode = errorCode;
            }

            public String getErrorMessage() {
                return errorMessage;
            }

            public void setErrorMessage(String errorMessage) {
                this.errorMessage = errorMessage;
            }
        }
    }
}
