package com.doge.dogeapp.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.doge.dogeapp.Adapters.DogAdapter;
import com.doge.dogeapp.Models.Dog;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class DogActivity extends AppCompatActivity {

    private final String URL = getString(R.string.url) + "/api/dogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog);
        final RecyclerView dogList = findViewById(R.id.dogList);
        dogList.setLayoutManager(new LinearLayoutManager(this));

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                String dataArray = null;
                try {
                    dataArray = response.getString("data");
                } catch (JSONException e) {
                    Log.e(this.getClass().toString(), e.getMessage());
                }
                Dog[] dogs = gson.fromJson(dataArray, Dog[].class);
                dogList.setAdapter(new DogAdapter(DogActivity.this, dogs));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DogActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }
}
