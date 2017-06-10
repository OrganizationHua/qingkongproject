package com.iwangcn.qingkong.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.ui.model.QkTagModel;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

/**
 * Created by zzjs on 2017/6/7.
 */

public class QKTagAdapter extends TagAdapter<QkTagModel> {
    private List<QkTagModel> mList;
    private Context mContext;
    private String[] tagColors = new String[]{"#0a83c9", "#59b95e", "#ff8400", "#efbf00"};
    public QKTagAdapter(Context context,List<QkTagModel> data) {
        super(data);
        this.mList = data;
        this.mContext = context;
    }

    @Override
    public View getView(FlowLayout parent, int position, QkTagModel qkTagModel) {
        TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.tv,
                parent, false);
        tv.setText(qkTagModel.getTagText());
        tv.setBackground(getShapeDrawable(qkTagModel.getColorType()));
        return tv;
    }
    private Drawable getShapeDrawable(int colorType) {
        int strokeWidth = 0;     // 1dp 边框宽度
        int roundRadius = 10;     // 5dp 圆角半径
        int strokeColor = Color.parseColor("#FFFF0000");//边框颜色
        int fillColor = Color.parseColor(tagColors[colorType]); //内部填充颜色

        GradientDrawable gd = new GradientDrawable();//创建drawable
        gd.setColor(fillColor);

        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, strokeColor);
        gd.setShape(GradientDrawable.RECTANGLE);
        return gd;
    }
}
