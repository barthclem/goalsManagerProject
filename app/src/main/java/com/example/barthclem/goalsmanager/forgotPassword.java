package com.example.barthclem.goalsmanager;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
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


public class forgotPassword extends Fragment implements View.OnClickListener{
    private TextView securityQuestionText;
    private EditText usernameField,securityAnswerField;
    private Button recoverPassword;
    private String username,securityAnswer,securityQuestion;
    private View fView;
    private OnFragmentInteractionListener mListener;
    public forgotPassword() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null){
            usernameField.setText(savedInstanceState.getCharSequence("username"));
            securityAnswerField.setText(savedInstanceState.getCharSequence("answer"));
        }//close that stuff
        fetchLoginDetails();//fetch the original user data from the user base

    }//close

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence("username",usernameField.getText());
        outState.putCharSequence("answer",securityAnswerField.getText());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fView= inflater.inflate(R.layout.fragment_forgot_password, container, false);
        usernameField=(EditText)fView.findViewById(R.id.username);
        securityAnswerField=(EditText)fView.findViewById(R.id.securityAnswer);
        securityQuestionText=(TextView)fView.findViewById(R.id.securityQuestions);
        securityQuestionText.setText(securityQuestion);
        recoverPassword=(Button) fView.findViewById(R.id.recoverPassword);
        recoverPassword.setOnClickListener(this);
        return fView;
    }

   public void onClick(View view){

       if(TextUtils.isEmpty(usernameField.getText())){
           usernameField.setError("Username cannot be left empty");
       }//close
       else if(TextUtils.isEmpty(securityAnswerField.getText())){
           securityAnswerField.setError("You must answer the security question to continue");
       }//close validation
       else{
           if(
                   //username.equals(usernameField.getText().toString())&&
           securityAnswer.equals(securityAnswerField.getText().toString())){
               Toast forgotPasswordError=Toast.makeText(getContext(),"The Password has been sent to your email",Toast.LENGTH_SHORT);
               forgotPasswordError.show();
               mListener.responseToAnswer();
           }
           else{
               Toast forgotPasswordError=Toast.makeText(getContext(),"You have entered wrong answer to security question",Toast.LENGTH_SHORT);
                    forgotPasswordError.show();
           }

       }
   }//close onClick method

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
    @Override
    public void onDetach() {
        super.onDetach();

    }

    public void fetchLoginDetails() {
        //handling the database part of the login
        //we query the database to get the user's login credentials since the table contains only a user we are save

        GoalsDataBaseManager db=new GoalsDataBaseManager(getContext());
        Cursor cursor=db.getName("USERDETAILS");
        while(cursor.moveToNext()) {
            username= cursor.getString(1);
            securityQuestion=cursor.getString(4);
            securityAnswer=cursor.getString(5);
        }

    }//close


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void responseToAnswer();
    }
}
