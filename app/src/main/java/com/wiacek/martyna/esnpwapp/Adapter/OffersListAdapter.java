package com.wiacek.martyna.esnpwapp.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wiacek.martyna.esnpwapp.Domain.ESNPartner;
import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
import com.wiacek.martyna.esnpwapp.R;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class OffersListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private TreeMap<Integer, List<ESNPartner>> expandableListDetail;

    public OffersListAdapter(Context context, List<String> expandableListTitle,
                             TreeMap<Integer, List<ESNPartner>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(listPosition)
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final ESNPartner expandedListPartner = (ESNPartner) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.listview_offers_row, null);
        }

        TextView discount = (TextView) convertView
                .findViewById(R.id.offer_type_content);
        discount.setText(expandedListPartner.getDiscount());

        TextView howToUse = (TextView) convertView
                .findViewById(R.id.how_to_use_content);
        howToUse.setText(expandedListPartner.getHowToUse());

        TextView description = (TextView) convertView
                .findViewById(R.id.description_content);
        description.setText(expandedListPartner.getDescription());

        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
      //  return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
        //        .size();
        return 1;
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.listview_offers_group, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.name);
        Log.d("LISTTITLE",listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        Picasso.with(context).load(ServerUrl.BASE_URL + expandableListDetail.get(listPosition).get(0).getImage()).into(image);
      //  new DownloadImageTask((ImageView) convertView.findViewById(R.id.image))
        //        .execute(ServerUrl.BASE_URL + expandableListDetail.get(Integer.toString(listPosition)).get(0).getImage());

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}