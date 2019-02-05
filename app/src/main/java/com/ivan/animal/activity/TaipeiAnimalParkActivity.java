package com.ivan.animal.activity;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.ivan.animal.R;
import com.ivan.animal.fragment.AnimalFragment;
import com.ivan.animal.listener.MainUiControlCallBack;
import com.ivan.animal.utils.FragmentUtils;
import com.ivan.animal.utils.UrlUtils;

/**
 * Main Activity.
 * Created by Ivan on 2019/02/03.
 *
 * @author IvanLu
 */
public class TaipeiAnimalParkActivity extends AppCompatActivity implements MainUiControlCallBack {

    private final String TAG = TaipeiAnimalParkActivity.class.getSimpleName();
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView leftMenu;
    private ArrayAdapter mArrayAdapter;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity);
        setView();
        setAppBarUI();
        FragmentUtils.replaceFragment(getSupportFragmentManager(),
                AnimalFragment.class,
                R.id.container,
                null, true, false);
    }

    private void setView(){
        mDrawerLayout = findViewById(R.id.drawer_layout);
        leftMenu = findViewById(R.id.left_menu);
        mProgressBar = findViewById(R.id.mProgressBar);
    }

    private void setAppBarUI() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_bar_title);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar == null) {
            return;
        }
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerToggle.syncState();
        mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mDrawerToggle.isDrawerIndicatorEnabled()) {
                    onBackPressed();
                }
            }
        });
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        String[] menuList = {getString(R.string.about_me)};
        mArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, menuList);
        leftMenu.setAdapter(mArrayAdapter);
        leftMenu.setOnItemClickListener(mOnItemClickListener);
    }

    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            UrlUtils.openBrowserURL(getApplicationContext(), "https://github.com/TSUNGHSINLU");
        }
    };

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        Log.d(TAG, "count = " + count);
        if(count == 1){
            finish();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void setDrawerMode(boolean enabled) {
        int lockMode = enabled ? DrawerLayout.LOCK_MODE_UNLOCKED :
                DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
        mDrawerLayout.setDrawerLockMode(lockMode);
        mDrawerToggle.setDrawerIndicatorEnabled(enabled);
    }

    @Override
    public void setToolbarTitle(String title) {
        toolbar.setTitle(title);
    }

    @Override
    public void setProgressBar(boolean enable) {
        if(enable){
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }
    }
}
