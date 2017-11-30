package com.example.barthclem.goalsmanager;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WhatIsYourFocus extends AppCompatActivity{

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] icons={};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toolbar=(Toolbar)findViewById(R.id.toolBAR);
        tabLayout=(TabLayout)findViewById(R.id.tablayout);
        viewPager=(ViewPager)findViewById(R.id.viewpager);

        setSupportActionBar(toolbar);
        setupTabLayout();
        tabLayout.setupWithViewPager(viewPager);
        //setIcons();

    }
    public void setIcons(){
        //tabLayout.getTabAt(0).setIcon(icons[0]);
        // tabLayout.getTabAt(1).setIcon(icons[1]);

    }

    public void setupTabLayout(){
        FocusViewerAdapter adapter=new FocusViewerAdapter(getSupportFragmentManager());
        adapter.addFragment(DayGoals.newInstance(),"Goals");
        adapter.addFragment(PrevGoals.newInstance(),"Unaccomplished Goals");
        viewPager.setAdapter(adapter);
    }

    class FocusViewerAdapter extends FragmentPagerAdapter{

        private List<String> fragmentTitles=new ArrayList<>();
        private List<Fragment> fragments=new ArrayList<>();


        public FocusViewerAdapter(FragmentManager fm) {
            super(fm);
        }
        public void addFragment(Fragment fm,String title){
            fragmentTitles.add(title);
            fragments.add(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitles.get(position);
        }

    }
}