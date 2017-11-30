package com.example.barthclem.goalsmanager;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by barthclem on 5/17/16.
 */
public class DateToDurationConverter {
    private long numOfYear;
    private long numOfDays;
    private long numOfHours;
    private long numOfMins;
    private long numOfMonths;
    private Date storedDate,currentDate;
    private long dateDifference;


    public DateToDurationConverter(String storedDate) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        Calendar calendar=Calendar.getInstance();
        try{

            this.storedDate=dateFormatter.parse(storedDate);
            this.currentDate=dateFormatter.parse(dateFormatter.format(calendar.getTime()));
            dateDifference=(this.storedDate.getTime()-this.currentDate.getTime());
            numOfMins=dateDifference/(60000);
            numOfHours=numOfMins/60;
            numOfDays=numOfHours/24;
            numOfMonths=numOfDays/30;
            numOfYear=numOfMonths/12;

        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
    public DateToDurationConverter(long dateInt) {
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        try{
            this.currentDate=dateFormatter.parse(dateFormatter.format(calendar.getTime()));
            long dateDifference=Math.abs(dateInt-this.currentDate.getTime());
            numOfMins=dateDifference/(60000);
            numOfHours=numOfMins/60;
            numOfDays=numOfHours/24;
            numOfMonths=numOfDays/30;
            numOfYear=numOfMonths/12;
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    public long getNumOfYear() {
        return numOfYear;
    }

    private void setNumOfYear(int numOfYear) {

        this.numOfYear = numOfYear;
    }

    public long getNumOfMonths() {
        while(numOfMonths/12!=0){
            numOfMonths/=12;
        }
        return (int)(numOfMonths%60);
    }

    private void setNumOfMonths(int Months) {

    }



    public long getNumOfDays() {
        while (numOfDays / 30 != 0) {
            numOfDays /= 30;
        }
        return (int) (numOfDays % 30);
    }

    private void setNumOfDays(int days) {

        this.numOfDays = days;
    }

    public long getNumOfHours() {
        while(numOfHours/24!=0){
            numOfHours/=24;
            Log.e("Now Hour is : ",""+numOfHours);
        }
        return (int)(numOfHours%24);

    }

    private void setNumOfHours(int hours) {
        this.numOfHours =hours;
    }

    public int getNumOfMins() {
        while(numOfMins/60!=0){
            numOfMins/=60;
        }
        return (int)(numOfMins%60);
    }

    private void setNumOfMins(int numOfMins) {
        this.numOfMins = numOfMins;
    }


}
