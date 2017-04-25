package com.iwangcn.qingkong.business;

import android.content.Context;

import com.iwangcn.qingkong.net.BaseSubscriber;
import com.iwangcn.qingkong.net.ExceptionHandle;
import com.iwangcn.qingkong.net.NetConst;
import com.iwangcn.qingkong.net.NetResponse;
import com.iwangcn.qingkong.net.RetrofitInstance;
import com.iwangcn.qingkong.providers.UserManager;
import com.iwangcn.qingkong.ui.model.HelperInfo;

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

    private void getHelperEventList(int index,String sourceType ,String tags) {
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());
        paratems.put("sourceType", sourceType);
        paratems.put("tags", tags);
        paratems.put("pageno",index);
        RetrofitInstance.getInstance().post(URL_EVENT_HELP, paratems, HelperInfo.class, new BaseSubscriber<NetResponse<HelperInfo>>(false) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

            @Override
            public void onNext(NetResponse<HelperInfo> netResponse) {

            }
        });
    }

    public void getMoreEvent() {
        indexPage++;
        HashMap paratems = new HashMap();
        getHelperEventList(indexPage, "1","1");
    }

    public void getRefreshEventList() {
        indexPage = 0;
        HashMap paratems = new HashMap();
        getHelperEventList(indexPage, "1","1");
    }
}
