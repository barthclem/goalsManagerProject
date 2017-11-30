package com.example.barthclem.goalsmanager;

import android.content.Context;
import android.database.Cursor;
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
import android.widget.TextView;
import android.widget.Toast;

import static java.security.AccessController.getContext;


public class LoginFrag extends Fragment implements View.OnClickListener{
    //list of Buttons
    EditText usernameField,passwordField;
    TextView fPassowrd;
    Button login;
    //Strings
    String username,password,dbUsername,dbPassword;


    public LoginFrag() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null){
            passwordField.setText(savedInstanceState.getCharSequence("password"));
            usernameField.setText(savedInstanceState.getCharSequence("username"));
        }

        //get login data from database

        GoalsDataBaseManager db=new GoalsDataBaseManager(getContext());
        Cursor cursor=db.getName("USERDETAILS");

        while(cursor.moveToNext()){
            dbUsername=cursor.getString(1);
            dbPassword=cursor.getString(2);
        }//close while
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence("password",password);
        outState.putCharSequence("username",password);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View LoginView= inflater.inflate(R.layout.fragment_login, container, false);
        login=(Button)LoginView.findViewById(R.id.loginBtn);
        usernameField=(EditText)LoginView.findViewById(R.id.username);
        passwordField=(EditText)LoginView.findViewById(R.id.password);

        login.setOnClickListener(this);
        fPassowrd=(TextView)LoginView.findViewById(R.id.lostPassword);

        return LoginView;
    }

    public void onClick(View view){
        username=usernameField.getText().toString();
        password=passwordField.getText().toString();

        if(TextUtils.isEmpty(username)){
            usernameField.setError("username field cannot be left empty");
        }//close
        else if(TextUtils.isEmpty(password)){
            usernameField.setError("password field cannot be left empty");
        }//close onClickLogin
        else{
            if(!dbUsername.equals(username)){
                Toast loginDetails=Toast.makeText(getContext(),"username is not correct",Toast.LENGTH_SHORT);
                loginDetails.show();
            }
            else if(!dbPassword.equals(password)){
                Toast loginDetails=Toast.makeText(getContext(),"passowrd is not correct",Toast.LENGTH_SHORT);
                loginDetails.show();
            }
            else{
                Toast loginDetails=Toast.makeText(getContext(),"log in successfully",Toast.LENGTH_SHORT);
                loginDetails.show();
            }

        }

        //

    }//close



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
