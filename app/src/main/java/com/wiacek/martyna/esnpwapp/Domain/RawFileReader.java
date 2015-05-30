package com.wiacek.martyna.esnpwapp.Domain;

import android.content.Context;

import com.wiacek.martyna.esnpwapp.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Martyna on 2015-04-28.
 */
public class RawFileReader {

    private final Context mContext;

    public RawFileReader(Context context) {
        mContext = context;
    }


    public StringBuffer DocumentsRawResources() {

        StringBuffer s = new StringBuffer();
        try {
            s = RawResources(R.raw.esn_documents_data);
        } catch (Exception e) {
        }
        return s;
    }

    public StringBuffer RentHouseRawResources() {

        StringBuffer s = new StringBuffer();
        try {
            s = RawResources(R.raw.esn_rent_house_data);
        } catch (Exception e) {
        }
        return s;
    }

    public StringBuffer TransportationRawResources() {

        StringBuffer s = new StringBuffer();
        try {
            s = RawResources(R.raw.esn_transportation_data);
        } catch (Exception e) {
        }
        return s;
    }

    public StringBuffer ESNOfficeRawResources() {

        StringBuffer s = new StringBuffer();
        try {
            s = RawResources(R.raw.esn_office_hours_data);
        } catch (Exception e) {
        }
        return s;
    }

    StringBuffer RawResources(int resources) throws IOException {
        String str;
        StringBuffer buf = new StringBuffer();
        InputStream is = mContext.getResources().openRawResource(resources);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        while ((str = reader.readLine()) != null) {
            buf.append(str).append("<br/><br/>");
        }
        is.close();

        return buf;
    }
}
