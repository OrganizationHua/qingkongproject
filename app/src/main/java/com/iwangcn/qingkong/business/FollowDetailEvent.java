package com.iwangcn.qingkong.business;

import android.content.Context;

import com.iwangcn.qingkong.net.BaseSubscriber;
import com.iwangcn.qingkong.net.ExceptionHandle;
import com.iwangcn.qingkong.net.NetConst;
import com.iwangcn.qingkong.net.NetResponse;
import com.iwangcn.qingkong.net.RetrofitInstance;
import com.iwangcn.qingkong.providers.UserManager;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

/**
 * Created by RF on 2017/4/24.
 */

public class FollowDetailEvent extends Event implements NetConst {
    private Context mContext;
    private int indexPage = 0;//当前页数
    private int type;

    public FollowDetailEvent(Context context) {
        this.mContext = context;
    }

    public void doCancleFollow(String infoId) {//取消跟进
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());
        paratems.put("infoId", infoId);
        RetrofitInstance.getInstance().post(URL_EVENT_FOLLOWUP_CANCELFOLLOW, paratems, String.class, new BaseSubscriber<NetResponse<String>>(false) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

            @Override
            public void onNext(NetResponse<String> netResponse) {
                FollowDetailEvent.this.setId(1);
                EventBus.getDefault().post(FollowDetailEvent.this);
            }
        });
    }

    public void doFollowSetUp(String infoId) {//置顶
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());
        paratems.put("infoId", infoId);
        RetrofitInstance.getInstance().post(URL_EVENT_FOLLOWUP_SETTOP, paratems, String.class, new BaseSubscriber<NetResponse<String>>(false) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

            @Override
            public void onNext(NetResponse<String> netResponse) {
                FollowDetailEvent.this.setId(2);
                EventBus.getDefault().post(FollowDetailEvent.this);
            }
        });
    }

    public void doFollowSetUpCancleTop(String infoId) {//取消置顶
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());
        paratems.put("infoId", infoId);
        RetrofitInstance.getInstance().post(URL_EVENT_FOLLOWUP_CANCELTOP, paratems, String.class, new BaseSubscriber<NetResponse<String>>(false) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

            @Override
            public void onNext(NetResponse<String> netResponse) {
                FollowDetailEvent.this.setId(3);
                EventBus.getDefault().post(FollowDetailEvent.this);
            }
        });
    }

    public void doFollowDone(String infoId) {//已处理
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());
        paratems.put("infoId", infoId);
        RetrofitInstance.getInstance().post(URL_EVENT_FOLLOWUP_DONE, paratems, String.class, new BaseSubscriber<NetResponse<String>>(false) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

            @Override
            public void onNext(NetResponse<String> netResponse) {
                FollowDetailEvent.this.setId(4);
                EventBus.getDefault().post(FollowDetailEvent.this);
            }
        });
    }
}
