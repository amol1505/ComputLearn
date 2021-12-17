package com.example.computlearn;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    //initiate arraylist
    public static ArrayList<Topic> topicList = new ArrayList<Topic>();
    //search view and list view which are displayed in layout
    SearchView searchView;
    private ListView topicListView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        //initiating views
        topicListView = v.findViewById(R.id.topicsListView);
        searchView = v.findViewById(R.id.topicSearch);
        //resetting topics so there are no duplicates and adding it to the topiclist as a topic object
        topicList.clear();
        Topic topic1 = new Topic("0", "Programming", R.drawable.ic_baseline_code_24);
        topicList.add(topic1);
        Topic topic2 = new Topic("1", "AI", R.drawable.ic_baseline_adb_24);
        topicList.add(topic2);
        Topic topic3 = new Topic("2", "Technologies", R.drawable.ic_baseline_computer_24);
        topicList.add(topic3);
        Topic topic4 = new Topic("3", "Cyber Security", R.drawable.ic_baseline_security_24);
        topicList.add(topic4);
        Topic topic5 = new Topic("4", "Quiz", R.drawable.ic_baseline_school_24);
        topicList.add(topic5);
        //creating topicadapter object and setting it as an adapter to the listview
        TopicAdapter topicAdapter = new TopicAdapter(getActivity(), 0, topicList);
        topicListView.setAdapter(topicAdapter);

        //query text listener to filter the results of whatever a user enters utilising a new arraylist to add the filtered results containing users input
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Topic> filteredTopics = new ArrayList<Topic>();
                for(Topic topic: topicList) {
                    if (topic.getName().toLowerCase().contains(newText.toLowerCase())) {
                        filteredTopics.add(topic);
                    }
                }
                TopicAdapter adapter = new TopicAdapter(getActivity(), 0, filteredTopics);
                topicListView.setAdapter(adapter);
                return false;
            }
        });
        //modules directing to their specific fragment depending on which module is clicked
        topicListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (id==0) {
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProgrammingFragment()).commit();
                }
                else if (id==1) {
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, new ArtificialIntelligenceFragment()).commit();
                }
                else if (id==2) {
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, new TechnologiesFragment()).commit();
                }
                else if (id==3) {
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, new CyberSecFragment()).commit();
                }
                else if (id==4) {
                    startActivity(new Intent(getContext(), QuizActivity.class));
                }
            }
        });
        return v;
    }


}
