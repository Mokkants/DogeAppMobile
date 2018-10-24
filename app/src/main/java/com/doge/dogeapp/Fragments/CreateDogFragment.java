package com.doge.dogeapp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.doge.dogeapp.Activities.R;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateDogFragment extends BaseFragment implements View.OnClickListener {
    private EditText mName;
    private EditText mBreed;
    private EditText mInfo;
    private CheckBox mSocial;
    private boolean social;
    private Button addBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_dog, container, false);
        ((Button) view.findViewById(R.id.add_button)).setOnClickListener(this);
        mName = (EditText) view.findViewById(R.id.dog_name);
        mBreed = (EditText) view.findViewById(R.id.dog_breed);
        mInfo = (EditText) view.findViewById(R.id.dog_info);
        mSocial = (CheckBox) view.findViewById(R.id.dog_social);
        mSocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                if(checked){
                    social = true;
                }
                else social = false;
            }
        });
        return view;
    }

    public JSONObject getNewDog(View view) {
        JSONObject dog = new JSONObject();

        try {

            dog.put("owner", mName.getText().toString());
            dog.put("name", mBreed.getText().toString());
            dog.put("breed", mBreed.getText().toString());
            dog.put("isSocial", social);
            dog.put("shortInfo", mInfo.getText().toString());

        } catch (JSONException e) {

        }
        return dog;
    }

    @Override
    public String getTitle() {
        return "Add a new dog";
    }

    @Override
    public void onClick(View view) {
        JSONObject newDog = getNewDog(view);

        String url = getString(R.string.url) + "/api/dogs/";

        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest JSONObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, newDog, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        System.out.println(error.getMessage());
                    }
                });


        queue.add(JSONObjectRequest);
        DogFragment dogFragment = new DogFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, dogFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
