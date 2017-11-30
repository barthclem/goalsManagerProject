package com.example.barthclem.goalsmanager;


import android.util.Log;

public class DurationFormatPrinter {
    DateToDurationConverter   converter;
    private String duration;
    private boolean setYear=false,setMonth=false,setDay=false,setHours=false,setMinutes=false;
    public DurationFormatPrinter(String StoredDate) {
        converter=new DateToDurationConverter(StoredDate);
        duration=new String();
    }
    public DurationFormatPrinter(long StoredDate) {
        converter=new DateToDurationConverter(StoredDate);
        duration=new String();
    }


    public String Printer(){

        if((converter.getNumOfYear())>0){
            duration+=(converter.getNumOfYear());
            duration+=(converter.getNumOfYear()==1?" year ":" years ");
            setYear=true;
        }

         if((converter.getNumOfMonths())>0) {
            duration+=(converter.getNumOfMonths());
            duration+=(converter.getNumOfMonths() == 1 ? " month " : " months ");
             setMonth=true;
        }

         if(((converter.getNumOfDays())>0)&&(!setYear))
        {     duration+=(converter.getNumOfDays());
            duration+=(converter.getNumOfDays()==1?" day ":" days ");
            setMonth=true;
        }
        if(((converter.getNumOfHours())>0)){
            Log.e("currentHour",""+converter.getNumOfHours());
            if(logicalHour()) {
                duration += (converter.getNumOfHours());
                duration += (converter.getNumOfHours() == 1 ? " hour " : " hours ");
                setHours = true;
            }
        }
         if(((converter.getNumOfMins())>0)) {
             if (logicalMinute())
             {   duration += (converter.getNumOfMins());
                 duration += (converter.getNumOfMins() == 1 ? " minutes  " : " minutes ");
             }
         }

        if(setYear||setMonth||setDay||setHours||setMinutes){
        duration+=(" left");}
        else{
            duration+=(" today");
        }


        Log.e("currentHour",""+converter.getNumOfHours());
        return duration.toString();
    }

    public boolean logicalHour(){
        return((!setYear)&&(!setMonth)||(!setYear) && (!setDay)||(!setMonth) && (!setDay));
    }
    public boolean logicalMinute(){
        return (logicalHour()||((!setYear) && (!setHours))||((!setHours) && (!setDay)||((!setHours) && (!setMonth))));
        }
}
