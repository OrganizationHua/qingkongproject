package com.iwangcn.qingkong.ui.activity;

import android.graphics.Color;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.ui.adapter.UltraPagerAdapter;
import com.iwangcn.qingkong.ui.base.QkBaseActivity;
import com.iwangcn.qingkong.utils.GlideUtils;
import com.tmall.ultraviewpager.UltraViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class PicActivity extends QkBaseActivity {
    @BindView(R.id.rc_ad)
    UltraViewPager ultraViewPager;
    private UltraPagerAdapter adapter;

    private List<String> listPic = new ArrayList<>();
    private int position;

    @Override
    public int layoutChildResID() {
        return R.layout.activity_pic;
    }

    @Override
    public void initView() {
        setTitle("图片");
        initViewPager();
        listPic = getIntent().getStringArrayListExtra("url");
        position = getIntent().getIntExtra("position", 0);
        if (!listPic.isEmpty()) {
            setData(listPic);
        }
    }

    /**
     * 轮播图适配器设置
     */
    public void initViewPager() {
        ultraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        adapter = new UltraPagerAdapter();
        ultraViewPager.setAdapter(adapter);
    }

    /**
     * 轮播图数据填充
     *
     * @param urlList
     */
    private void setData(List<String> urlList) {
        adapter.notifyDataSetChanged(urlList);
        //initialize built-in indicator
        ultraViewPager.initIndicator();
        //set style of indicators
        ultraViewPager.getIndicator()
                .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                .setFocusColor(Color.WHITE)
                .setNormalColor(Color.LTGRAY)
                .setMargin(0, 0, 0, 30)
                .setIndicatorPadding(20)
                .setRadius((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics()));
        //set the alignment
        ultraViewPager.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        //construct built-in indicator, and add it to  UltraViewPager
        ultraViewPager.getIndicator().build();
        ultraViewPager.getViewPager().setPageMargin(0);
        //set an infinite loop
        ultraViewPager.setInfiniteLoop(false);
        ultraViewPager.disableAutoScroll();
        ultraViewPager.setCurrentItem(position);
    }
}
