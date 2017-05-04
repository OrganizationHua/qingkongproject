package com.iwangcn.qingkong.ui.activity;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.business.Event;
import com.iwangcn.qingkong.business.MineEvent;
import com.iwangcn.qingkong.sp.SpConstant;
import com.iwangcn.qingkong.sp.SpUtils;
import com.iwangcn.qingkong.ui.base.QkBaseActivity;
import com.iwangcn.qingkong.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 系统设置
 */
public class SettingsActivity extends QkBaseActivity implements View.OnFocusChangeListener {
    @BindView(R.id.set_ed_pw_original)
    EditText mEdOriginal;
    @BindView(R.id.set_ed_pw_new)
    EditText mEdNewPw;
    @BindView(R.id.set_ed_pw_new_again)
    EditText mEdAgainPw;
    @BindView(R.id.set_btn_changePw)
    Button mButtonpw;//修改密码
    @BindView(R.id.mine_switch_notify)
    Switch mSwitchNotify;//通知开关
    @BindView(R.id.mine_switch_sound)
    Switch mSwitchSound;//声音开关
    @BindView(R.id.mine_switch_vibrate)
    Switch mSwitchVibrate;//震动开关
    private Context mContext = this;

    @Override
    public int layoutChildResID() {
        return R.layout.activity_set;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        setTitle(getResources().getString(R.string.mine_system_set));
        setSwitch();
        mEdOriginal.setOnFocusChangeListener(this);
        mEdNewPw.setOnFocusChangeListener(this);
        mEdAgainPw.setOnFocusChangeListener(this);
    }

    private void setSwitch() {
        boolean isNotify = (boolean) SpUtils.get(this, SpConstant.IS_NOTIFY, false);
        boolean isSound = (boolean) SpUtils.get(this, SpConstant.IS_SOUND, false);
        boolean isVibrate = (boolean) SpUtils.get(this, SpConstant.IS_VIBRATE, false);
        mSwitchNotify.setChecked(isNotify);
        mSwitchSound.setChecked(isSound);
        mSwitchVibrate.setChecked(isVibrate);
        if (!isNotify) {
            mSwitchSound.setEnabled(false);
            mSwitchVibrate.setEnabled(false);
        }
        mSwitchNotify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SpUtils.put(mContext, SpConstant.IS_NOTIFY, b);
                if (b == true) {
                    mSwitchSound.setEnabled(true);
                    mSwitchVibrate.setEnabled(true);
                } else {
                    mSwitchSound.setChecked(false);
                    mSwitchVibrate.setChecked(false);
                    mSwitchSound.setEnabled(false);
                    mSwitchVibrate.setEnabled(false);
                }
            }
        });
        mSwitchSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SpUtils.put(mContext, SpConstant.IS_SOUND, b);
            }
        });
        mSwitchVibrate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SpUtils.put(mContext, SpConstant.IS_VIBRATE, b);
            }
        });
    }

    @OnClick(R.id.set_btn_changePw)//修改密码
    public void onBtnChangePw() {
        String strOriginalPw = mEdOriginal.getText().toString().trim();
        String strNewPw = mEdNewPw.getText().toString().trim();
        String strAgainPw = mEdAgainPw.getText().toString().trim();
        if (TextUtils.isEmpty(strOriginalPw) || TextUtils.isEmpty(strNewPw) || TextUtils.isEmpty(strAgainPw)) {
            ToastUtil.showToast(this, "输入密码不能为空");
            return;
        }
        if (strOriginalPw.length() < 6 || strNewPw.length() < 6 || strAgainPw.length() < 6) {
            ToastUtil.showToast(this, "密码不能少于6位");
            return;
        }
        if (!strNewPw.endsWith(strAgainPw)) {
            ToastUtil.showToast(this, "两次密码输入不一致");
            return;
        }
        MineEvent mineEvent = new MineEvent(this);
        mineEvent.updatePwd(strOriginalPw, strNewPw);
    }

    @Subscribe
    public void onEventMainThread(Event event) {
        if (event instanceof MineEvent) {
            if (event.getId() == ((MineEvent) event).updatePaw) {
                String strResult = (String) event.getObject();
                ToastUtil.showToast(this, strResult);
                mEdOriginal.setText("");
                mEdNewPw.setText("");
                mEdAgainPw.setText("");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (b) {
            mButtonpw.setEnabled(true);
        } else {
            String strOriginalPw = mEdOriginal.getText().toString().trim();
            String strNewPw = mEdNewPw.getText().toString().trim();
            String strAgainPw = mEdAgainPw.getText().toString().trim();
            if (TextUtils.isEmpty(strOriginalPw) || TextUtils.isEmpty(strNewPw) || TextUtils.isEmpty(strAgainPw)) {
                mButtonpw.setEnabled(false);
            } else {
                mButtonpw.setEnabled(true);
            }
        }
    }
}
