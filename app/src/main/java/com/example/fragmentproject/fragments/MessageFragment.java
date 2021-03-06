package com.example.fragmentproject.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.fragmentproject.R;
import com.example.fragmentproject.adapter.MessageAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageFragment extends Fragment {
    private ListView listView;
    private ArrayList<String> messages;
    private MessageAdapter listAdapter;
    private String TAG=getClass().getSimpleName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MessageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MessageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MessageFragment newInstance(String param1, String param2) {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_message, container, false);
        messages=new ArrayList<>();
        messages.add("????????????:????????????.");
        messages.add("????????????:?????????????????????.");
        messages.add("????????????:????????????????????????????????????.");
        messages.add("????????????:????????????????????????.");
        messages.add("????????????:?????????????????????????????????.");
        messages.add("????????????:?????????????????????.");
        messages.add("????????????:????????????,?????????????????????????????????.");
        messages.add("????????????:?????????????????????????????????.");
        messages.add("????????????:???????????????????????????????????????.");
        messages.add("????????????:??????????????????????????????.");
        messages.add("????????????:??????????????????,??????????????????.");
        messages.add("????????????:????????????????????????.");
        messages.add("????????????:??????????????????????????????????????????????????????.");
        messages.add("????????????:??????????????????????????????????????????????????????.");
        messages.add("????????????:????????????????????????????????????????????????????????????.");
        messages.add("????????????:????????????APP???????????????,????????????.");
        messages.add("????????????:????????????,???????????????????????????.");
        messages.add("????????????:?????????????????????????????????????????????.");

        listView=root.findViewById(R.id.message_list);
        listAdapter=new MessageAdapter(getContext(),R.layout.item_message_layout);
        listView.setAdapter(listAdapter);
        listAdapter.setData(messages);
        listAdapter.setOnItemClickListener(new MessageAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                Log.d(TAG, "onItemClickListener: "+position);
            }
        });
        return root;
    }
}