package fsoft.training.movieapplication.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fsoft.training.movieapplication.R;
import fsoft.training.movieapplication.application.utils.AppSingleton;
import fsoft.training.movieapplication.constant.Constants;
import fsoft.training.movieapplication.data.preference.BaseSharedPreference;
import fsoft.training.movieapplication.domain.model.dto.reminder.ReminderDto;
import fsoft.training.movieapplication.listener.ReminderListPresenterListener;
import fsoft.training.movieapplication.presentation.ReminderListPresenter;
import fsoft.training.movieapplication.view.adapter.ReminderAdapter;
import fsoft.training.movieapplication.view.fragment.AboutFragment;
import fsoft.training.movieapplication.view.fragment.FavouriteMovieFragment;
import fsoft.training.movieapplication.view.fragment.ListMovieFragment;
import fsoft.training.movieapplication.view.fragment.MovieDetailFragment;
import fsoft.training.movieapplication.view.fragment.SettingEmptyFragment;

/**
 * Created by ManhND16 on 10/22/2017
 */
public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MovieDetailFragment.SendDataToActivity
        , ReminderListPresenterListener, View.OnClickListener {

    ////////////////////////////////////////////////////////////////////////////
    // instance fields
    ////////////////////////////////////////////////////////////////////////////
    private ImageView mImageAvatar;
    private TextView mName;
    private TextView mEmail;
    private TextView mBirthDay;
    private TextView mGender;
    private String avatar;
    private Button mEditProfileBtn;
    private Button mShowAllBtn;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private boolean mTypeLayout;
    private String[] mTabTitle = {
            Constants.MOVIES,
            Constants.FAVOURITE,
            Constants.SETTINGS,
            Constants.ABOUT
    };
    private Menu mCustomMenu;
    private String mTitleDetail;
    private ReminderListPresenter mReminderListPresenter;
    private DrawerLayout mDrawer;
    private RecyclerView mRecyclerViewReminder;
    private ReminderAdapter mReminderAdapter;
    private List<ReminderDto> mReminderDtoList;
    private SharedPreferences mSharedPreferences;
    public static TextView count;
    private static final String ACTION_HOGEHOGE = "com.android.broadcast.FLAG_COUNT_FAVOURITE";

    ///////////////////////////////////////////////////////////////////////////
    // public method
    ////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(4);

        /**
         * SET UP FOR TABLAYOUT
         */
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mTabLayout.setupWithViewPager(mViewPager);
        setTabLayout();

        mTypeLayout = new BaseSharedPreference().restoringTypeLayout(Constants.FILE_NAME_TYPE_LAYOUT
                , Context.MODE_PRIVATE, getBaseContext());

        setTitlePage();
        getSupportActionBar().setTitle(Constants.MOVIES);

        mReminderListPresenter = new ReminderListPresenter(this);
        mRecyclerViewReminder = (RecyclerView) findViewById(R.id.nav_recycler_reminder);
        mEditProfileBtn = (Button) findViewById(R.id.nav_edit_profile_button);
        mShowAllBtn = (Button) findViewById(R.id.show_all_button);
        mName = (TextView) findViewById(R.id.nav_name_tv);
        mBirthDay = (TextView) findViewById(R.id.nav_birthday_tv);
        mEmail = (TextView) findViewById(R.id.nav_email_tv);
        mGender = (TextView) findViewById(R.id.nav_gender_tv);
        mImageAvatar = (ImageView) findViewById(R.id.nav_avatar_img);
        /**
         * When open actionbar will loading reminder list
         */
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (mDrawer.isDrawerOpen(drawerView)) {
                    mReminderListPresenter.requestListReminderToResponsitory(getBaseContext());
                    setDataListReminder();
                }
                mReminderListPresenter.requestListReminderToResponsitory(BaseActivity.this);
                setDataListReminder();
            }
        };
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        mEditProfileBtn.setOnClickListener(this);
        mShowAllBtn.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        AppSingleton.isClickDetail = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        restoringDataProfile();
        IntentFilter filter = new IntentFilter(ACTION_HOGEHOGE);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.base, menu);
        this.mCustomMenu = menu;
        if (mTypeLayout) {
            AppSingleton.isChangelayout = true;
            menu.findItem(R.id.change_view_actionbar).setIcon(R.mipmap.ic_grid_on_white);
        } else {
            AppSingleton.isChangelayout = true;
            menu.findItem(R.id.change_view_actionbar).setIcon(R.mipmap.ic_list_white);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        if (id == R.id.change_view_actionbar) {
            mTypeLayout = !mTypeLayout;
            SharedPreferences preferences = getSharedPreferences("type_layout", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("typelayout", mTypeLayout);
            editor.apply();
            if (mTypeLayout) {
                item.setIcon(R.mipmap.ic_grid_on_white);
            } else {
                item.setIcon(R.mipmap.ic_list_white);
            }

        } else if (id == R.id.movies_actionbar) {
            mViewPager.setCurrentItem(0);
        } else if (id == R.id.favourite_actionbar) {
            mViewPager.setCurrentItem(1);
        } else if (id == R.id.setting_actionbar) {
            mViewPager.setCurrentItem(2);
        } else if (id == R.id.about_actionbar) {
            mViewPager.setCurrentItem(3);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPassData(String data) {
        this.mTitleDetail = data;
    }

    @Override
    public void resultListFromPresenter(List<ReminderDto> reminderDtoList) {
        this.mReminderDtoList = reminderDtoList;
    }

    @Override
    public void resultRemoveReminderFromPresenter(String result) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nav_edit_profile_button:
                Intent intent = new Intent(BaseActivity.this, EditProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constants.EDIT_PROFILE_BUNDLE_KEY_NAME, mName.getText().toString());
                bundle.putString(Constants.EDIT_PROFILE_BUNDLE_KEY_BIRTHDAY, mBirthDay.getText().toString());
                bundle.putString(Constants.EDIT_PROFILE_BUNDLE_KEY_EMAIL, mEmail.getText().toString());
                bundle.putString(Constants.EDIT_PROFILE_BUNDLE_KEY_GENDER, mGender.getText().toString());
                intent.putExtras(bundle);
                startActivityForResult(intent, Constants.REQUEST_CODE_EDIT_PROFILE);
                mDrawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.show_all_button:
                SettingEmptyFragment blankFragment = new SettingEmptyFragment();
                TabLayout.Tab tab = mTabLayout.getTabAt(2);
                tab.select();
                switchContentReminder(R.id.frame_home, blankFragment, Constants.TITLE_ALL_REMINDERS);
                mDrawer.closeDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    /**
     * replace fragment
     *
     * @param id
     * @param fragment
     * @param title
     */
    public void switchContent(int id, Fragment fragment, String title) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        getSupportActionBar().setTitle(title);
        ft.replace(id, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    public void switchContentReminder(int id, Fragment fragment, String title) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Bundle mBundle = new Bundle();
        mBundle.putBoolean(Constants.FLAG_REMINDER, true);
        fragment.setArguments(mBundle);
        getSupportActionBar().setTitle(title);
        ft.replace(id, fragment, fragment.toString());
        ft.commit();
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    ////////////////////////////////////////////////////////////////////////////
    // private method
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Setup viewpager tab functional
     *
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ListMovieFragment(), Constants.MOVIES);
        adapter.addFragment(new FavouriteMovieFragment(), Constants.FAVOURITE);
        adapter.addFragment(new SettingEmptyFragment(), Constants.SETTINGS);
        adapter.addFragment(new AboutFragment(), Constants.ABOUT);
        viewPager.setAdapter(adapter);
    }

    /**
     * Setup custom tab layout in viewpager
     */
    private void setTabLayout() {
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.tab_layout_item, null);
        TextView title = (TextView) view.findViewById(R.id.item_tablayout_title_txt);
        ImageView imgIcon = (ImageView) view.findViewById(R.id.item_tablayout_img);
        title.setText(Constants.MOVIES);
        imgIcon.setImageResource(R.mipmap.ic_home);
        mTabLayout.getTabAt(0).setCustomView(view);

        view =
                LayoutInflater.from(getApplicationContext()).inflate(R.layout.tab_layout_item, null);
        title = (TextView) view.findViewById(R.id.item_tablayout_title_txt);
        count = (TextView) view.findViewById(R.id.item_tablayout_count_txt);
        imgIcon = (ImageView) view.findViewById(R.id.item_tablayout_img);
        title.setText(Constants.FAVOURITE);
        imgIcon.setImageResource(R.mipmap.ic_favorite);
        count.setVisibility(View.VISIBLE);
        mTabLayout.getTabAt(1).setCustomView(view);

        view =
                LayoutInflater.from(getApplicationContext()).inflate(R.layout.tab_layout_item, null);
        title = (TextView) view.findViewById(R.id.item_tablayout_title_txt);
        imgIcon = (ImageView) view.findViewById(R.id.item_tablayout_img);
        title.setText(Constants.SETTINGS);
        imgIcon.setImageResource(R.mipmap.ic_settings);
        mTabLayout.getTabAt(2).setCustomView(view);

        view =
                LayoutInflater.from(getApplicationContext()).inflate(R.layout.tab_layout_item, null);
        title = (TextView) view.findViewById(R.id.item_tablayout_title_txt);
        imgIcon = (ImageView) view.findViewById(R.id.item_tablayout_img);
        title.setText(Constants.ABOUT);
        imgIcon.setImageResource(R.mipmap.ic_info);
        mTabLayout.getTabAt(3).setCustomView(view);
    }

    /**
     * set title for tab item when switch
     */
    private void setTitlePage() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d("onPageScrolled", "Khi scroll được gọi," + "Mới" + position);
            }

            @Override
            public void onPageSelected(int position) {
                Log.d("SELECT TAB", mTabTitle[position]);
                if (position == 0) {
                    if (AppSingleton.isClickDetail == false) {
                        getSupportActionBar().setTitle(mTabTitle[0]);
                    } else {
                        getSupportActionBar().setTitle(mTitleDetail);
                    }
                    mCustomMenu.findItem(R.id.change_view_actionbar).setVisible(true);
                    mCustomMenu.findItem(R.id.action_search).setVisible(false);
                } else if (position == 1) {
                    getSupportActionBar().setTitle(mTabTitle[1]);
                    if (mCustomMenu != null) {
                        mCustomMenu.findItem(R.id.change_view_actionbar).setVisible(false);
                        mCustomMenu.findItem(R.id.action_search).setVisible(true);
                    }

                } else if (position == 2) {
                    getSupportActionBar().setTitle(mTabTitle[2]);
                    if (mCustomMenu != null) {
                        mCustomMenu.findItem(R.id.change_view_actionbar).setVisible(false);
                        mCustomMenu.findItem(R.id.action_search).setVisible(false);

                    }


                } else if (position == 3) {
                    getSupportActionBar().setTitle(mTabTitle[3]);
                    if (mCustomMenu != null) {
                        mCustomMenu.findItem(R.id.change_view_actionbar).setVisible(false);
                        mCustomMenu.findItem(R.id.action_search).setVisible(false);
                    }

                }
//                getSupportActionBar().setTitle(tabTitle[position]);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });

    }

    /**
     * RESTORING DATA FROM SHAREDPREFERENCE PROFILE PICTURE
     */
    private void restoringDataProfile() {
        mSharedPreferences = getSharedPreferences(Constants.PROFILE_NAME, MODE_PRIVATE);
        avatar = mSharedPreferences.getString(Constants.EDIT_PROFILE_PREFERENCES_KEY_PICTURE_PROFILE, "");
        Bitmap savingAvatar = AppSingleton.decodeBase64(avatar);
        if (savingAvatar != null) {
            mImageAvatar.setImageBitmap(savingAvatar);
        }

        mEmail.setText(mSharedPreferences.getString(Constants.EDIT_PROFILE_PREFERENCES_KEY_EMAIL, "starmovie@gmail.com"));
        mName.setText(mSharedPreferences.getString(Constants.EDIT_PROFILE_PREFERENCES_KEY_NAME, "StarMovie"));
        mBirthDay.setText(mSharedPreferences.getString(Constants.EDIT_PROFILE_PREFERENCES_KEY_BIRTHDAY, "01/01/1994"));
        mGender.setText(mSharedPreferences.getString(Constants.EDIT_PROFILE_PREFERENCES_KEY_GENDER, "Male"));
    }

    /**
     * set data to list reminder in navigation drawer
     */
    private void setDataListReminder() {
        mReminderAdapter = new ReminderAdapter(mReminderDtoList, BaseActivity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(BaseActivity.this, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewReminder.setLayoutManager(mLayoutManager);
        mReminderAdapter.notifyDataSetChanged();
        mRecyclerViewReminder.setAdapter(mReminderAdapter);
    }


    ////////////////////////////////////////////////////////////////////////////
    // inner class
    ////////////////////////////////////////////////////////////////////////////

    private static class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {

            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public float getPageWidth(int position) {
            switch (position) {
                case 0:
                    Log.d("onCreate", "case 0");
                    break;
            }
            return super.getPageWidth(position);

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public static class CountBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("CountBroadcast", "onReceive: ABCABC" + intent.getStringExtra(Constants.FLAG_ACTION_BROADCAST_COUNT_FAVOURITE));
            BaseActivity.count.setText(intent.getStringExtra(Constants.FLAG_ACTION_BROADCAST_COUNT_FAVOURITE)+"");
            String mCount = intent.getStringExtra(Constants.FLAG_ACTION_BROADCAST_COUNT_FAVOURITE);
        }
    }
}
