package com.rutu.tataconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener
{
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.homeBottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.HomeBtmNavigationHome);

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
        }
        return true;
    }

    HomeFragment H1 = new HomeFragment();
    CategoryFragment C1 = new CategoryFragment();
    MyOrderFragment M1 = new MyOrderFragment();


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