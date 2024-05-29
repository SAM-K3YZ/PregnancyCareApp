package com.finalYearProject.myapplication;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.finalYearProject.myapplication.fragments.BabyPage;
import com.finalYearProject.myapplication.fragments.ChatPage;
import com.finalYearProject.myapplication.fragments.DoctorsPage;
import com.finalYearProject.myapplication.fragments.HomePage;
import com.finalYearProject.myapplication.fragments.SettingsPage;
import com.finalYearProject.myapplication.utils.FirebaseUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    /*implement this above if using drawer navigation
    implements NavigationView.OnNavigationItemSelectedListener*/
    //ActivityMainBinding binding;
    FragmentManager fragmentManager;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    BottomNavigationView bottomNav;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.DLmain), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE);
        setContentView(R.layout.activity_main);

        getFCMToken();

        //open the home page after signing up or in
        openFragment(new HomePage());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.DLmain);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_drawer);
        navigationView.setNavigationItemSelectedListener(this);

        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setBackground(null);

        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.homeB) {
                    openFragment(new HomePage());
                } else if (itemId == R.id.babyB) {
                    openFragment(new BabyPage());
                } else if (itemId == R.id.messsageB) {
                    openFragment(new ChatPage());
                } else if (itemId == R.id.docB) {
                    openFragment(new DoctorsPage());
                } else if (itemId == R.id.settingsB) {
                    openFragment(new SettingsPage());
                }
                return true;
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();
        if (itemId == R.id.homeB) {
            openFragment(new HomePage());
            return true;
        } else if (itemId == R.id.babyB) {
            openFragment(new BabyPage());
            return true;
        } else if (itemId == R.id.messsageB) {
            openFragment(new ChatPage());
            return true;
        } else if (itemId == R.id.docB) {
            openFragment(new DoctorsPage());
            return true;
        } else if (itemId == R.id.settingsB) {
            openFragment(new SettingsPage());
            return true;
        } else if (itemId == R.id.version) {
            Toast.makeText(this, "Still in development stage", Toast.LENGTH_SHORT).show();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void openFragment(Fragment fragment) {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    void getFCMToken(){
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                String token = task.getResult();
                FirebaseUtil.currentUserDetails().update("fcmToken",token);

            }
        });
    }
}