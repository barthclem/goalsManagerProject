package com.example.barthclem.goalsmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Welcome extends AppCompatActivity implements WelcomeFrag.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container0,new WelcomeFrag()).commit();
        //getSupportFragmentManager().beginTransaction()

    }//close

    @Override
    public void onRegisterButtonClick() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container0,new Registration()).commit();
    }

    @Override
    public void onLoginButtonClick() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container0,new LoginFrag()).commit();

    }
}
