package com.iwangcn.qingkong.business;

import android.content.Context;
import android.util.Log;

import com.iwangcn.qingkong.net.BaseSubscriber;
import com.iwangcn.qingkong.net.ExceptionHandle;
import com.iwangcn.qingkong.net.NetConst;
import com.iwangcn.qingkong.net.NetResponse;
import com.iwangcn.qingkong.net.RetrofitInstance;
import com.iwangcn.qingkong.providers.UserManager;
import com.iwangcn.qingkong.ui.model.HelperInfo;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

/**
 * Created by RF on 2017/4/24.
 */

public class FollowDetailEvent extends Event implements NetConst {
    private Context mContext;
    private int indexPage = 0;//当前页数

    public FollowDetailEvent(Context context) {
        this.mContext = context;

    }

    private void getCommentEventList(int index, int infoId) {
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());
        paratems.put("infoId", infoId);
        paratems.put("pageno",index);
        RetrofitInstance.getInstance().post(URL_EVENT_FOLLOWUP_DETAIL_COMMENT, paratems, HelperInfo.class, new BaseSubscriber<NetResponse<HelperInfo>>(false) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                EventBus.getDefault().post(new LoadFailEvent());
            }

            @Override
            public void onNext(NetResponse<HelperInfo> netResponse) {
                Log.e("fjg", netResponse.getDataList().size() + "");
                FollowDetailEvent.this.setObject(netResponse.getDataList());
                FollowDetailEvent.this.setId(0);
                if (indexPage == 1) {
                    FollowDetailEvent.this.setIsMore(false);
                } else {
                    FollowDetailEvent.this.setIsMore(true);
                }
                EventBus.getDefault().post(FollowDetailEvent.this);
            }
        });
    }

    public void doFollowDetail(int infoId) {
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());
        paratems.put("infoId", infoId);
        RetrofitInstance.getInstance().post(URL_EVENT_FOLLOWUP_DETAIL, paratems, String.class, new BaseSubscriber<NetResponse<String>>(false) {
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



    public void getMoreEvent() {
        indexPage++;
        getCommentEventList(indexPage,1);
    }

    public void getRefreshEventList() {
        indexPage = 1;
        getCommentEventList(indexPage,1);
    }
}
