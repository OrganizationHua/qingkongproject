package com.iwangcn.qingkong.business;

import android.content.Context;

import com.iwangcn.qingkong.net.BaseSubscriber;
import com.iwangcn.qingkong.net.ExceptionHandle;
import com.iwangcn.qingkong.net.NetConst;
import com.iwangcn.qingkong.net.RetrofitInstance;
import com.iwangcn.qingkong.ui.model.UserInfo;

import java.util.HashMap;

/**
 * Created by RF on 2017/4/24.
 */

public class HelperEvent extends Event implements NetConst {
    private Context mContext;
    private int indexPage = 0;//当前页数
    public HelperEvent(Context context) {
        this.mContext = context;

    }
    private void getHelperEventList(int index, HashMap paratems) {
        RetrofitInstance.getInstance().post(URL_EVENT_HELP, paratems, UserInfo.class, new BaseSubscriber(false) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

            @Override
            public void onNext(Object o) {

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
