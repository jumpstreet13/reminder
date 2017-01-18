package com.example.somethingstrange;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Абакар on 12/22/2016.
 */
public class MyBase {

    private static final String DB_NAME = "Tasks";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE = "tasks";
    private static final String DB_TABLE_TWO = "checked";
    private static final String DB_TABLE_THREE = "colors";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_TASKS = "task";
    public static final String IS_CHECKED = "checked";
    public static final String IS_GREEN = "is_green";


    private static final String DB_CREATE =
            "create table " + DB_TABLE + "(" + COLUMN_ID + " integer primary key autoincrement, " +
             COLUMN_TITLE + " string, " +
             COLUMN_TASKS + " string" + ");";

    private static final String DB_CREATE_CHECK =
            "create table " + DB_TABLE_TWO + "(" + COLUMN_ID + " integer primary key autoincrement , " +
                    COLUMN_TITLE + " string, " +  COLUMN_TASKS  + " string, " +
                    IS_CHECKED + " string" + ");";

    private static final String DB_CREATE_COLORS =
            "create table " + DB_TABLE_THREE + "(" + COLUMN_ID + " integer primary key autoincrement , " +
                    COLUMN_TITLE + " string, " +  IS_GREEN  + " string" + ");";



    private final Context mCtx;

    private  DBHelper mDBHelper;
    private  SQLiteDatabase mDB;

    public MyBase(Context ctx){
        mCtx = ctx;
    }

    // открыть подключение
    public void open() {
        mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }

    // закрыть подключение
    public void close() {
        if (mDBHelper!=null) mDBHelper.close();
    }

    // получить все данные из таблицы DB_TABLE
    public Cursor getAllData() {
        return mDB.query(DB_TABLE, null, null, null, null, null, null);
    }

    public Cursor getNameData(String ss){
        return mDB.query(DB_TABLE, new String[]{COLUMN_TITLE,COLUMN_TASKS}, "title=?", new String[]{ss}, null, null, null);
    }

    public Cursor getTitles(){
        return mDB.query(DB_TABLE, new String[]{COLUMN_TITLE},null,null,null,null,null);
    }

    public Cursor getCheked(String ss){
        return mDB.query(DB_TABLE_TWO, new String[]{COLUMN_TITLE, COLUMN_TASKS, IS_CHECKED},"title=?",new String[]{ss},null,null,null);
    }

    public Cursor getColors(String ss){
        return mDB.query(DB_TABLE_THREE, new String[]{COLUMN_TITLE,IS_GREEN},"title=?",new String[]{ss},null,null,null);
    }

   /* public Cursor getChekedNumber(String ss){
        return
    }*/

    public Cursor getAllCheked(){
        return mDB.query(DB_TABLE_TWO, new String[]{COLUMN_TITLE, COLUMN_TASKS, IS_CHECKED},null,null,null,null,null);
    }

    // добавить запись в DB_TABLE
    public void addRec(String title, String tasks) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_TASKS, tasks);
        mDB.insert(DB_TABLE, null, cv);
    }

    public void addUpdateCheck(String title, String task, String cheked){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_TASKS, task);
        cv.put(IS_CHECKED, cheked);
        mDB.update(DB_TABLE_TWO, cv, "task=?", new String[]{task});
    }

    public void addDefaultCheck( String title, String task, String cheked){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_TASKS, task);
        cv.put(IS_CHECKED, cheked);
        mDB.insert(DB_TABLE_TWO, null, cv);
    }

    public void addColors(String title, String is_green){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(IS_GREEN, is_green);
        mDB.insert(DB_TABLE_THREE, null, cv);
    }

    public void changeColors(String title, String is_green){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(IS_GREEN, is_green);
        mDB.update(DB_TABLE_THREE,cv, "title=?", new String[]{title});
    }



    // удалить запись из DB_TABLE
    public void delRec(long id) {
        mDB.delete(DB_TABLE, COLUMN_ID + " = " + id, null);
    }




    private class DBHelper extends SQLiteOpenHelper{

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                        int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);
            db.execSQL(DB_CREATE_CHECK);
            db.execSQL(DB_CREATE_COLORS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
