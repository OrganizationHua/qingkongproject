package com.iwangcn.qingkong.ui.view.freshwidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.ui.view.freshwidget.Footer.FooterSinaRefreshView;
import com.iwangcn.qingkong.ui.view.freshwidget.header.SinaRefreshView;
import com.iwangcn.qingkong.ui.view.freshwidget.utils.DensityUtil;
import com.iwangcn.qingkong.ui.view.freshwidget.utils.ScrollingUtil;

import java.util.Calendar;

/**
 * 全新的布局刷新．
 * Created by lcodecore on 16/3/2.
 */
public class ReloadRefreshLayout extends RelativeLayout {

    //波浪的高度,最大扩展高度
    protected float mWaveHeight;

    //头部的高度
    protected float mHeadHeight;

    //允许的越界回弹的高度
    protected float mOverScrollHeight;

    //子控件
    private View mChildView;

    //头部layout
    protected FrameLayout mHeadLayout;

    //整个头部
    private FrameLayout mExtraHeadLayout;
    //附加顶部高度
    private int mExHeadHeight = 0;

    private IHeaderView mHeadView;
    private IBottomView mBottomView;

    //底部高度
    private float mBottomHeight;

    //底部layout
    private FrameLayout mBottomLayout;

    //是否刷新视图可见
    protected boolean isRefreshVisible = false;

    //是否加载更多视图可见
    protected boolean isLoadingVisible = false;

    //是否需要加载更多,默认需要
    protected boolean enableLoadmore = true;
    //是否需要下拉刷新,默认需要
    protected boolean enableRefresh = true;

    //是否在越界回弹的时候显示下拉图标
    protected boolean isOverScrollTopShow = false;
    //是否在越界回弹的时候显示上拉图标
    protected boolean isOverScrollBottomShow = false;

    //是否隐藏刷新控件,开启越界回弹模式(开启之后刷新控件将隐藏)
    protected boolean isPureScrollModeOn = true;

    //是否自动加载更多
    protected boolean autoLoadMore = false;

    //是否开启悬浮刷新模式
    protected boolean floatRefresh = false;

    //是否允许进入越界回弹模式
    protected boolean enableOverScroll = true;

    //默认的延时
    protected long delaytime = 1200;
    private CoProcessor cp;
    private Handler mHandler = new Handler();
    private long milieTime;//延时处理
    private long milieendTime;//延时处理
    private Context context;
    public ReloadRefreshLayout(Context context) {
        this(context, null, 0);
    }

    public ReloadRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReloadRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
         this.context=context  ;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ReloadRefreshLayout, defStyleAttr, 0);
        mWaveHeight = a.getDimensionPixelSize(R.styleable.ReloadRefreshLayout_reloadwave_height, (int) DensityUtil.dp2px(context, 120));
        mHeadHeight = a.getDimensionPixelSize(R.styleable.ReloadRefreshLayout_reloadhead_height, (int) DensityUtil.dp2px(context, 50));
        mBottomHeight = a.getDimensionPixelSize(R.styleable.ReloadRefreshLayout_reloadbottom_height, (int) DensityUtil.dp2px(context, 50));
        mOverScrollHeight = a.getDimensionPixelSize(R.styleable.ReloadRefreshLayout_reloadoverscroll_height, (int) mHeadHeight);
        enableLoadmore = a.getBoolean(R.styleable.ReloadRefreshLayout_reloadenable_loadmore, true);
        enableRefresh = a.getBoolean(R.styleable.ReloadRefreshLayout_reloadenable_fresh, true);
        isPureScrollModeOn = a.getBoolean(R.styleable.ReloadRefreshLayout_reloadpureScrollMode_on, false);
        isOverScrollTopShow = a.getBoolean(R.styleable.ReloadRefreshLayout_reloadoverscroll_top_show, false);
        isOverScrollBottomShow = a.getBoolean(R.styleable.ReloadRefreshLayout_reloadoverscroll_bottom_show, false);
        enableOverScroll = a.getBoolean(R.styleable.ReloadRefreshLayout_reloadenable_overscroll, true);
        a.recycle();

        cp = new CoProcessor();
    }

    private void init() {
        //使用isInEditMode解决可视化编辑器无法识别自定义控件的问题
        if (isInEditMode()) return;

        setPullListener(new TwinklingPullListener());
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        //添加头部
        if (mHeadLayout == null) {
            FrameLayout headViewLayout = new FrameLayout(getContext());
            LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, 0);
            layoutParams.addRule(ALIGN_PARENT_TOP);
            layoutParams.addRule(CENTER_VERTICAL);

            FrameLayout extraHeadLayout = new FrameLayout(getContext());
            extraHeadLayout.setId(R.id.ex_header);
            LayoutParams layoutParams2 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

            this.addView(extraHeadLayout, layoutParams2);
            this.addView(headViewLayout, layoutParams);

            mExtraHeadLayout = extraHeadLayout;
            mHeadLayout = headViewLayout;

            if (mHeadView == null) {
                SinaRefreshView head = new SinaRefreshView(getContext());
                setHeaderView(head);
            }
        }

        //添加底部
        if (mBottomLayout == null) {
            FrameLayout bottomViewLayout = new FrameLayout(getContext());
            LayoutParams layoutParams2 = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
            layoutParams2.addRule(ALIGN_PARENT_BOTTOM);
            layoutParams2.addRule(CENTER_VERTICAL);
            bottomViewLayout.setLayoutParams(layoutParams2);

            mBottomLayout = bottomViewLayout;
            this.addView(mBottomLayout);

            if (mBottomView == null) {
//                BottomProgressView mProgressView = new BottomProgressView(getContext());
//                mProgressView.setIndicatorId(ProgressView.BallSpinFadeLoader);
                setBottomView(new FooterSinaRefreshView(context));
            }
        }

        //获得子控件
        mChildView = getChildAt(0);

        cp.init();
    }


    /*************************************  触摸事件处理  *****************************************/
    /**
     * 拦截事件
     *
     * @return return true时,ViewGroup的事件有效,执行onTouchEvent事件
     * return false时,事件向下传递,onTouchEvent无效
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = cp.interceptTouchEvent(ev);
        return intercept || super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        boolean consume = cp.consumeTouchEvent(e);
        return consume || super.onTouchEvent(e);
    }

    /*************************************
     * 开放api区
     *****************************************/
    //主动刷新
    public void startRefresh() {
        cp.startRefresh();
    }

    //主动加载跟多
    public void startLoadMore() {
        cp.startLoadMore();
    }

    /**
     * 刷新结束
     */
    public void finishRefreshing() {
        milieendTime = Calendar.getInstance().getTimeInMillis();
        if ((milieendTime - milieTime) > delaytime)
            cp.finishRefreshing();
        else mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finishRefreshing();
            }
        }, milieTime + delaytime - milieendTime);
    }

    /**
     * 加载更多结束
     */
    public void finishLoadmore() {
        milieendTime = Calendar.getInstance().getTimeInMillis();
        if ((milieendTime - milieTime) > delaytime)
            cp.finishLoadmore();
        else mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finishLoadmore();
            }
        }, milieTime + delaytime - milieendTime);
    }

    /**
     * 设置头部View
     */
    public void setHeaderView(final IHeaderView headerView) {
        if (headerView != null) {
            post(new Runnable() {
                @Override
                public void run() {
                    mHeadLayout.removeAllViewsInLayout();
                    mHeadLayout.addView(headerView.getView());
                }
            });
            mHeadView = headerView;
        }
    }

    /**
     * 设置固定在顶部的header
     */
    public void addFixedExHeader(final View view) {

        post(new Runnable() {
            @Override
            public void run() {
                if (view != null && mExtraHeadLayout != null) {
                    mExtraHeadLayout.addView(view);
                    cp.onAddExHead();
                    cp.setExHeadFixed();
                }
            }
        });
    }

    /**TODO 适配可以随界面滚动的Header
     public void addNormalExHeader(View view) {
     if (view != null && mExtraHeadLayout != null) {
     mExtraHeadLayout.addView(view);
     cp.onAddExHead();
     cp.setExHeadNormal();
     }
     }
     **/

    /**
     * 获取额外附加的头部
     */
    public View getExtraHeaderView() {
        return mExtraHeadLayout;
    }

    /**
     * 设置底部View
     */
    public void setBottomView(final IBottomView bottomView) {
        if (bottomView != null) {
            post(new Runnable() {
                @Override
                public void run() {
                    mBottomLayout.removeAllViewsInLayout();
                    mBottomLayout.addView(bottomView.getView());
                }
            });
            mBottomView = bottomView;
        }
    }

    public void setFloatRefresh(boolean ifOpenFloatRefreshMode) {
        floatRefresh = ifOpenFloatRefreshMode;
    }

    /**
     * 设置wave的下拉高度
     *
     * @param waveHeightDp
     */
    public void setWaveHeight(float waveHeightDp) {
        this.mWaveHeight = DensityUtil.dp2px(getContext(), waveHeightDp);
    }

    /**
     * 设置下拉头的高度
     */
    public void setHeaderHeight(float headHeightDp) {
        this.mHeadHeight = DensityUtil.dp2px(getContext(), headHeightDp);
    }

    /**
     * 设置底部高度
     */
    public void setBottomHeight(float bottomHeightDp) {
        this.mBottomHeight = DensityUtil.dp2px(getContext(), bottomHeightDp);
    }

    /**
     * 是否允许加载更多
     */
    public void setEnableLoadmore(boolean enableLoadmore1) {
        enableLoadmore = enableLoadmore1;
        if (mBottomView != null) {
            if (enableLoadmore) mBottomView.getView().setVisibility(VISIBLE);
            else mBottomView.getView().setVisibility(GONE);
        }
    }

    /**
     * 是否允许下拉刷新
     */
    public void setEnableRefresh(boolean enableRefresh1) {
        this.enableRefresh = enableRefresh1;
    }

    /**
     * 是否允许越界时显示刷新控件
     */
    public void setOverScrollTopShow(boolean isOverScrollTopShow) {
        this.isOverScrollTopShow = isOverScrollTopShow;
    }

    public void setOverScrollBottomShow(boolean isOverScrollBottomShow) {
        this.isOverScrollBottomShow = isOverScrollBottomShow;
    }

    public void setOverScrollRefreshShow(boolean isOverScrollRefreshShow) {
        this.isOverScrollTopShow = isOverScrollRefreshShow;
        this.isOverScrollBottomShow = isOverScrollRefreshShow;
    }

    /**
     * 是否允许开启越界回弹模式
     */
    public void setEnableOverScroll(boolean enableOverScroll1) {
        this.enableOverScroll = enableOverScroll1;
    }

    /**
     * 是否开启纯净的越界回弹模式,开启时刷新和加载更多控件不显示
     */
    public void setPureScrollModeOn(boolean pureScrollModeOn) {
        isPureScrollModeOn = pureScrollModeOn;
        if (pureScrollModeOn) {
            isOverScrollTopShow = false;
            isOverScrollBottomShow = false;
            setWaveHeight(mOverScrollHeight);
            setHeaderHeight(mOverScrollHeight);
            setBottomHeight(mOverScrollHeight);
        }
    }

    /**
     * 设置越界高度
     */
    public void setOverScrollHeight(float overScrollHeightDp) {
        this.mOverScrollHeight = DensityUtil.dp2px(getContext(), overScrollHeightDp);
    }

    /**
     * 设置OverScroll时自动加载更多
     *
     * @param ifAutoLoadMore 为true表示底部越界时主动进入加载跟多模式，否则直接回弹
     */
    public void setAutoLoadMore(boolean ifAutoLoadMore) {
        autoLoadMore = ifAutoLoadMore;
    }

    /**
     * 设置刷新控件监听器
     */
    private RefreshListenerAdapter refreshListener;

    public void setOnRefreshListener(RefreshListenerAdapter refreshListener) {
        if (refreshListener != null) {
            this.refreshListener = refreshListener;
        }
    }

    //设置拖动屏幕的监听器
    private PullListener pullListener;

    private void setPullListener(PullListener pullListener) {
        this.pullListener = pullListener;
    }

    private class TwinklingPullListener implements PullListener {

        @Override
        public void onPullingDown(ReloadRefreshLayout refreshLayout, float fraction) {
            mHeadView.onPullingDown(fraction, mWaveHeight, mHeadHeight);
            if (refreshListener != null) refreshListener.onPullingDown(refreshLayout, fraction);
        }

        @Override
        public void onPullingUp(ReloadRefreshLayout refreshLayout, float fraction) {
            mBottomView.onPullingUp(fraction, mWaveHeight, mHeadHeight);
            if (refreshListener != null) refreshListener.onPullingUp(refreshLayout, fraction);
        }

        @Override
        public void onPullDownReleasing(ReloadRefreshLayout refreshLayout, float fraction) {
            mHeadView.onPullReleasing(fraction, mWaveHeight, mHeadHeight);
            if (refreshListener != null)
                refreshListener.onPullDownReleasing(refreshLayout, fraction);
            milieTime = Calendar.getInstance().getTimeInMillis();
        }

        @Override
        public void onPullUpReleasing(ReloadRefreshLayout refreshLayout, float fraction) {
            mBottomView.onPullReleasing(fraction, mWaveHeight, mHeadHeight);
            if (refreshListener != null) refreshListener.onPullUpReleasing(refreshLayout, fraction);
            milieTime = Calendar.getInstance().getTimeInMillis();
        }

        @Override
        public void onRefresh(ReloadRefreshLayout refreshLayout) {
            mHeadView.startAnim(mWaveHeight, mHeadHeight);
            if (refreshListener != null) refreshListener.onRefresh(refreshLayout);
        }

        @Override
        public void onLoadMore(ReloadRefreshLayout refreshLayout) {
            mBottomView.startAnim(mWaveHeight, mHeadHeight);
            if (refreshListener != null) refreshListener.onLoadMore(refreshLayout);
        }

        @Override
        public void onFinishRefresh() {
            if (!isRefreshVisible) return;
            mHeadView.onFinish(new OnAnimEndListener() {
                @Override
                public void onAnimEnd() {
                    cp.finishRefreshAfterAnim();
                }
            });
        }

        @Override
        public void onFinishLoadMore() {
            if (!isLoadingVisible) return;
            mBottomView.onFinish();
        }

        @Override
        public void onRefreshCanceled() {
            if (refreshListener != null) refreshListener.onRefreshCanceled();
        }

        @Override
        public void onLoadmoreCanceled() {
            if (refreshListener != null) refreshListener.onLoadmoreCanceled();
        }
    }

    public class CoProcessor {
        private RefreshProcessor refreshProcessor;
        private OverScrollProcessor overScrollProcessor;
        private AnimProcessor animProcessor;

        private final static int PULLING_TOP_DOWN = 0;
        private final static int PULLING_BOTTOM_UP = 1;
        private int state = PULLING_TOP_DOWN;

        private static final int EX_MODE_NORMAL = 0;
        private static final int EX_MODE_FIXED = 1;
        private int exHeadMode = EX_MODE_NORMAL;


        public CoProcessor() {
            animProcessor = new AnimProcessor(this);
            overScrollProcessor = new OverScrollProcessor(this);
            refreshProcessor = new RefreshProcessor(this);
        }

        public void init() {
            if (isPureScrollModeOn) {
                setOverScrollTopShow(false);
                setOverScrollBottomShow(false);
                if (mHeadLayout != null) mHeadLayout.setVisibility(GONE);
                if (mBottomLayout != null) mBottomLayout.setVisibility(GONE);
            }

            overScrollProcessor.init();
            animProcessor.init();
        }

        public AnimProcessor getAnimProcessor() {
            return animProcessor;
        }

        public float getMaxHeadHeight() {
            return mWaveHeight;
        }

        public int getHeadHeight() {
            return (int) mHeadHeight;
        }

        public int getExtraHeadHeight() {
            return mExtraHeadLayout.getHeight();
        }

        public int getBottomHeight() {
            return (int) mBottomHeight;
        }

        public int getOsHeight() {
            return (int) mOverScrollHeight;
        }

        public View getScrollableView() {
            return mChildView;
        }

        public View getContent() {
            return mChildView;
        }

        public View getHeader() {
            return mHeadLayout;
        }

        public View getFooter() {
            return mBottomLayout;
        }

        public Context getContext() {
            return ReloadRefreshLayout.this.getContext();
        }

        public int getTouchSlop() {
            return ViewConfiguration.get(getContext()).getScaledTouchSlop();
        }

        public boolean interceptTouchEvent(MotionEvent ev) {
            return refreshProcessor.interceptTouchEvent(ev);
        }

        public boolean consumeTouchEvent(MotionEvent ev) {
            return refreshProcessor.consumeTouchEvent(ev);
        }

        /**
         * 在越界时阻止再次进入这个状态而导致动画闪烁。  Prevent entering the overscroll-mode again on animating.
         */
        private boolean isOverScrollTopLocked = false;

        public void lockOsTop() {
            isOverScrollTopLocked = true;
        }

        public void releaseOsTopLock() {
            isOverScrollTopLocked = false;
        }

        public boolean isOsTopLocked() {
            return isOverScrollTopLocked;
        }

        private boolean isOverScrollBottomLocked = false;

        public void lockOsBottom() {
            isOverScrollBottomLocked = true;
        }

        public void releaseOsBottomLock() {
            isOverScrollBottomLocked = false;
        }

        public boolean isOsBottomLocked() {
            return isOverScrollBottomLocked;
        }

        public void resetHeaderView() {
            if (mHeadView != null) mHeadView.reset();
        }

        public void resetBottomView() {
            if (mBottomView != null) mBottomView.reset();
        }

        /**
         * 在添加附加Header前锁住，阻止一些额外的位移动画
         */
        private boolean isExHeadLocked = true;

        public boolean isExHeadLocked() {
            return isExHeadLocked;
        }

        private void unlockExHead() {
            isExHeadLocked = false;
        }

        public View getExHead() {
            return mExtraHeadLayout;
        }

        public void setExHeadNormal() {
            exHeadMode = EX_MODE_NORMAL;
        }

        public void setExHeadFixed() {
            exHeadMode = EX_MODE_FIXED;
        }

        public boolean isExHeadNormal() {
            return exHeadMode == EX_MODE_NORMAL;
        }

        public boolean isExHeadFixed() {
            return exHeadMode == EX_MODE_FIXED;
        }

        //添加了额外头部时触发
        public void onAddExHead() {
            unlockExHead();
            LayoutParams params = (LayoutParams) mChildView.getLayoutParams();
            params.addRule(BELOW, mExtraHeadLayout.getId());
            mChildView.setLayoutParams(params);
            requestLayout();
        }


        /**
         * 主动刷新、加载更多、结束
         */
        public void startRefresh() {
            post(new Runnable() {
                @Override
                public void run() {
                    setStatePTD();
                    if (!isPureScrollModeOn && mChildView != null) {
                        setRefreshing(true);
                        animProcessor.animHeadToRefresh();
                    }
                }
            });
        }

        public void startLoadMore() {
            post(new Runnable() {
                @Override
                public void run() {
                    setStatePBU();
                    if (!isPureScrollModeOn && mChildView != null) {
                        setLoadingMore(true);
                        animProcessor.animBottomToLoad();
                    }
                }
            });
        }

        public void finishRefreshing() {
            onFinishRefresh();
        }

        public void finishRefreshAfterAnim() {
            if (isRefreshVisible() && mChildView != null) {
                setRefreshing(false);
                animProcessor.animHeadBack();
            }
        }

        public void finishLoadmore() {
            onFinishLoadMore();
            if (isLoadingVisible() && mChildView != null) {
                ScrollingUtil.scrollAViewBy(mChildView, (int) mBottomHeight);
                setLoadingMore(false);
                animProcessor.animBottomBack();
            }
        }

        //TODO 支持分别设置头部或者顶部允许越界
        private boolean enableOverScrollTop = false, enableOverScrollBottom = false;

        public boolean enableOverScroll() {
            return enableOverScroll;
        }

        public boolean allowPullDown() {
            return enableRefresh || enableOverScrollTop;
        }

        public boolean allowPullUp() {
            return enableLoadmore || enableOverScrollBottom;
        }

        public boolean allowOverScroll() {
            return (!isRefreshVisible && !isLoadingVisible);
        }

        public boolean isRefreshVisible() {
            return isRefreshVisible;
        }

        public boolean isLoadingVisible() {
            return isLoadingVisible;
        }

        public void setRefreshing(boolean refreshing) {
            isRefreshVisible = refreshing;
        }

        public void setLoadingMore(boolean loadingMore) {
            isLoadingVisible = loadingMore;
        }

        public boolean isOpenFloatRefresh() {
            return floatRefresh;
        }

        public boolean autoLoadMore() {
            return autoLoadMore;
        }

        public boolean isPureScrollModeOn() {
            return isPureScrollModeOn;
        }

        public boolean isOverScrollTopShow() {
            return isOverScrollTopShow;
        }

        public boolean isOverScrollBottomShow() {
            return isOverScrollBottomShow;
        }

        public void onPullingDown(float offsetY) {
            pullListener.onPullingDown(ReloadRefreshLayout.this, offsetY / mHeadHeight);
        }

        public void onPullingUp(float offsetY) {
            pullListener.onPullingUp(ReloadRefreshLayout.this, offsetY / mBottomHeight);
        }

        public void onRefresh() {
            pullListener.onRefresh(ReloadRefreshLayout.this);
        }

        public void onLoadMore() {
            pullListener.onLoadMore(ReloadRefreshLayout.this);
        }

        public void onFinishRefresh() {
            pullListener.onFinishRefresh();
        }

        public void onFinishLoadMore() {
            pullListener.onFinishLoadMore();
        }

        public void onPullDownReleasing(float offsetY) {
            pullListener.onPullDownReleasing(ReloadRefreshLayout.this, offsetY / mHeadHeight);
        }

        public void onPullUpReleasing(float offsetY) {
            pullListener.onPullUpReleasing(ReloadRefreshLayout.this, offsetY / mBottomHeight);
        }

        public void onRefreshCanceled() {
            pullListener.onRefreshCanceled();
        }

        public void onLoadmoreCanceled() {
            pullListener.onLoadmoreCanceled();
        }

        public void setStatePTD() {
            state = PULLING_TOP_DOWN;
        }

        public void setStatePBU() {
            state = PULLING_BOTTOM_UP;
        }

        public boolean isStatePTD() {
            return PULLING_TOP_DOWN == state;
        }

        public boolean isStatePBU() {
            return PULLING_BOTTOM_UP == state;
        }
    }
}
