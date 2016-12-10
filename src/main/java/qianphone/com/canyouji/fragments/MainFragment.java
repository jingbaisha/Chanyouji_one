package qianphone.com.canyouji.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import qianphone.com.canyouji.R;
import qianphone.com.canyouji.adapter.MyPagerAdapter;

/**
 * Created by Admin on 2016/12/5.
 */

public class MainFragment extends Fragment implements TabLayout.OnTabSelectedListener, ViewPager.OnPageChangeListener {
    private View mRootView;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.main_fragment_layout, container, false);
        mTabLayout = (TabLayout) mRootView.findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) mRootView.findViewById(R.id.viewPager);

        mTabLayout.setTabTextColors(0xFF000000, 0xFF009BE1);
        mTabLayout.setSelectedTabIndicatorColor(0xFF009BE1);
        TabLayout.Tab tab = mTabLayout.newTab();
        tab.setText("游记");
        mTabLayout.addTab(tab, true);

        tab = mTabLayout.newTab();
        tab.setText("攻略");
        mTabLayout.addTab(tab);

        tab = mTabLayout.newTab();
        tab.setText("工具箱");
        mTabLayout.addTab(tab);

        mTabLayout.addOnTabSelectedListener(this);

        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getChildFragmentManager());
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new YouJiFragment());
        fragments.add(new DestinationFragment());
        fragments.add(new ToolBoxFragment());

        pagerAdapter.setFragments(fragments);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.addOnPageChangeListener(this);
        return mRootView;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mViewPager.setCurrentItem(tab.getPosition(), true);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mTabLayout.getTabAt(position).select();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
