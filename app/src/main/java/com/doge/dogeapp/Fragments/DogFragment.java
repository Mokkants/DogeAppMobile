package com.doge.dogeapp.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.doge.dogeapp.Activities.R;
import com.doge.dogeapp.Adapters.DogAdapter;
import com.doge.dogeapp.Helpers.Settings;
import com.doge.dogeapp.Models.Dog;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DogFragment extends Fragment {
    private final String URL = getString(R.string.url) + "/api/dogs";
    private RecyclerView dogView;
    private DogAdapter dogAdapter;
    private ArrayList<Dog> listDog;

    public DogFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dog, container, false);

        dogView = (RecyclerView) view.findViewById(R.id.dogList);
        dogView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listDog = new ArrayList<Dog>();
        dogAdapter = new DogAdapter(getActivity());
        dogView.setAdapter(dogAdapter);
        fetchData();
        return view;
    }

    private void fetchData() {
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
                            Dog[] allDogs = gson.fromJson(dataArray, Dog[].class);
                            ArrayList<Dog> dogs = new ArrayList<>();
                            for (int i = 0; i < allDogs.length; i++) {
                                if (allDogs[i].getOwner().equals(Settings.getLoggedInUser())) {
                                    dogs.add(allDogs[i]);
                                }
                            }
                            dogAdapter.setDogList(dogs);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        }
                });
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(jsonObjectRequest);
    }
}
