package com.example.hostelhelpbox;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class AdminHome extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawLay;
    private ActionBarDrawerToggle mToggle;
    SharedPreferenceConfig sharedPreferenceConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        sharedPreferenceConfig = new SharedPreferenceConfig(getApplicationContext());

        mDrawLay = findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawLay, R.string.open, R.string.close);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mDrawLay.addDrawerListener(mToggle);
        mToggle.syncState();

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentProfile()).commit();
            navigationView.setCheckedItem(R.id.nav_account);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.mDrawLay);
        if (mDrawLay.isDrawerOpen(GravityCompat.START)) {
            mDrawLay.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_account:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentProfile()).commit();
                break;
            case R.id.nav_AddSecy:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentAddSecy()).commit();
                break;

            case R.id.nav_DeleteSecy:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentDelSecy()).commit();
                break;

            case R.id.nav_logout:
                UserInfo.logout();
                sharedPreferenceConfig.writeLoginStatus(false);

                this.finish();
        }
        mDrawLay.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
