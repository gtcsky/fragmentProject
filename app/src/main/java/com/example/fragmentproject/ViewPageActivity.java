package com.example.fragmentproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.fragmentproject.adapter.UserViewAdapter;
import com.example.fragmentproject.fragments.ChannelFragment;

public class ViewPageActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener ,ViewPager.OnPageChangeListener{
    private String TAG=getClass().getSimpleName();
    private ViewPager viewPager;
    private UserViewAdapter viewAdapter;
    private FragmentManager fragmentManager;
    private RadioGroup radioGroup;
    private RadioButton chlBtn,msgBtn,setBtn,favBtn;
    private boolean isChlViewSelected,isMsgViewSelected,isFavViewSelected,isSetViewSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_page);
        bindView();
    }

    private void bindView(){
        fragmentManager=getSupportFragmentManager();
        viewPager=findViewById(R.id.view_page);
        viewAdapter=new UserViewAdapter(fragmentManager);
        viewPager.setAdapter(viewAdapter);
        radioGroup=findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(this);
        viewPager.addOnPageChangeListener(this);

        chlBtn=findViewById(R.id.channel_btn);
        msgBtn=findViewById(R.id.message_btn);
        setBtn=findViewById(R.id.setting_btn);
        favBtn=findViewById(R.id.favorite_btn);

        ((ChannelFragment)viewAdapter.getItem(1)).setOnFragmentUpdateListener((view, name, Value, Type) -> {
//            Log.d(TAG, "onFragmentUpdate: id="+view.getId());
            int id=view.getId();
            if(id==ChannelFragment.CHANNEL_BASIC){
                viewPager.setCurrentItem(0,true);
                viewAdapter.getItem(0);
                isChlViewSelected=false;
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.channel_btn) {
            if (!isChlViewSelected) {
                viewPager.setCurrentItem(1);
                isChlViewSelected=true;
            }
        } else if (checkedId == R.id.message_btn) {
            viewPager.setCurrentItem(2);
            isChlViewSelected=false;
        } else if (checkedId == R.id.setting_btn) {
            viewPager.setCurrentItem(4);
            isChlViewSelected=false;
        } else if (checkedId == R.id.favorite_btn) {
            viewPager.setCurrentItem(3);
            isChlViewSelected=false;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
//        viewPager.setCurrentItem(position);

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
        if (state == 2) {
            int currentItem = viewPager.getCurrentItem();
//            Log.d(TAG, "currentItem="+currentItem);
            switch (currentItem) {
                case 0:
                    radioGroup.check(-1);
                    isChlViewSelected=false;
                    break;
                case 1:
                    chlBtn.setChecked(true);                //设置Radio Button状态
                    break;
                case 2:
                    msgBtn.setChecked(true);
                    break;
                case 3:
                    favBtn.setChecked(true);
                    break;
                case 4:
                    setBtn.setChecked(true);
                    break;
            }
        }
    }
}