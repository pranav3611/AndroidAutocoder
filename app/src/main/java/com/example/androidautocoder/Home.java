package com.example.androidautocoder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.androidautocoder.Databases.SessionManager;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.net.URI;

import static com.example.androidautocoder.Databases.SessionManager.KEY_USERNAME;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private View decorView;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Button btnNew,Logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Logout = (Button)findViewById(R.id.nav_logoutbtn);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUserFromApp();
            }
        });

        //        hide bars
        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == 0) {
                    decorView.setSystemUiVisibility(hideSystemBars());
                }
            }
        });

        btnNew = (Button)findViewById(R.id.btnNew);
        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent apknewbtn = new Intent(Home.this,subscribe.class);
            startActivity(apknewbtn);
                Toast toast = Toast.makeText(getApplicationContext(),"You need to purchase PRO PACK to make custom apks",Toast.LENGTH_LONG);
                toast.show();
            }
        });


        drawerLayout = findViewById(R.id.home_layout);
        navigationView = findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.click_home);


        Menu menu = navigationView.getMenu();
    }



    //        hide bars
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            decorView.setSystemUiVisibility(hideSystemBars());
        }
    }

    private int hideSystemBars() {
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }
    //    end hide bar

    public void menu_btn(View view) {
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.click_home:break;
            case R.id.click_orderApp:
                Intent intent = new Intent(Home.this,Order_app.class);
                startActivity(intent);
                break;
            case R.id.click_subscribe:
                Intent intent1 = new Intent(Home.this,subscribe.class);
                startActivity(intent1);
                break;
            case R.id.click_profile:
                Intent intent3 = new Intent(Home.this,userProfile.class);
                startActivity(intent3);
                break;

            case R.id.share:
                ApplicationInfo api = getApplicationContext().getApplicationInfo();
                String apkpath = api.sourceDir;
                Intent intent2 = new Intent(Intent.ACTION_SEND);
                intent2.setType("text/plain");
                String shareBody = "link";
                String sharesub = "AndroidAutocoder";
                intent2.putExtra(Intent.EXTRA_SUBJECT,shareBody);
                intent2.putExtra(Intent.EXTRA_TEXT,shareBody);
                startActivity(Intent.createChooser(intent2,"ShareVia"));
                break;

            case R.id.click_logout:
                logoutUserFromApp();
                break;

        }


        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    public void logoutUserFromApp(){
        //Session
        SessionManager sessionManager = new SessionManager(Home.this);
        sessionManager.logoutUserFromSession();
        Intent takeUserToLogin = new Intent(Home.this,Login_page.class);
        startActivity(takeUserToLogin);
        finish();
    }

}