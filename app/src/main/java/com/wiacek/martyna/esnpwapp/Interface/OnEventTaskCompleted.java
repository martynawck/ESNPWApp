package com.wiacek.martyna.esnpwapp.Interface;

import com.wiacek.martyna.esnpwapp.Domain.Event;

import java.util.ArrayList;

/**
 * Created by Martyna on 2015-05-20.
 */
public interface OnEventTaskCompleted {
    void onTaskCompleted(ArrayList<Event> strings);
}