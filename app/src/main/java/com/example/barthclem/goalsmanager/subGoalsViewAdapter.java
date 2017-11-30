package com.example.barthclem.goalsmanager;

import android.content.Context;
import android.database.Cursor;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by barthclem on 5/13/16.
 */
public class subGoalsViewAdapter extends CursorAdapter
{

    private TextView title,duration;
    private Button rating,priority;
    private ImageButton deleteButton,editButton;
    private CheckBox status;
    private EditText editTitle;
    private Date date;
    private SimpleDateFormat dateFormatter;
    private String storedDate;
    private long dateInt, storedTime;
    private Cursor cursor;
    private ViewSwitcher textSwitcher;
    private subGoalClickListener listener;
    private DurationFormatPrinter dateDuration;
    private String checkBoxStatus,newTitle;// this is the checkbox that shows the status of the goal
    private int position;
    public subGoalsViewAdapter(Context context, subGoalClickListener listener) {
        super(context,null,false);
        cursor=null;
        this.listener=listener;
    }//close constructors





    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView=inflater.inflate(R.layout.each_sub_task,parent,false);



        return convertView;
    }

    @Override
    public void bindView(View view, Context context, final Cursor cursor) {

        final String itemId=cursor.getString(cursor.getColumnIndex("_id"));


        Log.e("Errors", "onClick: " + cursor.getLong(cursor.getColumnIndex("_id")));
        title = (TextView) view.findViewById(R.id.taskTitle);
        duration = (TextView) view.findViewById(R.id.taskDuration);
        rating=(Button)view.findViewById(R.id.ratingButton);
        editButton = (ImageButton) view.findViewById(R.id.editTaskButton);
        status=(CheckBox)view.findViewById(R.id.statusBox);

        //textSwitcher=(ViewSwitcher)view.findViewById(R.id.switchText);
        deleteButton = (ImageButton) view.findViewById(R.id.deleteTaskButton);



        deleteButton.setTag(cursor.getInt(cursor.getColumnIndex(AddSubContent.COLUMN_ID)));
//        textView.setTag(cursor.getInt(cursor.getColumnIndex(AddContent.COLUMN_ID)));
        rating.setTag(cursor.getInt(0));
        status.setTag(cursor.getInt(cursor.getColumnIndex(AddSubContent.COLUMN_ID)));
        editButton.setTag(new Integer(cursor.getPosition())+"/"+cursor.getInt(cursor.getColumnIndex(AddContent.COLUMN_ID)));
        status.setOnClickListener(onStatusClick);
        editButton.setOnClickListener(onEditClick);
        //edit button set only the tag of the position so as to display to know the current position of the item
        deleteButton.setOnClickListener(onDeleteClick);
        rating.setOnClickListener(onRatingClick);



        //<--SETTING THE CONTENT OF THE PAGE-->
        title.setText(cursor.getString(1));

        //SETTING THE DURATION
        //The first thing to do is to format the date and time to what can be processed bt the converter

        storedDate=cursor.getString(cursor.getColumnIndex(AddSubContent.COLUMN_DUEDATE));
        storedTime=Long.parseLong(cursor.getString(cursor.getColumnIndex(AddSubContent.COLUMN_DURATION)));
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        Calendar calendar=Calendar.getInstance();


                try{
                    date=dateFormatter.parse(storedDate);
                    dateInt=date.getTime();
                    dateInt=dateInt+storedTime;
                    dateDuration=new DurationFormatPrinter(dateInt);
                    Log.e("Stored Time",""+storedTime);
                    duration.setText(dateDuration.Printer());
                }
                catch(Exception w){}




        checkBoxStatus=cursor.getString(cursor.getColumnIndex(AddSubContent.COLUMN_STATUS));
        if(checkBoxStatus.equals("alive"))
            status.setChecked(false);
        else
            status.setChecked(true);

        rating.setText(""+cursor.getInt(cursor.getColumnIndex(AddSubContent.COLUMN_RATING)));
        //Log.e("rating display",""+setRatingDisplay(cursor.getInt(cursor.getColumnIndex(AddSubContent.COLUMN_RATING))));

        Log.e("RatingCursor Content : ", "" + cursor.getColumnIndex(AddSubContent.COLUMN_RATING));
    }

    public View.OnClickListener onEditClick=new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Log.e("Edit hry: ", "" + position);
            String [] args=new String((String)v.getTag()).split("/");
            listener.onClickEdit(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
        }
    };


    public View.OnClickListener onDeleteClick=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Integer hey = (Integer)v.getTag();
            Log.e("hry: ", "" + hey);

            listener.onClickDelete(hey);
        }

    };
    public View.OnClickListener onRatingClick=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.e("View tag :",""+v.getTag());
            listener.onRatingClick((Integer)v.getTag());
        }

    };
    public View.OnClickListener onStatusClick=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String stat;
            if ( status.isChecked())
                stat="dead";
            else
                stat="alive";
            listener.onStatusClick((Integer)v.getTag(),stat);
        }

    };



    @Override
    public Cursor swapCursor(Cursor newCursor) {
        return super.swapCursor(newCursor);
    }

    public String setRatingDisplay(int rating){
        String ratingMessage="";
        switch(rating){
            case 5:
                ratingMessage="Perfect";
                break;
            case 4:
                ratingMessage="Superb";
                break;
            case 3:
                ratingMessage="Average done";
                break;
            case 2:
                ratingMessage="Briefly done";
                break;
            case 1:
                ratingMessage="no strength";
                break;
            default:
                ratingMessage="yet to be done";
                break;
        }
        return  ratingMessage;
    }



    public interface subGoalClickListener{
        void onClickEdit(int position,int uri);
        void onClickDelete(int goalUri);
        void onStatusClick(int status, String content);
        void onRatingClick(int rating);
    }

}
