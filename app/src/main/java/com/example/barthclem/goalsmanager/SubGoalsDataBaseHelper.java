package com.example.barthclem.goalsmanager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by barthclem on 5/17/16.
 */
public class SubGoalsDataBaseHelper extends SQLiteOpenHelper{
    //MAIN GOALS SECTION
    public static  final String COLUMN_ID="_id";
    public static  final String COLUMN_RATING="rating";
    public static  final String COLUMN_MAINGOALID="mainGoalId";
    public static  final String COLUMN_DURATION="duration";
    public static  final String COLUMN_DUEDATE="dueDate";
    public static final String COLUMN_TITLE="title";
    public static final String COLUMN_STATUS="status";

    private SQLiteDatabase db;
    static final String DATABASE_NAME = "SubGoalsDataBase";
    static final String GOALS_TABLE_NAME = "subGoalTable";
    static final int DATABASE_VERSION = 1;
    static final String CREATE_DB_TABLE =
            " CREATE TABLE " + GOALS_TABLE_NAME +
                    " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " title TEXT NOT NULL, " +
                    "mainGoalId NUMBER, " +
                    "rating NUMBER, " +
                    "duration NUMBER,"+
                    "dueDate TEXT,"+
                    "status TEXT NOT NULL);";

    //SUB GOALS SECTION


    SubGoalsDataBaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(CREATE_DB_TABLE);

//                ContentValues values=new ContentValues();
//                values.put(COLUMN_TITLE,"First guy");
//                values.put(COLUMN_DURATION,78);
//                values.put(COLUMN_STATUS,"alive");
//                db.insert(GOALS_TABLE_NAME,null,values);

        }
        public Cursor performanceIndex(){

            SQLiteDatabase db=this.getReadableDatabase();
            Cursor cursor=db.rawQuery("select * from "+GOALS_TABLE_NAME,null);
            return cursor;
        }//close
        public Cursor performanceIndex(int id){

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from "+GOALS_TABLE_NAME+" where "+COLUMN_MAINGOALID+" =? ",new String[]{String.valueOf(id)});
        return cursor;
    }//close
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + GOALS_TABLE_NAME);
            onCreate(db);
        }
    }
