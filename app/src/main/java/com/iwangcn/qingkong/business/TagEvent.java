package com.iwangcn.qingkong.business;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.iwangcn.qingkong.net.BaseBean;
import com.iwangcn.qingkong.net.BaseSubscriber;
import com.iwangcn.qingkong.net.ExceptionHandle;
import com.iwangcn.qingkong.net.NetConst;
import com.iwangcn.qingkong.net.NetResponse;
import com.iwangcn.qingkong.net.RetrofitInstance;
import com.iwangcn.qingkong.providers.UserManager;
import com.iwangcn.qingkong.ui.model.ClientLabel;
import com.iwangcn.qingkong.ui.model.LabelError;
import com.iwangcn.qingkong.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
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

        RetrofitInstance.getInstance().post(URL_TAG_TAGLIST, paratems, String.class, new BaseSubscriber<NetResponse>(true) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                ToastUtil.showToast(mContext, e.codeMessage);
            }

            @Override
            public void onNext(NetResponse o) {
                if (!TextUtils.isEmpty(o.getData())) {
                    ArrayList<ArrayList<ClientLabel>> list = parserTagData(o.getData());
                    if (list != null) {
                        // baseSubscriber.onNext(list);
                        TagEvent event = new TagEvent();
                        event.setObject(list);
                        EventBus.getDefault().post(event);
                    } else {
                        ToastUtil.showToast(mContext, "数据异常");
                    }
                }
            }
        });
    }

    private ArrayList<ArrayList<ClientLabel>> parserTagData(String array) {
        try {
            JSONArray jsonArray = new JSONArray(array);
            ArrayList<ArrayList<ClientLabel>> listSum = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                ArrayList<ClientLabel> list = (ArrayList<ClientLabel>) JSON.parseArray(jsonArray.get(i).toString(), ClientLabel.class);
                listSum.add(list);
            }
            return listSum;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public class test {
        ArrayList<ClientLabel> list;

        public ArrayList<ClientLabel> getList() {
            return list;
        }

        public void setList(ArrayList<ClientLabel> list) {
            this.list = list;
        }
    }

    public void submitTags(String tags, BaseSubscriber baseSubscriber) {
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());
        paratems.put("tags", tags);
        RetrofitInstance.getInstance().post(URL_TAG_SUBMITTAGS, paratems, BaseBean.class, baseSubscriber);
    }

    public void deleteTags(ClientLabel clientLabel, BaseSubscriber baseSubscriber) {
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());
        paratems.put("tagId", String.valueOf(clientLabel.getAutoId()));
        RetrofitInstance.getInstance().post(URL_TAG_DELTAGS, paratems, BaseBean.class, baseSubscriber);
    }

    /**
     * 查询报错标签
     *
     * @param baseSubscriber
     */
    public void getErrorLabels(BaseSubscriber baseSubscriber) {
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());
        RetrofitInstance.getInstance().post(URL_REROR_LABELS, paratems, LabelError.class, baseSubscriber);
    }

    /**
     * @param autoId
     * @param listData
     */
    public void reportLabels(String autoId, List<LabelError> listData) {
        StringBuilder stringBuilder = new StringBuilder();
        for (LabelError model : listData) {
            if (model.isSelect()) {
                stringBuilder.append(model.getName());
                stringBuilder.append(",");
            }
        }
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());
        paratems.put("infoId", autoId);
        paratems.put("tags", stringBuilder.toString());
        RetrofitInstance.getInstance().post(URL_REPORT_ERROR, paratems, LabelError.class, new BaseSubscriber<NetResponse>(true) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                ToastUtil.showToast(mContext, e.codeMessage);
            }

            @Override
            public void onNext(NetResponse o) {
                ToastUtil.showToast(mContext, o.getMessage());
            }
        });
    }
}
