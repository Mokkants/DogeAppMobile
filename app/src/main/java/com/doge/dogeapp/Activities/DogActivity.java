package com.doge.dogeapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class DogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickNewActivity (View view) {
        TextView mDogView = findViewById(R.id.camelTextView);

        //Starts a new activity, providing the text from my HTTP text field as an input
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra(HTTP_PARAM, mDogView.getText().toString());
        startActivity(intent);
    }

    public void onClickGetCamels (View view) {
        //Get the text view in which we will show the result.
        final TextView mDogView = findViewById(R.id.dogTextView);

        String url = getString(R.string._url) + "/api/camels";

        //This uses Volley (Threading and a request queue is automatically handled in the background)
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //GSON allows to parse a JSON string/JSONObject directly into a user-defined class
                        Gson gson = new Gson();

                        String dataArray = null;

                        try {
                            dataArray = response.getString("data");
                        } catch (JSONException e) {
                            Log.e(this.getClass().toString(), e.getMessage());
                        }

                        StringBuilder dogString = new StringBuilder();
                        dogString.append("This is the list of my dogs: \n");

                        Dog[] dogs = gson.fromJson(dataArray, Dog[].class);

                        for (Dog current : dogs) {
                            dogString.append("Dog is " + current.name + " at "
                                    + current.position + "\n");
                        }

                        mDogView.setText(dogString.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mDogView.setText("Error! " + error.toString());
                    }
                });

        //The request queue makes sure that HTTP requests are processed in the right order.
        queue.add(jsonObjectRequest);
    }
}
