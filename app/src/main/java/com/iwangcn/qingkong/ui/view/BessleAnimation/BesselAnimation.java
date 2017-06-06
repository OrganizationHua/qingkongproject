package com.iwangcn.qingkong.ui.view.BessleAnimation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.iwangcn.qingkong.R;

/**
 * Created by czh on 2017/4/8.
 * 购物车收回动画
 */

public class BesselAnimation {
    private Context mContext;
    private RelativeLayout containerView;//容器
    private View itemView;
    private View collectView;
    public BesselAnimation(Context context,View containerView, View itemView, View collectView) {
        this.mContext=context;
        this.collectView=collectView;
        this.containerView= (RelativeLayout) containerView;
        this.itemView=itemView;
    }

    public void startAnimation(final  Animator.AnimatorListener listener){
        int[] childCoordinate = new int[2];
        int[] parentCoordinate = new int[2];
        int[] shopCoordinate = new int[2];
        //1.分别获取被点击View、父布局、购物车在屏幕上的坐标xy。
        itemView.getLocationInWindow(childCoordinate);
        containerView.getLocationInWindow(parentCoordinate);
        collectView.getLocationInWindow(shopCoordinate);
        //2.自定义ImageView 继承ImageView
        MoveImageView img = new MoveImageView(this.mContext);
       // img.setImageResource(R.mipmap.ic_launcher);
        img.addView(LayoutInflater.from(mContext).inflate(R.layout.collect_btn_bg,null));
        //3.设置img在父布局中的坐标位置
        img.setX(childCoordinate[0] - parentCoordinate[0]);
        img.setY(childCoordinate[1] - parentCoordinate[1]);
        //4.父布局添加该Img
        ((RelativeLayout)containerView).addView(img);
        //5.利用 二次贝塞尔曲线 需首先计算出 MoveImageView的2个数据点和一个控制点
        PointF startP = new PointF();
        PointF endP = new PointF();
        PointF controlP = new PointF();
        //开始的数据点坐标就是 addV的坐标
        startP.x = childCoordinate[0] - parentCoordinate[0];
        startP.y = childCoordinate[1] - parentCoordinate[1];
        //结束的数据点坐标就是 shopImg的坐标
        endP.x = shopCoordinate[0] - parentCoordinate[0];
        endP.y = shopCoordinate[1] - parentCoordinate[1];
        //控制点坐标 x等于 购物车x；y等于 addV的y
        controlP.x = endP.x;
        controlP.y = startP.y;
        //启动属性动画
        ObjectAnimator animator = ObjectAnimator.ofObject(img, "mPointF",
                new PointFTypeEvaluator(controlP), startP, endP);
        animator.setDuration(1000);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                listener.onAnimationStart(animator);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //动画结束后 父布局移除 img
                Object target = ((ObjectAnimator) animator).getTarget();
                containerView.removeView((View) target);
                //shopImg 开始一个放大动画
                Animation scaleAnim = AnimationUtils.loadAnimation(mContext, R.anim.collect_scale);
                collectView.startAnimation(scaleAnim);
                listener.onAnimationEnd(animator);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                listener.onAnimationCancel(animator);
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
                listener.onAnimationRepeat(animator);
            }
        });
        animator.start();
    }
}
