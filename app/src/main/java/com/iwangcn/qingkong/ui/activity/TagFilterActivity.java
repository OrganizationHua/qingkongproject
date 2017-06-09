package com.iwangcn.qingkong.ui.activity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.business.Event;
import com.iwangcn.qingkong.business.TagEvent;
import com.iwangcn.qingkong.sp.SpUtils;
import com.iwangcn.qingkong.ui.base.QkBaseActivity;
import com.iwangcn.qingkong.ui.model.ClientLabel;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class TagFilterActivity extends QkBaseActivity {
    @BindView(R.id.tag_source)
    TagFlowLayout tag_source;
    @BindView(R.id.tag_biz)
    TagFlowLayout tag_biz;
    @BindView(R.id.tag_diy)
    TagFlowLayout tag_diy;
    private TagEvent mTagEvent;

    @Override
    public int layoutChildResID() {
        return R.layout.activity_tag_filter;
    }

    @Override
    public void initView() {
        setTitle("筛选");
    }

    @Override
    public void initData() {
        EventBus.getDefault().register(this);
        mTagEvent = new TagEvent(this);
        mTagEvent.getTagList();
    }

    @Subscribe
    public void onEventMainThread(Event event) {
        if (event instanceof TagEvent) {
            final ArrayList<ArrayList<ClientLabel>> list = (ArrayList<ArrayList<ClientLabel>>) event.getObject();
            final ArrayList<String> sourceList = new ArrayList<>();
            int size = (Integer) SpUtils.get(this, "size", 0) + 1;//存储值是从1开始
            for (int i = 1; i < size; i++) {
                sourceList.add((String) SpUtils.get(this, i + "", "1"));
            }
            tag_source.setAdapter(new TagAdapter(sourceList) {
                @Override
                public View getView(FlowLayout parent, int position, Object o) {
                    TextView tv = (TextView) LayoutInflater.from(TagFilterActivity.this).inflate(R.layout.tag_source,
                            parent, false);
                    tv.setText(sourceList.get(position));
                    return tv;
                }
            });
            tag_biz.setAdapter(new TagAdapter(list.get(0)) {
                @Override
                public View getView(FlowLayout parent, int position, Object o) {
                    TextView tv = (TextView) LayoutInflater.from(TagFilterActivity.this).inflate(R.layout.tag_biz,
                            parent, false);
                    tv.setText(list.get(0).get(position).getName());
                    return tv;
                }
            });
            tag_diy.setAdapter(new TagAdapter(list.get(1)) {
                @Override
                public View getView(FlowLayout parent, int position, Object o) {
                    TextView tv = (TextView) LayoutInflater.from(TagFilterActivity.this).inflate(R.layout.tag_diy,
                            parent, false);
                    tv.setText(list.get(1).get(position).getName());
                    return tv;
                }
            });
        }
    }
}
