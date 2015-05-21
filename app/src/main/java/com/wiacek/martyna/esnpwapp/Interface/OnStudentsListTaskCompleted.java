package com.wiacek.martyna.esnpwapp.Interface;

import com.wiacek.martyna.esnpwapp.Domain.Student;

import java.util.ArrayList;

/**
 * Created by Martyna on 2015-05-20.
 */
public interface OnStudentsListTaskCompleted {
    void onTaskCompleted(ArrayList<Student> strings);
}