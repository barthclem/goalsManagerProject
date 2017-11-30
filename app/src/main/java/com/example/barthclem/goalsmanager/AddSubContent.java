package com.example.barthclem.goalsmanager;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.HashMap;

/**
 * Created by barthclem on 5/15/16.
 */
public class AddSubContent extends ContentProvider {
        static final String PROVIDER_NAME = "com.example.provider.SubGoalsDataBase";
        static final String URL = "content://" + PROVIDER_NAME + "/subGoalTable";
        static final Uri CONTENT_URI = Uri.parse(URL);
        private static HashMap<String, String> STUDENTS_PROJECTION_MAP;
        static final int ALLGOALS = 1;
        static final int GOAL_ID = 2;
        static final UriMatcher uriMatcher;
        static{
            uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
            uriMatcher.addURI(PROVIDER_NAME, "subGoalTable", ALLGOALS);
            uriMatcher.addURI(PROVIDER_NAME, "subGoalTable/#", GOAL_ID);
        }


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
                        "duration TEXT,"+
                        "dueDate TEXT,"+
                        "status TEXT NOT NULL);";



        @Override
        public boolean onCreate() {
            Context context = getContext();
            SubGoalsDataBaseHelper dbHelper = new SubGoalsDataBaseHelper(context);

            db = dbHelper.getWritableDatabase();
            return (db == null)? false:true;
        }
        @Override
        public Uri insert(Uri uri, ContentValues values) {

            long rowID = db.insert(
                    GOALS_TABLE_NAME, "", values);
            if (rowID > 0)
            {
                Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
                getContext().getContentResolver().notifyChange(_uri, null);
                return _uri;
            }
            throw new SQLException("Failed to add a record into " + uri);
        }
        @Override
        public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder){
            SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
            qb.setTables(GOALS_TABLE_NAME);
            switch (uriMatcher.match(uri)) {
                case ALLGOALS:
                    qb.setProjectionMap(STUDENTS_PROJECTION_MAP);
                    break;
                case GOAL_ID:
                    qb.appendWhere( COLUMN_ID + "=" + uri.getPathSegments().get(1));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown URI " + uri);
            }
            if (sortOrder == null || sortOrder == ""){

                sortOrder = null;
            }
            Cursor c = qb.query(db,
                    projection,
                    selection, selectionArgs,null, null, sortOrder);

            c.setNotificationUri(getContext().getContentResolver(), uri);
            return c;
        }
        @Override
        public int delete(Uri uri, String selection, String[] selectionArgs) {
            int count = 0;
            switch (uriMatcher.match(uri)){
                case ALLGOALS:
                    count = db.delete(GOALS_TABLE_NAME, selection, selectionArgs);
                    break;
                case GOAL_ID:
                    String id = uri.getLastPathSegment();
                    count = db.delete( GOALS_TABLE_NAME, COLUMN_ID + " = " + id +
                            (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown URI " + uri);
            }
            getContext().getContentResolver().notifyChange(uri, null);
            return count;
        }
        @Override
        public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
            int count = 0;
            switch (uriMatcher.match(uri)){
                case ALLGOALS:
                    count = db.update(GOALS_TABLE_NAME, values, selection, selectionArgs);
                    break;
                case GOAL_ID:
                    count = db.update(GOALS_TABLE_NAME, values, COLUMN_ID + " = " + uri.getPathSegments().get(1) +
                            (!TextUtils.isEmpty(selection) ? " AND (" +selection + ')' : ""), selectionArgs);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown URI " + uri );
            }
            getContext().getContentResolver().notifyChange(uri, null);
            return count;
        }
        @Override
        public String getType(Uri uri) {
            switch (uriMatcher.match(uri)){

                                case ALLGOALS:
                    return "hey Clement is on the wheel of success";
                case GOAL_ID:
                    return "setting high goals and getting them done";
                default:
                    throw new IllegalArgumentException("Unsupported URI: " + uri);
            }
        }



        //this is used to get the uri from the id in the database///
        public static Uri buildGoalUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

