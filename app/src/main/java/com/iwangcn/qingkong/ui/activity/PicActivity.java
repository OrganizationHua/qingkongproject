package com.iwangcn.qingkong.ui.activity;

import android.text.TextUtils;
import android.widget.ImageView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.ui.base.QkBaseActivity;
import com.iwangcn.qingkong.utils.GlideUtils;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class PicActivity extends QkBaseActivity {
    @BindView(R.id.img)
    ImageView img;
    String url;
    private List<String> listPic;

    @Override
    public int layoutChildResID() {
        return R.layout.activity_pic;
    }

    @Override
    public void initView() {
        setTitle("跟进");
        url = getIntent().getStringExtra("imgurl");
        if (!TextUtils.isEmpty(url)) {
            listPic = Arrays.asList(url.split(","));
            for (int i = 0; i < listPic.size(); i++) {
                GlideUtils.loadImageView(this, listPic.get(i), img, R.drawable.default_icon_bg, R.drawable.default_icon_bg);
            }
        }
    }

}
