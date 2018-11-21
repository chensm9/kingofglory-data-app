package com.example.a17980.herolist;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class ListActivity extends AppCompatActivity{
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        mTabLayout = findViewById(R.id.sliding_tabs);
        mViewPager = findViewById(R.id.viewpager);
        mFragmentManager = getSupportFragmentManager();
        //为viewpager设置适配器
        mViewPager.setAdapter(new HeroPageAdapter(ListActivity.this, mFragmentManager));
        mViewPager.setOffscreenPageLimit(8);
        // 绑定TabLayout和ViewPager
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
