package com.greymat9er.shutup;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {

    private List<ChatList> fakeData;
    private RecyclerView recyclerView;
    private View view;

    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        generateFakeData();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_chat, container, false);

        //configuring RecyclerView
        recyclerViewConfig();

        return view;
    }

    private void recyclerViewConfig() {
        recyclerView = (RecyclerView) view.findViewById(R.id.chat_list_recycler_view);
        ChatRecyclerViewAdapter chatRecyclerViewAdapter = new ChatRecyclerViewAdapter(getContext(), fakeData);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(chatRecyclerViewAdapter);
    }

    private void generateFakeData() {
        fakeData = new ArrayList<>();

        fakeData.add(new ChatList("World Chat", R.drawable.person));
        fakeData.add(new ChatList("World Chat 2", R.drawable.profile_pic_circle));
        fakeData.add(new ChatList("World Chat 3", R.drawable.person));
        fakeData.add(new ChatList("World Chat 5", R.drawable.profile_pic_circle));
        fakeData.add(new ChatList("World Chat 6", R.drawable.person));
        fakeData.add(new ChatList("World Chat 7", R.drawable.profile_pic_circle));
        fakeData.add(new ChatList("World Chat 8", R.drawable.person));
        fakeData.add(new ChatList("World Chat 9", R.drawable.profile_pic_circle));
        fakeData.add(new ChatList("World Chat 10", R.drawable.person));
        fakeData.add(new ChatList("World Chat", R.drawable.person));
        fakeData.add(new ChatList("World Chat 2", R.drawable.profile_pic_circle));
        fakeData.add(new ChatList("World Chat 3", R.drawable.person));
        fakeData.add(new ChatList("World Chat 5", R.drawable.profile_pic_circle));
        fakeData.add(new ChatList("World Chat 6", R.drawable.person));
        fakeData.add(new ChatList("World Chat 7", R.drawable.profile_pic_circle));
        fakeData.add(new ChatList("World Chat 8", R.drawable.person));
        fakeData.add(new ChatList("World Chat 9", R.drawable.profile_pic_circle));
        fakeData.add(new ChatList("World Chat 10", R.drawable.person));
    }
}