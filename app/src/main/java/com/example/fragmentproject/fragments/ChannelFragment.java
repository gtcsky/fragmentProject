package com.example.fragmentproject.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import com.example.fragmentproject.R;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChannelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChannelFragment extends Fragment  implements View.OnClickListener {
    private String TAG = getClass().getSimpleName();
    private GridLayout gridLayout;
    private String[] chs = {"年度纪事", "热点", "推荐", "同城", "关注", "金融", "军事", "科技", "航空", "情感", "农业", "教育", "文学", "直播", "历史", "人文", "民生", "交通", "游戏", "邮箱"};
    private OnFragmentUpdateListener mOnFragmentUpdateListener;
    public final static int TYPE_CLICK = 1001;
    public final static int CHANNEL_BASIC=1008600;
    private View root;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChannelFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChannelFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChannelFragment newInstance(String param1, String param2) {
        ChannelFragment fragment = new ChannelFragment();
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
        if (root == null)
            root = inflater.inflate(R.layout.fragment_channel, container, false);

        gridLayout = root.findViewById(R.id.grid_layout);

        gridLayout.post(() -> {
            int mWidth = gridLayout.getWidth() / 5;
            int mHeight = gridLayout.getHeight() / 4;
            for (int i = 0; i < 20; i++) {
                TextView textView = new TextView(getContext());
                textView.setText(chs[i]);
                textView.setGravity(Gravity.CENTER);
                textView.setTextColor(Color.WHITE);
                textView.setId(CHANNEL_BASIC + i);
                textView.setOnClickListener(ChannelFragment.this);

                gridLayout.addView(textView, mWidth, mHeight);
            }
        });
        return root;
    }

    @Override
    public void onClick(View v) {
        if (mOnFragmentUpdateListener != null) {
            mOnFragmentUpdateListener.onFragmentUpdate(v, null, null, TYPE_CLICK);
        }else{
            Log.d(TAG, "mOnFragmentUpdateListener==null");
        }
    }

    public interface OnFragmentUpdateListener {
        public void onFragmentUpdate(View view, String name, String Value, int Type);
    }

    public void setOnFragmentUpdateListener(OnFragmentUpdateListener mOnFragmentUpdateListener) {
        this.mOnFragmentUpdateListener = mOnFragmentUpdateListener;
    }
}