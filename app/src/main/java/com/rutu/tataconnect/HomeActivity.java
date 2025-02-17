package com.rutu.tataconnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.prefs.AbstractPreferences;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener
{
    BottomNavigationView bottomNavigationView;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.homeBottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.HomeBtmNavigationHome);

        preferences = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this);
        editor = preferences.edit();

        boolean firstTime = preferences.getBoolean("isFirstTime", true);

        if (firstTime) {
            welcome();
        }



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.Whishlist)
        {

        } else if (item.getItemId() == R.id.MyOffers) {

        } else if (item.getItemId() == R.id.MyProfile) {
            Intent intent =  new Intent(HomeActivity.this, MyProfileActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.logout) {
            logout();
        }

        return true;
    }

    HomeFragment H1 = new HomeFragment();
    CategoryFragment C1 = new CategoryFragment();
    MyOrderFragment M1 = new MyOrderFragment();


    private void welcome() {
        AlertDialog.Builder ad = new AlertDialog.Builder(HomeActivity.this);
        ad.setTitle("Vendor Connect..");
        ad.setMessage("Welcome to the World of Products");
        ad.setPositiveButton("Thank You", (dialog, which) -> dialog.cancel()).create().show();
        editor.putBoolean("isFirstTime",false).commit();

    }

    private void logout() {
        AlertDialog.Builder ad = new AlertDialog.Builder(HomeActivity.this);
        ad.setTitle("Vendor Connect ");
        ad.setMessage("Are You Sure, You Want to Logout..?");
        ad.setPositiveButton("CANCEL", (dialog, which) -> dialog.cancel());
        ad.setNegativeButton("LOGOUT", (dialog, which) -> {
            Intent i1 = new Intent(HomeActivity.this, LoginActivity.class);
            editor.putBoolean("isLogin", false).commit();
            startActivity(i1);
        }).create().show();
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
    {

        if (menuItem.getItemId() == R.id.HomeBtmNavigationHome)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout,H1).commit();
        } else if (menuItem.getItemId() == R.id.HomeBtmNavigationCategory)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout,C1).commit();

        } else if (menuItem.getItemId() == R.id.HomeBtmNavigationMyOrder)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout,M1).commit();
        }


        return true;
    }
}