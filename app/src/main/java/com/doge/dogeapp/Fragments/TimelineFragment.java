package com.doge.dogeapp.Fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.doge.dogeapp.Models.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TimelineFragment extends BaseFragment {

    private View mListView;
    private View mProgressView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup vg = (ViewGroup) inflater.inflate(R.layout.fragment_timeline, container, false);
        mListView = vg.findViewById(R.id.posts_list);
        mProgressView = vg.findViewById(R.id.getposts_progress);
        showProgress(true);
        getPosts(vg, inflater);
        return vg;
    }

    @Override
    public String getTitle() {
        return "Timeline";
    }

    private void getPosts(final ViewGroup vg, final LayoutInflater inflater) {
        String url = getString(R.string.url) + "/api/posts";
        RequestQueue queue = Volley.newRequestQueue((getActivity()).getApplicationContext());
        JsonObjectRequest JSONObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        ArrayList<Post> posts = new ArrayList<>();

                        try {
                            JSONArray dataArray = response.getJSONArray("data");
                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject current = dataArray.getJSONObject(i);
                                if (current.getJSONObject("postedBy").getInt("_id") == Settings.getLoggedInUser().getId()) {
                                    posts.add(new Post(current));
                                }
                            }
                            insertSubviews(vg, inflater, posts);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getActivity().getApplicationContext(), "Unable to load posts.", Toast.LENGTH_LONG).show();
                    }
                });
        queue.add(JSONObjectRequest);

    }

    private void insertSubviews(ViewGroup vg, LayoutInflater inflater, ArrayList<Post> posts) {
        for (Post post : posts) {
            View postView = inflater.inflate(R.layout.fragment_post, vg, false);
            ((TextView) postView.findViewById(R.id.messageTextContainer)).setText(post.getText());
            ((LinearLayout) vg.findViewById(R.id.lin)).addView(postView);
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

}
