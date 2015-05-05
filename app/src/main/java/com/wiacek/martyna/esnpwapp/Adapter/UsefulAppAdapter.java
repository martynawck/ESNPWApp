package com.wiacek.martyna.esnpwapp.Adapter;

import android.widget.AbsListView;
import android.widget.BaseAdapter;

/**
 * Created by Martyna on 2015-04-30.
 */
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.wiacek.martyna.esnpwapp.R;

public class UsefulAppAdapter extends BaseAdapter {
    private Context mContext;

    // Keep all Images in array
    public Integer[] mThumbIds = {
            R.drawable.jakdojade, R.drawable.uber, R.drawable.itaxi,
            R.drawable.gdzieturilo, R.drawable.moovit, R.drawable.mobilet,
            R.drawable.sciezka_rowerowa, R.drawable.pitu_pitu, R.drawable.polish_with_babel,
            R.drawable.alko_mapa, R.drawable.travel_poland, R.drawable.pw_wifi,
            R.drawable.kantor, R.drawable.pocket_guide, R.drawable.warsaw_travel_guide,
    };

    // Constructor
    public UsefulAppAdapter(Context c){
        mContext = c;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(mThumbIds[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(120,120));
        return imageView;
    }

}