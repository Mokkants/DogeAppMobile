package com.doge.dogeapp.Fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.doge.dogeapp.Activities.R;
import com.doge.dogeapp.Helpers.Settings;
import com.doge.dogeapp.Models.Dog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DogFragment extends BaseFragment implements View.OnClickListener {

    private View mListView;
    private View mProgressView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup vg = (ViewGroup) inflater.inflate(R.layout.fragment_dog, container, false);
        mListView = vg.findViewById(R.id.dogs_list);
        mProgressView = vg.findViewById(R.id.getdogs_progress);
        showProgress(true);
        getDogs(vg, inflater);
        ((Button) vg.findViewById(R.id.button)).setOnClickListener(this);
        return vg;
    }

    @Override
    public String getTitle() {
        return "Dog";
    }

    private void getDogs(final ViewGroup vg, final LayoutInflater inflater) {
        String url = getString(R.string.url) + "/api/dogs";
        RequestQueue queue = Volley.newRequestQueue((getActivity()).getApplicationContext());
        JsonObjectRequest JSONObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        ArrayList<Dog> dogs = new ArrayList<>();

                        try {
                            JSONArray dataArray = response.getJSONArray("data");
                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject current = dataArray.getJSONObject(i);
                                if (current.getInt("owner") == Settings.getLoggedInUser().getId()) {
                                    dogs.add(new Dog(current));
                                }
                            }
                            insertSubviews(vg, inflater, dogs);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getActivity().getApplicationContext(), "Unable to load dogs.", Toast.LENGTH_LONG).show();
                    }
                });
        queue.add(JSONObjectRequest);

    }

    private void insertSubviews(ViewGroup vg, LayoutInflater inflater, ArrayList<Dog> dogs) {
        for (Dog dog : dogs) {
            View dogView = inflater.inflate(R.layout.dog_row_item, vg, false);
            ((TextView) dogView.findViewById(R.id.dog_name)).setText(dog.getName());
            ((TextView) dogView.findViewById(R.id.dog_breed)).setText(dog.getBreed());
            ((TextView) dogView.findViewById(R.id.dog_social)).setText(dog.isSocial());
            ((TextView) dogView.findViewById(R.id.dog_info)).setText(dog.getShortInfo());
            ((LinearLayout) vg.findViewById(R.id.lin)).addView(dogView);
        }
        showProgress(false);
    }

    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mListView.setVisibility(show ? View.GONE : View.VISIBLE);
        mListView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mListView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public void onClick(View view) {
        CreateDogFragment fragmentCreateDog = new CreateDogFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragmentCreateDog);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
