package com.mgc.club.app.Adapters;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by savva on 06.07.2015.
 */
public class SectionsPager_Adapter extends FragmentPagerAdapter {

    Activity activity = new Activity();
    private List<Fragment> fragments;


    public SectionsPager_Adapter(FragmentManager fm, Activity activity, List<Fragment> fragments) {
        super(fm);
        this.activity = activity;
        this.fragments = fragments;
    }

    public SectionsPager_Adapter(FragmentManager supportFragmentManager, List<Fragment> fragments) {
        super(supportFragmentManager);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    /* (non-Javadoc)
     * @see android.support.v4.view.PagerAdapter#getCount()
     */
    @Override
    public int getCount() {
        return this.fragments.size();
    }


}
