package com.iwangcn.qingkong.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected LayoutInflater mLayoutInflater;
    protected Context mContext;
    public List<T> mList = null;
    public OnRecyclerItemClickListener onRecyclerItemClickListener = null;

    public interface OnRecyclerItemClickListener {
        void onItemClickListener(RecyclerView.ViewHolder viewHolder,int pos);
    }

    public void setOnItemClickListener(OnRecyclerItemClickListener ItemClickListener) {
        if (onRecyclerItemClickListener == null) {
            onRecyclerItemClickListener = ItemClickListener;
        }
    }

    public BaseRecyclerViewAdapter(Context context, List<T> list) {
        mContext = context;
        mList = list;
        mLayoutInflater = LayoutInflater.from(context);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public abstract int getLayoutId();


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

        itemView = mLayoutInflater.inflate(getLayoutId(), parent, false);


        return onCreateItemView(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyclerItemClickListener.onItemClickListener(holder,position);
            }
        });
        bindData(holder, mList.get(position),position);
    }

    public abstract void bindData(RecyclerView.ViewHolder holder, T t,int pos);

    public abstract RecyclerView.ViewHolder onCreateItemView(View view);


}
