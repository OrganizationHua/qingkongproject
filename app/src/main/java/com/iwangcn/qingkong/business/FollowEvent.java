package com.iwangcn.qingkong.business;

import android.content.Context;

import com.iwangcn.qingkong.net.BaseSubscriber;
import com.iwangcn.qingkong.net.ExceptionHandle;
import com.iwangcn.qingkong.net.NetConst;
import com.iwangcn.qingkong.net.NetResponse;
import com.iwangcn.qingkong.net.RetrofitInstance;
import com.iwangcn.qingkong.ui.model.HelperListModel;

import java.util.HashMap;

/**
 * Created by RF on 2017/4/24.
 */

public class FollowEvent extends Event implements NetConst {
    private Context mContext;
    private int indexPage = 0;//当前页数
    public FollowEvent(Context context) {
        this.mContext = context;

    }
    private void getHelperEventList(int index, HashMap paratems) {
        RetrofitInstance.getInstance().post(URL_EVENT_HELP, paratems, HelperListModel.class, new BaseSubscriber<NetResponse<HelperListModel>>(false) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

            @Override
            public void onNext(NetResponse<HelperListModel> netResponse) {

            }
        });
    }

    public void getMoreEvent() {
        indexPage++;
        HashMap paratems = new HashMap();
        getHelperEventList(indexPage, paratems);
    }

    public void getRefreshEventList() {
        indexPage = 0;
        HashMap paratems = new HashMap();
        getHelperEventList(indexPage, paratems);
    }
}
