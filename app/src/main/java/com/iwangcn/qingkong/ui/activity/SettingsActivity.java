package com.iwangcn.qingkong.ui.activity;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.sp.SpConstant;
import com.iwangcn.qingkong.sp.SpUtils;
import com.iwangcn.qingkong.ui.base.QkBaseActivity;
import com.iwangcn.qingkong.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 系统设置
 */
public class SettingsActivity extends QkBaseActivity {
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
    private Context mContext=this;
    @Override
    public int layoutChildResID() {
        return R.layout.activity_set;
    }

    @Override
    public void initView() {
        setTitle(getResources().getString(R.string.mine_system_set));
        setSwitch();
        mEdAgainPw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() > 6) {
                    mButtonpw.setEnabled(true);
                } else {
                    mButtonpw.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void setSwitch() {
        boolean isNotify = (boolean) SpUtils.get(this, SpConstant.IS_NOTIFY, false);
        boolean isSound = (boolean) SpUtils.get(this, SpConstant.IS_SOUND, false);
        boolean isVibrate = (boolean) SpUtils.get(this, SpConstant.IS_VIBRATE, false);
        mSwitchNotify.setChecked(isNotify);
        mSwitchSound.setChecked(isSound);
        mSwitchVibrate.setChecked(isVibrate);
        mSwitchNotify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SpUtils.put(mContext,SpConstant.IS_NOTIFY,b);
            }
        });
        mSwitchSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SpUtils.put(mContext,SpConstant.IS_SOUND,b);
            }
        });
        mSwitchVibrate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SpUtils.put(mContext,SpConstant.IS_VIBRATE,b);
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
            ToastUtil.showToast(this, "输入密码不能为空");
            return;
        }
        if (strNewPw.endsWith(strAgainPw)) {
            ToastUtil.showToast(this, "两次密码输入不一致");
            return;
        }
    }

}
