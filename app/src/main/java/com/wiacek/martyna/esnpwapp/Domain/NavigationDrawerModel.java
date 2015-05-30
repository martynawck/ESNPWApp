package com.wiacek.martyna.esnpwapp.Domain;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.wiacek.martyna.esnpwapp.Fragment.BuddyFragment;
import com.wiacek.martyna.esnpwapp.Fragment.CampusMap;
import com.wiacek.martyna.esnpwapp.Fragment.DocumentsFragment;
import com.wiacek.martyna.esnpwapp.Fragment.ESNEventsFragment;
import com.wiacek.martyna.esnpwapp.Fragment.ESNOfficeFragment;
import com.wiacek.martyna.esnpwapp.Fragment.ESNPWWebsiteFragment;
import com.wiacek.martyna.esnpwapp.Fragment.EmergencyFragment;
import com.wiacek.martyna.esnpwapp.Fragment.FacultyFragment;
import com.wiacek.martyna.esnpwapp.Fragment.FindPWFragment;
import com.wiacek.martyna.esnpwapp.Fragment.FragmentFunMap;
import com.wiacek.martyna.esnpwapp.Fragment.GetInTouchFragment;
import com.wiacek.martyna.esnpwapp.Fragment.HelpFragment;
import com.wiacek.martyna.esnpwapp.Fragment.HomeFragment;
import com.wiacek.martyna.esnpwapp.Fragment.OffersFragment;
import com.wiacek.martyna.esnpwapp.Fragment.PWWebsiteFragment;
import com.wiacek.martyna.esnpwapp.Fragment.QAFragment;
import com.wiacek.martyna.esnpwapp.Fragment.RentAHouseFragment;
import com.wiacek.martyna.esnpwapp.Fragment.SettingsFragment;
import com.wiacek.martyna.esnpwapp.Fragment.ToDoFragment;
import com.wiacek.martyna.esnpwapp.Fragment.TransportationFragment;
import com.wiacek.martyna.esnpwapp.Fragment.UsefulAppsFragment;
import com.wiacek.martyna.esnpwapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martyna on 2015-05-21.
 */
public class NavigationDrawerModel {

    private List<DrawerItem> dataList;
    private android.app.Fragment nativeFragment;
    private Fragment fragment;
    private boolean useNativeFragment;
    private Bundle args;

    public NavigationDrawerModel() {
        useNativeFragment = false;
        nativeFragment = null;
    }

    public android.app.Fragment getNativeFragment() {
        return nativeFragment;
    }

    public  void setNativeFragment(android.app.Fragment fragment) {
        nativeFragment = fragment;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public boolean getUseNativeFragment() {
        return useNativeFragment;
    }

    public Bundle getArgs() {
        return args;
    }

    public List<DrawerItem> getMenu() {
        dataList = new ArrayList<>();

        dataList.add(new DrawerItem("HOME", R.drawable.ic_home));
        dataList.add(new DrawerItem("EMERGENCY", R.drawable.ic_emergency));

        dataList.add(new DrawerItem("YOUR STAY"));
        dataList.add(new DrawerItem("ESN PW website", R.drawable.ic_web));
        dataList.add(new DrawerItem("Your buddy", R.drawable.ic_buddy));
        dataList.add(new DrawerItem("Documents", R.drawable.ic_documents));
        dataList.add(new DrawerItem("Transportation", R.drawable.ic_transport));
        dataList.add(new DrawerItem("Rent a house", R.drawable.ic_rent));
        dataList.add(new DrawerItem("ToDos", R.drawable.ic_todos));
        dataList.add(new DrawerItem("FAQ", R.drawable.ic_faq));
        dataList.add(new DrawerItem("Useful apps", R.drawable.ic_apps));

        dataList.add(new DrawerItem("UNIVERSITY"));// adding a header to the list
        dataList.add(new DrawerItem("Your faculty", R.drawable.ic_faculty));
        dataList.add(new DrawerItem("ESN office", R.drawable.ic_office));
        dataList.add(new DrawerItem("Campus map", R.drawable.ic_campus));
        dataList.add(new DrawerItem("PW website", R.drawable.ic_network));
        dataList.add(new DrawerItem("PW online", R.drawable.ic_online));

        dataList.add(new DrawerItem("ENTERTAINMENT")); // adding a header to the list
        dataList.add(new DrawerItem("ESN offers", R.drawable.ic_partners));
        dataList.add(new DrawerItem("ESN events", R.drawable.ic_events));
        dataList.add(new DrawerItem("ESN FunMap", R.drawable.ic_fun));
        dataList.add(new DrawerItem("Get in touch", R.drawable.ic_intouch));

        dataList.add(new DrawerItem("OTHERS")); // adding a header to the list
        dataList.add(new DrawerItem("Settings", R.drawable.settings));
        dataList.add(new DrawerItem("Help", R.drawable.ic_help));

        return dataList;
    }

    public void getProperFragment( int position) {

        nativeFragment = null;
        fragment = null;
      //  useNativeFragment = false;
        args = new Bundle();
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

    }

}
