package com.iwangcn.qingkong.business;

import android.content.Context;

import com.iwangcn.qingkong.dao.imp.EventInfoDao;
import com.iwangcn.qingkong.dao.manager.DaoFactory;
import com.iwangcn.qingkong.net.BaseSubscriber;
import com.iwangcn.qingkong.net.ExceptionHandle;
import com.iwangcn.qingkong.net.NetConst;
import com.iwangcn.qingkong.net.NetResponse;
import com.iwangcn.qingkong.net.RetrofitInstance;
import com.iwangcn.qingkong.providers.UserManager;
import com.iwangcn.qingkong.ui.model.EventInfo;
import com.iwangcn.qingkong.ui.model.EventInfoVo;
import com.orhanobut.logger.Logger;

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

    private void getNewsEventList(int indexPage, String keyword) {
        HomeEvent homeEvent = new HomeEvent();
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());
        paratems.put("keyword", keyword);
        paratems.put("pageno", indexPage);
        RetrofitInstance.getInstance().post(URL_EVENT, paratems, EventInfoVo.class, new BaseSubscriber
                <NetResponse<EventInfoVo>>(false) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                Logger.e(e.toString());
                EventBus.getDefault().post(new LoadFailEvent());
            }

            @Override

            public void onNext(NetResponse<EventInfoVo> o) {
                Logger.e(o.getDataList().size()+"");
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
        long currentTime = System.currentTimeMillis();
        long test = 1492773512791l;
        mList = mDao.getList();
        for (int i = 0; i < 15; i++) {
            EventInfoVo infoVo = new EventInfoVo();
            EventInfo model = new EventInfo();

            if (i == 0) {
                model.setUpdateTime2(System.currentTimeMillis());
            } else if (i == 1) {
                model.setUpdateTime2(1492708630000l);
            } else if (i == 2 || i == 3) {
                model.setUpdateTime2(1492622230000l);
            } else if (i == 4) {
                model.setUpdateTime2(1492535830000l);
            } else {
                model.setUpdateTime2(1489857430000l);
            }
            model.setCreateUid("2223条");
            model.setName("当地时间6日，国家主席习近平在美国佛罗里达州海湖庄园同美国总统特朗普举行中美元首会晤");
            infoVo.setEventInfo(model);
            infoVo.setInfoCount(3333);
            mList.add(infoVo);
        }

        return mList;
    }
}
