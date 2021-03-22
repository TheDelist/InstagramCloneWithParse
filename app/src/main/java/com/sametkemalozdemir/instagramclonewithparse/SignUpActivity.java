package com.sametkemalozdemir.instagramclonewithparse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {
        EditText nameText,passText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        nameText=findViewById(R.id.text_Sign_Up_activity_NickName);
        passText=findViewById(R.id.text_Sign_up_activity_Pass);
        ParseUser user=ParseUser.getCurrentUser();
        if(user!=null){
            Intent intent=new Intent(SignUpActivity.this,FeedActivity.class);
            startActivity(intent);
        }


    }

    public void signUp(View view){
        ParseUser user=new ParseUser();
        user.setUsername(nameText.getText().toString());
        user.setPassword(passText.getText().toString());
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e!=null){
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage().toString(),Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"User "+nameText.getText().toString()+" are Created",Toast.LENGTH_SHORT).show();
                    //intent
                    Intent intent=new Intent(SignUpActivity.this,FeedActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
    public void signIn(View view){
        ParseUser.logInInBackground(nameText.getText().toString(), passText.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e!=null){
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage().toString(),Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Welcome "+user.getUsername(),Toast.LENGTH_SHORT).show();
                    //intent
                    Intent intent=new Intent(SignUpActivity.this,FeedActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

}