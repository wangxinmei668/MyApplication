package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class IndexActivity extends AppCompatActivity {
    // 定义保存ImageView的对象
    private ImageView Iv;
    //定义手势检测器对象
    private GestureDetector gestureDetector;
    //定义图片的资源数组
    private int[] ResId = new int[]{
            R.drawable.logo,
            R.drawable.k1,
            R.drawable.k2,
            R.drawable.k3,
            R.drawable.k4,
    };
    //定义当前显示的图片的下标
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        findView();
        setListener();
    }

    private void setListener() {
        //设置手势监听器的处理效果由onGestureListener来处理
        gestureDetector = new GestureDetector(IndexActivity.this,
                onGestureListener);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //当前Activity被触摸时回调
        return gestureDetector.onTouchEvent(event);
    }

    private void findView() {
        //得到当前页面的imageview控件
        Iv = (ImageView) findViewById(R.id.index_picture);
    }
    //定义了GestureDetector的手势识别监听器
    private GestureDetector.OnGestureListener onGestureListener
            = new GestureDetector.SimpleOnGestureListener() {
        //当识别的收拾是滑动手势时回调onFinger方法
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            //得到滑动手势的其实和结束点的x，y坐标，并进行计算
            float x = e2.getX() - e1.getX();
            float y = e2.getY() - e1.getY();

            //通过计算结果判断用户是向左滑动或者向右滑动
            if (x > 0) {
                count++;
                count %= 3;
            } else if (x < 0) {
                count--;
                count = (count + 3) % 3;
            }
            //切换imageview的图片
            changeImg();
            return true;
        }
    };

    public void changeImg() {
        //设置当前位置的图片资源
        Iv.setImageResource(ResId[count]);
    }

    public void change(View btn){
        if(btn.getId()==R.id.aboutusButton){
            Log.i("open","openOne: ");
            Intent config_us =new Intent(this, AboutActivity.class);
            startActivity(config_us);
        }else if(btn.getId()==R.id.newsButton){
            Log.i("open","openOne: ");
            Intent config_news =new Intent(this, NewsActivity.class);
            startActivity(config_news);
        }else if(btn.getId()==R.id.courseButton) {
            Log.i("open","openOne: ");
            Intent config_course =new Intent(this, ViewActivity.class);
            startActivity(config_course);
        }else if(btn.getId()==R.id.volunteerButton) {
            Log.i("open","openOne: ");
            Intent config_volunteer =new Intent(this, VolActivity.class);
            startActivity(config_volunteer);
        }
    }


}
