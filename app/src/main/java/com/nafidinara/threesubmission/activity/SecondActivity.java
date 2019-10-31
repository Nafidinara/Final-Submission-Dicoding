package com.nafidinara.threesubmission.activity;

import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.nafidinara.threesubmission.R;
import com.nafidinara.threesubmission.fragment.MovieFavoriteFragment;
import com.nafidinara.threesubmission.fragment.TvShowFavFragment;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        toolbar = findViewById(R.id.toolbar1);
        tabLayout = findViewById(R.id.tabLayout1);
        viewPager = findViewById(R.id.viewPager1);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            toolbar.setTitle(getString(R.string.fav_title));
        }

        toolbar.inflateMenu(R.menu.menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.action_change_settings:
                        Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                        startActivity(mIntent);
                        return true;
                    case R.id.myfav:
                        return true;
                    case R.id.mymain:
                        finish();
                        return true;
                    case R.id.notification_setting:
                        Intent notifIntent = new Intent(getApplicationContext(),SettingActivity.class);
                        startActivity(notifIntent);
                }
                return true;
            }
        });

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }



    private void setupViewPager (ViewPager viewPager){
        SecondActivity.viewPagerAdapter adapter = new SecondActivity.viewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MovieFavoriteFragment(),getString(R.string.fav_movie));
        adapter.addFragment(new TvShowFavFragment(),getString(R.string.fav_tvshow));

        viewPager.setAdapter(adapter);
    }

    class viewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> FragmentList = new ArrayList<>();
        private final List<String> TitleList = new ArrayList<>();

        public viewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return FragmentList.get(i);
        }

        @Override
        public int getCount() {
            return FragmentList.size();
        }

        public void addFragment (Fragment fragment,String title){
            FragmentList.add(fragment);
            TitleList.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return TitleList.get(position);
        }
    }
}

