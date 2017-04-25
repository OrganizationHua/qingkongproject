package com.iwangcn.qingkong.net;


import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import com.iwangcn.qingkong.BuildConfig;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 静态内部类--单例模式
 *
 * @param <T> 请求客户端核心类 主要功能有： okhttp基本配置 retrofit 发起请求 service执行  rx 响应response处理
 * @author fjg
 */
public class RetrofitInstance<T> {

    private static final int DEFAULT_TIMEOUT = 60;

    private static ApiService apiService;

    private static OkHttpClient okHttpClient;
    private OkHttpClient.Builder okHttpClientBuilder;

    private static Retrofit retrofit;
    private Retrofit.Builder retrofitBuilder;
    private HeaderInterceptor headerInterceptor;
    private String baseUrl;
    private ArrayMap<String, String> headers;

    private static class Single {
        private static final RetrofitInstance INSTANCE = new RetrofitInstance();
    }

    public static RetrofitInstance getInstance() {
        return Single.INSTANCE;
    }

    /**
     * 配置okhttp retrofit
     */
    private RetrofitInstance() {
        if (TextUtils.isEmpty(baseUrl)) {
            baseUrl = getDefaultUrl();
        }
        if (headers == null) {
            headers = getDefaultHeaders();
        }
        if (headerInterceptor == null) {
            headerInterceptor = new HeaderInterceptor().addHeaders(headers);
        }
        buildOkHttp();
        buildOkHttpBuilder();
        buildRetrofitBuilder();

    }

    public RetrofitInstance baseUrl(String url) {
        this.baseUrl = url;
        return this;
    }

    public RetrofitInstance addHeaders(ArrayMap<String, String> headers) {
        okHttpClientBuilder.addInterceptor(headerInterceptor.addHeaders(headers));
        return this;
    }

    private RetrofitInstance build() {
        retrofit = retrofitBuilder.client(okHttpClientBuilder.build()).baseUrl(baseUrl).build();
        createBaseApi();
        return this;
    }

    private void buildOkHttp() {
        okHttpClient = new OkHttpClient();
    }

    private void buildOkHttpBuilder() {

        okHttpClientBuilder = okHttpClient.newBuilder()
                .addInterceptor(headerInterceptor)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClientBuilder.addInterceptor(logging);
        }
    }

    private void buildRetrofitBuilder() {
        retrofitBuilder = new Retrofit.Builder()
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
    }

    /**
     * @param subscriber 通用post请求－－－－－单次网络请求
     */
    public void post(String interfaceName, Map<String, String> parameters, Class<T> clazz, BaseSubscriber<T> subscriber) {
        this.build();
        apiService.post(interfaceName, parameters)
                .compose(schedulersTransformer())
                .compose(transformer(clazz))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * @param subscriber 通用get请求－－－－－单次网络请求
     */
    public void get(String interfaceName, Map<String, String> parameters, Class<T> clazz, BaseSubscriber<T> subscriber) {
        this.build();
        apiService.post(interfaceName, parameters)
                .compose(schedulersTransformer())
                .compose(transformer(clazz))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 线程切换器
     */
    public Observable.Transformer schedulersTransformer() {
        return new Observable.Transformer() {
            @Override
            public Object call(Object observable) {
                return ((Observable) observable).subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io());
            }
        };
    }

    /**
     * 数据流转换器
     */
    public Observable.Transformer<NetResponse, T> transformer(final Class<T> clazz) {

        return new Observable.Transformer<NetResponse, T>() {
            @Override
            public Observable<T> call(Observable<NetResponse> netResponseObservable) {
                return netResponseObservable.map(new HttpSuccessResponseFunc<T>(clazz)).onErrorResumeNext(new HttpFailResponseFunc());

            }
        };


    }


    /**
     * create BaseApi  defalte ApiService
     *
     * @return ApiService
     */
    public RetrofitInstance createBaseApi() {
        apiService = create(ApiService.class);
        return this;
    }

    /**
     * create you ApiService
     * Create an implementation of the API endpoints defined by the {@code service} interface.
     */
    public <T> T create(final Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        return retrofit.create(service);
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

    private String getDefaultUrl() {
        return NetConfig.SERVER_URL;
    }

}
