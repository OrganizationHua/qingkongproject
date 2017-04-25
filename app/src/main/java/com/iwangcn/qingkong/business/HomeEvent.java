package com.iwangcn.qingkong.business;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.iwangcn.qingkong.dao.imp.EventInfoDao;
import com.iwangcn.qingkong.dao.manager.DaoFactory;
import com.iwangcn.qingkong.net.ACache;
import com.iwangcn.qingkong.net.BaseSubscriber;
import com.iwangcn.qingkong.net.ExceptionHandle;
import com.iwangcn.qingkong.net.NetConst;
import com.iwangcn.qingkong.net.NetResponse;
import com.iwangcn.qingkong.net.RetrofitInstance;
import com.iwangcn.qingkong.providers.UserManager;
import com.iwangcn.qingkong.ui.model.EventInfoVo;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 头条首页
 * Created by czh on 2017/4/17.
 */

public class HomeEvent extends Event implements NetConst {
    private Context mContext;
    private EventInfoDao mDao;
    private int indexPage = 1;//当前页数

    public HomeEvent() {

    }

    public HomeEvent(Context context) {
        mContext = context;
        mDao = (EventInfoDao) DaoFactory.getDao(DaoFactory.DaoType.EVENT_INFO);
    }

    private void getNewsEventList(int indexPage) {
        getNewsEventList(indexPage, "");
    }

    private void getNewsEventList(final int indexPage, String keyword) {
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());
        paratems.put("keyword", keyword);
        paratems.put("pageno", indexPage);
        RetrofitInstance.getInstance().post(URL_EVENT, paratems, EventInfoVo.class, new BaseSubscriber
                <NetResponse<EventInfoVo>>(false) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                EventBus.getDefault().post(new LoadFailEvent());
            }

            @Override

            public void onNext(NetResponse<EventInfoVo> o) {
                HomeEvent homeEvent = new HomeEvent();
                homeEvent.setObject(o.getDataList());
                if (indexPage == 1) {
                    homeEvent.setIsMore(false);
                } else {
                    homeEvent.setIsMore(true);
                }
                EventBus.getDefault().post(homeEvent);
                ACache.get(mContext).put(URL_EVENT, JSON.toJSONString(o.getDataList()));
            }
        });
    }

    public void getMoreEvent() {
        indexPage++;
        getNewsEventList(indexPage);
    }

    public void getRefreshEventList() {
        indexPage = 1;
        getNewsEventList(indexPage);
    }

    public List<EventInfoVo> getCacheNews() {
        List mList = new ArrayList<>();
        try {
            mList= JSON.parseArray(ACache.get(mContext).getAsString(URL_EVENT), EventInfoVo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mList;
    }
}
