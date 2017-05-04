package com.iwangcn.qingkong.business;

import android.content.Context;

import com.iwangcn.qingkong.net.ACache;
import com.iwangcn.qingkong.net.BaseSubscriber;
import com.iwangcn.qingkong.net.ExceptionHandle;
import com.iwangcn.qingkong.net.NetConst;
import com.iwangcn.qingkong.net.NetResponse;
import com.iwangcn.qingkong.net.RetrofitInstance;
import com.iwangcn.qingkong.providers.UserManager;
import com.iwangcn.qingkong.ui.model.EventData;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by czh on 2017/4/27.
 */

public class NewsSearchEvent extends Event implements NetConst {
    public static final String LOACT_HISTORY_KEY = "local_history_key";
    private Context mContext;
    private int indexPage = 1;//当前页数

    public NewsSearchEvent() {
    }

    public NewsSearchEvent(Context context) {
        this.mContext = context;
    }

    public void saveSearchHistory(String strSearch) {
        try {
            List<String> list = (List) ACache.get(mContext).getAsObject(LOACT_HISTORY_KEY);
            if (list == null) {
                list = new ArrayList();
            }
            for (String str : list) {
                if (strSearch.equals(str)) {
                    return;
                }
            }
            if (list.size() >= 10) {
                list.remove(0);
            }
            list.add(strSearch);
            ACache.get(mContext).put(LOACT_HISTORY_KEY, (Serializable) list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getListHistory() {
        List<String> list = (List) ACache.get(mContext).getAsObject(LOACT_HISTORY_KEY);
        if (list == null) {
            list = new ArrayList<>();
        }
        Collections.reverse(list);
        return list;
    }

    private void getDataList(final int index, String keyword) {
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());
        paratems.put("pageno", index);
        paratems.put("keyword", keyword);
        RetrofitInstance.getInstance().post(URL_EVENT_SEARCH_NEWS, paratems, EventData.class, new BaseSubscriber<NetResponse<List<EventData>>>(true) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                EventBus.getDefault().post(new LoadFailEvent());
            }

            @Override

            public void onNext(NetResponse<List<EventData>> o) {
                NewsSearchEvent event = new NewsSearchEvent();
                event.setObject(o.getDataList());
                if (index == 1) {
                    event.setIsMore(false);
                } else {
                    event.setIsMore(true);
                }
                EventBus.getDefault().post(event);
            }
        });
    }

    public void getMoreEvent(String keyword) {
        indexPage++;
        getDataList(indexPage, keyword);
    }

    public void getRefreshEventList(String keyword) {
        indexPage = 1;
        getDataList(indexPage, keyword);
    }
}
