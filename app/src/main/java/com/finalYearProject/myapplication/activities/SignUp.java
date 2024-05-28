package com.finalYearProject.myapplication.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.finalYearProject.myapplication.MainActivity;
import com.finalYearProject.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;

import java.util.Calendar;

public class SignUp extends AppCompatActivity {

    EditText emailText;
    EditText passwordText, firstNameText, lastNameText;
    EditText phoneNumberInput;
    CountryCodePicker countryCodePicker;
    TextView toSignIn;
    Button signUpBtn;
    private EditText concepDate;
    //private ImageButton dateBtn;
    private int y, m, d;
    private int yy, mm, dd;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.SUmain), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        emailText = findViewById(R.id.signUp_email);
        passwordText = findViewById(R.id.signUp_password);
        toSignIn = findViewById(R.id.toSignIn);
        signUpBtn = findViewById(R.id.next_btn);
        firstNameText = findViewById(R.id.signUp_firstName);
        lastNameText = findViewById(R.id.signUp_lastName);
        concepDate = findViewById(R.id.signUp_conceptionDate);
//        dateBtn = findViewById(R.id.imageButton);
        countryCodePicker = findViewById(R.id.signUp_countrycode);
        phoneNumberInput = findViewById(R.id.signUp_mobile_number);

        listeners();

    }

    private void listeners(){
        toSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(SignUp.this, SignIn.class);
                startActivity(in);
                finish();
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password, firstname, lastname, conceptionDate;
                email = String.valueOf(emailText.getText());
                password= String.valueOf(passwordText.getText());
                firstname= String.valueOf(firstNameText.getText());
                lastname = String.valueOf(lastNameText.getText());
                conceptionDate = String.valueOf(concepDate.getText());

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(SignUp.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(SignUp.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                countryCodePicker.registerCarrierNumberEditText(phoneNumberInput);
                if (!countryCodePicker.isValidFullNumber()){
                    phoneNumberInput.setError("Phone number is not valid");
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(SignUp.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignUp.this, SignUpUsername.class);
                                    intent.putExtra("phone",countryCodePicker.getFullNumberWithPlus());
                                    intent.putExtra("email", email);
                                    intent.putExtra("firstName", firstname);
                                    intent.putExtra("lastName", lastname);
                                    intent.putExtra("conceptionDate", conceptionDate);
                                    //intent.putExtra("password", password);
                                    startActivity(intent);
                                    finish();
                                }
                                else{
                                    Toast.makeText(SignUp.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                //setUsername();

            }
        });

        concepDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                yy = calendar.get(Calendar.YEAR);
                mm = calendar.get(Calendar.MONTH);
                dd = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog;
                dialog = new DatePickerDialog(SignUp.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        yy = year;
                        mm = month;
                        dd = dayOfMonth;

                        concepDate.setText(dd + " - " + mm + " - " + yy);
                    }
                },yy,mm,dd);
                dialog.show();
            }
        });
    }
}