package com.iwangcn.qingkong.business;

import android.content.Context;

import com.iwangcn.qingkong.net.BaseSubscriber;
import com.iwangcn.qingkong.net.ExceptionHandle;
import com.iwangcn.qingkong.net.NetConst;
import com.iwangcn.qingkong.net.RetrofitInstance;
import com.iwangcn.qingkong.ui.model.UserInfo;

import java.util.HashMap;

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
        HashMap paratems = null;
        RetrofitInstance.getInstance().post(URL_TAG_TAGLIST, paratems, UserInfo.class, new BaseSubscriber(false) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

            @Override
            public void onNext(Object o) {

            }
        });
    }

    public void submitTags() {
        HashMap paratems = null;
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
        HashMap paratems = null;
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
