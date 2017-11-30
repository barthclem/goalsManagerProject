package com.example.barthclem.goalsmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;


public class StartPage extends Fragment implements View.OnClickListener{
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    //list of Buttons
    private EditText passwordField;

    private Button login;
    private Button forgotPasswordButton;
    //Strings
    private String password,dbPassword;
    private View FragView;

    private OnFragmentInteractionListener mListener;

    public StartPage() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null){
            passwordField.setText(savedInstanceState.getCharSequence("password"));
        }

        fetchLoginDetails();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragView= inflater.inflate(R.layout.fragment_start_page, container, false);
        passwordField=(EditText)FragView.findViewById(R.id.spassword);
        login=(Button)FragView.findViewById(R.id.userButton);
        forgotPasswordButton=(Button)FragView.findViewById(R.id.forgotPasswordClick);

        forgotPasswordButton.setOnClickListener(this);
        login.setOnClickListener(this);

        //onClickLogin(FragView);
        return FragView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence("password",password);

    }


    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    public void onClick(View view){
        int clicks=view.getId();
        switch(clicks){
            case R.id.userButton:
                onButtonClick();
                break;
            case R.id.forgotPasswordClick:
                mListener.onLinkClick();
                break;

        }

    }//close


        public void onButtonClick() {
            password=passwordField.getText().toString();

            if(TextUtils.isEmpty(passwordField.getText())){
                passwordField.setError("password field cannot be left empty");
            }//close onClickLogin

            if(password.equals(dbPassword)){
                Toast loginDetails=Toast.makeText(getContext(),"Login Successful",Toast.LENGTH_SHORT);
                loginDetails.show();
                Intent intent=new Intent(getActivity(),Tester.class);
                startActivity(intent);
            }
            else{
                Toast loginDetails=Toast.makeText(getContext(),"Login not Successful",Toast.LENGTH_SHORT);
                loginDetails.show();
            }
        }//close



    public void fetchLoginDetails() {
        //handling the database part of the login
        //we query the database to get the user's login credentials since the table contains only a user we are save

        GoalsDataBaseManager db=new GoalsDataBaseManager(getContext());
        Cursor cursor=db.getName("USERDETAILS");
        while(cursor.moveToNext()) {
            dbPassword = cursor.getString(2);
        }

    }//close

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onLinkClick();
    }



}
