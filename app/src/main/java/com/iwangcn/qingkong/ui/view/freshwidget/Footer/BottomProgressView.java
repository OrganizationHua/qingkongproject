package com.iwangcn.qingkong.ui.view.freshwidget.Footer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;

import com.iwangcn.qingkong.ui.view.freshwidget.IBottomView;


/**
 * 底部默认的条目...三个闪烁的蛋蛋.
 * <p>
 * Created by lcodecore on 2016/10/1.
 */
public class BottomProgressView extends ProgressView implements IBottomView {


    public BottomProgressView(Context context) {
        this(context, null);
    }

    public BottomProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        setLayoutParams(params);
        setIndicatorId(BallPulse);
    }

    private int normalColor = 0xffAAAAAA;
    private int animatingColor = 0xff0099ff;

    public void setNormalColor( int color) {
        normalColor = color;
    }

    public void setAnimatingColor(int color) {
        animatingColor = color;
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onPullingUp(float fraction, float maxHeadHeight, float headHeight) {
        setIndicatorColor(normalColor);
        stopAnim();
    }

    @Override
    public void startAnim(float maxHeadHeight, float headHeight) {
        setIndicatorColor(animatingColor);
        startAnim();
    }

    @Override
    public void onPullReleasing(float fraction, float maxHeadHeight, float headHeight) {
        stopAnim();
    }

    @Override
    public void onFinish() {
        stopAnim();
    }

    @Override
    public void reset() {
        stopAnim();
    }
}
