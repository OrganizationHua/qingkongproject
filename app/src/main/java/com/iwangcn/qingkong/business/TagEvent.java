package com.iwangcn.qingkong.business;

import android.content.Context;

import com.iwangcn.qingkong.net.BaseSubscriber;
import com.iwangcn.qingkong.net.ExceptionHandle;
import com.iwangcn.qingkong.net.NetConst;
import com.iwangcn.qingkong.net.RetrofitInstance;
import com.iwangcn.qingkong.providers.UserManager;
import com.iwangcn.qingkong.ui.model.CilentLabel;
import com.iwangcn.qingkong.ui.model.UserInfo;
import com.iwangcn.qingkong.utils.ToastUtil;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.List;

/**
 * 标签管理
 * Created by czh on 2017/4/27.
 */

public class TagEvent extends Event implements NetConst {
    //来源标签是蓝的，关键字标签是绿的，业务标签是橙的，自定义标签是黄的，报错标签是红的
    private Context mContext;

    public TagEvent() {
    }

    public TagEvent(Context context) {
        this.mContext = context;
    }

    public void getTagList() {
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());
        RetrofitInstance.getInstance().post(URL_TAG_TAGLIST, paratems, CilentLabel.class, new BaseSubscriber<List<CilentLabel>>(false) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                ToastUtil.showToast(mContext, e.codeMessage);
            }

            @Override
            public void onNext(List<CilentLabel> o) {
                Logger.e("11");
            }
        });
    }

    public void submitTags(String tags) {
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());
        paratems.put("tags", tags);
        RetrofitInstance.getInstance().post(URL_TAG_SUBMITTAGS, paratems, UserInfo.class, new BaseSubscriber(false) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

            @Override
            public void onNext(Object o) {

            }
        });
    }

    public void deleteTags() {
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());
        RetrofitInstance.getInstance().post(URL_TAG_DELTAGS, paratems, UserInfo.class, new BaseSubscriber(false) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

            @Override
            public void onNext(Object o) {

            }
        });
    }
}
