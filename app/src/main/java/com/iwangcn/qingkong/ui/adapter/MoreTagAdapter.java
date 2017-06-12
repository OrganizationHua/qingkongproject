package com.iwangcn.qingkong.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.ui.model.ClientLabel;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

/**
 * Created by czh on 2017/6/12.
 */

public class MoreTagAdapter extends TagAdapter {
    private List<ClientLabel> mylist;
    private Context mContext;

    public MoreTagAdapter(Context context, List<ClientLabel> data) {
        super(data);
        this.mylist = data;
        this.mContext = context;
    }

    @Override
    public View getView(FlowLayout parent, int position, Object o) {
        LinearLayout ll = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.more_tag_delete_item,
                null);
        TextView tv = (TextView) ll.findViewById(R.id.tv);
        tv.setText(mylist.get(position).getName());

       // tv.setBackground(AbViewUtil.getShapeDrawableWithStroke("#FFFFFF", mContext.getString(R.string.tag_color_orange_my)));
        return ll;
    }
}