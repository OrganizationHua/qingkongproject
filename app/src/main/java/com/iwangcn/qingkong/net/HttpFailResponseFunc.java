package com.iwangcn.qingkong.net;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by fjg on 2016/12/13.
 * 接口响应失败
 */

public class HttpFailResponseFunc<T> implements Func1<Throwable, Observable<NetResponse<T>>> {
    @Override
    public Observable<NetResponse<T>> call(Throwable t) {
        return Observable.error(ExceptionHandle.handleException(t));
    }
}
