package com.iwangcn.qingkong.net;

import android.net.ParseException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.adapter.rxjava.HttpException;


/**
 * Created by Tamic on 2016-08-12.
 */
public class ExceptionHandle {

    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static ResponeThrowable handleException(Throwable e) {
        ResponeThrowable ex;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ResponeThrowable(ERROR.HTTP_ERROR, ERROR.tip_message);
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    ex.codeMessage = "网络服务不给力,请重试";
                    break;
            }
            return ex;
        } else if (e instanceof ServerException) {//服务端异常
            ServerException resultException = (ServerException) e;
            ex = new ResponeThrowable(resultException.code, resultException.codeMessage);
            return ex;
        } else if (e instanceof JSONException
                || e instanceof ParseException) {
            ex = new ResponeThrowable(ERROR.PARSE_ERROR, ERROR.tip_message);
            return ex;
        } else if (e instanceof ConnectException) {
            ex = new ResponeThrowable(ERROR.NETWORD_ERROR, ERROR.tip_message);
            return ex;
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex = new ResponeThrowable(ERROR.SSL_ERROR, ERROR.tip_message);
            return ex;
        } else if (e instanceof ConnectTimeoutException || e instanceof SocketTimeoutException) {
            ex = new ResponeThrowable(ERROR.TIMEOUT_ERROR, ERROR.tip_message);
            return ex;
        } else if (e instanceof ClassCastException) {
            ex = new ResponeThrowable(ERROR.CLASS_ERROR, ERROR.tip_message);
            return ex;
        } else if (e instanceof UnknownHostException) {
            ex = new ResponeThrowable(ERROR.NO_ADDRESS_ERROR, ERROR.tip_message);
            return ex;
        } else {//未知错误 rx等
            ex = new ResponeThrowable(ERROR.UNKNOWN, ERROR.tip_message);
            return ex;
        }
    }


    /**
     * 约定异常
     */
    public class ERROR {
        public static final String tip_message = "网络服务不给力,请重试";
        /**
         * 自定义错误码 -1 对应于系统级异常（未返回结果集）与正常返回结果集status字段对应
         */
        public static final String status = "-1";
        /**
         * 未知错误
         */
        public static final String UNKNOWN = 1000 + "";
        /**
         * 解析错误
         */
        public static final String PARSE_ERROR = 1001 + "";
        /**
         * 网络错误
         */
        public static final String NETWORD_ERROR = 1002 + "";
        /**
         * 协议出错
         */
        public static final String HTTP_ERROR = 1003 + "";

        /**
         * 证书出错
         */
        public static final String SSL_ERROR = 1005 + "";

        /**
         * 连接超时
         */
        public static final String TIMEOUT_ERROR = 1006 + "";
        /**
         * 类型转换错误
         */
        public static final String CLASS_ERROR = 1007 + "";
        /**
         * 连接不上主机
         */
        public static final String NO_ADDRESS_ERROR = 1008 + "";
    }

    public static class ResponeThrowable extends RuntimeException {
        public String code;
        public String codeMessage;

        public ResponeThrowable(String code, String codeMessage) {
            this.code = code;
            this.codeMessage = codeMessage;
        }

        @Override
        public String toString() {
            return "ResponeThrowable{" +
                    "code='" + code + '\'' +
                    ", codeMessage='" + codeMessage + '\'' +
                    '}';
        }
    }

    /**
     * 自定义接口级异常
     */
    public static class ServerException extends RuntimeException {
        public String code;
        public String codeMessage;

        public ServerException(String code, String codeMessage) {

            this.code = code;
            this.codeMessage = codeMessage;
        }
    }

}

