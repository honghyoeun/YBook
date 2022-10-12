package com.example.nodejs_login;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainPageActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    Fragment firstFragment = new FirstFragment();
    Fragment secondFragment = new SecondFragment();
    Fragment thirdFragment = new ThirdFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, firstFragment).commit();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        System.out.println("Menu Item ---->>>>" + item.getItemId());
        switch (item.getItemId()){
            case R.id.library:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, firstFragment).commit();
                return true;
            case R.id.add:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, secondFragment).commit();
                return true;
            case R.id.search:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, thirdFragment).commit();
                return true;
        }
        return false;
    }
}
