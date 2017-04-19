package com.iwangcn.qingkong.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.ui.model.NewsInfo;
import com.iwangcn.qingkong.ui.view.MyCommonDialog;
import com.nhaarman.listviewanimations.ArrayAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.UndoAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * demo Adapter Created by zhchen on 15/8/5
 */
public class CollectAdapter extends ArrayAdapter implements UndoAdapter{

    public interface OnClickCancleCollectListener {
        public void onClickCancleCollect(int position);
    }

    private Context mContext;
    private OnClickCancleCollectListener listener;

    public CollectAdapter(Context context) {
        this.mContext = context;
    }

    public void setCancleCollcetListener(OnClickCancleCollectListener listener) {
        this.listener = listener;
    }

    @Override
    public long getItemId(final int position) {
        return getItem(position).hashCode();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.activity_collect_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final NewsInfo model = (NewsInfo) getItem(position);
        if (!TextUtils.isEmpty(model.getTitle())) {
            viewHolder.title.setText(model.getTitle());
        }
        if (!TextUtils.isEmpty(model.getNumb())) {
            viewHolder.tvNumb.setText(model.getNumb());
        }
//        if (!TextUtils.isEmpty(model.getPubtime())) {
//            viewHolder.tvTime.setText(model.getPubtime());
//        }
        viewHolder.linCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final MyCommonDialog dialog = new MyCommonDialog(mContext);
                dialog.setContent("确认取消？");
                dialog.setOnDialogClick(new MyCommonDialog.DialogInterface() {
                    @Override
                    public void onCancle() {

                    }

                    @Override
                    public void onConfirm() {
                        dialog.dismiss();
                        if (listener != null) {
                            listener.onClickCancleCollect(position);
                        }
                    }
                });
                dialog.show();
            }
        });
        return convertView;
    }


    @NonNull
    @Override
    public View getUndoView(final int position, final View convertView, @NonNull final ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.undo_row, parent, false);
        }
        return view;
    }

    @NonNull
    @Override
    public View getUndoClickView(@NonNull final View view) {
        return view.findViewById(R.id.undo_row_undobutton);
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.news_title)
        public TextView title;//标题
        @BindView(R.id.new_item_num)
        public TextView tvNumb;//新闻数量
        @BindView(R.id.new_item_time)
        public TextView tvTime;//新闻数量
        @BindView(R.id.homefragment_lin_collect)
        public LinearLayout linCollect;//取消收藏

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}