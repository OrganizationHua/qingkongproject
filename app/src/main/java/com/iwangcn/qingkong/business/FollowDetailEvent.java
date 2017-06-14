package com.iwangcn.qingkong.business;

import android.content.Context;
import android.text.TextUtils;

import com.iwangcn.qingkong.net.BaseSubscriber;
import com.iwangcn.qingkong.net.ExceptionHandle;
import com.iwangcn.qingkong.net.NetConst;
import com.iwangcn.qingkong.net.NetResponse;
import com.iwangcn.qingkong.net.RetrofitInstance;
import com.iwangcn.qingkong.providers.UserManager;
import com.iwangcn.qingkong.ui.model.ClientLabel;
import com.iwangcn.qingkong.ui.model.HeadLineModel;
import com.iwangcn.qingkong.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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


    public void getDetailData(String infoId) {//获取详情数据
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());
        paratems.put("infoId", infoId);
        RetrofitInstance.getInstance().post(URL_EVENT_FOLLOWUP_DETAIL, paratems, HeadLineModel.class, new BaseSubscriber<NetResponse<HeadLineModel>>(true) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                ToastUtil.showToast(mContext, e.codeMessage);
            }

            @Override
            public void onNext(NetResponse<HeadLineModel> netResponse) {
                FollowDetailEvent.this.setObject(netResponse.getDataObject());
                FollowDetailEvent.this.setId(0);
                EventBus.getDefault().post(FollowDetailEvent.this);
            }
        });
    }

    public void doCancleFollow(String infoId,final BaseSubscriber baseSubscriber) {//取消跟进
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());
        paratems.put("infoId", infoId);
        RetrofitInstance.getInstance().post(URL_EVENT_FOLLOWUP_CANCELFOLLOW, paratems, String.class, new BaseSubscriber<NetResponse<String>>(false) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                ToastUtil.showToast(mContext, e.codeMessage);
            }

            @Override
            public void onNext(NetResponse<String> netResponse) {
                ToastUtil.showToast(mContext, "取消跟进成功");
                if(baseSubscriber!=null){
                    baseSubscriber.onNext(netResponse);
                }
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
                ToastUtil.showToast(mContext, e.codeMessage);
            }

            @Override
            public void onNext(NetResponse<String> netResponse) {
                ToastUtil.showToast(mContext, "置顶成功");
                FollowDetailEvent.this.setId(2);
                EventBus.getDefault().post(FollowDetailEvent.this);
            }
        });
    }
    public void doFollowSetUp(String infoId,final BaseSubscriber baseSubscriber) {//置顶
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());
        paratems.put("infoId", infoId);
        RetrofitInstance.getInstance().post(URL_EVENT_FOLLOWUP_SETTOP, paratems, String.class, new BaseSubscriber<NetResponse<String>>(false) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                ToastUtil.showToast(mContext, e.codeMessage);
            }

            @Override
            public void onNext(NetResponse<String> netResponse) {
                ToastUtil.showToast(mContext, "置顶成功");
                FollowDetailEvent.this.setId(2);
                baseSubscriber.onNext(netResponse);
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
                ToastUtil.showToast(mContext, e.codeMessage);
            }

            @Override
            public void onNext(NetResponse<String> netResponse) {
                ToastUtil.showToast(mContext, "取消置顶成功");
                FollowDetailEvent.this.setId(3);
                EventBus.getDefault().post(FollowDetailEvent.this);
            }
        });
    }
    public void doFollowSetUpCancleTop(String infoId,final BaseSubscriber baseSubscriber) {//取消置顶
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());
        paratems.put("infoId", infoId);
        RetrofitInstance.getInstance().post(URL_EVENT_FOLLOWUP_CANCELTOP, paratems, String.class, new BaseSubscriber<NetResponse<String>>(false) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                ToastUtil.showToast(mContext, e.codeMessage);
            }

            @Override
            public void onNext(NetResponse<String> netResponse) {
                ToastUtil.showToast(mContext, "取消置顶成功");
                FollowDetailEvent.this.setId(3);
                baseSubscriber.onNext(netResponse);
                EventBus.getDefault().post(FollowDetailEvent.this);
            }
        });
    }

    public void doFollowDone(String infoId,final BaseSubscriber baseSubscriber) {//已处理
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());
        paratems.put("infoId", infoId);
        RetrofitInstance.getInstance().post(URL_EVENT_FOLLOWUP_DONE, paratems, String.class, new BaseSubscriber<NetResponse<String>>(false) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                ToastUtil.showToast(mContext, e.codeMessage);
            }

            @Override
            public void onNext(NetResponse<String> netResponse) {
                ToastUtil.showToast(mContext, "已处理成功");
                if(baseSubscriber!=null){
                    baseSubscriber.onNext(netResponse);
                }
            }
        });
    }

    public void doFollowEvent(String eventId, String infoId, List<ClientLabel> recommendList, List<ClientLabel> myList, BaseSubscriber baseSubscriber) {//头条跟进
        StringBuilder builder = new StringBuilder();
        ArrayList<ClientLabel> recommendSelect = new ArrayList<>();
        for (ClientLabel clientLabel : recommendList
                ) {
            if (clientLabel.isSelect()) {
                recommendSelect.add(clientLabel);
            }
        }
        for (int i = 0; i < recommendSelect.size(); i++) {
            ClientLabel clientLabel = recommendSelect.get(i);
            if (clientLabel.isSelect()) {
                builder.append(clientLabel.getName());
                if (i != recommendSelect.size() - 1) {
                    builder.append(",");
                } else {
                    builder.append("##");
                }
            }
        }
        for (ClientLabel clientLable : myList
                ) {
            if (clientLable.isSelect()) {
                builder.append(clientLable.getName());
                builder.append(",");
            }
        }
        if(TextUtils.isEmpty(builder.toString())){
            ToastUtil.showToast(mContext,"请至少选择一个标签");
            return;
        }
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());
        paratems.put("eventId", eventId);
        paratems.put("infoId", infoId);
        paratems.put("sourceType", "1");
        paratems.put("tags", builder.toString());
        RetrofitInstance.getInstance().post(URL_EVENT_DOFOLLOW, paratems, String.class, baseSubscriber);
    }

}
