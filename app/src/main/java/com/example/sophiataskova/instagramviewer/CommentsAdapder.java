package com.example.sophiataskova.instagramviewer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CommentsAdapder extends ArrayAdapter<Comment> {

    public CommentsAdapder(Context context, List<Comment> objects) {
        super(context, R.layout.comment, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Comment comment = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.comment, parent, false);
        }
        TextView tvUsername = (TextView)convertView.findViewById(R.id.comment_username);
        TextView tvComment = (TextView)convertView.findViewById(R.id.comment_text);

        tvUsername.setText(comment.username);
        tvComment.setText(comment.commentString);

        return convertView;
    }
}