package com.iwangcn.qingkong.net;


import android.support.v4.util.ArrayMap;

import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 作者：fjg on 16/9/26 22:49
 * 根据具体业务进行封装
 */

public class RetrofitFactory<T> {
    private static RetrofitFactory mInstance;
    private RetrofitClient retrofitClient;
    public ApiService apiService;
    private ArrayMap<String, String> headers;
    private String baseUrl;

    private static class SingletonHolder{
        private final static RetrofitFactory mInstance=new RetrofitFactory();
    }
    public static RetrofitFactory getInstance(){
        return SingletonHolder.mInstance;
    }

    public RetrofitFactory() {
    }

    /**
     * 自定义服务器url
     */
    public RetrofitFactory baseUrl(String url) {
        retrofitClient.newBuilder().baseUrl(url).build();
        return this;
    }

    /**
     * 自定义请求头
     */
    public RetrofitFactory addHeaders(ArrayMap<String, String> headers) {
        retrofitClient.newBuilder().addHeaders(headers).build();
        return this;
    }

    /**
     * create defalte ApiService
     *
     * @return ApiService
     */
    public ApiService createApiService() {
        if (apiService == null) {
            apiService = create(ApiService.class);
        }
        return apiService;
    }

    public <T> T create(final Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        return retrofitClient.retrofit.create(service);
    }

    /**
     * @param subscriber 通用post请求－－－－－单次网络请求
     */
    public void post(String interfaceName, Map<String, String> parameters, Class<T> clazz, Subscriber<T> subscriber) {
        createApiService().post(interfaceName, parameters)
                .compose(schedulersTransformer)
                .compose(transformer(clazz))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * @param subscriber 通用get请求－－－－－单次网络请求
     */
    public void get(String interfaceName, Map<String, String> parameters, Class<T> clazz, Subscriber<T> subscriber) {
        createApiService().post(interfaceName, parameters)
                .compose(schedulersTransformer)
                .compose(transformer(clazz))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 线程切换器
     */
    final Observable.Transformer schedulersTransformer = new Observable.Transformer() {
        @Override
        public Object call(Object observable) {
            return ((Observable) observable).subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io());
        }
    };

    /**
     * 数据流转换器
     */
    public <T> Observable.Transformer<NetResponse<T>, T> transformer(final Class<T> clazz) {

        return new Observable.Transformer() {

            @Override
            public Object call(Object observable) {

                return ((Observable) observable).map(new HttpSuccessResponseFunc<T>(clazz)).onErrorResumeNext(new HttpFailResponseFunc<T>());
            }
        };
    }

    /**
     * 默认请求头
     */
    private ArrayMap<String, String> getDefaultHeaders() {
        ArrayMap<String, String> headers = new ArrayMap<>();
        headers.put("di", "");
        headers.put("token", "");
        headers.put("User-Agent", "");
        return headers;
    }
}
