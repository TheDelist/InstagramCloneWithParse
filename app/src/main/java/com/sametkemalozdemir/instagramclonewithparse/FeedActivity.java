package com.sametkemalozdemir.instagramclonewithparse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity {
    ListView listView;
     ArrayList<String> usernameFromParse;
     ArrayList<String> userCommentFromParse;
    ArrayList<Bitmap> userImageFromParse;
    PostClass postClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        listView=findViewById(R.id.listView);
        userCommentFromParse=new ArrayList<>();
        userImageFromParse=new ArrayList<>();
        usernameFromParse=new ArrayList<>();
        postClass=new PostClass(usernameFromParse,userCommentFromParse,userImageFromParse,this);
        listView.setAdapter(postClass);

        download();

    }

    public void download(){

        ParseQuery<ParseObject> query=ParseQuery.getQuery("Posts");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e!=null){
                    Toast.makeText(getApplicationContext(), e.getLocalizedMessage().toString(),Toast.LENGTH_SHORT).show();
                }else{
                    if(objects.size()>0 ){
                        for(final ParseObject object:objects){
                            ParseFile parseFile=(ParseFile)object.get("images");
                            parseFile.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] data, ParseException e) {
                                    if(data!=null && e==null){
                                        Bitmap downloadedImage= BitmapFactory.decodeByteArray(data,0,data.length);
                                        userImageFromParse.add(downloadedImage);
                                        usernameFromParse.add(object.getString("userName"));
                                        userCommentFromParse.add(object.getString("comment"));
                                        postClass.notifyDataSetChanged();
                                    }
                                }
                            });


                        }
                    }
                }
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.add_post){
            //intent
            Intent intent=new Intent(FeedActivity.this,PostUploadActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.sign_out){
            ParseUser.logOutInBackground(new LogOutCallback() {
                @Override
                public void done(ParseException e) {
                    if(e!=null){
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage().toString(),Toast.LENGTH_SHORT).show();
                    }else{
                        Intent intent=new Intent(FeedActivity.this,SignUpActivity.class);
                        startActivity(intent);
                    }

                }
            });
        }
        return super.onOptionsItemSelected(item);
    }
}