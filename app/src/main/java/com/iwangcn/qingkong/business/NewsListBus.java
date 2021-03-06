package com.iwangcn.qingkong.business;

import android.content.Context;

import com.iwangcn.qingkong.net.BaseSubscriber;
import com.iwangcn.qingkong.net.ExceptionHandle;
import com.iwangcn.qingkong.net.NetConst;
import com.iwangcn.qingkong.net.NetResponse;
import com.iwangcn.qingkong.net.RetrofitInstance;
import com.iwangcn.qingkong.providers.UserManager;
import com.iwangcn.qingkong.ui.model.EventDataVo;
import com.iwangcn.qingkong.ui.model.EventInfo;
import com.iwangcn.qingkong.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 事件详情（新闻列表）
 * Created by czh on 2017/4/26.
 */

public class NewsListBus extends Event implements NetConst {
    private Context mContext;
    private int indexPage = 1;//当前页数

    public NewsListBus() {
    }

    public NewsListBus(Context context) {
        this.mContext = context;
    }

    private void getDataList(final int indexPage, EventInfo eventInfo, boolean isShow, String tags, String sourceType) {
        if (eventInfo == null) {
            ToastUtil.showToast(mContext, "数据异常");
            return;
        }
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());
        paratems.put("pageno", indexPage);
        paratems.put("sourceType", sourceType);
        paratems.put("eventId", eventInfo.getAutoId());
        paratems.put("tags", tags);

        RetrofitInstance.getInstance().post(URL_EVENT_RELINFO, paratems, EventDataVo.class, new BaseSubscriber<NetResponse<List<EventDataVo>>>(isShow) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                EventBus.getDefault().post(new LoadFailEvent());
            }

            @Override

            public void onNext(NetResponse<List<EventDataVo>> o) {
                if (o.getDataList() == null) {
                 //   ToastUtil.showToast(mContext, "暂无相关数据");
                    o.setDataList(new ArrayList<List<EventDataVo>>());
                }
                NewsListBus event = new NewsListBus();
                event.setObject(o.getDataList());
                if(indexPage>1){
                    event.setIsMore(true);
                }else {
                    event.setIsMore(false);
                }
                EventBus.getDefault().post(event);
            }
        });
    }

    public void getMoreEvent(EventInfo eventInfoVo, String tags, String sourceType) {
        indexPage++;
        getDataList(indexPage, eventInfoVo, true, tags, sourceType);
    }

    public void getRefreshEventList(EventInfo eventInfoVo, boolean isShow, String tags, String sourceType) {
        indexPage = 1;
        getDataList(indexPage, eventInfoVo, isShow, tags, sourceType);
    }
}
