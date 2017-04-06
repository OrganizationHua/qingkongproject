package com.iwangcn.qingkong.ui.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.ui.base.BaseFragment;
import com.iwangcn.qingkong.ui.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment {
    @BindView(R.id.homefragment_edit_search)
    EditText mEditSearch;//输入框
    @BindView(R.id.home_rel_search_bg_mark)
    RelativeLayout mRelMark;//黑色蒙层
    @Override
    protected int layoutResID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initEditText();
    }

     @OnClick(R.id.homeFragment_lin_search)//搜索按钮
    public void btnSearch() {
        ToastUtil.showToast(getActivity(),mEditSearch.getText().toString().trim());
    }
    @OnClick(R.id.homeFragment_btn_collected)//收藏
    public void btnCollect() {
        ToastUtil.showToast(getActivity(),"收藏");
    }
    private void initEditText(){
        mEditSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()>0){
                    mRelMark.setVisibility(View.GONE);
                }else{
                    mRelMark.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mEditSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH|| actionId == EditorInfo.IME_ACTION_UNSPECIFIED)
                {
                    ToastUtil.showToast(getActivity(),"搜索");
                }
                return false;
            }
        });
    }
}
