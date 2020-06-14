package com.example.myapplication;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
public class ViewActivity extends AppCompatActivity implements FirrstFragment.OnFragmentInteractionListener, SecondFragment.OnFragmentInteractionListener, ThirdFragment.OnFragmentInteractionListener, FourFragment.OnFragmentInteractionListener {

    private FragmentManager fManager;
    private FragmentTransaction ftTransaction;

    private TextView[] RB;
    private ViewPager viewpager;//用来存放三个不同的fragment
    private LinearLayout tab_container_layout;//包含三个textView的容器
    private String[] titles={"课程1","课程2","课程3","课程4"};
    private Fragment[] fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        ViewPager viewPager = findViewById(R.id.viewpager);
        MyPageAdapter pageAdapter = new MyPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pageAdapter);

        TabLayout tabLayout  = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        init();


//        fManager=getSupportFragmentManager();
//        ftTransaction= fManager.beginTransaction();
//        ftTransaction.replace(R.id.viewpager,new FirrstFragment());
//        ftTransaction.commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {


    }

    private void init() {
        viewpager =(ViewPager)findViewById(R.id.viewpager);
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){//添加点击选项卡来切换页面的监听器
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectedTitle(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        fragments = new Fragment[4];
        fragments[0] = new FirrstFragment();
        fragments[1] = new SecondFragment();
        fragments[2] = new ThirdFragment();
        fragments[3] = new FourFragment();
        tab_container_layout =findViewById(R.id.tab_container_layout);//关联XML中的容器控件
        int count = tab_container_layout.getChildCount();//得到这个容器控件中textView的数量
        RB = new TextView[count];
        //从容器中的得到子控件
        for (int i = 0; i < count;i++){
            RB[i] = (TextView) tab_container_layout.getChildAt(i);
            RB[i].setText(titles[i]);
            RB[i].setTag(i);
            //可被单击
            RB[i].setEnabled(true);
            //选项卡单击，用户希望换一个选项
            RB[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int item = (Integer) v.getTag();
                    viewpager.setCurrentItem(item);
                    selectedTitle(item);
                }
            });
        }
        //不可被单击
        RB[0].setEnabled(false);
        //表示为选中项
        RB[0].setTextColor(Color.RED);
    }
    //重新设置选项卡的选中
    private void selectedTitle(int index){
        for (int i = 0;i < RB.length;i++){
            RB[i].setTextColor(Color.BLACK);
            RB[i].setEnabled(true);
        }
        RB[index].setTextColor(Color.RED);
        RB[index].setEnabled(false);
    }

}
