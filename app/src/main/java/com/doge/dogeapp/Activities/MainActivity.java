package com.doge.dogeapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.doge.dogeapp.Helpers.Settings;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();

        Class startIntent = Settings.getLoggedInUser() == null ? LoginActivity.class : NavigationActivity.class;
        startActivity(new Intent(this, startIntent));
    }
}