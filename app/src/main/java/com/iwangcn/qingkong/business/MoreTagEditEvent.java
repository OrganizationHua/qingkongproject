package com.iwangcn.qingkong.business;

import android.content.Context;

import com.iwangcn.qingkong.net.BaseSubscriber;
import com.iwangcn.qingkong.net.ExceptionHandle;
import com.iwangcn.qingkong.net.NetConst;
import com.iwangcn.qingkong.net.RetrofitInstance;
import com.iwangcn.qingkong.providers.UserManager;
import com.iwangcn.qingkong.ui.model.ClientLabel;
import com.iwangcn.qingkong.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;

/**
 * Created by czh on 2017/6/11.
 */

public class MoreTagEditEvent extends Event implements NetConst {
    private Context mContext;
    public static final int TAG_UPDATE_TOUTIAO = 1;//头条标签更新
    public static final int TAG_UPDATE_HELP = 2;//助手标签更新

    public MoreTagEditEvent() {

    }

    public MoreTagEditEvent(Context context) {
        mContext = context;
    }

    public void updateLabels(final int type, String autoId, List<ClientLabel> listData, List<ClientLabel> myListData) {
        StringBuilder strListData = new StringBuilder();
        for (ClientLabel model : listData) {
            if (model.isSelect()) {
                strListData.append(model.getName());
                strListData.append(",");
            }
        }
        StringBuilder strMyListData = new StringBuilder();
        for (ClientLabel model : myListData) {
            if (model.isSelect()) {
                strMyListData.append(model.getName());
                strMyListData.append(",");
            }
        }
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());
        paratems.put(type, String.valueOf(type));
        paratems.put("processId", autoId);
        paratems.put("businessTags", strListData.toString());
        paratems.put("selfTags", strMyListData.toString());
        RetrofitInstance.getInstance().post(URL_UPDATE_LABELS, paratems, String.class, new BaseSubscriber(true) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                ToastUtil.showToast(mContext, e.codeMessage);
            }

            @Override
            public void onNext(Object o) {
                ToastUtil.showToast(mContext, "更新成功");
                if (type == 2) {
                    MoreTagEditEvent tagEvent = new MoreTagEditEvent();
                    tagEvent.setId(TAG_UPDATE_HELP);
                    EventBus.getDefault().post(tagEvent);
                }
            }
        });
    }

}
