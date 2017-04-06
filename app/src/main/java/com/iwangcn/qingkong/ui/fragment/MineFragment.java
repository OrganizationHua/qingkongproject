package com.iwangcn.qingkong.ui.fragment;

import android.content.Intent;
import android.widget.TextView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.ui.activity.SettingsActivity;
import com.iwangcn.qingkong.ui.base.BaseFragment;
import com.iwangcn.qingkong.ui.utils.ToastUtil;

import butterknife.BindView;
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
    protected int layoutResID() {
        return R.layout.fragment_mine;
    }
    @OnClick(R.id.mine_rel_set)//系统设置
    public void appSet() {
        Intent intent=new Intent(getActivity(), SettingsActivity.class);
        getActivity().startActivity(intent);
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
