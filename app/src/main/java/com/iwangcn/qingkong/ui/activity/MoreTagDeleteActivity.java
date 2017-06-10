package com.iwangcn.qingkong.ui.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.business.Event;
import com.iwangcn.qingkong.business.TagEvent;
import com.iwangcn.qingkong.net.BaseSubscriber;
import com.iwangcn.qingkong.net.ExceptionHandle;
import com.iwangcn.qingkong.ui.base.QkBaseActivity;
import com.iwangcn.qingkong.ui.model.ClientLabel;
import com.iwangcn.qingkong.ui.view.MyCommonDialog;
import com.iwangcn.qingkong.utils.AbViewUtil;
import com.iwangcn.qingkong.utils.ToastUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MoreTagDeleteActivity extends QkBaseActivity {
    @BindView(R.id.tag_source)
    TagFlowLayout tag_source;
    private Context mContext = this;
    private TagEvent mTagEvent;
    private ArrayList<ClientLabel> mList = new ArrayList<>();

    @Override
    public int layoutChildResID() {
        return R.layout.activity_more_tag_delete;
    }

    @Override
    public void initView() {
        setTitle("更多业务标签");
        setRightImg(R.drawable.toutiao_btn_done);
    }

    @Override
    public void initData() {
        EventBus.getDefault().register(this);
        mTagEvent = new TagEvent(this);
        mTagEvent.getTagList();
        tag_source.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, final int position, FlowLayout parent) {
                deleteTag(position);
                return true;
            }
        });
    }

    private void deleteTag(final int position) {
        final MyCommonDialog dialog = new MyCommonDialog(mContext);
        dialog.setContent("确认取消？");
        dialog.setOnDialogClick(new MyCommonDialog.DialogInterface() {
            @Override
            public void onCancle() {

            }

            @Override
            public void onConfirm() {
                dialog.dismiss();
                mTagEvent.deleteTags(mList.get(position), new BaseSubscriber(true) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        ToastUtil.showToast(mContext, e.codeMessage);
                    }

                    @Override
                    public void onNext(Object o) {
                        ToastUtil.showToast(mContext, "删除成功");
                        mList.remove(position);
                        tag_source.setAdapter(new MyTagAdapter(mList));
                        mTagEvent.setId(TagEvent.TAG_DELETE);
                        EventBus.getDefault().post(mTagEvent);
                    }
                });
            }
        });
        dialog.show();
    }

    @Subscribe
    public void onEventMainThread(Event event) {
        if (event instanceof TagEvent) {
            ArrayList<ArrayList<ClientLabel>> list = (ArrayList<ArrayList<ClientLabel>>) event.getObject();
            if (list.get(1) != null) {
                mList = list.get(1);
            }
            tag_source.setAdapter(new MyTagAdapter(mList));
            //  mAdapter.setDataList(mList);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public class MyTagAdapter extends TagAdapter {

        public MyTagAdapter(List datas) {
            super(datas);
        }

        @Override
        public View getView(FlowLayout parent, int position, Object o) {
            TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.more_tag_delete_item,
                    parent, false);
            tv.setText(mList.get(position).getName());

            tv.setBackground(AbViewUtil.getShapeDrawableWithStroke("#FFFFFF", mContext.getString(R.string.tag_color_orange_my)));
            return tv;
        }
    }
}
