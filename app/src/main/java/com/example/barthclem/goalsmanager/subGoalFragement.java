package com.example.barthclem.goalsmanager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class subGoalFragement extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>,subGoalsViewAdapter.subGoalClickListener{


    private static int mainGoalId;

    private int selectedPriority,rating,raterID;
    private long currentTime,hours,minutes;
    private double rateMe;


    private RadioGroup priorityList;

    private SubGoalsDataBaseHelper database;

    private OnFragmentInteractionListener mListener;

    private subGoalsViewAdapter customAdapter;
    private Cursor cursor;
    public static final int goal_Loader=0;

    FloatingActionButton addButton;
    private Button durationButton,displayGraph;
    private Button timeSetterButton;
    private TextView ratingTab;

    private LineGraph graph;

    private DatePickerDialog datePickerDialog;
    DateFormat dateFormatter;
    private int year,month,day;
    private String setDate;
    private RatingCalculator calculator;
    private boolean ratingClicked;
    private Intent intent;
    private Calendar calendar;


    public subGoalFragement() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLoaderManager().initLoader(goal_Loader,null,this);

        calendar=Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        day=calendar.get(Calendar.DAY_OF_MONTH);
        DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        database=new SubGoalsDataBaseHelper(getContext());
        graph=new LineGraph();

        setDate();


        //setList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_test_goal_fragement,container, false);

        ratingTab=(TextView)view.findViewById(R.id.rating);
        calculator=new RatingCalculator();
        displayRate();

        customAdapter=new subGoalsViewAdapter(getActivity(),this);

        ListView highScoreView=(ListView)view.findViewById(R.id.testGoals);
        highScoreView.setAdapter(customAdapter);


//        displayGraph=(Button)view.findViewById(R.id.graph);
//        displayGraph.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final String holder=ratingTab.getText().toString();
//                double[] input=new CursorToArrayConverter(new SubGoalsDataBaseHelper(getActivity()).performanceIndex(mainGoalId),AddSubContent.GOALS_TABLE_NAME).getArray();
//
//                if(input.length!=0){
//                intent=graph.getIntent(getContext(),input);
//                startActivity(intent);
//               }
//                else
//               {
//                  ratingTab.setText("No graph Available ,no data to work with");
//                   new Handler().postDelayed(new Runnable() {
//                       @Override
//                       public void run() {
//                           ratingTab.setText(holder);
//                       }
//                   },3000);
//               }
//
//            }
//        });

        addButton=(FloatingActionButton)view.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mListener.onAddGoal();
                addGoal();

            }
        });

        return view;
    }//close

    public static subGoalFragement newInstance(int GoalId) {
        subGoalFragement fragment = new subGoalFragement();
        Bundle args = new Bundle();
        mainGoalId=GoalId;
        fragment.setArguments(args);
        return fragment;
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(goal_Loader,null,this);
    }




    @Override
    public void onDetach() {
        super.onDetach();

    }//




    @Override
    public void onClickEdit(int position, int uri) {
       edit(position,uri);
    }

    @Override
    public void onClickDelete(int goalUri) {
        Log.e("From Adapter:",""+goalUri);
        Uri uri=AddSubContent.buildGoalUri(goalUri);
        getActivity().getContentResolver().delete(uri,null,null);
        Toast.makeText(getContext(),"Path Is "+uri.toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusClick(int status,String content) {
        Uri uri=AddSubContent.buildGoalUri(status);
        ContentValues value=new ContentValues();
        value.put(AddSubContent.COLUMN_STATUS,content);
        String update=String.valueOf(status);
        getActivity().getContentResolver().update(uri,value,AddSubContent.COLUMN_ID+"=?",new String[]{update});
        customAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRatingClick(int itemId) {
        setRatingDialog(itemId);

        //RatingClicked(true);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch(id)
        {
            case goal_Loader:
                //modify the cursor loader to select the rows belonging to an id
                String selection=AddSubContent.COLUMN_MAINGOALID+"=?";
                return new CursorLoader(getActivity(),AddSubContent.CONTENT_URI,null,selection,new String[]{String.valueOf(mainGoalId)},null);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
          cursor=data;
          customAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        customAdapter.swapCursor(null);
    }





    public interface OnFragmentInteractionListener {

    }//close this

    public void addGoal(){

        android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(getActivity());
        LayoutInflater inflater=LayoutInflater.from(getContext());
        View customView=inflater.inflate(R.layout.fragment_sub_add_goal,null,false);
        final EditText nEt = (EditText)customView.findViewById(R.id.addTitleText);

        durationButton=(Button)customView.findViewById(R.id.addDurationButton);
        timeSetterButton=(Button)customView.findViewById(R.id.addTimeButton);


        durationButton.setOnClickListener(OnSetDuration);
        timeSetterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int hour=calendar.get(Calendar.HOUR_OF_DAY);
                int minute=calendar.get(Calendar.MINUTE);
                new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hours=hourOfDay;
                        minutes=minute;

                        currentTime= (hours*60*60*1000)+(minutes*60*1000);
                        Log.e(" current  : ",""+currentTime);

                    }
                },hour,minute,true).show();
            }
        });



        //this converts the current time to milliseconds



        builder.setTitle("Add  Goal!")
                .setView(customView)
                .setPositiveButton("Add Goal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        String title = nEt.getText().toString();


                        if(title.equals(""))
                        { nEt.setError("Field cannot be left empty");}

                        Log.e(" current  : ",""+currentTime);
                        ContentValues values=new ContentValues();
                        values.put(AddSubContent.COLUMN_TITLE,nEt.getText().toString());
                        values.put(AddSubContent.COLUMN_MAINGOALID,mainGoalId);
                        values.put(AddSubContent.COLUMN_STATUS,"alive");
                        values.put(AddSubContent.COLUMN_DURATION,""+currentTime);
                        values.put(AddSubContent.COLUMN_DUEDATE,setDate);
                        Uri goals=getActivity().getContentResolver().insert(AddSubContent.CONTENT_URI,values);

                        customAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }

                })
                .show();}




    public View.OnClickListener OnSetDuration=new View.OnClickListener() {

        @Override
        public void onClick(View v) {
          datePickerDialog.show();
            durationButton.setText(setDate);
        }
    };





//this code id for the date picker in adding and editing goals
//for the add dialog
public void setDate()
    {
        Calendar newCalendar = Calendar.getInstance();

            datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                setDate = dateFormatter.format(newDate.getTime());

            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }


public void displayRate(){
    rateMe=calculator.CalculateRating(database.performanceIndex(mainGoalId));
    ratingTab.setText("Your Rating is "+String.format("%.2f",rateMe) +"%");
}

//Setting Preference part of the code

    public void setRatingDialog(int item){
        final int itemId=item;

        android.app.AlertDialog.Builder build=new android.app.AlertDialog.Builder(getActivity());
        LayoutInflater inflater=LayoutInflater.from(getContext());
        View priorityView=inflater.inflate(R.layout.ratinglayout,null,false);
         priorityList=(RadioGroup)priorityView.findViewById(R.id.radioGroup);

        build.setTitle("GOAL ACHIEVEMENT");
        build.setView(priorityView);
        build.setPositiveButton("set",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                View radioButton=priorityList.findViewById(priorityList.getCheckedRadioButtonId());
                rating=5-(int)(priorityList.indexOfChild(radioButton));

                Uri uri=AddSubContent.buildGoalUri(itemId);
                ContentValues value=new ContentValues();
                value.put(AddSubContent.COLUMN_RATING,rating);

                String update=String.valueOf(itemId);
                Log.e("item id : ",""+itemId);
                long u=getActivity().getContentResolver().update(uri,value,AddSubContent.COLUMN_ID+"=?",new String[]{update});
                displayRate();
                new MainGoalsDataBaseHelper(getContext()).updateGoal(AddContent.COLUMN_SUBGOALCOUNT,rateMe,mainGoalId);


                customAdapter.notifyDataSetChanged();
                dialog.dismiss();


            }//close

        });
        build.show();

    }


    public void edit(int position,int ury){

        final Uri uri=AddSubContent.buildGoalUri(ury);
        final int id_position=ury;
        android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(getActivity());
        LayoutInflater inflater=LayoutInflater.from(getContext());
        View customView=inflater.inflate(R.layout.fragment_sub_add_goal,null,false);
        final EditText nEt = (EditText)customView.findViewById(R.id.addTitleText);
        durationButton=(Button)customView.findViewById(R.id.addDurationButton);
        timeSetterButton=(Button)customView.findViewById(R.id.addTimeButton);

        durationButton.setOnClickListener(OnSetDuration);
        timeSetterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int hour=calendar.get(Calendar.HOUR_OF_DAY);
                int minute=calendar.get(Calendar.MINUTE);
                new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hours=hourOfDay;
                        minutes=minute;


                        currentTime= (hours*60*60*1000)+(minutes*60*1000);
                        Log.e(" current  wsde: ",""+currentTime);
                    }
                },hour,minute,true).show();
            }
        });

        if (cursor.moveToPosition(position)) {
            nEt.setText(cursor.getString(cursor.getColumnIndex(AddSubContent.COLUMN_TITLE)));



            //this converts the current time to milliseconds





            builder.setTitle("Edit Goal!")
                    .setView(customView)
                    .setPositiveButton("Edit Goal", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            String title = nEt.getText().toString();

                            if (!title.equals("")) {
                                nEt.setError("Field cannot be left empty");
                            }

                            Log.e(" current  wsde: ",""+currentTime);
                            ContentValues values=new ContentValues();
                            values.put(AddSubContent.COLUMN_TITLE,nEt.getText().toString());
                            values.put(AddSubContent.COLUMN_MAINGOALID,mainGoalId);
                            values.put(AddSubContent.COLUMN_DURATION,""+currentTime);
                            values.put(AddSubContent.COLUMN_DUEDATE,setDate);
                            getActivity().getContentResolver().update(uri,values,AddSubContent.COLUMN_ID+"=?",new String[]{String.valueOf(id_position)});

                            customAdapter.notifyDataSetChanged();
                            dialog.dismiss();
                            //this is to update the rating tab with the new priority


                        }
                    }).show();
        }

    }





}