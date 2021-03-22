package com.sametkemalozdemir.instagramclonewithparse;

import android.app.Activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;


public class PostClass  extends ArrayAdapter<String> {

    private final ArrayList<String> username;
    private final ArrayList<String> userComment;
    private final ArrayList<Bitmap> userImage;
    private final Activity context;

    public PostClass(ArrayList<String> username, ArrayList<String> userComment, ArrayList<Bitmap> userImage, Activity context) {
        super(context,R.layout.customview,username);
        this.username = username;
        this.userComment = userComment;
        this.userImage = userImage;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View view=inflater.inflate(R.layout.customview,null,true);
        TextView userNameText=view.findViewById(R.id.customView_username);
        TextView userCommentText=view.findViewById(R.id.customView_comment);
        ImageView userImageText=view.findViewById(R.id.customView_image_post);

        userNameText.setText(username.get(position));
        userCommentText.setText(userComment.get(position));

        userImageText.setImageBitmap(userImage.get(position));


        return view;
    }
}
