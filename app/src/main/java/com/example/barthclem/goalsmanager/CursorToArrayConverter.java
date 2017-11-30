package com.example.barthclem.goalsmanager;

import android.database.Cursor;
import android.util.Log;

/**
 * Created by barthclem on 5/19/16.
 */
public class CursorToArrayConverter {
    Cursor cursor;
    double[] convert;
    String TABLE_NAME;

    public CursorToArrayConverter(Cursor cursor,String table) {

        this.cursor = cursor;
        this.TABLE_NAME=table;
    }

    public int getCursorLength() {
        int i = 0;
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                i++;
                Log.e("count : ",""+i);
                cursor.moveToNext();
            }


        }//
        return i;
    }

    public double[] subConverter() {
        int j = 0;

        double[] convert = new double[getCursorLength()];
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                convert[j] = cursor.getInt(cursor.getColumnIndex(AddSubContent.COLUMN_RATING))*20.0;
                Log.e(" cursor content : ",""+convert[j]);
                cursor.moveToNext();
                j++;
            }

            cursor.close();
        }
        return convert;
    }
    public double[] mainConverter() {
        int j = 0;

        double[] convert = new double[getCursorLength()];
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                convert[j] = cursor.getInt(cursor.getColumnIndex(AddContent.COLUMN_SUBGOALCOUNT));
                Log.e(" cursor content : ",""+convert[j]);
                cursor.moveToNext();
                j++;
            }

            cursor.close();
        }
        return convert;
    }

    public double[] getArray(){
        if(TABLE_NAME.equals(AddSubContent.GOALS_TABLE_NAME)){
            return subConverter();
        }
        else
            return mainConverter();
    }


}
