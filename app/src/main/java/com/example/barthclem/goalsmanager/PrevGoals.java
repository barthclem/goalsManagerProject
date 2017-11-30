package com.example.barthclem.goalsmanager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class PrevGoals extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private OnFragmentInteractionListener mListener;

    private WhatisYourFocusAdapter customAdapter;
    private Cursor cursor;
    private Calendar calendar;
    private Intent intent;
    private int year,month,day;
    public static final int goal_Loader=0;
    private FloatingActionButton enterButton;

    public PrevGoals() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static DayGoals newInstance() {
        DayGoals fragment = new DayGoals();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_day_goals, container, false);

        getActivity().getSupportLoaderManager().initLoader(goal_Loader,null,this);

        enterButton=(FloatingActionButton)view.findViewById(R.id.enterApp);
        intent=new Intent(getActivity(),LoaderPage.class);
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        customAdapter=new WhatisYourFocusAdapter(getContext());


        ListView goalsView=(ListView)view.findViewById(R.id.listView);
        //ListView missedGoalsView=(ListView)findViewById(R.id.listView1);

        goalsView.setAdapter(customAdapter);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch(id){
            case goal_Loader:
                calendar=Calendar.getInstance();
                try{
                SimpleDateFormat date=new SimpleDateFormat("dd-MM-yyyy", Locale.UK);
                String toDay=date.format(calendar.getTime());
                String stored=AddSubContent.COLUMN_DUEDATE;
                String selection=AddSubContent.COLUMN_STATUS+"=?"+date.parse(stored)+"<?";
                    return new CursorLoader(getContext(),AddSubContent.CONTENT_URI,null,selection,new String[]{"alive",},null);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                  default:
                return null;
        }

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        customAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        customAdapter.swapCursor(null);
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
