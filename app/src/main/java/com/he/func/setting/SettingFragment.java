package com.he.func.setting;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.he.config.HeTask;
import com.he.config.KeyConfig;
import com.lq.ren.chat.R;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SettingFragment extends Fragment{

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
