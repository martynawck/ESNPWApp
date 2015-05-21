package com.wiacek.martyna.esnpwapp.Interface;

import com.wiacek.martyna.esnpwapp.Domain.ESNPartner;
import com.wiacek.martyna.esnpwapp.Domain.FunMapCategory;
import com.wiacek.martyna.esnpwapp.Domain.FunMapPlace;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by Martyna on 2015-05-21.
 */
public interface OnOffersDownload {
    void onTaskCompleted( List<String> expandableListTitle, TreeMap<Integer, List<ESNPartner>> expandableListDetail);
}