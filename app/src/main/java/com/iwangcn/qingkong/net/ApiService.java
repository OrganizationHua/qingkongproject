package com.iwangcn.qingkong.net;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by RF on 2017/4/5.
 */

public interface ApiService {
    String interfaceVersion = "";//接口版本号

    /**
     * post请求
     * 服务端json格式数据请求
     * 处理普通接口网络请求
     *
     * @param interfaceName 接口名称
     * @param maps 请求体参数键值对
     * @return Rx Ovserable
     * @author fjg
     */
    @FormUrlEncoded
    @POST("{interface}")
    Observable<NetResponse> post(
            @Path(value = "interface", encoded = true) String interfaceName,
            @FieldMap Map<String, String> maps);

    /**
     * get请求
     * 服务端json格式数据请求
     * 处理普通接口网络请求
     *
     * @param interfaceName 接口名称
     * @param maps 请求体参数键值对
     * @return Rx Ovserable
     * @author fjg
     */
    @GET("{interface}")
    Observable<NetResponse<BaseBean>> get(
            @Path(value = "interface", encoded = true) String interfaceName,
            @QueryMap Map<String, String> maps);
}
