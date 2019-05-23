package com.example.planzajec.planzajec.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.planzajec.planzajec.Model.Classes;
import com.example.planzajec.planzajec.Util.Constants;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private Context ctx;
    public DatabaseHandler(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CLASS_TABLE = "CREATE TABLE " + Constants.TABLE_NAME + "("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY," + Constants.KEY_NAME_ITEM + " TEXT,"
                + Constants.KEY_PLACE_ITEM + " TEXT," + Constants.KEY_TEACHER_ITEM + " TEXT,"
                + Constants.KEY_START_TIME + " TEXT," + Constants.KEY_END_TIME + " TEXT,"
                + Constants.KEY_CLASS_TYPE + " TEXT," + Constants.KEY_WEEKDAY + " TEXT);";

        db.execSQL(CREATE_CLASS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);

        onCreate(db);
    }

    /**
     * CRUD OPERATIONS
     */

    //Add Classes
    public void addClass(Classes cl){
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.KEY_NAME_ITEM, cl.getName());
        values.put(Constants.KEY_PLACE_ITEM, cl.getPlace());
        values.put(Constants.KEY_TEACHER_ITEM, cl.getTeacher());
        values.put(Constants.KEY_START_TIME, cl.getStartTime());
        values.put(Constants.KEY_END_TIME,cl.getEndTime());
        values.put(Constants.KEY_CLASS_TYPE,cl.getClassType());
        values.put(Constants.KEY_WEEKDAY,cl.getWeekDay());

        //Insert the row
        db.insert(Constants.TABLE_NAME,null,values);

        Log.d("Saved!", "Zapisane do bazy!");


    }

    //Get Classes
    private Classes getClasses(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(Constants.TABLE_NAME, new String[] {Constants.KEY_ID,Constants.KEY_NAME_ITEM, Constants.KEY_PLACE_ITEM, Constants.KEY_TEACHER_ITEM,
                Constants.KEY_START_TIME, Constants.KEY_END_TIME, Constants.KEY_CLASS_TYPE, Constants.KEY_WEEKDAY},
                Constants.KEY_ID + "=?",new String [] {String.valueOf(id)},null,null,null,null);

        if (cursor!=null)
            cursor.moveToFirst();

        Classes classes = new Classes();
        classes.setId(Integer.parseInt(cursor.getString((cursor.getColumnIndex(Constants.KEY_ID)))));
        classes.setName(cursor.getString(cursor.getColumnIndex(Constants.KEY_NAME_ITEM)));
        classes.setPlace(cursor.getString(cursor.getColumnIndex(Constants.KEY_PLACE_ITEM)));
        classes.setTeacher(cursor.getString(cursor.getColumnIndex(Constants.KEY_TEACHER_ITEM)));
        classes.setStartTime(cursor.getString(cursor.getColumnIndex(Constants.KEY_START_TIME)));
        classes.setEndTime(cursor.getString(cursor.getColumnIndex(Constants.KEY_END_TIME)));
        classes.setClassType(cursor.getString(cursor.getColumnIndex(Constants.KEY_CLASS_TYPE)));
        classes.setWeekDay(cursor.getString(cursor.getColumnIndex(Constants.KEY_WEEKDAY)));
        return classes;
    }

    //Get all Classes
    public List<Classes> getAllClasses(String day) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Classes> classesList = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE_NAME, new String[]{
           Constants.KEY_ID,Constants.KEY_NAME_ITEM, Constants.KEY_PLACE_ITEM, Constants.KEY_TEACHER_ITEM,
                Constants.KEY_START_TIME, Constants.KEY_END_TIME, Constants.KEY_CLASS_TYPE, Constants.KEY_WEEKDAY},
                Constants.KEY_WEEKDAY + "=?", new String[]{day}, null, null, Constants.KEY_START_TIME + " ASC");

        if(cursor.moveToFirst()){
            do {
                Classes classes = new Classes();
                classes.setId(Integer.parseInt(cursor.getString((cursor.getColumnIndex(Constants.KEY_ID)))));
                classes.setName(cursor.getString(cursor.getColumnIndex(Constants.KEY_NAME_ITEM)));
                classes.setPlace(cursor.getString(cursor.getColumnIndex(Constants.KEY_PLACE_ITEM)));
                classes.setTeacher(cursor.getString(cursor.getColumnIndex(Constants.KEY_TEACHER_ITEM)));
                classes.setStartTime(cursor.getString(cursor.getColumnIndex(Constants.KEY_START_TIME)));
                classes.setEndTime(cursor.getString(cursor.getColumnIndex(Constants.KEY_END_TIME)));
                classes.setClassType(cursor.getString(cursor.getColumnIndex(Constants.KEY_CLASS_TYPE)));
                classes.setWeekDay(cursor.getString(cursor.getColumnIndex(Constants.KEY_WEEKDAY)));

                classesList.add(classes);
            }while(cursor.moveToNext());
        }
        return classesList;
    }

    //Update Classes
    public int updateClasses(Classes cl) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.KEY_NAME_ITEM, cl.getName());
        values.put(Constants.KEY_PLACE_ITEM, cl.getPlace());
        values.put(Constants.KEY_TEACHER_ITEM, cl.getTeacher());
        values.put(Constants.KEY_START_TIME, cl.getStartTime());
        values.put(Constants.KEY_END_TIME,cl.getEndTime());
        values.put(Constants.KEY_CLASS_TYPE,cl.getClassType());
        values.put(Constants.KEY_WEEKDAY,cl.getWeekDay());

        //Update row
        return db.update(Constants.TABLE_NAME,values,Constants.KEY_ID + "=?", new String[] { String.valueOf(cl.getId())});
    }

    //Delete Classes
    public void deleteClasses(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME, Constants.KEY_ID + "=?",new String[] {String.valueOf(id)});

        db.close();
    }

    //Get count
    public int getClassesCount() {
        String countQuery = "SELECT * FROM " + Constants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery,null);
        return cursor.getCount();
    }


}
