package com.example.barthclem.goalsmanager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by barthclem on 5/18/16.
 */
public class WhatIsYourFocusPopulator {

    private Date storedDate,currentDate;

    public boolean todayGoals(String date){
        return Math.abs(durationCalculator(date)) <= 86400;
    }

    public boolean missedGoals(String date,String status){
      return (Math.abs(durationCalculator(date)) > 80000) && (status.equals("alive"));
    }

    private long durationCalculator(String Date){
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        Calendar calendar=Calendar.getInstance();
        long dateDifference=0;
        try {

            this.storedDate = dateFormatter.parse(Date);
            this.currentDate = dateFormatter.parse(dateFormatter.format(calendar.getTime()));
            dateDifference = (this.storedDate.getTime() - this.currentDate.getTime());
        }
        catch(Exception exception){
            exception.printStackTrace();
        }
        return dateDifference;
    }
}//close
