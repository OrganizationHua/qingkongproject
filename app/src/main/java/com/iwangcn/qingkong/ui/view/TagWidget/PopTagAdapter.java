package com.iwangcn.qingkong.ui.view.TagWidget;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.iwangcn.qingkong.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

/**
 * Created by czh on 2017/4/21.
 */

public class PopTagAdapter extends TagAdapter<TagModel> {
    private List<TagModel> mList;
    private Context mContext;

    public PopTagAdapter(List<TagModel> datas, Context context) {
        super(datas);
        this.mList = datas;
        this.mContext = context;
    }

    @Override
    public View getView(FlowLayout parent, int position, TagModel tagModel) {
        TagModel model = mList.get(position);
        TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.tag_popupwindow_item,
                parent, false);
        if (model.isSelect()) {
            tv.setSelected(true);
            tv.setTextColor(Color.WHITE);
            tv.setBackgroundResource(R.drawable.tag_popupwindown_select_red);
        } else {
            tv.setSelected(false);
            tv.setTextColor(mContext.getResources().getColor(R.color.font_gray_666));
            tv.setBackgroundResource(R.drawable.tag_popupwindown_select_norml);
        }
        tv.setText(model.getTag());
        return tv;
    }
}
