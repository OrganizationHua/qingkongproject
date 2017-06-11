package com.iwangcn.qingkong.ui.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.ui.base.BaseActivity;
import com.iwangcn.qingkong.utils.GlideUtils;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PicActivity extends BaseActivity {
    @BindView(R.id.img)
    ImageView img;
    String url;
    private List<String> listPic;


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pic);
        ButterKnife.bind(this);
        initView();
    }


    public void initView() {
        url = getIntent().getStringExtra("imgurl");
        if (!TextUtils.isEmpty(url)) {
            listPic = Arrays.asList(url.split(","));
            for (int i = 0; i < listPic.size(); i++) {
                GlideUtils.loadImageView(this, listPic.get(i), img, R.drawable.default_icon_bg, R.drawable.default_icon_bg);
            }
        }
    }

}
