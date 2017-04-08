package com.iwangcn.qingkong.ui.view.BessleAnimation;

import android.content.Context;
import android.graphics.PointF;
import android.widget.LinearLayout;

/**
 * Created by czh on 2017/4/7.
 */

public class MoveImageView extends LinearLayout {

    public MoveImageView(Context context) {
        super(context);
    }

    public void setMPointF(PointF pointF) {
        setX(pointF.x);
        setY(pointF.y);
    }
}
