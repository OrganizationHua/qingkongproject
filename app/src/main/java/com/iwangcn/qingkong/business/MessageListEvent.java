package com.iwangcn.qingkong.business;

import android.content.Context;

import com.iwangcn.qingkong.net.BaseSubscriber;
import com.iwangcn.qingkong.net.ExceptionHandle;
import com.iwangcn.qingkong.net.NetConst;
import com.iwangcn.qingkong.net.NetResponse;
import com.iwangcn.qingkong.net.RetrofitInstance;
import com.iwangcn.qingkong.providers.UserManager;
import com.iwangcn.qingkong.ui.model.HelperFeedbackDetail;
import com.iwangcn.qingkong.ui.model.HelperListModel;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

/**
 * Created by RF on 2017/4/24.
 */

public class MessageListEvent extends Event implements NetConst {
    private Context mContext;
    private int indexPage = 0;//当前页数
    private String infoId;

    public MessageListEvent(Context context, String infoId) {
        this.mContext = context;
        this.infoId = infoId;
    }

    private void getMessageList(int index) {
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());
        paratems.put("infoId", infoId);
        paratems.put("pageno", index);

        RetrofitInstance.getInstance().post(URL_EVENT_AIDE_MESSAGELIST, paratems, HelperFeedbackDetail.class, new BaseSubscriber<NetResponse<HelperFeedbackDetail>>(false) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                EventBus.getDefault().post(new LoadFailEvent());
            }

            @Override
            public void onNext(NetResponse<HelperFeedbackDetail> netResponse) {
                MessageListEvent.this.setObject(netResponse.getDataList());
                MessageListEvent.this.setId(0);
                if (indexPage == 1) {
                    MessageListEvent.this.setIsMore(false);
                } else {
                    MessageListEvent.this.setIsMore(true);
                }
                EventBus.getDefault().post(MessageListEvent.this);
            }
        });
    }

    public void commitMessage(String infoId) {//提交留言
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());
        paratems.put("infoId", infoId);
        RetrofitInstance.getInstance().post(URL_EVENT_AIDE_SUBMIT, paratems, String.class, new BaseSubscriber<NetResponse<String>>(false) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

            @Override
            public void onNext(NetResponse<String> netResponse) {
                MessageListEvent.this.setId(1);
                EventBus.getDefault().post(MessageListEvent.this);
            }
        });
    }

    public void getMoreEvent() {
        indexPage++;
        getMessageList(indexPage);
    }

    public void getRefreshEventList() {
        indexPage = 1;
        getMessageList(indexPage);
    }
}
