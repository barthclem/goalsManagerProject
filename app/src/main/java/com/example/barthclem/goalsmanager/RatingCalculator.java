/**
 * Created by barthclem on 5/17/16.
 */

package com.example.barthclem.goalsmanager;

import android.database.Cursor;
import android.util.Log;


class RatingCalculator {
    Cursor cursor;
    double priority=0;

    RatingCalculator() {

    }

    /**
     *
     * @param subCursor {Cursor} - A cursor object
     * @return calculated rating of a goal
     */
    double CalculateRating(Cursor subCursor){
        //the max rating a user can set is 5
        int sumOfRowRating = 0;
        int numberOfRatingCounter = 0;
           if(subCursor.moveToFirst()) {
               while (!subCursor.isAfterLast()) {
                   sumOfRowRating += subCursor.getInt(subCursor.getColumnIndex(AddSubContent.COLUMN_RATING));
                   numberOfRatingCounter++;
                   Log.e("Sum of Row Rating : ", "" + sumOfRowRating);
                   subCursor.moveToNext();
               }
               subCursor.close();
               int maxRatingSum = 5 * numberOfRatingCounter;
               double rating = ((double) sumOfRowRating / (double) maxRatingSum);
//        Rating % is used for sub goals
//        convert to percentage by multiplying by 100


               return rating *100;
           }
        else
           {
               return  0;
           }
    }

    /**
     *
     * @param  mainCursor {Object} - a cursor object used in the database
     * @return {double} the calculated performance Index
     */
    double generatePerformanceIndex(Cursor mainCursor){

        double sumPerformance = 0;
        double sumOfPriority = 0;

        mainCursor.moveToFirst();
        while(!mainCursor.isAfterLast()){
            double perform = mainCursor.getDouble(mainCursor.getColumnIndex(AddContent.COLUMN_SUBGOALCOUNT));
            priority=mainCursor.getInt(mainCursor.getColumnIndex(AddContent.COLUMN_PRIORITY));
            if(perform ==0)
            {
                sumPerformance +=(priority>0?priority:1)*5;
                sumOfPriority +=(priority>0?priority:1);
            }
            else{
                sumPerformance +=(priority>0?priority:1)* perform;
                sumOfPriority +=(priority>0?priority:1);
            }
            mainCursor.moveToNext();
        }

        return sumPerformance / sumOfPriority;
    }
}
