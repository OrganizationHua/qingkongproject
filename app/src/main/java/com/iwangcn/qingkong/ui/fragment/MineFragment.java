package com.iwangcn.qingkong.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iwangcn.qingkong.ui.base.BaseFragment;
import com.iwangcn.qingkong.ui.utils.ToastUtil;
import com.iwangcn.qingkong.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的
 */
public class MineFragment extends BaseFragment  {
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.mine_rel_set)//系统设置
    public void appSet() {
        ToastUtil.showToast(getActivity(),"系统设置");
    }
    @OnClick(R.id.mine_rel_information)//APP信息
    public void appInformation() {
        ToastUtil.showToast(getActivity(),"APP信息");
    }
    @OnClick(R.id.mine_rel_loginOut)//退出登录
    public void loginOut() {
        ToastUtil.showToast(getActivity(),"退出登录");
    }
}
