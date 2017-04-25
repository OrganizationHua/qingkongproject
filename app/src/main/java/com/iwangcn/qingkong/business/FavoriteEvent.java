package com.iwangcn.qingkong.business;

import android.content.Context;

import com.iwangcn.qingkong.net.BaseBean;
import com.iwangcn.qingkong.net.BaseSubscriber;
import com.iwangcn.qingkong.net.ExceptionHandle;
import com.iwangcn.qingkong.net.NetConst;
import com.iwangcn.qingkong.net.RetrofitInstance;
import com.iwangcn.qingkong.providers.UserManager;
import com.iwangcn.qingkong.ui.model.FavoriteInfo;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;

/**
 * 收藏Event
 * Created by czh on 2017/4/19.
 */

public class FavoriteEvent extends Event implements NetConst {
    private Context mContext;
    private int indexPage = 1;//当前页数

    public FavoriteEvent() {

    }

    public FavoriteEvent(Context context) {
        mContext = context;
    }


    private void getFavoriteList(int indexPage) {
        FavoriteEvent homeEvent = new FavoriteEvent();
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());
        paratems.put("pageno", indexPage);
        RetrofitInstance.getInstance().post(URL_EVENT_FAVORITE, paratems, FavoriteInfo.class, new BaseSubscriber
                <List<FavoriteInfo>>(false) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                Logger.e(e.toString());
                EventBus.getDefault().post(new LoadFailEvent());
            }

            @Override

            public void onNext(List<FavoriteInfo> o) {
                Logger.e(o.toString());
            }
        });
    }

    public void getMoreEvent() {
        indexPage++;
        getFavoriteList(indexPage);
    }

    public void getRefreshEventList() {
        indexPage = 1;
        getFavoriteList(indexPage);
    }

    /**
     * 添加收藏
     *
     * @param eventId
     */
    public void addFavoritet(String eventId,BaseSubscriber<BaseBean> subscriber) {
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());
        paratems.put("eventId", eventId);
        RetrofitInstance.getInstance().post(URL_EVENT_ADD, paratems, BaseBean.class,subscriber);
    }

    /**
     * 取消收藏
     *
     * @param fId
     */
    private void removeFavoritet(String fId,BaseSubscriber baseSubscriber) {
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());
        paratems.put("fId", fId);
        RetrofitInstance.getInstance().post(URL_EVENT_REMOVE, paratems, BaseBean.class, new BaseSubscriber
                <BaseBean>(false) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                postResult(false);
            }

            @Override

            public void onNext(BaseBean o) {
                postResult(true);
            }
        });
    }

    private void postResult(boolean isSuccess) {
        Event favoriteEvent = new Event();
        favoriteEvent.setIsSuccess(isSuccess);
        EventBus.getDefault().post(favoriteEvent);
    }
}
