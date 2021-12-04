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
        messages.add("系统消息:注册成功.");
        messages.add("系统消息:请完成实名登记.");
        messages.add("好友申请:系统客服申请成为你的好友.");
        messages.add("系统推送:横琴新区挂牌成立.");
        messages.add("系统推送:长沙货拉拉案件一审宣判.");
        messages.add("系统推送:全运会圆满结束.");
        messages.add("系统推送:国庆临近,福建疫情仍未见明显好转.");
        messages.add("系统推送:中美启动新一轮贸易对话.");
        messages.add("系统推送:二季度我国进出口贸易额剧增.");
        messages.add("系统推送:第九批志愿军遗体归国.");
        messages.add("系统推送:电力供应紧张,部分省市限电.");
        messages.add("系统推送:中国开放三胎政策.");
        messages.add("系统推送:东京奥运会苏炳添创造黄种人短跑新纪录.");
        messages.add("系统推送:今年高温持续时间相比往原有较明显增长.");
        messages.add("系统推送:我国宣布今后不再在中国以外新建立热电项目.");
        messages.add("系统推送:滴滴所有APP被要求下架,接受审查.");
        messages.add("系统推送:我国首例,特斯拉被判退一赔三.");
        messages.add("系统推送:我国宣布停止进口台湾雾莲等水果.");

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