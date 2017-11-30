package com.example.barthclem.goalsmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by barthclem on 5/8/16.
 */
public class GoalsDataBaseManager extends SQLiteOpenHelper {


    private static final String DB_NAME="GoalsManager";
    private static final int DB_VERSION=1;
    //parameters
    private static  final String ID="id";
    public static final String REMINDER_TABLE="reminder_table";
    private static final String TIME="time";
    private static final String HOUR="hour";
    private static final String MINUTE="minute";
    private static final String REMINDERSTATUS="status";
    public static final String MORNING="MORNING";
    public static final String EVENING="EVENING";
    public static final String STATUSOFF="off";
    public static final String STATUSON="on";





    public GoalsDataBaseManager(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //we create a table of userDetails in the database by using the following query
        db.execSQL("create table USERDETAILS (id INTEGER, username TEXT,password TEXT, email TEXT, securityQuestion TEXT,securityAnswer TEXT)");


        //we create a table of goals in the database


        //startReminder();

    }

    public boolean insertRegister(int i,String username,String password,String email,String securityQuestion,String securityAnswer){
        //the table of this insert is userdetails
        //this use to insert data into the user
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues userdetails=new ContentValues();
        userdetails.put("id",i);
        userdetails.put("username",username);
        userdetails.put("password",password);
        userdetails.put("email",email);
        userdetails.put("securityQuestion",securityQuestion);
        userdetails.put("securityAnswer",securityAnswer);
        //insert into database

        long result=db.insert("USERDETAILS",null,userdetails);

        return result==-1?false:true;

    }//close

    public boolean updateField(String Field,String value){
        //this handle the user details database
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues update=new ContentValues();
        update.put(Field,value);
        long result=db.update("USERDETAILS",update,null,null);

        return result==-1?false:true;
    }//close

    public Cursor getName(String TABLE_NAME){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from "+TABLE_NAME,null);

        return cursor;
    }//close



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}