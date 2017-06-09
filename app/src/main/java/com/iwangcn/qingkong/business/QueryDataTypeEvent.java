package com.iwangcn.qingkong.business;

import android.content.Context;
import android.content.res.TypedArray;

import com.iwangcn.qingkong.app.MobileApplication;
import com.iwangcn.qingkong.net.BaseBean;
import com.iwangcn.qingkong.net.BaseSubscriber;
import com.iwangcn.qingkong.net.ExceptionHandle;
import com.iwangcn.qingkong.net.NetConst;
import com.iwangcn.qingkong.net.NetResponse;
import com.iwangcn.qingkong.net.RetrofitInstance;
import com.iwangcn.qingkong.providers.UserManager;
import com.iwangcn.qingkong.sp.SpUtils;
import com.iwangcn.qingkong.ui.model.QueryDataType;
import com.iwangcn.qingkong.ui.model.UserInfo;
import com.iwangcn.qingkong.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;

/**
 * Created by czh on 2017/4/18.
 */

public class QueryDataTypeEvent extends Event implements NetConst {

    public void queeryDataType() {
        HashMap paratems = new HashMap();

        RetrofitInstance.getInstance().post(URL_SOURRE_TYPE, paratems, QueryDataType.class, new BaseSubscriber<NetResponse<QueryDataType>>(false) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

            @Override
            public void onNext(NetResponse<QueryDataType> queryDataType) {
                List<QueryDataType> list = queryDataType.getDataList();
                for (int i = 0; i < list.size(); i++) {
                    SpUtils.put(MobileApplication.getInstance(), list.get(i).getTypeCode() + "", list.get(i).getName());
                }
                SpUtils.put(MobileApplication.getInstance(), "size", list.size());

            }
        });
    }

}
