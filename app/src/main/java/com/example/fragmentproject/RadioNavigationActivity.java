package com.example.fragmentproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.fragmentproject.fragments.ChannelFragment;
import com.example.fragmentproject.fragments.FavoriteFragment;
import com.example.fragmentproject.fragments.MessageFragment;
import com.example.fragmentproject.fragments.SettingFragment;

public class RadioNavigationActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG=getClass().getSimpleName();
    private FragmentManager fragmentManager;
    private  FragmentTransaction fragmentTransaction;
    private RadioGroup radioGroup;
    private RadioButton chlBtn,msgBtn,setBtn,favBtn;
    private Fragment channelFragment, messageFragment, favoriteFragment, settingFragment,mainFg;
    private boolean isChlViewSelected,isMsgViewSelected,isFavViewSelected,isSetViewSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_navigation);
        bindView();
    }

    private void bindView(){
        fragmentManager = getSupportFragmentManager();

        radioGroup=findViewById(R.id.radio_group);
        chlBtn=findViewById(R.id.channel_btn);
        msgBtn=findViewById(R.id.message_btn);
        setBtn=findViewById(R.id.setting_btn);
        favBtn=findViewById(R.id.favorite_btn);
        mainFg=fragmentManager.findFragmentById(R.id.main_fragment);        //获取当前Activity中的Fragment

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            fragmentTransaction = fragmentManager.beginTransaction();
            if (checkedId == R.id.channel_btn) {
                if (chlBtn.isChecked()) {
                    hideAllFragment(fragmentTransaction);
                    if (channelFragment == null) {
                        channelFragment = new ChannelFragment();
                        fragmentTransaction.add(R.id.fragment_layout, channelFragment);
                    } else {
                        fragmentTransaction.show(channelFragment);
                    }
                    ((ChannelFragment) channelFragment).setOnFragmentUpdateListener(new ChannelFragment.OnFragmentUpdateListener() {
                        @Override
                        public void onFragmentUpdate(View view, String name, String Value, int Type) {
                            if (view.getId() == ChannelFragment.CHANNEL_BASIC) {
                                fragmentTransaction = fragmentManager.beginTransaction();
                                hideAllFragment(fragmentTransaction);
                                fragmentTransaction.show(mainFg);
                                fragmentTransaction.commit();               //提交事务
                                chlBtn.setChecked(false);
                                group.clearCheck();
                            }
                        }
                    });
                } else {
                    fragmentTransaction.commit();               //提交事务
                    return;
                }
            } else if (checkedId == R.id.message_btn) {
                Log.d(TAG, "msgBtn Stts: " + msgBtn.isChecked());
                hideAllFragment(fragmentTransaction);
                if (messageFragment == null) {
                    messageFragment = new MessageFragment();
                    fragmentTransaction.add(R.id.fragment_layout, messageFragment);
                } else {
                    fragmentTransaction.show(messageFragment);
                }
            } else if (checkedId == R.id.setting_btn) {
                hideAllFragment(fragmentTransaction);
                if (settingFragment == null) {
                    settingFragment = new SettingFragment();
                    fragmentTransaction.add(R.id.fragment_layout, settingFragment);
                } else {
                    fragmentTransaction.show(settingFragment);
                }
            } else if (checkedId == R.id.favorite_btn) {
                hideAllFragment(fragmentTransaction);
                if (favoriteFragment == null) {
                    favoriteFragment = new FavoriteFragment();
                    fragmentTransaction.add(R.id.fragment_layout, favoriteFragment);
                } else {
                    fragmentTransaction.show(favoriteFragment);
                }
            }
            fragmentTransaction.commit();               //提交事务
        });
    }


    @Override
    public void onClick(View v) {

    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (channelFragment != null) {
            fragmentTransaction.hide(channelFragment);
//            isChlViewSelected=false;
        }
        if (messageFragment != null) {
            fragmentTransaction.hide(messageFragment);
//            isMsgViewSelected=false;
        }
        if (favoriteFragment != null) {
            fragmentTransaction.hide(favoriteFragment);
//            isFavViewSelected=false;
        }
        if (settingFragment != null) {
            fragmentTransaction.hide(settingFragment);
//            isSetViewSelected=false;
        }
        if (mainFg != null) {
            fragmentTransaction.hide(mainFg);
        }
    }

}