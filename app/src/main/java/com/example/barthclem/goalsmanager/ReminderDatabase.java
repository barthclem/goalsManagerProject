package com.example.barthclem.goalsmanager;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ReminderDatabase  extends SQLiteOpenHelper {


        private static final String DB_NAME="ReminderDatabase";
        private static final int DB_VERSION=2;
        //parameters
        private static  final String ID="_id";
        public static final String REMINDER_TABLE="reminder_table";
        public  static final String TIME="time";
        public static final String HOUR="hour";
        public static final String MINUTE="minute";
        public static final String REMINDERSTATUS="status";
        public static final String MORNING="morning";
        public static final String EVENING="evening";
        public static final String STATUSOFF="off";
        public static final String STATUSON="on";





        public ReminderDatabase(Context context){
            super(context,DB_NAME,null,DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
             //we create a table of reminder in the database

            db.execSQL("CREATE TABLE "+REMINDER_TABLE+"("
                    +"_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    +TIME+" TEXT, " +
                    HOUR+" INTEGER, "+
                    MINUTE+" INTEGER, "+
                    REMINDERSTATUS+" TEXT "+
                    " );"
            );

        }


        public boolean intiatiateReminder(String time,String status,int hour,int min){
        //this handle the user details database
            SQLiteDatabase db=this.getWritableDatabase();
            ContentValues start=new ContentValues();
            start.put(TIME,time);
            start.put(HOUR,hour);
            start.put(MINUTE,min);
            start.put(REMINDERSTATUS,status);
             long result=db.insert(REMINDER_TABLE,null,start);


        return result==-1?false:true;
        }//close




        public boolean changeReminderTime(String time,String status,int hour,int min){
            //this handle the user details database
            SQLiteDatabase db=this.getWritableDatabase();
            ContentValues update=new ContentValues();
            update.put(HOUR,hour);
            update.put(MINUTE,min);
            update.put(REMINDERSTATUS,status);
            long result=db.update(REMINDER_TABLE,update,TIME+"=?",new String[]{time});


            return result==-1?false:true;
        }//close

        public Cursor selectTime(){
            SQLiteDatabase db=this.getReadableDatabase();
            Cursor cursor=db.rawQuery("select * from "+REMINDER_TABLE,null);
            return cursor;
        }//close



        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }


    }
