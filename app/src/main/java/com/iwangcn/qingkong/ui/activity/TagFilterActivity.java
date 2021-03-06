package com.iwangcn.qingkong.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
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
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;


public class TagFilterActivity extends QkBaseActivity {
    @BindView(R.id.ll_source)
    LinearLayout ll_source;
    @BindView(R.id.tag_source)
    TagFlowLayout tag_source;
    @BindView(R.id.ll_biz)
    LinearLayout ll_biz;
    @BindView(R.id.tag_biz)
    TagFlowLayout tag_biz;
    @BindView(R.id.ll_diy)
    LinearLayout ll_diy;
    @BindView(R.id.tag_diy)
    TagFlowLayout tag_diy;
    private TagEvent mTagEvent;
    private ArrayList<String> sourceList;
    private ArrayList<ArrayList<ClientLabel>> list;
    private static String sourceType;
    private int from;
    private static int fromDiff;//是否与上次进入时的界面相同
    private static Set<Integer> selectPosSetSource, selectPosSetbiz, selectPosSetdiy;

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
        if (getIntent() != null) {
            from = getIntent().getIntExtra("from", 0);
            if (fromDiff != from) {
                fromDiff = from;
                if (selectPosSetSource != null) {
                    selectPosSetSource.clear();
                }
                if (selectPosSetbiz != null) {
                    selectPosSetbiz.clear();
                }
                if (selectPosSetdiy != null) {
                    selectPosSetdiy.clear();
                }
            }
            if (from == 1) {
                ll_diy.setVisibility(View.GONE);
            } else {
                ll_diy.setVisibility(View.VISIBLE);
            }
        }
        EventBus.getDefault().register(this);
        mTagEvent = new TagEvent(this);
        mTagEvent.getTagList();

        sourceList = new ArrayList<>();
        int size = (Integer) SpUtils.get(this, "size", 0) + 1;//存储值是从1开始
        for (int i = 1; i < size; i++) {
            sourceList.add((String) SpUtils.get(this, i + "", "1"));
        }
        tag_source.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                sourceType = (int) SpUtils.get(TagFilterActivity.this, sourceList.get(position), 1) + "";
                return true;
            }
        });

    }

    @Override
    public void goBack() {
        super.goBack();
        tag_source.onChanged();
        tag_biz.onChanged();
        tag_diy.onChanged();
        markSelectedTags();
    }

    @OnClick(R.id.btn_cancel)
    public void onCancel() {
        tag_source.onChanged();
        tag_biz.onChanged();
        tag_diy.onChanged();
        markSelectedTags();
        finish();
    }

    private void markSelectedTags() {
        selectPosSetSource = tag_source.getSelectedList();
        selectPosSetbiz = tag_biz.getSelectedList();
        selectPosSetdiy = tag_diy.getSelectedList();
    }

    @OnClick(R.id.btn_sure)
    public void onSure() {
//        ToastUtil.showToast(this, sourceType + getTags());
        markSelectedTags();
        if (selectPosSetSource.isEmpty()) {
            sourceType = "";
        }
        Bundle bundle = new Bundle();
        bundle.putString("sourceType", sourceType);
        bundle.putString("tags", getTags());
        setResult(RESULT_OK, getIntent().putExtras(bundle));
        finish();
    }

    @Subscribe
    public void onEventMainThread(Event event) {
        if (event instanceof TagEvent) {
            if (event.getId() == TagEvent.TAG_GETLIST) {
                list = (ArrayList<ArrayList<ClientLabel>>) event.getObject();

                tag_source.setAdapter(new TagAdapter(sourceList) {
                    @Override
                    public View getView(FlowLayout parent, int position, Object o) {
                        TextView tv = (TextView) LayoutInflater.from(TagFilterActivity.this).inflate(R.layout.tag_source,
                                parent, false);
                        tv.setText(sourceList.get(position));
                        return tv;
                    }
                });
                tag_source.getAdapter().setSelectedList(selectPosSetSource);
                tag_biz.setAdapter(new TagAdapter(list.get(0)) {
                    @Override
                    public View getView(FlowLayout parent, int position, Object o) {
                        TextView tv = (TextView) LayoutInflater.from(TagFilterActivity.this).inflate(R.layout.tag_biz,
                                parent, false);
                        tv.setText(list.get(0).get(position).getName());
                        return tv;
                    }
                });
                tag_biz.getAdapter().setSelectedList(selectPosSetbiz);
                tag_diy.setAdapter(new TagAdapter(list.get(1)) {
                    @Override
                    public View getView(FlowLayout parent, int position, Object o) {
                        TextView tv = (TextView) LayoutInflater.from(TagFilterActivity.this).inflate(R.layout.tag_diy,
                                parent, false);
                        tv.setText(list.get(1).get(position).getName());
                        return tv;
                    }
                });
                tag_diy.getAdapter().setSelectedList(selectPosSetdiy);
            }
        }
    }

    public String getTags() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tag_biz.getSelectedList().size(); i++) {
            sb.append(list.get(0).get((int) tag_biz.getSelectedList().toArray()[i]).getName()).append(",");
        }
        for (int j = 0; j < tag_diy.getSelectedList().size(); j++) {
            sb.append(list.get(1).get((int) tag_diy.getSelectedList().toArray()[j]).getName()).append(",");
        }
        return sb.toString();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
