package com.example.computlearn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;



import java.util.List;

public class TopicAdapter extends ArrayAdapter<Topic> {

    //topic adapter comstructor

    public TopicAdapter(Context context, int resource, List<Topic> topicList) {
        super(context, resource, topicList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //displaying the results of the modules in the listview with the specific layout of topic_cell and their variables within the constructor
        Topic topic = getItem(position);
        if(convertView==null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.topic_cell, parent, false);
        }
        TextView topicTitle = (TextView) convertView.findViewById(R.id.topicTitle);
        ImageView topicImage = (ImageView) convertView.findViewById(R.id.topicImage);
        topicTitle.setText(topic.getName());
        topicImage.setImageResource(topic.getImg());
        return convertView;
    }
}
