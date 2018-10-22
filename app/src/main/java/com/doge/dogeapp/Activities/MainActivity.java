package com.doge.dogeapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.doge.dogeapp.Helpers.Settings;
import com.doge.dogeapp.Models.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO
        //User currentUser = Settings.getCurrentUser();
        Settings.setLoggedInUser(new User());
    }

    @Override
    protected void onResume() {
        super.onResume();

        Class startIntent = Settings.getLoggedInUser() == null ? LoginActivity.class : NavigationActivity.class;
        startActivity(new Intent(this, startIntent));
    }
}
