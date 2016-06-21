package com.he.func.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.he.base.HePresenter;
import com.lq.ren.chat.R;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 下拉列表
 */
public class PopAdapter extends SimpleAdapter {

    private List<HashMap<String, Object>> data;
    private final Context context;
    private final HePresenter presenter;
    private int layoutId;
    public PopAdapter(Context context, List<HashMap<String, Object>> data, HePresenter presenter,
                      int resource, String[] from,  int[] to) {
        super(context, data, resource, from, to);
        this.data = data;
        this.presenter = presenter;
        this.context = context;
         this.layoutId = resource;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(layoutId, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv.setText(data.get(position).get("name").toString());
        holder.tv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String[] arg0 = presenter.getArg0();
                presenter.setText(arg0[position],
                        presenter.getArg1ByArg0(arg0[position]));

            }
        });
        return convertView;
    }
    static class ViewHolder {
        @BindView(R.id.droptv)
        public TextView tv;

        public ViewHolder(View view){
            super();
            ButterKnife.bind(this,view);
        }
    }

}


