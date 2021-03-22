package com.sametkemalozdemir.instagramclonewithparse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PostUploadActivity extends AppCompatActivity {
    ImageView imageView;
    EditText comment;
    Bitmap chosenImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_upload);
        imageView=findViewById(R.id.imageView);
        comment=findViewById(R.id.TextComment);
    }

    public void upload(View view){


        String commentText=comment.getText().toString();

        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        chosenImage.compress(Bitmap.CompressFormat.PNG,50,byteArrayOutputStream);
        byte[] bytes =byteArrayOutputStream.toByteArray();

        ParseFile file=new ParseFile("image.png",bytes);


        ParseObject object=new ParseObject("Posts");
        object.put("comment",commentText);
        object.put("images",file);
        object.put("userName", ParseUser.getCurrentUser().getUsername());
        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e!=null){
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage().toString(),Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Uploaded",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(PostUploadActivity.this,FeedActivity.class);
                    startActivity(intent);
                }

            }
        });


    }


    public void selectImage(View view){
        if(ContextCompat.checkSelfPermission(PostUploadActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(PostUploadActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},2);
        }
        else{
            Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent,1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==2){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,1);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1){
           if( resultCode==RESULT_OK   &&data!=null){
               Uri dataUri=data.getData();
               try {
                   if(Build.VERSION.SDK_INT>=28){
                       ImageDecoder.Source source= ImageDecoder.createSource(this.getContentResolver(),dataUri);
                       chosenImage=ImageDecoder.decodeBitmap(source);
                       imageView.setImageBitmap(chosenImage);
                   }else{
                       chosenImage= MediaStore.Images.Media.getBitmap(this.getContentResolver(),dataUri);
                       imageView.setImageBitmap(chosenImage);
                   }

               } catch (IOException e) {
                   e.printStackTrace();
               }

           }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}