package com.iwangcn.qingkong.business;

import android.content.Context;

import com.iwangcn.qingkong.net.BaseSubscriber;
import com.iwangcn.qingkong.net.ExceptionHandle;
import com.iwangcn.qingkong.net.NetConst;
import com.iwangcn.qingkong.net.NetResponse;
import com.iwangcn.qingkong.net.RetrofitInstance;
import com.iwangcn.qingkong.providers.UserManager;
import com.iwangcn.qingkong.ui.model.HeadLineModel;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

/**
 * Created by RF on 2017/4/24.
 */

public class HeadLineFollowEvent extends Event implements NetConst {
    private Context mContext;
    private int indexPage = 0;//当前页数

    public HeadLineFollowEvent(Context context) {
        this.mContext = context;

    }

    private void getHelperEventList(int index,String sourceType ,String tags) {
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());
        paratems.put("sourceType", sourceType);
//        paratems.put("tags", tags);
        paratems.put("pageno",index);
        paratems.put("dealFlag","0");
        RetrofitInstance.getInstance().post(URL_EVENT_FOLLOWUP, paratems, HeadLineModel.class, new BaseSubscriber<NetResponse<HeadLineModel>>(false) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                EventBus.getDefault().post(new LoadFailEvent());
            }

            @Override
            public void onNext(NetResponse<HeadLineModel> netResponse) {
                HeadLineFollowEvent.this.setObject(netResponse.getDataList());
                HeadLineFollowEvent.this.setId(0);
                if (indexPage == 1) {
                    HeadLineFollowEvent.this.setIsMore(false);
                } else {
                    HeadLineFollowEvent.this.setIsMore(true);
                }
                EventBus.getDefault().post(HeadLineFollowEvent.this);
            }
        });
    }
    public void doCancleFollow(String infoId,final int position) {//取消跟进
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());
        paratems.put("infoId", infoId);
        RetrofitInstance.getInstance().post(URL_EVENT_FOLLOWUP_CANCELFOLLOW, paratems, String.class, new BaseSubscriber<NetResponse<String>>(false) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

            @Override
            public void onNext(NetResponse<String> netResponse) {
                HeadLineFollowEvent.this.setId(1);
                HeadLineFollowEvent.this.setObject(position);
                EventBus.getDefault().post(HeadLineFollowEvent.this);
            }
        });
    }
    public void doFollowSetUp(String infoId,final int position) {//置顶
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());
        paratems.put("infoId", infoId);
        RetrofitInstance.getInstance().post(URL_EVENT_FOLLOWUP_SETTOP, paratems, String.class, new BaseSubscriber<NetResponse<String>>(false) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

            @Override
            public void onNext(NetResponse<String> netResponse) {
                HeadLineFollowEvent.this.setId(2);
                HeadLineFollowEvent.this.setObject(position);
                EventBus.getDefault().post(HeadLineFollowEvent.this);
            }
        });
    }
    public void doFollowSetUpCancleTop(String infoId,final int position) {//取消置顶
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());
        paratems.put("infoId", infoId);
        RetrofitInstance.getInstance().post(URL_EVENT_FOLLOWUP_CANCELTOP, paratems, String.class, new BaseSubscriber<NetResponse<String>>(false) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

            @Override
            public void onNext(NetResponse<String> netResponse) {
                HeadLineFollowEvent.this.setId(3);
                HeadLineFollowEvent.this.setObject(position);
                EventBus.getDefault().post(HeadLineFollowEvent.this);
            }
        });
    }
    public void doFollowDone(String infoId,final int position) {//已处理
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());
        paratems.put("infoId", infoId);
        RetrofitInstance.getInstance().post(URL_EVENT_FOLLOWUP_DONE, paratems, String.class, new BaseSubscriber<NetResponse<String>>(false) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

            @Override
            public void onNext(NetResponse<String> netResponse) {
                HeadLineFollowEvent.this.setId(4);
                HeadLineFollowEvent.this.setObject(position);
                EventBus.getDefault().post(HeadLineFollowEvent.this);
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
