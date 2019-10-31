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
import android.view.Menu;
import android.view.MenuItem;

import com.nafidinara.threesubmission.R;
import com.nafidinara.threesubmission.fragment.MovieFragment;
import com.nafidinara.threesubmission.fragment.TvShowFragment;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            toolbar.setTitle(getString(R.string.app_name));

        }
        setSupportActionBar(toolbar);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }



    private void setupViewPager (ViewPager viewPager){
        viewPagerAdapter adapter = new viewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MovieFragment(),getString(R.string.fragment_movie_title));
        adapter.addFragment(new TvShowFragment(),getString(R.string.fragment_tvshow_title));

        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
                    case R.id.action_change_settings:
                        Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                        startActivity(mIntent);
                        return true;
                    case R.id.myfav:
                        Intent intent = new Intent(getApplicationContext(),SecondActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.mymain:
                        return true;
            case R.id.notification_setting:
                Intent notifIntent = new Intent(getApplicationContext(),SettingActivity.class);
                startActivity(notifIntent);
        }
        return super.onOptionsItemSelected(item);

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
