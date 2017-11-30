package com.example.barthclem.goalsmanager;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Registration extends Fragment implements View.OnClickListener{
    private View RegView;
    private EditText usernameField,passwordField,confirmPasswordField,emailField,securityAnswerField;
    private String username,password,email,securityQuestion,securityAnswer;
    private Spinner securityQuestionField;
    private Button register;
    private Boolean inputOk=false,usernameError=false,passwordError=false,emailError=false,answerError=false;
    private int errorCount=0;

    public Registration() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null) {
            usernameField.setText(savedInstanceState.getCharSequence("username"));
            passwordField.setText(savedInstanceState.getCharSequence("password"));
            confirmPasswordField.setText(savedInstanceState.getCharSequence("confirmPassword"));
            emailField.setText(savedInstanceState.getCharSequence("email"));
            securityAnswerField.setText(savedInstanceState.getCharSequence("securityAnswer"));
        }


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence("username",usernameField.getText());
        outState.putCharSequence("password",passwordField.getText());
        outState.putCharSequence("confirmPassword",confirmPasswordField.getText());
        outState.putCharSequence("email",emailField.getText());
        outState.putCharSequence("securityAnswer",securityAnswerField.getText());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RegView= inflater.inflate(R.layout.fragment_registration, container, false);
        usernameField=(EditText)RegView.findViewById(R.id.username);
        passwordField=(EditText)RegView.findViewById(R.id.password);
        confirmPasswordField=(EditText)RegView.findViewById(R.id.confirmPassword);
        emailField=(EditText)RegView.findViewById(R.id.email);
        securityQuestionField=(Spinner)RegView.findViewById(R.id.securityQuestionSpinner);
        securityAnswerField=(EditText)RegView.findViewById(R.id.securityAnswer);
        register=(Button)RegView.findViewById(R.id.registerButton);
        register.setOnClickListener(this);

        return RegView;
    }//close createView method


    public void onClick(View view){
        if(inputVerification()) {

            GoalsDataBaseManager register = new GoalsDataBaseManager(getContext());
            if (register.insertRegister(1, username, password, email, securityQuestion, securityAnswer)) {
                Toast good = Toast.makeText(getContext(), "registration successful    \n " + username + "\t" + password, Toast.LENGTH_SHORT);
                good.show();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            } else {
                Toast display = Toast.makeText(getContext(), "Unable to Connect Please try Again later", Toast.LENGTH_SHORT);
                display.show();
            }
        }
    }//close


    public boolean inputVerification(){
        if(TextUtils.isEmpty(usernameField.getText())){
            usernameField.setError("Username Field must be filled");
            usernameError=true;
            errorCount++;
        }
        else if(TextUtils.isEmpty(passwordField.getText())){
            passwordField.setError("password Feild must be filled");
            passwordError=true;
            errorCount++;
        }
        else if(TextUtils.isEmpty(confirmPasswordField.getText())){
            confirmPasswordField.setError("password Feild must be filled");
            passwordError=true;
            errorCount++;
        }
        else if(TextUtils.isEmpty(emailField.getText())){
            emailField.setError("password Feild must be filled");
            emailError=true;
            errorCount++;
        }
        else if(!usernameField.getText().toString().matches("[A-za-z,0-9]+")){
            usernameError=true;
            Toast registrationError=Toast.makeText(getContext(),"the username format is not allowed",Toast.LENGTH_SHORT);
            registrationError.show();
            errorCount++;
        }
        else if(!emailField.getText().toString().matches("[A-za-z,0-9,_-]+@[a-z]+.[a-z]+")){
            Toast registrationError=Toast.makeText(getContext(),"please ensure your email is correct",Toast.LENGTH_SHORT);
            registrationError.show();
            usernameError=true;
            errorCount++;
        }

        else if(TextUtils.isEmpty(securityAnswerField.getText())){
            securityAnswerField.setError("password Feild must be filled");
            answerError=true;errorCount++;
        }
        else if(!passwordField.getText().toString().equals(confirmPasswordField.getText().toString())){
            passwordError=true;errorCount++;
            Toast registrationError=Toast.makeText(getContext(),"The two passwords you entered do not match",Toast.LENGTH_SHORT);
            registrationError.show();
        }
        else if(usernameError==false&&passwordError==false&&emailError==false&&answerError==false) {
            //upload the data to the database
            username=usernameField.getText().toString();
            password=passwordField.getText().toString();
            email=emailField.getText().toString();
            securityQuestion=securityQuestionField.getSelectedItem().toString();
            securityAnswer=securityAnswerField.getText().toString();

        }
        else{
            errorCount++;
            usernameError=false;passwordError=false;emailError=false;answerError=false;
        }

        if(errorCount==0)
            inputOk=true;
        else
        {
            inputOk=false;
            errorCount=0;
        }
        return inputOk;
    }//close


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
