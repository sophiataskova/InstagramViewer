package com.example.sophiataskova.instagramviewer;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class CommentsActivity extends FragmentActivity {
    public static String KEY_COMMENTS_LIST = "comments";
    private ArrayList<Comment> commentsList;
    private CommentsAdapder aComments;
    private ListView mLvComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_comments);

        commentsList = new ArrayList<Comment>();

        mLvComments = (ListView) findViewById(R.id.lv_comments);

        commentsList = getIntent().getParcelableArrayListExtra(KEY_COMMENTS_LIST);
        aComments = new CommentsAdapder(this, commentsList);

        mLvComments.setAdapter(aComments);
    }
}
