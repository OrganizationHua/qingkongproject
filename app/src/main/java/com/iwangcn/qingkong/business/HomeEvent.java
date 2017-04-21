package com.iwangcn.qingkong.business;

import android.content.Context;

import com.iwangcn.qingkong.dao.imp.EventInfoDao;
import com.iwangcn.qingkong.dao.manager.DaoFactory;
import com.iwangcn.qingkong.net.BaseSubscriber;
import com.iwangcn.qingkong.net.ExceptionHandle;
import com.iwangcn.qingkong.net.NetConst;
import com.iwangcn.qingkong.net.RetrofitInstance;
import com.iwangcn.qingkong.ui.model.EventInfo;
import com.iwangcn.qingkong.ui.model.UserInfo;

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
    private int indexPage = 0;//当前页数

    public HomeEvent(Context context) {
        mContext = context;
        mDao = (EventInfoDao) DaoFactory.getDao(DaoFactory.DaoType.EVENT_INFO);
    }

    private void getNewsEventList(int index, HashMap paratems) {
        RetrofitInstance.getInstance().post(URL_EVENT, paratems, UserInfo.class, new BaseSubscriber(false) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

            @Override
            public void onNext(Object o) {

            }
        });
    }

    public void getMoreEvent() {
        indexPage++;
        HashMap paratems = new HashMap();
        getNewsEventList(indexPage, paratems);
    }

    public void getRefreshEventList() {
        indexPage = 0;
        HashMap paratems = new HashMap();
        getNewsEventList(indexPage, paratems);
    }

    public List<EventInfo> getCacheNews() {
        List mList = new ArrayList<>();
        long currentTime=System.currentTimeMillis();
        long test=1492773512791l;
        mList = mDao.getList();
        for (int i = 0; i < 15; i++) {
            EventInfo model = new EventInfo();

            if(i==0){
                model.setUpdateTime(System.currentTimeMillis());
            }else if(i==1){
                model.setUpdateTime(1492708630000l);
            }else if(i==2||i==3){
                model.setUpdateTime(1492622230000l);
            }else if(i==4){
                model.setUpdateTime(1492535830000l);
            }else{
                model.setUpdateTime(1489857430000l);
            }
            model.setCreateUid("2223条");
            model.setName("当地时间6日，国家主席习近平在美国佛罗里达州海湖庄园同美国总统特朗普举行中美元首会晤");
            mList.add(model);
        }

        return mList;
    }
}
