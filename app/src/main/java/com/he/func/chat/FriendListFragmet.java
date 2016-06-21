package com.he.func.chat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.he.data.friendcircle.Friend;
import com.he.func.chat.FriendList.FriendAdapter;
import com.he.util.Utils;
import com.lq.ren.chat.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;


public class FriendListFragmet extends Fragment {

    private static FriendListFragmet mInstance;
    @BindView(R.id.add_friendName)
    EditText friendName;
    @BindView(R.id.friend_list)
    ListView mListView;

    @OnClick(R.id.add_btn)
    public void onClick(View view){
        addFriend(friendName.getText().toString());
    }

    private View view;
    private ChatPresenter presenter;
    private FriendAdapter adapter;
    private List<Friend> friList = new ArrayList<>();

    public static FriendListFragmet getInstance() {
        if (mInstance == null) {
            mInstance = new FriendListFragmet();
        }
        return mInstance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.he_friendlist, null);
        ButterKnife.bind(this, view);
        presenter = new ChatPresenter(getContext());
        friList = presenter.getChatUserData();
        adapter = new FriendAdapter(getContext());//new ArrayAdapter<Friend>(getContext(), R.layout.he_item_friend, friList);
        adapter.setData(friList);
        mListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String target = (String) mListView.getAdapter().getItem(position);
                RongIM.getInstance().startPrivateChat(getContext(), target, "" + friList.get(position).getNickname());// String.valueOf(target)
            }
        });

//        view.findViewById(R.id.add_btn).setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addFriend(friendName.getText().toString());
//            }
//        });
        //refreshFriendList();

        return view;
//		view = inflater.inflate(R.layout.he_confriend, null);
//		mBtn = (Button)view.findViewById(R.id.btn);
//		mBtn.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				//if(RongIM.getInstance() != null){
//					Log.v(KeyConfig.TAG_NAME, "haoyou");
//					RongIM.getInstance().startPrivateChat(getContext(), "ren123", "我和你");
//				//}
//			}
//		});


    }

//	private void refreshFriendList(){
//		AsyncHttpClient client = new AsyncHttpClient();
//		RequestParams params = new RequestParams();
//		params.add("username", "ren1234");
//		client.post("", params, new AsyncHttpResponseHandler(){});
//
//			public void onFailure(String content) {
//				String response = new String(bytes);
//                Log.e("debug", response);
//                try {
//                    JSONArray array = new JSONArray(response);
//                    list.clear();
//                    for(int j = 0; j < array.length(); j++) {
//                        list.add((String)array.get(j));
//                    }
//                    adapter.notifyDataSetChanged();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//			};
//
//			public void onFailure(Throwable arg3) {
//				Toast.makeText("", "网络错误，请稍后重试", Toast.LENGTH_SHORT).show();
//			};
//		});
//	}

    public void addFriend(String userName) {
        if(userName == null || userName.equals("")) {
            Utils.showTips(getContext(),"请输入帐号");
            return;
        }
        Utils.hideSoftInput(getContext(), friendName);
        if (presenter.chackHasUser(getContext(), userName)) {
            adapter.setData(presenter.getAddChatUser());
            adapter.notifyDataSetChanged();
        }
        /**final HashMap<String,String> params = new HashMap<>();
         params.put("","");
         params.put("","");

         StringRequest request = new StringRequest(Request.Method.POST,
         "url",new Response.Listener<String>() {
         public void onResponse(String response) {
         //Toast.makeText(MainActivity.this, "ok ", 1).show();
         }
         }, new Response.ErrorListener() {
         public void onErrorResponse(VolleyError error) {
         //Toast.makeText(MainActivity.this, "fail ", 0).show();
         }
         }){
         protected java.util.Map<String,String> getParams() throws AuthFailureError {
         //				HashMap<String, String> map = new HashMap<String, String>();
         //				map.put("id", "");
         //				map.put("message", "13454678");
         return params;
         };
         };
         request.setTag("addFriend");
         HeApplication.getHttpQueue().add(request);*/
    }


    @Override
    public void onStop() {
        super.onStop();
    }

    //public class FriendAdapter2 extends


}
