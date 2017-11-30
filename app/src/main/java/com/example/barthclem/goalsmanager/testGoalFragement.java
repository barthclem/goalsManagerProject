package com.example.barthclem.goalsmanager;

import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;

public class testGoalFragement extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,GoalsListViewAdapter.GoalClickListener{

    private  String  name;
    private int image,score;
    private int selectedPriority;

    private RadioGroup priorityList;

    private OnFragmentInteractionListener mListener;

    private GoalsListViewAdapter customAdapter;
    private Cursor cursor;
    public static final int goal_Loader=0;

    FloatingActionButton addButton;
    private Button durationButton,priorityButton,displayGraph;
    private TextView ratingTab;

    private DatePickerDialog datePickerDialog;
    DateFormat dateFormatter;
    private int year,month,day;
    private String setDate;

    private RatingCalculator calculator;

    private Intent intent;
    private LineGraph graph;


    public testGoalFragement() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLoaderManager().initLoader(goal_Loader,null,this);

        Calendar calendar=Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        day=calendar.get(Calendar.DAY_OF_MONTH);
        DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        setDate();

        graph=new LineGraph();

        //setList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_test_goal_fragement,container, false);
        ratingTab=(TextView)view.findViewById(R.id.rating);

        calculator=new RatingCalculator();
        setRatingTab();
        customAdapter=new GoalsListViewAdapter(getActivity(),this);

        ListView highScoreView=(ListView)view.findViewById(R.id.testGoals);
        highScoreView.setAdapter(customAdapter);

        addButton=(FloatingActionButton)view.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mListener.onAddGoal();
                addGoal();
            }
        });


    //    displayGraph=(Button)view.findViewById(R.id.graph);
//        displayGraph.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final String holder=ratingTab.getText().toString();
//                double[] input=new CursorToArrayConverter(new MainGoalsDataBaseHelper(getContext()).performanceIndex(),AddContent.GOALS_TABLE_NAME).getArray();
//
//                if(input.length!=0){
//                    intent=graph.getIntent(getContext(),input);
//                    startActivity(intent);
//                }
//                else
//                {
//                    ratingTab.setText("No graph Available ,no data to work with");
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            ratingTab.setText(holder);
//                        }
//                    },3000);
//                }
//
//            }
//        });

        return view;
    }//close

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(goal_Loader,null,this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch(id)
        {
            case goal_Loader:

                return new CursorLoader(getActivity(), AddContent.CONTENT_URI
                ,null,null,null,null);
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



    @Override
    public void onDetach() {
        super.onDetach();

    }//



    @Override
    public void onClickEdit(int position,int uri) {
          editGoal(position,uri);
    }

    @Override
    public void onClickDelete(int goalUri) {
        Log.e("From Adapter:",""+goalUri);
        Uri uri=AddContent.buildGoalUri(goalUri);
        getActivity().getContentResolver().delete(uri,null,null);
        Toast.makeText(getContext(),"Path Is "+uri.toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusClick(int status,String content) {
        Uri uri=AddContent.buildGoalUri(status);
        ContentValues value=new ContentValues();
        value.put(AddContent.COLUMN_STATUS,content);
        String update=String.valueOf(status);
        getActivity().getContentResolver().update(uri,value,AddContent.COLUMN_ID+"=?",new String[]{update});
        customAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAddSubTaskButton(int uriId) {
        mListener.onAddSubGoalButtonClick(uriId);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onGoalSelected(Uri goalUri);
        void onAddSubGoalButtonClick(int mainTaskId);
    }//close this

    public void addGoal(){

        android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(getActivity());
        LayoutInflater inflater=LayoutInflater.from(getContext());
        View customView=inflater.inflate(R.layout.fragment_add_goal,null,false);
        final EditText nEt = (EditText)customView.findViewById(R.id.addTitleText);
        durationButton=(Button)customView.findViewById(R.id.addDurationButton);
        priorityButton=(Button)customView.findViewById(R.id.setPriorityButton);

//        //cursor.moveToPosition()
//        nEt.setHint(staffs.getName());
//        pET.setHint(staffs.getPosition());

        durationButton.setOnClickListener(OnSetDuration);
        priorityButton.setOnClickListener(OnSetPriority);

        builder.setTitle("Add  Goal!")
                .setView(customView)
                .setPositiveButton("Add Goal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {




                        String title = nEt.getText().toString();


                        if(title.equals(""))
                        { nEt.setError("Field cannot be left empty");}


                            ContentValues values=new ContentValues();
                            values.put(AddContent.COLUMN_TITLE,nEt.getText().toString());
                            values.put(AddContent.COLUMN_PRIORITY,selectedPriority);
                            values.put(AddContent.COLUMN_STATUS,"alive");
                            values.put(AddContent.COLUMN_DUEDATE,setDate);
                            Uri goals=getActivity().getContentResolver().insert(AddContent.CONTENT_URI,values);
                            if(goals==null){
                                Toast.makeText(getContext(), "goal not added",Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(getContext(), "goal is added ",Toast.LENGTH_LONG).show();
                            }
                            customAdapter.notifyDataSetChanged();
                            dialog.dismiss();
                            setRatingTab();//this is to update the rating tab with the new priority

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

    public View.OnClickListener OnSetPriority=new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Toast.makeText(getContext(),"toasters",Toast.LENGTH_SHORT).show();
            setPreference();
        }
    };



    public void editGoal(int position, int ury) {

        final Uri uri=AddContent.buildGoalUri(ury);
        final int id_position=ury;
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.fragment_add_goal, null, false);
        final EditText nEt = (EditText) customView.findViewById(R.id.addTitleText);
        durationButton=(Button)customView.findViewById(R.id.addDurationButton);
        priorityButton=(Button)customView.findViewById(R.id.setPriorityButton);

        durationButton.setOnClickListener(OnSetDuration);
        priorityButton.setOnClickListener(OnSetPriority);

        if (cursor.moveToPosition(position)) {
            nEt.setText(cursor.getString(1));
            durationButton.setText(cursor.getString(4));


            builder.setTitle("Edit Goal!")
                    .setView(customView)
                    .setPositiveButton("Edit Goal", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            String title = nEt.getText().toString();

                            if (!title.equals("")) {
                                nEt.setError("Field cannot be left empty");
                            }


                                ContentValues values = new ContentValues();
                                values.put(AddContent.COLUMN_TITLE, nEt.getText().toString());
                                values.put(AddContent.COLUMN_PRIORITY,selectedPriority);
                                values.put(AddContent.COLUMN_DUEDATE,setDate);
                                getActivity().getContentResolver().update(uri,values,AddContent.COLUMN_ID+"=?",new String[]{String.valueOf(id_position)});
//                                if (goals == null) {
//                                    Toast.makeText(getContext(), "goal not added", Toast.LENGTH_LONG).show();
//                                } else {
//                                    Toast.makeText(getContext(), "goal is added ", Toast.LENGTH_LONG).show();
//                                }

                                customAdapter.notifyDataSetChanged();
                                dialog.dismiss();
                                setRatingTab();//this is to update the rating tab with the new priority


                        }
                    }).show();
        }


    }



//this code id for the date picker in adding and editing goals
//for the add dialog
public void setDate()
    {
        final Calendar newCalendar = Calendar.getInstance();

            datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.UK);
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                view.setMinDate(newCalendar.getTimeInMillis());

                newDate.set(year, monthOfYear, dayOfMonth);
                setDate = dateFormatter.format(newDate.getTime());


            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

public void setRatingTab(){
    double performnceIndex=calculator.generatePerformanceIndex(new MainGoalsDataBaseHelper(getContext()).performanceIndex());
    ratingTab.setText("The Performance Index is "+String.format("%.2f",performnceIndex)+"%");
}


//Setting Preference part of the code

    public void setPreference(){
        Toast.makeText(getContext(),"Set Priority",Toast.LENGTH_SHORT).show();
        android.app.AlertDialog.Builder build=new android.app.AlertDialog.Builder(getActivity());
        LayoutInflater inflater=LayoutInflater.from(getContext());
        View priorityView=inflater.inflate(R.layout.priority,null,false);
         priorityList=(RadioGroup)priorityView.findViewById(R.id.radioGroup1);

        build.setTitle("set your Preference");
        build.setView(priorityView);
        build.setPositiveButton("set",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                View radio=priorityList.findViewById(priorityList.getCheckedRadioButtonId());
                selectedPriority=5-(int)(priorityList.indexOfChild(radio));
                Log.e("Priority is : ",""+selectedPriority);
                dialog.dismiss();
            }//close

        });
        build.show();

    }






}