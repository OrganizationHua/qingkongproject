package com.iwangcn.qingkong.business;

import android.content.Context;

import com.iwangcn.qingkong.net.BaseSubscriber;
import com.iwangcn.qingkong.net.ExceptionHandle;
import com.iwangcn.qingkong.net.NetConst;
import com.iwangcn.qingkong.net.NetResponse;
import com.iwangcn.qingkong.net.RetrofitInstance;
import com.iwangcn.qingkong.providers.UserManager;
import com.iwangcn.qingkong.ui.model.HelperListModel;

import org.greenrobot.eventbus.EventBus;

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
//        paratems.put("tags", tags);
        paratems.put("pageno",index);
        RetrofitInstance.getInstance().post(URL_EVENT_FOLLOWUP_AIDE, paratems, HelperListModel.class, new BaseSubscriber<NetResponse<HelperListModel>>(false) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                EventBus.getDefault().post(new LoadFailEvent());
            }

            @Override
            public void onNext(NetResponse<HelperListModel> netResponse) {
                HelperFollowEvent.this.setObject(netResponse.getDataList());
                HelperFollowEvent.this.setId(0);
                if (indexPage == 1) {
                    HelperFollowEvent.this.setIsMore(false);
                } else {
                    HelperFollowEvent.this.setIsMore(true);
                }
                EventBus.getDefault().post(HelperFollowEvent.this);
            }
        });
    }
    public void doCancleFollow(String infoId,final int position) {//取消跟进
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());
        paratems.put("infoId", infoId);
        RetrofitInstance.getInstance().post(URL_EVENT_AIDE_CANCELFOLLOW, paratems, String.class, new BaseSubscriber<NetResponse<String>>(false) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

            @Override
            public void onNext(NetResponse<String> netResponse) {
                HelperFollowEvent.this.setId(1);
                HelperFollowEvent.this.setObject(position);
                EventBus.getDefault().post(HelperFollowEvent.this);
            }
        });
    }
    public void doFollowSetUp(String infoId,final int position) {//置顶
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());
        paratems.put("infoId", infoId);
        RetrofitInstance.getInstance().post(URL_EVENT_AIDE_SETTOP, paratems, String.class, new BaseSubscriber<NetResponse<String>>(false) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

            @Override
            public void onNext(NetResponse<String> netResponse) {
                HelperFollowEvent.this.setId(2);
                HelperFollowEvent.this.setObject(position);
                EventBus.getDefault().post(HelperFollowEvent.this);
            }
        });
    }
    public void doFollowSetUpCancleTop(String infoId,final int position) {//取消置顶
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());
        paratems.put("infoId", infoId);
        RetrofitInstance.getInstance().post(URL_EVENT_AIDE_CANCELTOP, paratems, String.class, new BaseSubscriber<NetResponse<String>>(false) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

            @Override
            public void onNext(NetResponse<String> netResponse) {
                HelperFollowEvent.this.setId(3);
                HelperFollowEvent.this.setObject(position);
                EventBus.getDefault().post(HelperFollowEvent.this);
            }
        });
    }
    public void doFollowDone(String infoId,final int position) {//已处理
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());
        paratems.put("infoId", infoId);
        RetrofitInstance.getInstance().post(URL_EVENT_AIDE_DONE, paratems, String.class, new BaseSubscriber<NetResponse<String>>(false) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

            @Override
            public void onNext(NetResponse<String> netResponse) {
                HelperFollowEvent.this.setId(4);
                HelperFollowEvent.this.setObject(position);
                EventBus.getDefault().post(HelperFollowEvent.this);
            }
        });
    }
    public void getMoreEvent() {
        indexPage++;
        getHelperEventList(indexPage, "1","");
    }

    public void getRefreshEventList() {
        indexPage = 1;
        getHelperEventList(indexPage, "1","");
    }
}
