package com.example.fragmentproject.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.fragmentproject.fragments.ChannelFragment;
import com.example.fragmentproject.fragments.ContextFragment;
import com.example.fragmentproject.fragments.FavoriteFragment;
import com.example.fragmentproject.fragments.MessageFragment;
import com.example.fragmentproject.fragments.SettingFragment;

import java.util.ArrayList;
import java.util.List;

public class UserViewAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments=new ArrayList<>();
    public UserViewAdapter(@NonNull FragmentManager fm) {
        super(fm);
        fragments.add(null);
        fragments.add(null);
        fragments.add(null);
        fragments.add(null);
        fragments.add(null);
    }

    public UserViewAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (fragments.get(0) == null) {
                    fragments.set(0, new ContextFragment());
                }
                return fragments.get(0);
            case 1:
                if (fragments.get(1) == null) {
                    fragments.set(1, new ChannelFragment());
                }
                return fragments.get(1);
            case 2:
                if (fragments.get(2) == null) {
                    fragments.set(2, new MessageFragment());
                }
                return fragments.get(2);
            case 3:
                if (fragments.get(3) == null) {
                    fragments.set(3, new FavoriteFragment());
                }
                return fragments.get(3);
            case 4:
                if (fragments.get(4) == null) {
                    fragments.set(4, new SettingFragment());
                }
                return fragments.get(4);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 5;
    }
}
