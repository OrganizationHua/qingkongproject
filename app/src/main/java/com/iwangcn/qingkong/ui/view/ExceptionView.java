package com.iwangcn.qingkong.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iwangcn.qingkong.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by RF on 2017/4/22.
 */

public class ExceptionView extends RelativeLayout {
    @BindView(R.id.img_exception)
     ImageView imageView;
    @BindView(R.id.tv_exception)
     TextView textView;
    private View view;


    public ExceptionView(Context context) {
        this(context, null);

    }

    public ExceptionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExceptionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        view = inflate(context, R.layout.exceptionview, null);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        view.setLayoutParams(lp);
        addView(view);
        ButterKnife.bind(this, view);
    }

    public void setEmptyView() {
        imageView.setBackgroundResource(R.drawable.emptyimg);
        textView.setText("您尚无跟进的信息");
    }

    public void setNetExcepetion() {
        imageView.setBackgroundResource(R.drawable.emptyimg);
        textView.setText("网络加载失败，请检查您的网络设置");
    }

    public void setloadExcepetion() {
        imageView.setBackgroundResource(R.drawable.emptyimg);
        textView.setText("页面加载失败，请点击此处重新加载");
    }
}
