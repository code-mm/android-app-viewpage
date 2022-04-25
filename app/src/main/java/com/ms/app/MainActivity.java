package com.ms.app;

import android.os.Handler;
import android.os.Message;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager view_pager;
    private LinearLayout linear_layout;
    private List<String> images;
    private Adapter adapter;
    private List<ImageView> circles;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                //轮播+1
                view_pager.setCurrentItem(view_pager.getCurrentItem() + 1);
                //5秒之后重新发送
                handler.sendEmptyMessageDelayed(0, 1000 * 5);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //查找控件
        initView();
        //初始化数据
        initData();
        //设置适配器
        setAdapter();
        //设置小圆圈
        initCircle();
        //设置ViewPager的监听事件
        setViewPagerListener();
        //设置自动轮播
        setAutoRotation();
    }

    /**
     * 自动轮播
     */
    private void setAutoRotation() {
        new Thread(() -> {
            //发送消息
            handler.sendEmptyMessage(0);
        }).start();
    }

    /**
     * 设置监听
     */
    private void setViewPagerListener() {
        view_pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                //设置小圆圈移动
                for (int i = 0; i < images.size(); i++) {
                    if (position % circles.size() == i) {
                        //设置背景
                        circles.get(i).setImageResource(R.drawable.shape_little_circle_1);
                    } else {
                        //设置背景
                        circles.get(i).setImageResource(R.drawable.shape_little_circle_0);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //设置到viewPager的中间
        view_pager.setCurrentItem(Integer.MAX_VALUE / 2);

    }


    /**
     * 初始化小圆圈
     */


    private void initCircle() {
        //实例化
        circles = new ArrayList<>();
        //清空
        linear_layout.removeAllViews();

        for (int i = 0; i < images.size(); i++) {
            //创建图片
            ImageView imageView = new ImageView(MainActivity.this);

            if (i == 0) {
                //设置小圆圈
                imageView.setImageResource(R.drawable.shape_little_circle_1);
            } else {
                //设置小圆圈
                imageView.setImageResource(R.drawable.shape_little_circle_0);
            }

            //将图片添加到集合
            circles.add(imageView);

            //设置显示方式
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            //设置外边距
            params.setMargins(15, 5, 15, 5);

            //添加小圆圈图片到布局
            linear_layout.addView(imageView);
        }

    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        //对适配器判空
        if (adapter == null) {
            //实例化适配器
            adapter = new Adapter(MainActivity.this, images, handler);
            //设置适配器
            view_pager.setAdapter(adapter);

        } else {
            //刷新适配器
            adapter.notifyDataSetChanged();
        }
    }

    //初始化控件
    private void initView() {
        //查找控件
        view_pager = (ViewPager) findViewById(R.id.view_pager);
        linear_layout = (LinearLayout) findViewById(R.id.linear_layout);
    }

    /**
     * 初始化集合
     */
    private void initData() {
        //实例化集合
        images = new ArrayList<>();
        //添加数据(图片的地址)
        images.add("http://imgsrc.baidu.com/imgad/pic/item/241f95cad1c8a786ff65052a6d09c93d70cf5042.jpg");
        images.add("http://a.hiphotos.baidu.com/image/pic/item/b812c8fcc3cec3fdcc9b00b7df88d43f8694274e.jpg");
        images.add("http://g.hiphotos.baidu.com/image/pic/item/ac6eddc451da81cb571cd40c5b66d0160824317c.jpg");
        images.add("http://c.hiphotos.baidu.com/image/pic/item/8d5494eef01f3a29c715430e9025bc315d607c15.jpg");
        images.add("http://e.hiphotos.baidu.com/image/pic/item/94cad1c8a786c917131821f9c03d70cf3ac757d2.jpg");
    }
}