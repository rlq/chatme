package com.he.func.find;


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

public class FriendCircleFragment extends Fragment implements View.OnClickListener {

    private View view;

    private static FriendCircleFragment mInstance;

    public static FriendCircleFragment getInstance() {
        if (mInstance == null) {
            mInstance = new FriendCircleFragment();
        }
        return mInstance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.he_friendcircle, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.friendCircle_open,R.id.friendCircle_shake})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.friendCircle_open:
                HeTask.getInstance().startFriendCircle(getContext());
                break;
            case R.id.friendCircle_shake:
                HeTask.getInstance().startShake(getContext());
                break;
        }
    }
}
