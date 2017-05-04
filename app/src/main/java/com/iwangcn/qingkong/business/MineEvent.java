package com.iwangcn.qingkong.business;

import android.content.Context;

import com.iwangcn.qingkong.net.BaseSubscriber;
import com.iwangcn.qingkong.net.ExceptionHandle;
import com.iwangcn.qingkong.net.NetConst;
import com.iwangcn.qingkong.net.RetrofitInstance;
import com.iwangcn.qingkong.providers.UserManager;
import com.iwangcn.qingkong.ui.model.UserInfo;
import com.iwangcn.qingkong.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

/**
 * Created by czh on 2017/4/18.
 */

public class MineEvent extends Event implements NetConst {
    private Context mContext;
    final public int updatePaw = 0;
    final public int checkVersion =1;

    public MineEvent() {

    }

    public MineEvent(Context context) {
        this.mContext = context;
    }

    public void updatePwd(String oldPwd, String newPwd) {
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());
        paratems.put("oldPwd", oldPwd);
        paratems.put("newPwd", newPwd);
        paratems.put("renewPwd", newPwd);
        RetrofitInstance.getInstance().post(URL_UPDATE_PWD, paratems, UserInfo.class, new BaseSubscriber(true) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                ToastUtil.showToast(mContext,e.codeMessage);
            }

            @Override
            public void onNext(Object o) {
                MineEvent even = new MineEvent();
                even.setObject(o);
                even.setId(updatePaw);
                EventBus.getDefault().post(even);
            }
        });
    }
    public void checkVersion(String oldPwd, String newPwd) {
        HashMap paratems = new HashMap();
        RetrofitInstance.getInstance().post(URL_CHECK_VERSION, paratems, UserInfo.class, new BaseSubscriber(true) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

            @Override
            public void onNext(Object o) {
                MineEvent even = new MineEvent();
                even.setObject(o);
                even.setId(checkVersion);
                EventBus.getDefault().post(even);
            }
        });
    }
}
