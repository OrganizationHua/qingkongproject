package com.iwangcn.qingkong.ui.view.TagWidget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public abstract class BaseMultipleItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int ITEM_TYPE_ONE_TITLE = 1;
    public static final int ITEM_TYPE_ONE_CONTENT = 2;
    public static final int ITEM_TYPE_TWO_TITLE = 3;
    public static final int ITEM_TYPE_TWO_CONTENT = 4;
    public static final int ITEM_TYPE_THREE_TITLE = 5;
    public static final int ITEM_TYPE_THREE_CONTENT = 6;


    protected LayoutInflater mLayoutInflater;
    protected Context mContext;
    protected int mHeaderCount;//头部View个数

    public BaseMultipleItemAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_ONE_TITLE) {
            return onCreateHeaderView(parent);
        } else if (viewType == ITEM_TYPE_ONE_CONTENT) {
            return onCreateContentView(parent);
        } else if (viewType == ITEM_TYPE_TWO_TITLE) {
            return onCreateHeaderView(parent);
        } else if (viewType == ITEM_TYPE_TWO_CONTENT) {
            return onCreateContentView(parent);
        } else if (viewType == ITEM_TYPE_THREE_TITLE) {
            return onCreateHeaderView(parent);
        } else if (viewType == ITEM_TYPE_THREE_CONTENT) {
            return onCreateContentView(parent);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {//头部View
            return ITEM_TYPE_ONE_TITLE;
        } else if (position > getOneTitlePosition() && position < getTwoTitlePosition()) {
            return ITEM_TYPE_ONE_CONTENT;
        } else if (position == getTwoTitlePosition()) {
            return ITEM_TYPE_TWO_TITLE;
        } else if (position > getTwoTitlePosition() && position < getThreeTitlePosition()) {
            return ITEM_TYPE_TWO_CONTENT;
        } else if (position == getThreeTitlePosition()) {
            return ITEM_TYPE_THREE_TITLE;
        } else if (position > getThreeTitlePosition() && position <= getThreeTitlePosition() + getThreeContentItemCount()) {
            return ITEM_TYPE_THREE_CONTENT;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return getThreeTitlePosition() + getThreeContentItemCount() + 1;
    }

    public abstract RecyclerView.ViewHolder onCreateHeaderView(ViewGroup parent);//创建头部View

    public abstract RecyclerView.ViewHolder onCreateContentView(ViewGroup parent);//创建中间内容View

    public abstract int getOneContentItemCount();//获取中间内容个数

    public abstract int getTwoContentItemCount();//获取中间内容个数

    public abstract int getThreeContentItemCount();//获取中间内容个数

    public abstract int getOneTitlePosition();//获取中间内容个数

    public abstract int getTwoTitlePosition();//获取中间内容个数

    public abstract int getThreeTitlePosition();//获取中间内容个数
}
