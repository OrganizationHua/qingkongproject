package com.iwangcn.qingkong.net;


import android.support.v4.util.ArrayMap;

import java.io.IOException;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * @author fig
 *         添加headers 拦截器
 *         注意点:
 *         不要试图在此类中对response的body成员进行api调用,
 *         不然会导致response在此处io流就关闭,影响后续流程处理response;
 *         想要处理body请参考日志拦截器HttpLoggingInterceptor中对response的处理方式;
 */
public class HeaderInterceptor implements Interceptor {
    private ArrayMap<String, String> headers;
    public HeaderInterceptor addHeaders(ArrayMap<String, String> headers) {
        this.headers = headers;
        return this;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        if (headers != null && headers.size() > 0) {
            Set<String> keys = headers.keySet();
            for (String headerKey : keys) {
                builder.addHeader(headerKey, headers.get(headerKey)).build();
            }
        }
        Response response = chain.proceed(builder.build());
        return response;

    }

}