package com.iwangcn.qingkong.ui.view.freshwidget.Footer;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.ui.view.freshwidget.IBottomView;


/**
 * 经典的头部刷新,箭头和文字．
 * <p>
 * Created by lcodecore on 2016/10/2.
 */

public class FooterSinaRefreshView extends FrameLayout implements IBottomView {

    private ImageView refreshArrow;
    private ProgressBar loadingView;
    private TextView refreshTextView;
    private String pullDownStr = "下拉刷新";
    private String releaseRefreshStr = "释放刷新";
    private String refreshingStr = "正在刷新";

    public FooterSinaRefreshView(Context context) {
        this(context, null);
    }

    public FooterSinaRefreshView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FooterSinaRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View rootView = View.inflate(getContext(), R.layout.view_sinaheader, null);
        refreshArrow = (ImageView) rootView.findViewById(R.id.iv_arrow);
        refreshArrow.setImageResource(R.drawable.arrow_refresh);
        refreshArrow.setVisibility(GONE);
        refreshTextView = (TextView) rootView.findViewById(R.id.tv);
        loadingView = (ProgressBar) rootView.findViewById(R.id.iv_loading);
        loadingView.setIndeterminate(true);
        loadingView.setIndeterminateDrawable(context.getResources().getDrawable(R.drawable.pull_wait_indicate));
        addView(rootView);
        pullDownStr = getContext().getResources().getString(R.string.cube_ptr_pull_down_to_refresh);
        releaseRefreshStr = getContext().getResources().getString(R.string.cube_ptr_release_to_refresh);
        refreshingStr = getContext().getResources().getString(R.string.cube_ptr_loading);
    }

    /**
     * 默认已经有下拉箭头
     *
     * @param resId
     */
    public void setArrowResource(@DrawableRes int resId) {
        refreshArrow.setImageResource(resId);
    }

    public void setTextColor(@ColorInt int color) {
        refreshTextView.setTextColor(color);
    }

    public void setPullDownStr(String pullDownStr1) {
        pullDownStr = pullDownStr1;
    }

    public void setReleaseRefreshStr(String releaseRefreshStr1) {
        releaseRefreshStr = releaseRefreshStr1;
    }

    public void setRefreshingStr(String refreshingStr1) {
        refreshingStr = refreshingStr1;
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onPullingUp(float fraction, float maxHeadHeight, float headHeight) {
        refreshTextView.setText("");
        startAnim( maxHeadHeight,headHeight);
    }


    @Override
    public void onPullReleasing(float fraction, float maxHeadHeight, float headHeight) {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void startAnim(float maxHeadHeight, float headHeight) {
        refreshTextView.setText(refreshingStr);
        refreshArrow.setVisibility(GONE);
        loadingView.setVisibility(VISIBLE);
//        ((AnimationDrawable) loadingView.getDrawable()).start();
    }


    @Override
    public void reset() {
        refreshArrow.setVisibility(GONE);
        loadingView.setVisibility(GONE);
        refreshTextView.setText(pullDownStr);
    }
}
