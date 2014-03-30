package com.sciters.mytheater;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TabWidget;
import android.content.Intent;

import com.sciters.mytheater.fragments.ComingShowFragment;
import com.sciters.mytheater.fragments.MembersFragment;
import com.sciters.mytheater.fragments.RecentShowFragment;

import java.io.FileDescriptor;
import java.io.PrintWriter;

public class TabHomeActivity extends FragmentActivity {
    // Fragment TabHost as mTabHost
    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_home);
        //getActionBar().setDisplayHomeAsUpEnabled(false);

        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);


        mTabHost.setup(this,
                getSupportFragmentManager(),
                R.id.realtabcontent);
        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator("Coming"),
                ComingShowFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator("Recent"),
                RecentShowFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab3").setIndicator("Members"),
                MembersFragment.class, null);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tab_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                Intent launchNewIntent = new Intent(TabHomeActivity.this, About.class);
                startActivityForResult(launchNewIntent, 0);
        }
        return true;

    }

}