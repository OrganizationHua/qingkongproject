package com.iwangcn.qingkong.business;

import android.content.Context;

import com.iwangcn.qingkong.net.ACache;
import com.iwangcn.qingkong.net.NetConst;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by czh on 2017/4/27.
 */

public class NewsSearchEvent extends Event implements NetConst {
    public static final String LOACT_HISTORY_KEY = "local_history_key";
    private Context mContext;

    public NewsSearchEvent() {
    }

    public NewsSearchEvent(Context context) {
        this.mContext = context;
    }

    public void saveSearchHistory(String strSearch) {
        try {
            List<String> list = (List) ACache.get(mContext).getAsObject(LOACT_HISTORY_KEY);
            if (list == null) {
                list = new ArrayList();
            }
            for (String str : list) {
                if (strSearch.equals(str)) {
                    return;
                }
            }
            if (list.size() >= 10) {
                list.remove(0);
            }
            list.add(strSearch);
            ACache.get(mContext).put(LOACT_HISTORY_KEY, (Serializable) list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getListHistory() {
        List<String> list = (List) ACache.get(mContext).getAsObject(LOACT_HISTORY_KEY);
        if (list == null) {
            list = new ArrayList<>();
        }
        Collections.reverse(list);
        return list;
    }
}
