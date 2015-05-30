package com.wiacek.martyna.esnpwapp.SQLHelpers;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.wiacek.martyna.esnpwapp.Domain.Buddy;
import com.wiacek.martyna.esnpwapp.Domain.TodoTask;

import java.util.ArrayList;

/**
 * Created by Martyna on 2015-04-13.
 */
public class ESNPWSQLHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "com.wiacek.martyna.esnpw.database";


    private static final String TABLE_BUDDY = "BUDDY";
    private static final String TABLE_TODOS = "TODOS";

    // common column names
    private static final String KEY_ID = "id";
    private static final String KEY_USER_ID = "user_id";

    // BUDDY column names
    private static final String KEY_BUDDY_FIRST_NAME = "first_name";
    private static final String KEY_BUDDY_LAST_NAME = "last_name";
    private static final String KEY_BUDDY_EMAIL = "email";
    private static final String KEY_BUDDY_PHONE = "phone";
    private static final String KEY_BUDDY_WHATSAPP = "whatsapp";
    private static final String KEY_BUDDY_SKYPE = "skype";
    private static final String KEY_BUDDY_FACEBOOK = "facebook";

    // TODOS column names
    private static final String KEY_TODO_ID = "todo_id";
    private static final String KEY_TODO_NAME = "todo_name";
    private static final String KEY_TODO_TYPE = "todo_type";
    private static final String KEY_TODO_VALUE = "todo_value";


    private static final String create_Buddy_sql = "CREATE TABLE IF NOT EXISTS " + TABLE_BUDDY + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_USER_ID + " INTEGER,"
            + KEY_BUDDY_FIRST_NAME + " TEXT,"
            + KEY_BUDDY_LAST_NAME + " TEXT,"
            + KEY_BUDDY_EMAIL + " TEXT,"
            + KEY_BUDDY_PHONE + " TEXT,"
            + KEY_BUDDY_WHATSAPP + " TEXT,"
            + KEY_BUDDY_SKYPE + " TEXT,"
            + KEY_BUDDY_FACEBOOK + " TEXT )";

    private static final String create_Todos_sql = "CREATE TABLE IF NOT EXISTS " + TABLE_TODOS+ " ( "
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_USER_ID + " INTEGER,"
            + KEY_TODO_ID + " INTEGER,"
            + KEY_TODO_NAME + " TEXT,"
            + KEY_TODO_TYPE + " INTEGER,"
            + KEY_TODO_VALUE + " INTEGER )";

    public ESNPWSQLHelper(Context context) {
        //1 is todo list database version
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_Buddy_sql);
        db.execSQL(create_Todos_sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqlDB, int i, int i2) {
        sqlDB.execSQL("DROP TABLE IF EXISTS " + TABLE_BUDDY);
        sqlDB.execSQL("DROP TABLE IF EXISTS " + TABLE_TODOS);
        onCreate(sqlDB);
    }

    public void deleteBuddyTable() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_BUDDY);
    }

    public void deleteTodosTable() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_TODOS);
    }


    public void insertBuddy(int userId, Buddy buddy) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        Integer userIdInt = userId;
        values.put(KEY_USER_ID, userId);
        values.put(KEY_BUDDY_FIRST_NAME, buddy.getFirstname());
        values.put(KEY_BUDDY_LAST_NAME, buddy.getLastname());
        values.put(KEY_BUDDY_EMAIL, buddy.getEmail());
        values.put(KEY_BUDDY_PHONE, buddy.getPhone());
        values.put(KEY_BUDDY_WHATSAPP, buddy.getWhatsapp());
        values.put(KEY_BUDDY_SKYPE, buddy.getSkype());
        values.put(KEY_BUDDY_FACEBOOK, buddy.getFacebook());

        if (
            database.update( TABLE_BUDDY, values, KEY_USER_ID + " = ? ",
                new String[] { userIdInt.toString() } ) == 0)

            database.insertWithOnConflict(TABLE_BUDDY, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        database.close();
    }

    public void insertToDo(int userId, TodoTask todo) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        Integer userIdInt = userId;
        values.put(KEY_USER_ID, userId);
        values.put(KEY_TODO_ID, todo.getTodo_id());
        values.put(KEY_TODO_NAME, todo.getDescription());
        values.put(KEY_TODO_TYPE, todo.getType());
        values.put(KEY_TODO_VALUE, todo.getValue());

        if (
            database.update( TABLE_TODOS, values, KEY_USER_ID + " = ? AND " + KEY_TODO_ID + " = ?",
                new String[] { userIdInt.toString(), Integer.toString(todo.getTodo_id()) } ) == 0)

            database.insertWithOnConflict(TABLE_TODOS, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        database.close();
    }

    public boolean doesBuddyTableExist() {

        return doesTableExist(TABLE_BUDDY);
    }

    public boolean doesTodoTableExist() {

        return doesTableExist(TABLE_TODOS);
    }

    private boolean doesTableExist(String table_name) {

        SQLiteDatabase mDatabase = this.getWritableDatabase();
        Cursor cursor = mDatabase.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+ table_name +"'", null);
        if(cursor!=null) {
            if(cursor.getCount()>0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }



    public int updateToDo(int todoId, int userId, int todoValue) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        Integer todoIdInt = todoId;
        Integer userIdInt = userId;
        values.put(KEY_TODO_VALUE, todoValue);
        return database.update(TABLE_TODOS, values, KEY_TODO_ID + " = ? AND " + KEY_USER_ID + " = ?", new String[] { todoIdInt.toString(), userIdInt.toString()  });
    }

    public ArrayList<TodoTask> getTodosAfter(String s) {

        return getTodos("0", s);
    }

    public ArrayList<TodoTask> getTodosBefore(String s) {

        return  getTodos("1", s);
    }


    public Buddy getBuddyInfo(String id) {


        String selectQuery = "SELECT * FROM "+ TABLE_BUDDY + " where " + KEY_USER_ID + " = '" + id + "'";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        Buddy buddy = new Buddy();

        if (cursor.moveToFirst()) {
            do {
                buddy = new Buddy();
                buddy.setFirstname(cursor.getString(2));
                buddy.setLastname(cursor.getString(3));
                buddy.setEmail(cursor.getString(4));
                buddy.setPhone(cursor.getString(5));
                buddy.setWhatsapp(cursor.getString(6));
                buddy.setSkype(cursor.getString(7));
                buddy.setFacebook(cursor.getString(8));
            } while (cursor.moveToNext());
        }
        return buddy;

    }

    private ArrayList<TodoTask> getTodos(String value, String id) {
        ArrayList<TodoTask> taskList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_TODOS + " where " + KEY_TODO_TYPE + " = '" + value+"' AND " + KEY_USER_ID+ " = '" + id + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        TodoTask task;
        if (cursor.moveToFirst()) {
            do {
                task = new TodoTask(cursor.getInt(2), cursor.getString(3),
                        cursor.getInt(5), cursor.getInt(4)) ;
                taskList.add(task);
            } while (cursor.moveToNext());
        }
        return taskList;
    }


}
