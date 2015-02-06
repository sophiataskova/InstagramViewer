package com.example.sophiataskova.instagramviewer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {

    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, R.layout.item_photo, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstagramPhoto photo = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }
        TextView tvUsername = (TextView)convertView.findViewById(R.id.username);
        ImageView imgProfilePic = (ImageView)convertView.findViewById(R.id.profile_photo);
        TextView tvCaption = (TextView)convertView.findViewById(R.id.image_caption);
        ImageView imgPhoto = (ImageView)convertView.findViewById(R.id.image_photo);
        TextView likesCount = (TextView)convertView.findViewById(R.id.num_likes);
        LinearLayout topCommentsList = (LinearLayout)convertView.findViewById(R.id.top_comments);
        topCommentsList.removeAllViews();

        for (Comment comment : photo.topComments) {
            LinearLayout line = (LinearLayout)LayoutInflater.from(getContext()).inflate(R.layout.comment, null);
            ((TextView)line.findViewById(R.id.comment_username)).setText(comment.getUsername());
            ((TextView)line.findViewById(R.id.comment_text)).setText(comment.getCommentString());
            topCommentsList.addView(line);
        }

        likesCount.setText(Integer.toString(photo.likesCount));
        tvUsername.setText(photo.username);
        tvCaption.setText(photo.caption);
        imgProfilePic.setImageResource(0);
        imgPhoto.getLayoutParams().height = photo.imageHeight;
        imgPhoto.setImageResource(0);

        Picasso.with(getContext()).load(photo.imageUrl).placeholder(R.drawable.ic_launcher).into(imgPhoto);
        Picasso.with(getContext()).load(photo.profilePicUrl).into(imgProfilePic);
        return convertView;
    }
}
