package com.iwangcn.qingkong.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.business.Event;
import com.iwangcn.qingkong.jpush.NotifyEvent;
import com.iwangcn.qingkong.providers.Global;
import com.iwangcn.qingkong.providers.UserManager;
import com.iwangcn.qingkong.ui.activity.LoginActivity;
import com.iwangcn.qingkong.ui.activity.SettingsActivity;
import com.iwangcn.qingkong.ui.activity.UpdateActivity;
import com.iwangcn.qingkong.ui.base.BaseFragment;
import com.iwangcn.qingkong.ui.model.UserInfo;
import com.iwangcn.qingkong.ui.view.MyCommonDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的
 */
public class MineFragment extends BaseFragment {
    @BindView(R.id.mine_name)
    TextView mTvName;//姓名
    @BindView(R.id.mine_division)
    TextView mTvDivision;//科室
    @BindView(R.id.mine_team)
    TextView mTvTeam;//所在组
    @BindView(R.id.mine_tel)
    TextView mTvTel;//电话
    @BindView(R.id.mine_email)
    TextView mTvEmail;//邮箱
    @BindView(R.id.min_version_img_circle)
    ImageView mImgCircle;//小圆圈  暂时隐藏
    @BindView(R.id.min_version_img_next)
    ImageView mImgNext;//下一步icon
    private boolean isNewVersion;//是否是最新版本

    @Override
    protected int layoutResID() {
        return R.layout.fragment_mine;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        initUserInfo(UserManager.getUserInfo());
    }

    private void initUserInfo(UserInfo userInfo) {
        if (userInfo != null) {
            if (!TextUtils.isEmpty(userInfo.getName())) {
                mTvName.setText(userInfo.getName());
            }
            if (!TextUtils.isEmpty(userInfo.getTelephone())) {
                mTvTel.setText(userInfo.getTelephone());
            }
            if (!TextUtils.isEmpty(userInfo.getEmail())) {
                mTvEmail.setText(userInfo.getEmail());
            }
            if (!TextUtils.isEmpty(userInfo.getUserGroup())) {
                mTvTeam.setText(userInfo.getUserGroup());
            }
//            if (!TextUtils.isEmpty(userInfo.getGroupNum())) {
//                mTvDivision.setText(userInfo.getGroupNum());
//            }
        }
        mTvDivision.setText(UserManager.getClientUserInfo().getClientUser().getPost());
        if (!Global.isNewVersion) {//当前是否是最新版
            mImgCircle.setVisibility(View.VISIBLE);
            mImgNext.setVisibility(View.VISIBLE);
        } else {
            mImgCircle.setVisibility(View.GONE);
            mImgNext.setVisibility(View.GONE);
        }
        //
        mImgCircle.setVisibility(View.GONE);

    }

    @OnClick(R.id.mine_rel_set)//系统设置
    public void appSet() {
        Intent intent = new Intent(getActivity(), SettingsActivity.class);
        getActivity().startActivity(intent);
    }

    @OnClick(R.id.mine_rel_information)//APP信息
    public void appInformation() {
//        if (Global.isNewVersion) {
//            ToastUtil.showToast(getActivity(), "当前已经是最新版本");
//        } else {
//
//        }
        Intent intent = new Intent(getActivity(), UpdateActivity.class);
        startActivity(intent);
        mImgCircle.setVisibility(View.INVISIBLE);
        mImgNext.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.mine_rel_loginOut)//退出登录
    public void loginOut() {
        final MyCommonDialog dialog = new MyCommonDialog(getActivity());
        dialog.setTitle(getString(R.string.exit));
        dialog.setOnDialogClick(new MyCommonDialog.DialogInterface() {
            @Override
            public void onCancle() {

            }

            @Override
            public void onConfirm() {
                UserManager.clearUserInfo();
                dialog.dismiss();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });
        dialog.show();
    }

    @Subscribe
    public void onEventMainThread(Event event) {//升级完成之后还要回来
        if (event instanceof NotifyEvent) {
            Intent intent = (Intent) event.getObject();
            if (intent != null) {
                int type = intent.getIntExtra("type", 0);
                if (type == 3) {
                    mImgCircle.setVisibility(View.VISIBLE);
                    mImgNext.setVisibility(View.VISIBLE);
                }
            }
        }
    }

}
