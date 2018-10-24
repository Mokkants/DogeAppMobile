package com.doge.dogeapp.Fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
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

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;

public class TimelineFragment extends BaseFragment {

    private View mListView;
    private View mProgressView;

    private int createStageCounter;

    private ViewGroup viewGroup;
    private LayoutInflater layoutInflater;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup vg = (ViewGroup) inflater.inflate(R.layout.fragment_timeline, container, false);
        mListView = vg.findViewById(R.id.posts_list);
        mProgressView = vg.findViewById(R.id.getposts_progress);

        viewGroup = vg;
        layoutInflater = inflater;

        Button mAddButton = (Button) vg.findViewById(R.id.btnAddPost);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createStageCounter = 0;

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(container.getContext());
                final View mView = getLayoutInflater().inflate(R.layout.create_post, null);
                final TextView descTitle = mView.findViewById(R.id.textView3);
                final EditText mDescription = (EditText) mView.findViewById(R.id.etDesc);
                final TextView timeTitle = mView.findViewById(R.id.textView4);
                final DatePicker mDate = (DatePicker) mView.findViewById(R.id.etDate);
                final TimePicker mTime = (TimePicker) mView.findViewById(R.id.etTime);
                final Button mCreate = (Button) mView.findViewById(R.id.btnCreatePost);

                mDate.setVisibility(View.GONE);
                mTime.setVisibility(View.GONE);
                timeTitle.setVisibility(View.GONE);

                AlertDialog dialog;
                mCreate.setText("Next");
                mCreate.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(View v) {
                        createStageCounter++;
                        switch (createStageCounter) {
                            case 1:

                                ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getView().getWindowToken(), 0);
                                mDescription.setVisibility(View.GONE);
                                descTitle.setVisibility(View.GONE);
                                timeTitle.setVisibility(View.VISIBLE);
                                mDate.setVisibility(View.VISIBLE);
                                break;
                            case 2:
                                mDate.setVisibility(View.GONE);
                                mTime.setVisibility(View.VISIBLE);
                                mCreate.setText("Submit");
                                break;
                            case 3:
                                try {
                                    createPost(mDescription.getText().toString(), mDate, mTime);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                break;
                        }
                    }
                });
                mBuilder.setView(mView);
                dialog = mBuilder.create();
                dialog.show();
            }
        });

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

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void createPost(String description, DatePicker date, TimePicker time) throws JSONException {
        String url = getString(R.string.url) + "/api/posts";
        RequestQueue queue = Volley.newRequestQueue((getActivity()).getApplicationContext());

        JSONObject newPost = new JSONObject();

        int year = date.getYear();
        int month = date.getMonth();
        int day = date.getDayOfMonth();

        int hour = time.getHour();
        int minute = time.getMinute();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);

        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy hh:mm");
        String strDate = format.format(calendar.getTime());

        newPost.put("time", new JSONObject());
        newPost.getJSONObject("time").put("created", LocalTime.now());
        newPost.getJSONObject("time").put("lastModified", LocalTime.now());
        newPost.getJSONObject("time").put("walkOrder", strDate);

        newPost.put("walker", null);
        newPost.put("text", description);


        JsonObjectRequest JSONObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, newPost, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getActivity().getBaseContext(), "Post created!", Toast.LENGTH_LONG).show();
                        getPosts(viewGroup, layoutInflater);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getActivity().getBaseContext(), R.string.takenUser, Toast.LENGTH_LONG).show();
                    }
                });
        queue.add(JSONObjectRequest);
    }
}
