package com.wiacek.martyna.esnpwapp;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.wiacek.martyna.esnpwapp.Adapter.NavigationDrawerAdapter;
import com.wiacek.martyna.esnpwapp.AsyncTask.UpdateToDosOnServerTask;
import com.wiacek.martyna.esnpwapp.Domain.DrawerItem;
import com.wiacek.martyna.esnpwapp.Domain.NavigationDrawerModel;
import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
import com.wiacek.martyna.esnpwapp.Domain.SessionManager;
import com.wiacek.martyna.esnpwapp.Fragment.*;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NavigationDrawer extends ActionBarActivity {

    @InjectView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @InjectView(R.id.left_drawer)
    ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private boolean webBrowserBack;
    private NavigationDrawerAdapter adapter;
    private boolean uploadedPicture;
    private NavigationDrawerModel navigationDrawerModel;

    private android.app.Fragment nativeFragment = null;

    private SessionManager session;
    private List<DrawerItem> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entirely_new_drawer);
        ButterKnife.inject(this);
        session = new SessionManager(getApplicationContext());
        navigationDrawerModel = new NavigationDrawerModel();
        webBrowserBack = false;
        uploadedPicture = false;
        dataList = new ArrayList<>();
        mTitle = mDrawerTitle = getTitle();

        View header = getLayoutInflater().inflate(R.layout.custom_header, null);
        TextView headerFirstName = (TextView) header.findViewById(R.id.firstName);
        headerFirstName.setText(session.getValueOfFirstName()+ " " + session.getValueOfLastName());
        final ImageView profilePic = (ImageView) header.findViewById(R.id.imageView);
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePictureFragment fragment = new ChangePictureFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();

                if (navigationDrawerModel.getNativeFragment() != null) {
                    getFragmentManager().beginTransaction().
                            remove(getFragmentManager().findFragmentById(R.id.content_frame)).commit();
                    getSupportFragmentManager().beginTransaction()
                            .remove(getSupportFragmentManager().findFragmentById(R.id.content_frame)).commit();
                    navigationDrawerModel.setNativeFragment(null);
                }
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment).commit();

                setTitle("Change picture");
                mDrawerLayout.closeDrawer(mDrawerList);
                uploadedPicture = true;

            }
        });

       Picasso.with(getApplicationContext()).load(ServerUrl.BASE_URL + session.getValueOfProfileImage()).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(profilePic);

        mDrawerList.addHeaderView(header);

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);

        dataList = navigationDrawerModel.getMenu();

        adapter = new NavigationDrawerAdapter(this, R.layout.custom_drawer_item,
                dataList);

        mDrawerList.setAdapter(adapter);

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open,
                R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                adapter.notifyDataSetChanged();
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                if (uploadedPicture) {
                    Picasso.with(getApplicationContext()).load(ServerUrl.BASE_URL + session.getValueOfProfileImage()).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(profilePic);
                    uploadedPicture = false;
                }
                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
                SelectItem(1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_entirely_new_drawer, menu);
        return true;
    }

    void SelectItem(int position) {

        Fragment fragment = null;
        boolean useNativeFragment = false;
        Bundle args = new Bundle();
        int positionInDataSet = position - 1;
        switch (position) {

            case 1:
                fragment = new HomeFragment();
                args.putString(HomeFragment.ITEM_NAME, dataList.get(positionInDataSet)
                        .getItemName());
                args.putInt(HomeFragment.IMAGE_RESOURCE_ID, dataList
                        .get(positionInDataSet).getImgResID());
                break;
            case 2:
                fragment = new EmergencyFragment();
                args.putString(EmergencyFragment.ITEM_NAME, dataList.get(positionInDataSet)
                        .getItemName());
                args.putInt(EmergencyFragment.IMAGE_RESOURCE_ID, dataList.get(positionInDataSet)
                        .getImgResID());
                break;
            case 4:
                fragment = new ESNPWWebsiteFragment();
                args.putString(ESNPWWebsiteFragment.ITEM_NAME, dataList.get(positionInDataSet)
                        .getItemName());
                args.putInt(ESNPWWebsiteFragment.IMAGE_RESOURCE_ID, dataList.get(positionInDataSet)
                        .getImgResID());
                break;
            case 5:
                fragment = new BuddyFragment();
                args.putString(BuddyFragment.ITEM_NAME, dataList.get(positionInDataSet)
                        .getItemName());
                args.putInt(BuddyFragment.IMAGE_RESOURCE_ID, dataList.get(positionInDataSet)
                        .getImgResID());
                break;
            case 6:
                fragment = new DocumentsFragment();
                args.putString(DocumentsFragment.ITEM_NAME, dataList.get(positionInDataSet)
                        .getItemName());
                args.putInt(DocumentsFragment.IMAGE_RESOURCE_ID, dataList
                        .get(positionInDataSet).getImgResID());
                break;
            case 7:
                fragment = new TransportationFragment();
                args.putString(TransportationFragment.ITEM_NAME, dataList.get(positionInDataSet)
                        .getItemName());
                args.putInt(TransportationFragment.IMAGE_RESOURCE_ID, dataList.get(positionInDataSet)
                        .getImgResID());
                break;
            case 8:
                fragment = new RentAHouseFragment();
                args.putString(RentAHouseFragment.ITEM_NAME, dataList.get(positionInDataSet)
                        .getItemName());
                args.putInt(RentAHouseFragment.IMAGE_RESOURCE_ID, dataList.get(positionInDataSet)
                        .getImgResID());
                break;
            case 9:
                fragment = new ToDoFragment();
                args.putString(ToDoFragment.ITEM_NAME, dataList.get(positionInDataSet)
                        .getItemName());
                args.putInt(ToDoFragment.IMAGE_RESOURCE_ID, dataList.get(positionInDataSet)
                        .getImgResID());
                break;
            case 10:
                fragment = new QAFragment();
                args.putString(QAFragment.ITEM_NAME, dataList.get(positionInDataSet)
                        .getItemName());
                args.putInt(QAFragment.IMAGE_RESOURCE_ID, dataList
                        .get(positionInDataSet).getImgResID());
                break;
            case 11:
                fragment = new UsefulAppsFragment();
                args.putString(UsefulAppsFragment.ITEM_NAME, dataList.get(positionInDataSet)
                        .getItemName());
                args.putInt(UsefulAppsFragment.IMAGE_RESOURCE_ID, dataList.get(positionInDataSet)
                        .getImgResID());
                break;
            case 13:
                fragment = new FacultyFragment();
                args.putString(FacultyFragment.ITEM_NAME, dataList.get(positionInDataSet)
                        .getItemName());
                args.putInt(FacultyFragment.IMAGE_RESOURCE_ID, dataList.get(positionInDataSet)
                        .getImgResID());
                break;
            case 14:
                fragment = new ESNOfficeFragment();
                args.putString(ESNOfficeFragment.ITEM_NAME, dataList.get(positionInDataSet)
                        .getItemName());
                args.putInt(ESNOfficeFragment.IMAGE_RESOURCE_ID, dataList.get(positionInDataSet)
                        .getImgResID());
                break;
            case 15:
                fragment = new CampusMap();
                args.putString(CampusMap.ITEM_NAME, dataList.get(positionInDataSet)
                        .getItemName());
                args.putInt(CampusMap.IMAGE_RESOURCE_ID, dataList
                        .get(positionInDataSet).getImgResID());
                break;
            case 16:
                fragment = new PWWebsiteFragment();
                args.putString(PWWebsiteFragment.ITEM_NAME, dataList.get(positionInDataSet)
                        .getItemName());
                args.putInt(PWWebsiteFragment.IMAGE_RESOURCE_ID, dataList.get(positionInDataSet)
                        .getImgResID());
                break;

            case 17:
                fragment = new FindPWFragment();
                args.putString(FindPWFragment.ITEM_NAME, dataList.get(positionInDataSet)
                        .getItemName());
                args.putInt(FindPWFragment.IMAGE_RESOURCE_ID, dataList.get(positionInDataSet)
                        .getImgResID());
                break;
            case 19:
                fragment = new OffersFragment();
                args.putString(OffersFragment.ITEM_NAME, dataList.get(positionInDataSet)
                        .getItemName());
                args.putInt(OffersFragment.IMAGE_RESOURCE_ID, dataList.get(positionInDataSet)
                        .getImgResID());
                break;
            case 20:
                fragment = new ESNEventsFragment();
                args.putString(ESNEventsFragment.ITEM_NAME, dataList.get(positionInDataSet)
                        .getItemName());
                args.putInt(ESNEventsFragment.IMAGE_RESOURCE_ID, dataList.get(positionInDataSet)
                        .getImgResID());
                break;
            case 21:
                fragment = new FragmentFunMap();
                args.putString(FragmentFunMap.ITEM_NAME, dataList.get(positionInDataSet)
                        .getItemName());
                args.putInt(FragmentFunMap.IMAGE_RESOURCE_ID, dataList.get(positionInDataSet)
                        .getImgResID());
                break;
            case 22:
                fragment = new GetInTouchFragment();
                args.putString(GetInTouchFragment.ITEM_NAME, dataList.get(positionInDataSet)
                        .getItemName());
                args.putInt(GetInTouchFragment.IMAGE_RESOURCE_ID, dataList.get(positionInDataSet)
                        .getImgResID());
                break;
            case 24:
                useNativeFragment = true;
                nativeFragment = new SettingsFragment();
                args.putString(SettingsFragment.ITEM_NAME, dataList.get(positionInDataSet)
                        .getItemName());
                args.putInt(SettingsFragment.IMAGE_RESOURCE_ID, dataList.get(positionInDataSet)
                        .getImgResID());
                break;
            case 25:
                fragment = new HelpFragment();
                args.putString(HelpFragment.ITEM_NAME, dataList.get(positionInDataSet)
                        .getItemName());
                args.putInt(HelpFragment.IMAGE_RESOURCE_ID, dataList.get(positionInDataSet)
                        .getImgResID());
                break;
            default:
                break;
        }

        webBrowserBack = position == 10;

        if (useNativeFragment) {
            android.app.FragmentManager fragmentManager = getFragmentManager();

            if (fragmentManager.findFragmentById(R.id.content_frame) == null) {
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, nativeFragment).commit();
            }
        } else {
            if (nativeFragment != null) {
                getFragmentManager().beginTransaction().
                        remove(getFragmentManager().findFragmentById(R.id.content_frame)).commit();
                getSupportFragmentManager().beginTransaction()
                        .remove(getSupportFragmentManager().findFragmentById(R.id.content_frame)).commit();
                nativeFragment = null;
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment).commit();
        }
        mDrawerList.setItemChecked(position, true);
        setTitle(dataList.get(positionInDataSet).getItemName());
        mDrawerLayout.closeDrawer(mDrawerList);
    }


    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();

        switch (id) {
            case R.id.action_logout:
                //session.destroySession();
                moveToBackAndFinish();
                return true;
            case R.id.action_settings:
                SelectItem(24);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            if (position > 0) {
                if (dataList.get(position - 1).getTitle() == null) {
                    SelectItem(position);
                }
            }
        }
    }

    void moveToBackAndFinish() {
        UpdateToDosOnServerTask task = new UpdateToDosOnServerTask(getApplicationContext(), session);
        task.execute();
        moveTaskToBack(true);
        NavigationDrawer.this.finish();
    }

    public void onBackPressed() {

        StudentProfileFragment myFragment = (StudentProfileFragment)getSupportFragmentManager().findFragmentByTag("STUDENT_PROFILE");
        if (myFragment != null) {
            FragmentManager fm = getSupportFragmentManager();
            fm.popBackStack();
        } else {
            moveToBackAndFinish();
        }
    }





}