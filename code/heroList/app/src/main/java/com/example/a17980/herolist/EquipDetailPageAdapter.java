package com.example.a17980.herolist;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by fjj on 2017/6/16.
 */

public class EquipDetailPageAdapter extends FragmentPagerAdapter {

    private static int PAGE_COUNT;//表示要展示的页面数量
    String[] type = {"攻击", "法术", "防御", "移动", "打野", "辅助"};
    private Context mContext;

    public EquipDetailPageAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.mContext = context;
        PAGE_COUNT = 6;
    }

    @Override
    public Fragment getItem(int position) {
        return EquipInfoFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {//设置标题
        if (position < 6)
            return type[position];
        else
            return null;
    }
}
