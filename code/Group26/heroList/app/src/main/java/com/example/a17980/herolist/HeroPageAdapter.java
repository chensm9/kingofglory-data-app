package com.example.a17980.herolist;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class HeroPageAdapter extends FragmentPagerAdapter {

    private static int PAGE_COUNT;//表示要展示的页面数量
    String[] type = {"全部", "坦克", "战士", "刺客", "法师", "射手", "辅助"};
    List<Fragment> fragmentList = new ArrayList<>();
    private Context mContext;

    public HeroPageAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.mContext = context;
        for (int i = 0; i < 7; i++)
            fragmentList.add(HeroInfoFragment.newInstance(i, fragmentList));
        PAGE_COUNT = 7;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {//设置标题
        if (position < PAGE_COUNT)
            return type[position];
        else
            return null;
    }
}
