package com.wiacek.martyna.esnpwapp.Interface;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Martyna on 2015-05-21.
 */
public interface OnQADownload {
    void onTaskCompleted(List<String> titles, HashMap<String, List<String>> expandableListDetail);
}