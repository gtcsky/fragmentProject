package com.example.fragmentproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.fragmentproject.fragments.ChannelFragment;
import com.example.fragmentproject.fragments.FavoriteFragment;
import com.example.fragmentproject.fragments.MessageFragment;
import com.example.fragmentproject.fragments.SettingFragment;

public class NavigationActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = getClass().getSimpleName();
    private TextView chlView, msgView, favoriteView, settingView;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Fragment channelFragment, messageFragment, favoriteFragment, settingFragment,mainFg;
    private boolean isChlViewSelected,isMsgViewSelected,isFavViewSelected,isSetViewSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        chlView = findViewById(R.id.txt_channel);
        msgView = findViewById(R.id.txt_message);
        favoriteView = findViewById(R.id.txt_favorite);
        settingView = findViewById(R.id.txt_setting);

        fragmentManager=getSupportFragmentManager();
        mainFg=fragmentManager.findFragmentById(R.id.fragment_context);



        chlView.setOnClickListener(this);
        msgView.setOnClickListener(this);
        favoriteView.setOnClickListener(this);
        settingView.setOnClickListener(this);
    }

    private void clearSelected() {
        chlView.setSelected(false);
        msgView.setSelected(false);
        favoriteView.setSelected(false);
        settingView.setSelected(false);
    }

    @Override
    public void onClick(View v) {
        fragmentTransaction = fragmentManager.beginTransaction();
        hideAllFragment(fragmentTransaction);
        if (v.getId() == R.id.txt_channel) {
            clearSelected();
            if(!isChlViewSelected) {
                chlView.setSelected(true);
                if (channelFragment == null) {
                    channelFragment = new ChannelFragment();
                    fragmentTransaction.add(R.id.fragment_layout, channelFragment);
                } else {
                    fragmentTransaction.show(channelFragment);
                }
                isChlViewSelected=true;
                isMsgViewSelected=false;
                isFavViewSelected=false;
                isSetViewSelected=false;
            }else{
                isChlViewSelected=false;
                chlView.setSelected(false);
                fragmentTransaction.show(mainFg);
            }
        } else if (v.getId() == R.id.txt_message) {
            clearSelected();
            if(!isMsgViewSelected) {
                msgView.setSelected(true);
                if (messageFragment == null) {
                    messageFragment = new MessageFragment();
                    fragmentTransaction.add(R.id.fragment_layout, messageFragment);
                } else {
                    fragmentTransaction.show(messageFragment);
                }
                isChlViewSelected=false;
                isMsgViewSelected=true;
                isFavViewSelected=false;
                isSetViewSelected=false;
            }else{
                isMsgViewSelected=false;
                msgView.setSelected(false);
                fragmentTransaction.show(mainFg);
            }

        } else if (v.getId() == R.id.txt_favorite) {
            clearSelected();
            if(!isFavViewSelected) {
                favoriteView.setSelected(true);
                if (favoriteFragment == null) {
                    favoriteFragment = new FavoriteFragment();
                    fragmentTransaction.add(R.id.fragment_layout, favoriteFragment);
                } else {
                    fragmentTransaction.show(favoriteFragment);
                }
                isChlViewSelected=false;
                isMsgViewSelected=false;
                isFavViewSelected=true;
                isSetViewSelected=false;
            }else{
                isFavViewSelected=false;
                favoriteView.setSelected(false);
                fragmentTransaction.show(mainFg);
            }
        } else if (v.getId() == R.id.txt_setting) {
            clearSelected();
            if (!isSetViewSelected) {
                settingView.setSelected(true);
                if (settingFragment == null) {
                    settingFragment = new SettingFragment();
                    fragmentTransaction.add(R.id.fragment_layout, settingFragment);
                } else {
                    fragmentTransaction.show(settingFragment);
                }
                isChlViewSelected=false;
                isMsgViewSelected=false;
                isFavViewSelected=false;
                isSetViewSelected=true;
            }else{
                isSetViewSelected=false;
                settingView.setSelected(false);
                fragmentTransaction.show(mainFg);
            }
        }
        fragmentTransaction.commit();
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