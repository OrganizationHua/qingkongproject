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

public class HelperEvent extends Event implements NetConst {
    private Context mContext;
    private int indexPage = 0;//当前页数

    public HelperEvent(Context context) {
        this.mContext = context;

    }

    private void getHelperEventList(int index, String sourceType, String tags) {
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());
        paratems.put("sourceType", sourceType);
//        paratems.put("tags", tags);
//        paratems.put("pageno",index);
        RetrofitInstance.getInstance().post(URL_EVENT_HELP, paratems, HelperInfo.class, new BaseSubscriber<NetResponse<HelperInfo>>(false) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                EventBus.getDefault().post(new LoadFailEvent());
            }

            @Override
            public void onNext(NetResponse<HelperInfo> netResponse) {
                Log.e("fjg", netResponse.getDataList().size() + "");
                HelperEvent.this.setObject(netResponse.getDataList());
                HelperEvent.this.setId(0);
                if (indexPage == 1) {
                    HelperEvent.this.setIsMore(false);
                } else {
                    HelperEvent.this.setIsMore(true);
                }
                EventBus.getDefault().post(HelperEvent.this);
            }
        });
    }

    public void doHelperFollow(int infoId, final int position) {
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());
        paratems.put("infoId", infoId);
        RetrofitInstance.getInstance().post(URL_EVENT_DOFOllOW, paratems, String.class, new BaseSubscriber<NetResponse<String>>(false) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

            @Override
            public void onNext(NetResponse<String> netResponse) {
                HelperEvent.this.setId(1);
                HelperEvent.this.setObject(position);
                EventBus.getDefault().post(HelperEvent.this);
            }
        });
    }

    public void doDelete(int infoId, final int position) {
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());
        paratems.put("infoId", infoId);
        RetrofitInstance.getInstance().post(URL_EVENT_DELETE, paratems, String.class, new BaseSubscriber<NetResponse<String>>(false) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

            @Override
            public void onNext(NetResponse<String> netResponse) {
                HelperEvent.this.setId(2);
                HelperEvent.this.setObject(position);
                EventBus.getDefault().post(HelperEvent.this);
            }
        });
    }

    public void getMoreEvent() {
        indexPage++;
        HashMap paratems = new HashMap();
        getHelperEventList(indexPage, "", "");
    }

    public void getRefreshEventList() {
        indexPage = 1;
        HashMap paratems = new HashMap();
        getHelperEventList(indexPage, "", "");
    }
}
