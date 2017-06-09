package com.iwangcn.qingkong.ui.view.TagWidget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.ui.model.ClientLabel;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

/**
 * Created by czh on 2017/4/21.
 */

public class PopMoreTagAdapter extends TagAdapter<ClientLabel> {

    public static final String COLOR_RECOMMNF="#f19244";//推荐标签
    public static final String COLOR_MINE = "#e7bf4d";//自定义标签
    private List<ClientLabel> mList;
    private Context mContext;

    private String[] tagColors = new String[]{"#FFFFFFFF", "#FFf4924A", "#e6c14c", "#b33b37"};
    private String tagColorType = "#FFFFFFFF";

    public PopMoreTagAdapter(List<ClientLabel> datas, Context context) {
        super(datas);
        this.mList = datas;
        this.mContext = context;
    }


    public void setSelectTagColor(String colorType) {
        this.tagColorType = colorType;
    }

    @Override
    public View getView(FlowLayout parent, int position, ClientLabel ClientLabel) {
        ClientLabel model = mList.get(position);
        TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.tag_popupwindow_item,
                parent, false);
        if (model.isSelect()) {
            tv.setSelected(true);
            tv.setTextColor(Color.WHITE);
            tv.setBackground(getShapeDrawable(tagColorType));
        } else {
            tv.setSelected(false);
            tv.setTextColor(mContext.getResources().getColor(R.color.font_gray_666));
            tv.setBackground(getShapeDrawable("#FFFFFF"));
        }
        tv.setText(model.getName());
        return tv;
    }

    private Drawable getShapeDrawable(String colorType) {
        int strokeWidth = 0;     // 1dp 边框宽度
        int roundRadius = 10;     // 5dp 圆角半径
        int strokeColor = Color.parseColor("#FFFF0000");//边框颜色
        int fillColor = Color.parseColor(colorType); //内部填充颜色

        GradientDrawable gd = new GradientDrawable();//创建drawable
        gd.setColor(fillColor);

        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, strokeColor);
        gd.setShape(GradientDrawable.RECTANGLE);
        return gd;
    }
}
