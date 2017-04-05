package com.iwangcn.qingkong.net;


import android.support.v4.util.ArrayMap;

import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Builder 链式封装
 *
 * @author fjg
 * @comment 通过Builder组装模式将OkHttpClient和Retrofit的配置统一封装；
 * 扩展功能可按照builder的add***(***)的格式扩展配置,
 * 本类主要作用增强兼容性，OkHttp和Retrofit有重大升级时，升级本类的兼容性即可；
 */
public class RetrofitClient {

    final int connectTimeout;
    final int readTimeout;
    final int writeTimeout;
    final OkHttpClient.Builder okHttpBuilder;
    final Retrofit.Builder retrofitBuilder;
    public static Retrofit retrofit;

    public RetrofitClient() {
        this(new Builder());
    }


    private RetrofitClient(Builder builder) {
        this.okHttpBuilder = builder.okHttpBuilder;
        this.retrofitBuilder = builder.retrofitBuilder;
        this.connectTimeout = builder.connectTimeout;
        this.readTimeout = builder.readTimeout;
        this.writeTimeout = builder.writeTimeout;
    }

    public Builder newBuilder() {

        return new Builder(this);
    }

    public static final class Builder {
        OkHttpClient.Builder okHttpBuilder;
        Retrofit.Builder retrofitBuilder;
        int connectTimeout;
        int readTimeout;
        int writeTimeout;

        public Builder() {
            okHttpBuilder = new OkHttpClient.Builder();
            retrofitBuilder = new Retrofit.Builder();
            connectTimeout = 30_000;
            readTimeout = 30_000;
            writeTimeout = 30_000;
        }

        public Builder(RetrofitClient retrofitClient) {
            this.okHttpBuilder = retrofitClient.okHttpBuilder;
            this.retrofitBuilder = retrofitClient.retrofitBuilder;
            this.connectTimeout = retrofitClient.connectTimeout;
            this.readTimeout = retrofitClient.readTimeout;
            this.writeTimeout = retrofitClient.writeTimeout;

        }
        /********************************OkHttpClient的配置开始***************************/
        /**
         * Sets the default connect timeout for new connections. A value of 0 means no timeout,
         * otherwise values must be between 1 and {@link Integer#MAX_VALUE} when converted to
         * milliseconds.
         * 全局连接超时时间
         */
        public Builder connectTimeout(long timeout, TimeUnit unit) {
            okHttpBuilder.connectTimeout(timeout, unit);
            return this;
        }

        /**
         * Sets the default read timeout for new connections. A value of 0 means no timeout,
         * otherwise
         * values must be between 1 and {@link Integer#MAX_VALUE} when converted to milliseconds.
         * 全局读取超时时间
         */
        public Builder readTimeout(long timeout, TimeUnit unit) {
            okHttpBuilder.readTimeout(timeout, unit);
            return this;
        }

        /**
         * Sets the default read timeout for new connections. A value of 0 means no timeout,
         * otherwise
         * values must be between 1 and {@link Integer#MAX_VALUE} when converted to milliseconds.
         * 全局写入超时时间
         */
        public Builder writeTimeout(long timeout, TimeUnit unit) {
            okHttpBuilder.writeTimeout(timeout, unit);
            return this;
        }

        /**
         * Returns a modifiable list of interceptors that observe the full span of each call: from
         * before the connection is established (if any) until after the response source is
         * selected
         * (either the origin server, cache, or both).
         * 添加全局拦截器
         */
        public Builder addInterceptor(Interceptor interceptor) {
            okHttpBuilder.addInterceptor(interceptor);
            return this;
        }

        public Builder addNetworkInterceptor(Interceptor interceptor) {
            okHttpBuilder.addNetworkInterceptor(interceptor);
            return this;
        }

        public Builder addHeaders(ArrayMap<String, String> headers) {
            okHttpBuilder.addInterceptor(new HeaderInterceptor(headers));
            return this;
        }
        /********************************Retrofit的配置开始***************************/
        /** Add converter factory for serialization and deserialization of objects. */
        public Builder addConverterFactory(Converter.Factory factory) {
            retrofitBuilder.addConverterFactory(factory);
            return this;
        }

        /**
         * Add a call adapter factory for supporting service method return types other than {@link
         * Call}.
         */
        public Builder addCallAdapterFactory(CallAdapter.Factory factory) {
            retrofitBuilder.addCallAdapterFactory(factory);
            return this;
        }

        /**
         * Set the API base URL.
         *
         * @see (HttpUrl)
         */
        public Builder baseUrl(String url) {
            retrofitBuilder.baseUrl(url);
            return this;
        }

        /**
         * Create the {@link Retrofit} instance using the configured values.
         * <p>
         * Note: If neither {@link #} nor {@link } is called a default {@link
         * OkHttpClient} will be created and used.
         */
        public RetrofitClient build() {
            RetrofitClient retrofitClient = new RetrofitClient(this);
            retrofit = retrofitBuilder.client(okHttpBuilder.build()).build();
            return retrofitClient;
        }

    }

    public <T> T create(final Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        return RetrofitClient.retrofit.create(service);
    }
}
