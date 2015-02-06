package com.example.sophiataskova.instagramviewer;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class PhotosActivity extends FragmentActivity {

    public static final String CLIENT_ID = "1f10807b32284294bedf56273ccd3627";
    private ArrayList<InstagramPhoto> photos;

    private InstagramPhotosAdapter aPhotos;

    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView seeAllButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        photos = new ArrayList<InstagramPhoto>();

        aPhotos = new InstagramPhotosAdapter(this, photos);

        seeAllButton = (TextView) findViewById(R.id.view_all_comments_btn);

        ListView lvPhotos = (ListView) findViewById(R.id.lv_photos);
        lvPhotos.setAdapter(aPhotos);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchPopularPhotos();
            }
        });
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        fetchPopularPhotos();
//        showCommentsDialog();
    }

//    public void showCommentsDialog() {
//        FragmentManager fm = getSupportFragmentManager();
//        CommentsListFragment commentsListFragment = CommentsListFragment.newInstance("Some Title");
//        commentsListFragment.show(fm, "fragment_edit_name");
//    }

    private void fetchPopularPhotos() {

        String popularUrl = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(popularUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray photosJson;
                try {
                    photos.clear();
                    photosJson = response.getJSONArray("data");

                    for (int i = 0; i < photosJson.length(); i++) {
                        JSONObject photoJSON = photosJson.getJSONObject(i);
                        final InstagramPhoto instagramPhoto = new InstagramPhoto();

                        instagramPhoto.topComments = new ArrayList<Comment>();
                        instagramPhoto.allComments = new ArrayList<Comment>();

                        instagramPhoto.username = photoJSON.getJSONObject("user").getString("username");

                            if (!photoJSON.isNull("caption")) {
                                instagramPhoto.caption = photoJSON.getJSONObject("caption").getString("text");
                            }
                            instagramPhoto.imageHeight = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
                            instagramPhoto.imageUrl = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                            instagramPhoto.likesCount = photoJSON.getJSONObject("likes").getInt("count");
                            instagramPhoto.username = photoJSON.getJSONObject("user").getString("username");
                            instagramPhoto.profilePicUrl = photoJSON.getJSONObject("user").getString("profile_picture");



                            for (int j = 0; j < 2; j++) {
                                String commenter = photoJSON.getJSONObject("comments").getJSONArray("data").getJSONObject(j).getJSONObject("from").getString("username");
                                String comment =  photoJSON.getJSONObject("comments").getJSONArray("data").getJSONObject(j).getString("text");
                                instagramPhoto.topComments.add(new Comment(commenter, comment));
                            }

                        for (int j = 0; j < photoJSON.getJSONObject("comments").getJSONArray("data").length(); j++) {
                            String commenter = photoJSON.getJSONObject("comments").getJSONArray("data").getJSONObject(j).getJSONObject("from").getString("username");
                            String comment =  photoJSON.getJSONObject("comments").getJSONArray("data").getJSONObject(j).getString("text");
                            instagramPhoto.allComments.add(new Comment(commenter, comment));
                        }

                        photos.add(instagramPhoto);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                aPhotos.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
