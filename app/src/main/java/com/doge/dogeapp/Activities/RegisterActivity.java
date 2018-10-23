package com.doge.dogeapp.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class RegisterActivity extends AppCompatActivity {


    public static final String HTTP_PARAM = "httpResponse";

    public JSONObject getNewUser(View view){
        JSONObject user = new JSONObject();
        JSONObject location = new JSONObject();
        EditText reg_username = findViewById(R.id.reg_username);
        EditText reg_name = findViewById(R.id.reg_name);
        EditText reg_country = findViewById(R.id.reg_country);
        EditText reg_city = findViewById(R.id.reg_city);
        EditText reg_address = findViewById(R.id.reg_adress);

        RadioGroup reg_userType =  findViewById(R.id.user_radio);


        String value =
                ((RadioButton)findViewById(reg_userType.getCheckedRadioButtonId()))
                        .getText().toString();
        if(value.equals("Walker")){
            value = "true";
        }
        else if(value.equals("Owner")){
            value = "false";
        }

        try {

            user.put("username", reg_username.getText().toString());
            user.put("name", reg_name.getText().toString());
            location.put("country", reg_country.getText().toString());
            location.put( "city", reg_city.getText().toString());
            location.put( "address", reg_address.getText().toString());
            user.put( "location", location);
            user.put( "isWalker", value);

        }
        catch (JSONException e){

        }

        return user;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

    }



        public void onClickRegister(View view) {

        JSONObject newUser =  getNewUser(view);

        String url = getString(R.string.url) + "/api/users/";

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest JSONObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, newUser, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                       Toast.makeText(getBaseContext(), R.string.success, Toast.LENGTH_LONG).show();
                        Intent Intent = new Intent(getApplicationContext() , LoginActivity.class);
                        startActivity(Intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getBaseContext(), R.string.takenUser, Toast.LENGTH_LONG).show();
                    }
                });



            queue.add(JSONObjectRequest);








    }

    }

