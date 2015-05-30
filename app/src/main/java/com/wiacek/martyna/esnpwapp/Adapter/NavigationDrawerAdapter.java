package com.wiacek.martyna.esnpwapp.Adapter;

/**
 * Created by Martyna on 2015-04-04.
 */
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.wiacek.martyna.esnpwapp.Domain.DrawerItem;
import com.wiacek.martyna.esnpwapp.R;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class NavigationDrawerAdapter extends ArrayAdapter<DrawerItem> {

    private final Context context;
    private final List<DrawerItem> drawerItemList;
    private final int layoutResID;

    public NavigationDrawerAdapter(Context context, int layoutResourceID,
                                   List<DrawerItem> listItems) {
        super(context, layoutResourceID, listItems);
        this.context = context;
        this.drawerItemList = listItems;
        this.layoutResID = layoutResourceID;
    }

    public boolean isEnabled(int position)
    {
        DrawerItem dItem = this.drawerItemList.get(position);
        return !dItem.isTitle();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DrawerItemHolder drawerHolder;
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(layoutResID, parent, false);
            drawerHolder = new DrawerItemHolder(view);
            view.setTag(drawerHolder);
        } else {
            drawerHolder = (DrawerItemHolder) view.getTag();
        }

        DrawerItem dItem = this.drawerItemList.get(position);

        if (dItem.getTitle() != null) {
            drawerHolder.headerLayout.setVisibility(LinearLayout.VISIBLE);
            drawerHolder.itemLayout.setVisibility(LinearLayout.INVISIBLE);
            drawerHolder.title.setText(dItem.getTitle());

        } else {

            drawerHolder.headerLayout.setVisibility(LinearLayout.INVISIBLE);
            drawerHolder.itemLayout.setVisibility(LinearLayout.VISIBLE);

            drawerHolder.icon.setImageDrawable(view.getResources().getDrawable(
                    dItem.getImgResID()));
            drawerHolder.ItemName.setText(dItem.getItemName());

        }
        return view;
    }
    static class DrawerItemHolder {
        @InjectView(R.id.drawer_itemName)  TextView ItemName;
        @InjectView(R.id.drawerTitle) TextView title;
        @InjectView(R.id.drawer_icon) ImageView icon;
        @InjectView(R.id.headerLayout) LinearLayout headerLayout;
        @InjectView(R.id.itemLayout) LinearLayout itemLayout;

        public DrawerItemHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }


}