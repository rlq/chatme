package com.he.func.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.he.config.HeTask;
import com.lq.ren.chat.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SettingFragment extends Fragment {

    View view ;
    private static SettingFragment instance;
    public static SettingFragment getInstance(){
        if(instance == null){
            instance = new SettingFragment();
        }
        return instance;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.he_account, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.tv_my_username)
    public void onClick(){
        HeTask.getInstance().startVideoActivity(getContext());

    }
}
