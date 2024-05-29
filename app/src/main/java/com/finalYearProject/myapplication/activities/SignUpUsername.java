package com.finalYearProject.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.finalYearProject.myapplication.MainActivity;
import com.finalYearProject.myapplication.R;
import com.finalYearProject.myapplication.model.UserModel;
import com.finalYearProject.myapplication.utils.FirebaseUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

public class SignUpUsername extends AppCompatActivity {

    EditText usernameText;
    Button LMIBtn;
    ProgressBar progressBar;
    UserModel userModel;
    String phoneNumber, email, conceptionDate, firstName, lastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up_username);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.SUUmain), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        phoneNumber = getIntent().getExtras().getString("phone");
        email = getIntent().getExtras().getString("email");
        firstName = getIntent().getExtras().getString("firstName");
        lastName = getIntent().getExtras().getString("lastName");
        conceptionDate = getIntent().getExtras().getString("conceptionDate");

        usernameText = findViewById(R.id.signUp_username);
        LMIBtn = findViewById(R.id.signUp_let_me_in_btn);

        getUsername();

        LMIBtn.setOnClickListener(v -> {
            setUsername();
        });

    }

    void setUsername(){

        String username = usernameText.getText().toString();

        if(username.isEmpty() || username.length()<3){
            usernameText.setError("Username length should be at least 3 chars");
            return;
        }
        //setInProgress(true);

        if(userModel != null){
            userModel.setUsername(username);
        }else{
            userModel = new UserModel(phoneNumber, username, email, conceptionDate, firstName, lastName, Timestamp.now(), FirebaseUtil.currentUserId());
        }

        FirebaseUtil.currentUserDetails().set(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //setInProgress(false);
                if(task.isSuccessful()){
                    Intent intent = new Intent(SignUpUsername.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                    startActivity(intent);
                }
            }
        });

    }

    void getUsername(){
        //setInProgress(true);

        FirebaseUtil.currentUserDetails().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                //setInProgress(false);
                if(task.isSuccessful()){
                    userModel = task.getResult().toObject(UserModel.class);
                    if(userModel!=null){
                        usernameText.setText(userModel.getUsername());
                    }
                }
            }
        });
    }

//    void setInProgress(boolean inProgress){
//        if(inProgress){
//            progressBar.setVisibility(View.VISIBLE);
//            LMIBtn.setVisibility(View.GONE);
//        }else{
//            progressBar.setVisibility(View.GONE);
//            LMIBtn.setVisibility(View.VISIBLE);
//        }
//    }

}