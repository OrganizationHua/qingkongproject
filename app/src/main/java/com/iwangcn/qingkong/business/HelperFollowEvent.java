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

public class HelperFollowEvent extends Event implements NetConst {
    private Context mContext;
    private int indexPage = 0;//当前页数

    public HelperFollowEvent(Context context) {
        this.mContext = context;

    }

    private void getHelperEventList(int index,String sourceType ,String tags) {
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());
        paratems.put("sourceType", sourceType);
        paratems.put("tags", tags);
        paratems.put("pageno",index);
        RetrofitInstance.getInstance().post(URL_EVENT_FOLLOWUP_AIDE, paratems, HelperInfo.class, new BaseSubscriber<NetResponse<HelperInfo>>(false) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

            @Override
            public void onNext(NetResponse<HelperInfo> netResponse) {

            }
        });
    }
    private void doCancleFollow(String infoId) {//取消跟进
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());
        paratems.put("infoId", infoId);
        RetrofitInstance.getInstance().post(URL_EVENT_AIDE_CANCELFOLLOW, paratems, String.class, new BaseSubscriber<NetResponse<String>>(false) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

            @Override
            public void onNext(NetResponse<String> netResponse) {

            }
        });
    }
    private void doFollowSetUp(String infoId) {//置顶
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());
        paratems.put("infoId", infoId);
        RetrofitInstance.getInstance().post(URL_EVENT_AIDE_SETTOP, paratems, String.class, new BaseSubscriber<NetResponse<String>>(false) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

            @Override
            public void onNext(NetResponse<String> netResponse) {

            }
        });
    }
    private void doFollowSetUpCancleTop(String infoId) {//取消置顶
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());
        paratems.put("infoId", infoId);
        RetrofitInstance.getInstance().post(URL_EVENT_AIDE_CANCELTOP, paratems, String.class, new BaseSubscriber<NetResponse<String>>(false) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

            @Override
            public void onNext(NetResponse<String> netResponse) {

            }
        });
    }
    private void doFollowDone(String infoId) {//已处理
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());
        paratems.put("infoId", infoId);
        RetrofitInstance.getInstance().post(URL_EVENT_AIDE_DONE, paratems, String.class, new BaseSubscriber<NetResponse<String>>(false) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

            @Override
            public void onNext(NetResponse<String> netResponse) {

            }
        });
    }
    public void getMoreEvent() {
        indexPage++;
        HashMap paratems = new HashMap();
        getHelperEventList(indexPage, "","");
    }

    public void getRefreshEventList() {
        indexPage = 1;
        HashMap paratems = new HashMap();
        getHelperEventList(indexPage, "","");
    }
}
