package com.wiacek.martyna.esnpwapp.Interface;

import com.wiacek.martyna.esnpwapp.Domain.Event;
import com.wiacek.martyna.esnpwapp.Domain.FunMapCategory;
import com.wiacek.martyna.esnpwapp.Domain.FunMapPlace;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by Martyna on 2015-05-21.
 */
public interface OnFunMapCategory {
    void onTaskCompleted(  TreeMap<FunMapCategory, ArrayList<FunMapPlace>> places);
}