package com.iwangcn.qingkong.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.business.Event;
import com.iwangcn.qingkong.rxbus.Subscribe;
import com.iwangcn.qingkong.ui.activity.ProcessedActivity;
import com.iwangcn.qingkong.ui.activity.TagEditActivity;
import com.iwangcn.qingkong.ui.activity.TagFilterActivity;
import com.iwangcn.qingkong.ui.adapter.FollowTabAdapter;
import com.iwangcn.qingkong.ui.base.BaseFragment;
import com.iwangcn.qingkong.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

import static com.iwangcn.qingkong.utils.TabLayoutUtil.setIndicator;

public class FollowFragment extends BaseFragment {

    @BindView(R.id.iv_processed)
    ImageView iv_processed;
    @BindView(R.id.iv_sift)
    ImageView iv_sift;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;

    @Override
    public void onStart() {
        super.onStart();
        mTabLayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(mTabLayout, 66, 66);
            }
        });
    }

    @Override
    protected int layoutResID() {
        return R.layout.fragment_follow;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        final FollowTabAdapter tabLayoutAdapter = new FollowTabAdapter(getContext(), getChildFragmentManager(), 0);
        viewPager.setAdapter(tabLayoutAdapter);
        mTabLayout.setupWithViewPager(viewPager);
    }

    @OnClick(R.id.iv_processed)
    public void onProcessed() {
        Intent intent = new Intent(getActivity(), ProcessedActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.iv_sift)
    public void onSift() {
        EventBus.getDefault().postSticky(mTabLayout.getSelectedTabPosition());
//        Intent intent = new Intent(getActivity(), TagFilterActivity.class);
//        if (mTabLayout.getSelectedTabPosition() == 0) {
//            startActivityForResult(intent, 200);
//        } else if (mTabLayout.getSelectedTabPosition() == 1) {
//            startActivityForResult(intent, 300);
//        }
    }

    //    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Bundle bundle = data.getExtras();
//        if (requestCode == 200) {
//            if (resultCode == Activity.RESULT_OK) {
//                ToastUtil.showToast(getActivity(), requestCode + bundle.getInt("sourceType") + "" + bundle.getString("tags"));
//            }
//        } else if (requestCode == 300) {
//            if (resultCode == Activity.RESULT_OK) {
//                ToastUtil.showToast(getActivity(), requestCode + bundle.getInt("sourceType") + "" + bundle.getString("tags"));
//            }
//        }
//    }
}
