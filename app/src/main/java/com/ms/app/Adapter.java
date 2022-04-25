package com.ms.app;

import android.content.Context;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by maohuawei on 2017/10/23.
 */

public class Adapter extends PagerAdapter {

    //上下文
    private Context context;
    //存放图片地址的集合
    private List<String> images;
    //Handler
    private Handler handler;

    /**
     * 构造方法
     *
     * @param context
     * @param images
     * @param handler
     */
    public Adapter(Context context, List<String> images, Handler handler) {
        this.context = context;
        this.images = images;
        this.handler = handler;
    }

    /**
     * 返回条目数,为了使其能够无限轮播,所以设置一个较大的数字
     *
     * @return
     */
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    /**
     * view==object
     *
     * @param view
     * @param object
     * @return
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 添加图片到ViewGroup
     *
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        //创建ImageView对象
        ImageView imageView = new ImageView(context);
        //设置图片
        //获取图片的地址
        String url = images.get(position % images.size());
        //使用Glide加载图片
        Glide.with(context).load(url).into(imageView);
        //设置拉伸
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        //设置图片的触摸事件
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN://按下
                        //发送空消息使其暂停自动轮播
                        handler.removeCallbacksAndMessages(null);
                        break;
                    case MotionEvent.ACTION_MOVE://移动的动作
                        //发送空消息使其暂停自动轮播
                        handler.removeCallbacksAndMessages(null);
                        break;
                    case MotionEvent.ACTION_CANCEL://取消
                        //重新发送,继续自动轮播
                        handler.sendEmptyMessageDelayed(0, 1000 * 5);
                        break;
                    case MotionEvent.ACTION_UP://抬起
                        //重新发送,继续自动轮播
                        handler.sendEmptyMessageDelayed(0, 1000 * 5);
                        break;
                    default:
                        break;
                }
                //返回true表示自己处理事件
                return true;
            }
        });

        //添加
        container.addView(imageView);

        //返回图片
        return imageView;
    }

    /**
     * 删除图片
     *
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}