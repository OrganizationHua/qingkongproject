package com.iwangcn.qingkong.ui.view.freshwidget;

public abstract class RefreshListenerAdapter implements PullListener {
    @Override
    public void onPullingDown(ReloadRefreshLayout refreshLayout, float fraction) {
    }

    @Override
    public void onPullingUp(ReloadRefreshLayout refreshLayout, float fraction) {
    }

    @Override
    public void onPullDownReleasing(ReloadRefreshLayout refreshLayout, float fraction) {
    }

    @Override
    public void onPullUpReleasing(ReloadRefreshLayout refreshLayout, float fraction) {
    }

    @Override
    public void onRefresh(ReloadRefreshLayout refreshLayout) {
    }

    @Override
    public void onLoadMore(ReloadRefreshLayout refreshLayout) {
    }

    @Override
    public void onFinishRefresh() {

    }

    @Override
    public void onFinishLoadMore() {

    }

    @Override
    public void onRefreshCanceled() {

    }

    @Override
    public void onLoadmoreCanceled() {

    }
}