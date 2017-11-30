package com.example.barthclem.goalsmanager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class ChangeLastPassword extends Fragment implements View.OnClickListener{

    private EditText newPassword,confirmPassword;
    private Button submitPasswordChange;

    public ChangeLastPassword() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View CView= inflater.inflate(R.layout.fragment_change_last_password, container, false);
        newPassword=(EditText)CView.findViewById(R.id.newPassword);
        confirmPassword=(EditText)CView.findViewById(R.id.confirmNewPassword);
        submitPasswordChange=(Button)CView.findViewById(R.id.changeLostPassword);
        submitPasswordChange.setOnClickListener(this);
        return CView;
    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    @Override
    public void onClick(View v) {
        int pass=v.getId();
        switch(pass){
            case R.id.changeLostPassword:
                OnClickPasswordChange();

                break;

        }//close switch
    }//close onClick

    public void OnClickPasswordChange(){
        if(TextUtils.isEmpty(newPassword.getText())){
           newPassword.setError("please ensure field this field");
        }
        else if(TextUtils.isEmpty(confirmPassword.getText())){
            confirmPassword.setError("please ensure field this field");
        }
        else if(!newPassword.getText().toString().equals(confirmPassword.getText().toString())){
            Toast ChangePasswordError=Toast.makeText(getContext(),"The two passwords you entered do not match",Toast.LENGTH_SHORT);
            ChangePasswordError.show();
        }
        else{
            GoalsDataBaseManager db=new GoalsDataBaseManager(getContext());
            db.updateField("password",newPassword.getText().toString());
            Toast ChangePassword=Toast.makeText(getContext(),"Passwords change successfully",Toast.LENGTH_SHORT);
            ChangePassword.show();
            Intent intent=new Intent(getActivity(),MainActivity.class);
            startActivity(intent);
        }
    }//close method

    public void updatePassword(){

    }//close updatePassword method

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
