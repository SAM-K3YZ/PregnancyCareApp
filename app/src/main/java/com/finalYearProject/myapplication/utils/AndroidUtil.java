package com.finalYearProject.myapplication.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.finalYearProject.myapplication.model.UserModel;

public class AndroidUtil {
    public static  void showToast(Context context, String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

    public static void passUserModelAsIntent(Intent intent, UserModel model){
        intent.putExtra("username",model.getUsername());
        intent.putExtra("phone",model.getPhone());
        intent.putExtra("email", model.getEmail());
        intent.putExtra("firstName", model.getFirstName());
        intent.putExtra("lastName", model.getLastName());
        intent.putExtra("conceptionDate", model.getConceptionDate());
        intent.putExtra("userId",model.getUserId());
        intent.putExtra("fcmToken",model.getFcmToken());
    }

    public static UserModel getUserModelFromIntent(Intent intent){
        UserModel userModel = new UserModel();
        userModel.setUsername(intent.getStringExtra("username"));
        userModel.setPhone(intent.getStringExtra("phone"));
        userModel.setEmail(intent.getStringExtra("email"));
        userModel.setFirstName(intent.getStringExtra("firstName"));
        userModel.setLastName(intent.getStringExtra("lastName"));
        userModel.setConceptionDate(intent.getStringExtra("conceptionDate"));
        userModel.setUserId(intent.getStringExtra("userId"));
        userModel.setFcmToken(intent.getStringExtra("fcmToken"));
        return userModel;
    }

    public static void setProfilePic(Context context, Uri imageUri, ImageView imageView){
        Glide.with(context).load(imageUri).apply(RequestOptions.circleCropTransform()).into(imageView);
    }
}
