package com.example.barthclem.goalsmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by barthclem on 5/17/16.
 */
class MainGoalsDataBaseHelper extends SQLiteOpenHelper {
    //MAIN GOALS SECTION
    public static  final String COLUMN_ID="_id";
    public static  final String COLUMN_PRIORITY="priority";
    public static  final String COLUMN_SUBGOALCOUNT="subGoalCount";
    public static  final String COLUMN_DURATION="duration";
    public static  final String COLUMN_DUEDATE="dueDate";
    public static final String COLUMN_TITLE="title";
    public static final String COLUMN_STATUS="status";

    private SQLiteDatabase db;
    static final String DATABASE_NAME = "FocusMateDataBase";
    static final String GOALS_TABLE_NAME = "goal";
    static final int DATABASE_VERSION = 1;
    static final String CREATE_DB_TABLE =
            " CREATE TABLE " + GOALS_TABLE_NAME +
                    " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " title TEXT NOT NULL, " +
                    " priority NUMBER, " +
                    "subGoalCount REAL, " +
                    "duration NUMBER,"+
                    "dueDate NUMBER,"+
                    "status TEXT NOT NULL);";

    //SUB GOALS SECTION


    MainGoalsDataBaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(CREATE_DB_TABLE);

            ContentValues values=new ContentValues();
            values.put(COLUMN_TITLE,"First guy");
            values.put(COLUMN_DUEDATE,06-11-2016);
            values.put(COLUMN_PRIORITY,4);
            values.put(COLUMN_STATUS,"alive");
            db.insert(GOALS_TABLE_NAME,null,values);
            ContentValues value=new ContentValues();
            value.put(COLUMN_TITLE,"Prominent");
            values.put(COLUMN_DUEDATE,06-07-2016);
            values.put(COLUMN_PRIORITY,3);
            value.put(COLUMN_STATUS,"alive");
            db.insert(GOALS_TABLE_NAME,null,value);

        }
        Cursor performanceIndex(){

            SQLiteDatabase db=this.getReadableDatabase();
            return db.rawQuery("select * from "+GOALS_TABLE_NAME,null);
        }//close

       boolean updateGoal(String Field, double value, int mainGoalId){
        //this handle the user details database
           SQLiteDatabase db=this.getWritableDatabase();
           ContentValues update=new ContentValues();
           update.put(Field,value);

           long result=db.update(GOALS_TABLE_NAME,update,COLUMN_ID+"=?",new String[]{String.valueOf(mainGoalId)});

           return result != -1;
    }//close

    @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + GOALS_TABLE_NAME);
            onCreate(db);
        }
    }
