package com.example.fragmentproject.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fragmentproject.R;
import com.example.fragmentproject.TransferDataActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentOne#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentOne extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private final int RECEIVE_CYCLE=101;
    private String TAG=getClass().getSimpleName();
    private TextView textView;
    private OnFragmentUpdateListener onFragmentUpdateListener;//用于向Activity回传信息
    private Button button;
    private EditText editText;

    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (RECEIVE_CYCLE == msg.what) {
                if (getArguments() != null) {
                    if (getArguments().getString("data") != null) {

                        Log.d(TAG, "handleMessage: " + getArguments().getString("data"));
                        textView.setText(getArguments().getString("data"));
                        getArguments().remove("data");
                    }
                }
            }
            handler.sendEmptyMessageDelayed(RECEIVE_CYCLE, 100);
            return false;
        }
    });

    public FragmentOne() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentOne.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentOne newInstance(String param1, String param2) {
        FragmentOne fragment = new FragmentOne();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: *********");
//        handler.sendEmptyMessageDelayed(RECEIVE_CYCLE,100);                                       //使用handler定时接收来自于Activity的信息
        View root=inflater.inflate(R.layout.fragment_one, container, false);
        textView=root.findViewById(R.id.show_info);
        TransferDataActivity parentActivity=(TransferDataActivity)getActivity();
        assert parentActivity != null;
        parentActivity.setFragmentUpdateListener(new TransferDataActivity.OnInformationUpdateListener() {       //使用listener接收来自于Activity的信息
            @Override
            public void onInformationUpdate(Fragment fragment, String data) {
                Log.d(TAG, "onFragmentUpdate: "+data);
                textView.setText(getArguments().getString("data"));
            }
        });
        // Inflate the layout for this fragment
        button=root.findViewById(R.id.btn_send_back);
        editText=root.findViewById(R.id.edit_text);
        button.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        if(R.id.btn_send_back==v.getId()){
            if(onFragmentUpdateListener!=null){
                onFragmentUpdateListener.onFragmentUpdate(editText.getText().toString().trim());        //发送信息给Activity
            }
        }
    }

    public interface OnFragmentUpdateListener{              //用于发送信息给Activity
         void onFragmentUpdate(String data);
    }

    public void setOnFragmentUpdateListener(OnFragmentUpdateListener onFragmentUpdateListener) {        //用于发送信息给Activity
        this.onFragmentUpdateListener = onFragmentUpdateListener;
    }

}