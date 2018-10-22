package com.doge.dogeapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.doge.dogeapp.Models.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();

        //TODO
        //User currentUser = Settings.getCurrentUser();
        User currentUser = null;

        Class startIntent = currentUser == null ? LoginActivity.class : TimelineActivity.class;
        startActivity(new Intent(this, startIntent));
    }
}
