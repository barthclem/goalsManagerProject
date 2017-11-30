package com.example.barthclem.goalsmanager;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class WelcomeFrag extends Fragment implements View.OnClickListener{


    private OnFragmentInteractionListener mListener;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_welcome, container, false);
        Button register=(Button)view.findViewById(R.id.registerButton);
        register.setOnClickListener(this);
        Button login=(Button)view.findViewById(R.id.loginBtn);
        login.setOnClickListener(this);

        return view;
    }//close

    public void onClick(View view){
      int V=view.getId();
        switch(V){
            case R.id.registerButton:
                mListener.onRegisterButtonClick();
                break;
            case R.id.loginBtn:
                mListener.onLoginButtonClick();
                break;

        }//close switch braces

    }//close

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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onRegisterButtonClick();
        void onLoginButtonClick();
    }
}
