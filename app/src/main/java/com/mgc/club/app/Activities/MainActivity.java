package com.mgc.club.app.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import com.mgc.club.app.Adapters.SectionsPager_Adapter;
import com.mgc.club.app.Fragments.Certificates_Fragment;
import com.mgc.club.app.Fragments.Events_Fragment;
import com.mgc.club.app.Fragments.Places_Fragment;
import com.mgc.club.app.R;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * Created by savva on 06.07.2015.
 */
public class MainActivity extends AppCompatActivity implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {

    public static final String TAB_1 = "Tab1";
    public static final String TAB_2 = "Tab2";
    public static final String TAB_3 = "Tab3";

    private ViewPager mViewPager;
    SectionsPager_Adapter new_adapter;
    private TabHost tabHost;
    private HashMap<String, TabInfo> mapTabInfo = new HashMap<String, TabInfo>();

    private class TabInfo {
        private String tag;
        private Class<?> clss;
        private Bundle args;
        private Fragment fragment;

        TabInfo(String tag, Class<?> clazz, Bundle args) {
            this.tag = tag;
            this.clss = clazz;
            this.args = args;
        }

    }

    private void intialiseViewPager() {

        List<Fragment> fragments = new Vector<Fragment>();
        fragments.add(Fragment.instantiate(this, Certificates_Fragment.class.getName()));
        fragments.add(Fragment.instantiate(this, Places_Fragment.class.getName()));
        fragments.add(Fragment.instantiate(this, Events_Fragment.class.getName()));
        this.new_adapter = new SectionsPager_Adapter(super.getSupportFragmentManager(), fragments);

        this.mViewPager = (ViewPager) super.findViewById(R.id.pager);
        this.mViewPager.setAdapter(this.new_adapter);
        this.mViewPager.setOnPageChangeListener(this);
    }

    private void initialiseTabHost(Bundle args) {
        tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();
        TabInfo tabInfo = null;

        MainActivity.AddTab(this, this.tabHost, this.tabHost.newTabSpec(TAB_1).setIndicator("Сертификаты"), (tabInfo = new TabInfo(TAB_1, Certificates_Fragment.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        MainActivity.AddTab(this, this.tabHost, this.tabHost.newTabSpec(TAB_2).setIndicator("Места"), (tabInfo = new TabInfo(TAB_2, Places_Fragment.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        MainActivity.AddTab(this, this.tabHost, this.tabHost.newTabSpec(TAB_3).setIndicator("Новости"), (tabInfo = new TabInfo(TAB_3, Events_Fragment.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        //
        tabHost.setOnTabChangedListener(this);


        TabWidget tabWidget = tabHost.getTabWidget();
        for (int i = 0; i < tabWidget.getChildCount(); i++) {
            ViewGroup v = (ViewGroup) tabWidget.getChildAt(i);
            TextView tv = (TextView) v.getChildAt(1);
            if (tv != null) {
                tv.setTextSize(9);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        getActionBar().setTitle("Сертификаты");

        this.initialiseTabHost(savedInstanceState);
        if (savedInstanceState != null) {
            tabHost.setCurrentTabByTag(savedInstanceState.getString("tab")); //set the tab as per the saved state
        }
        // Intialise ViewPager
        this.intialiseViewPager();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
//        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setTitle("Сертификаты");
        return true;
    }

    class TabFactory implements TabHost.TabContentFactory {

        private final Context mContext;

        /**
         * @param context
         */
        public TabFactory(Context context) {
            mContext = context;
        }

        /**
         * (non-Javadoc)
         *
         * @see android.widget.TabHost.TabContentFactory#createTabContent(String)
         */
        public View createTabContent(String tag) {
            View v = new View(mContext);
            v.setMinimumWidth(0);
            v.setMinimumHeight(0);
            return v;
        }

    }

    private static void AddTab(MainActivity activity, TabHost tabHost, TabHost.TabSpec tabSpec, TabInfo tabInfo) {

        // Attach a Tab view factory to the spec
        tabSpec.setContent(activity.new TabFactory(activity));
        tabHost.addTab(tabSpec);
    }

    /**
     * (non-Javadoc)
     *
     * @see android.widget.TabHost.OnTabChangeListener#onTabChanged(String)
     */
    public void onTabChanged(String tag) {
        //TabInfo newTab = this.mapTabInfo.get(tag);
        int pos = this.tabHost.getCurrentTab();

        if (tag.equals(TAB_1)) {
            getSupportActionBar().setTitle("Сертификаты");
        } else if (tag.equals(TAB_2)) {
            getSupportActionBar().setTitle("Места");
        } else if (tag.equals(TAB_3)) {
            getSupportActionBar().setTitle("Новости");
        }

        this.mViewPager.setCurrentItem(pos);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        this.tabHost.setCurrentTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
