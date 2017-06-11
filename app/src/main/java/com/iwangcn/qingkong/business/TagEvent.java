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
    public static final int TAG_GETLIST = 0;
    public static final int TAG_DELETE = 1;
    public static final int TAG_UPDATE_TOUTIAO = 2;//头条标签更新
    public static final int TAG_UPDATE_HELP = 3;//助手标签更新

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
                        event.setId(TAG_GETLIST);
                        event.setObject(list);
                        EventBus.getDefault().post(event);
                    } else {
                        ToastUtil.showToast(mContext, "数据异常");
                    }
                }
            }
        });
    }

    public void getTagList(boolean isShow) {
        HashMap paratems = new HashMap();
        paratems.put(USER_ID, UserManager.getUserInfo().getAutoId());

        RetrofitInstance.getInstance().post(URL_TAG_TAGLIST, paratems, String.class, new BaseSubscriber<NetResponse>(isShow) {
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
        RetrofitInstance.getInstance().post(URL_TAG_SUBMITTAGS, paratems, String.class, baseSubscriber);
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
     * 我要报错
     *
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
        RetrofitInstance.getInstance().post(URL_REPORT_ERROR, paratems, BaseBean.class, new BaseSubscriber(true) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                ToastUtil.showToast(mContext, e.codeMessage);
            }

            @Override
            public void onNext(Object o) {
                ToastUtil.showToast(mContext, "已提交报错");
            }
        });
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
        RetrofitInstance.getInstance().post(URL_UPDATE_LABELS, paratems, BaseBean.class, new BaseSubscriber(true) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                ToastUtil.showToast(mContext, e.codeMessage);
            }

            @Override
            public void onNext(Object o) {
                ToastUtil.showToast(mContext, "更新成功");

                if (type == 2) {
                    TagEvent tagEvent = new TagEvent();
                    tagEvent.setId(TAG_UPDATE_HELP);
                    EventBus.getDefault().post(tagEvent);
                }
            }
        });
    }

}
