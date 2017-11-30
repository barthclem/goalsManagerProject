package com.example.barthclem.goalsmanager;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.util.List;

/**
 * Created by barthclem on 5/13/16.
 */
public class GoalsListViewAdapter extends CursorAdapter
{

    private Context context;
    private long rowId;
    private TextView title,duration;
    private ImageButton deleteButton,editButton,addsubGoalButton,priority;
    private CheckBox status;
    private EditText editText;
    private ViewSwitcher switcher;
    private Cursor cursor;
    private GoalClickListener listener;
    private String checkBoxStatus;
    private int position;
    private DurationFormatPrinter printDate;



    public GoalsListViewAdapter(Context context,GoalClickListener listener) {
        super(context,null,false);
        cursor=null;
        this.listener=listener;
    }//close constructors


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView=inflater.inflate(R.layout.each_task,parent,false);

        return convertView;
    }

    @Override
    public void bindView(View view, Context context, final Cursor cursor) {

        final String itemId=cursor.getString(cursor.getColumnIndex("_id"));

        title = (TextView) view.findViewById(R.id.taskTitle);
        duration = (TextView) view.findViewById(R.id.taskDuration);
        editButton = (ImageButton) view.findViewById(R.id.editTaskButton);
        status=(CheckBox)view.findViewById(R.id.statusBox);
        deleteButton = (ImageButton) view.findViewById(R.id.deleteTaskButton);
        addsubGoalButton=(ImageButton)view.findViewById(R.id.addSubTaskButton);



//        textView.setTag(cursor.getInt(cursor.getColumnIndex(AddContent.COLUMN_ID)));
        status.setTag(cursor.getInt(cursor.getColumnIndex(AddContent.COLUMN_ID)));
        editButton.setTag(new Integer(cursor.getPosition())+"/"+cursor.getInt(cursor.getColumnIndex(AddContent.COLUMN_ID)));
        deleteButton.setTag(cursor.getInt(cursor.getColumnIndex(AddContent.COLUMN_ID)));
        addsubGoalButton.setTag(cursor.getInt(cursor.getColumnIndex(AddContent.COLUMN_ID)));


        //<--STATUS BUTTON LISTENER-->
        //set values
        checkBoxStatus=cursor.getString(cursor.getColumnIndex(AddSubContent.COLUMN_STATUS));
        if(checkBoxStatus.equals("alive"))
            status.setChecked(false);
        else
            status.setChecked(true);
        status.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String stat;
               if ( status.isChecked())
                   stat="dead";
                else
                  stat="alive";
                listener.onStatusClick((Integer)v.getTag(),stat);
            }
        });

        //<--EDIT BUTTON LISTENER-->
        editButton.setOnClickListener(onEditClick);
        //edit button set only the tag of the position so as to display to know the current position of the item
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Integer hey = (Integer) deleteButton.getTag();
                Integer hey = (Integer)v.getTag();
                Log.e("hry: ", "" + hey);

                listener.onClickDelete(hey);

            }
        });

        //<--ADD BUTTON LISTENER-->
        addsubGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAddSubTaskButton((Integer)v.getTag());
            }
        });


        //SET THE CONTENT OF THE ADAPTER
        title.setText(cursor.getString(1));
        checkBoxStatus=cursor.getString(cursor.getColumnIndex(AddContent.COLUMN_STATUS));
        if(checkBoxStatus.equals("alive"))
            status.setChecked(false);
        else
          status.setChecked(true);

        //<--SETTING THE DURATION -->

                try{
                    if((cursor.getString(5))!=null) {
                        printDate = new DurationFormatPrinter(cursor.getString(5));
                        duration.setText(printDate.Printer());
                    }
                }
                catch(Exception w){}



    }

    public View.OnClickListener onEditClick=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

                Log.e("Edit hry: ", "" + position);
            String [] args=new String((String)v.getTag()).split("/");
               listener.onClickEdit(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
            //}
        }

    };



    @Override
    public Cursor swapCursor(Cursor newCursor) {
        return super.swapCursor(newCursor);
    }



    public interface GoalClickListener{
        void onClickEdit(int position,int uri);
        void onClickDelete(int goalUri);
        void onStatusClick(int status,String content);
        void onAddSubTaskButton(int uriId);
    }

}
