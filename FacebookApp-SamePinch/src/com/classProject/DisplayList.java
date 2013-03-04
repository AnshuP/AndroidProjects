package com.classProject;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DisplayList extends Activity implements OnItemClickListener {
    private Handler mHandler;

    protected ListView friendsList;
    protected static JSONArray jsonArray;
    protected String graph_or_fql;
    ArrayList<String> uid;
    ArrayList<String> name;
    ArrayList<String> bday;
    ArrayList<Integer> index;

    /*
     * Layout the friends' list
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHandler = new Handler();
        setContentView(R.layout.friends_list);

        Bundle extras = getIntent().getExtras();
         name = new ArrayList<String>();
        name = extras.getStringArrayList("name");
         bday = new ArrayList<String>();
        bday = extras.getStringArrayList("bday");
         uid = new ArrayList<String>();
        uid = extras.getStringArrayList("uid");
         index = new ArrayList<Integer>();
        index = extras.getIntegerArrayList("index");
        
      /*  graph_or_fql = extras.getString("METHOD");
        try {
            if (graph_or_fql.equals("graph")) {
                jsonArray = new JSONObject(apiResponse).getJSONArray("data");
            } else {
                jsonArray = new JSONArray(apiResponse);
            }
        } catch (JSONException e) {
            showToast("Error: " + e.getMessage());
            return;
        }*/
        
        friendsList = (ListView) findViewById(R.id.friends_list);
        friendsList.setOnItemClickListener(this);
        friendsList.setAdapter(new FriendListAdapter(this));

       // showToast(getString(R.string.can_post_on_wall));
    }

    /*
     * Clicking on a friend should popup a dialog for user to post on friend's
     * wall.
     */
   // @Override
    @SuppressWarnings("deprecation")
    public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
      /*  try {
            final long friendId;
            if (graph_or_fql.equals("graph")) {
                friendId = jsonArray.getJSONObject(position).getLong("id");
            } else {
                friendId = jsonArray.getJSONObject(position).getLong("uid");
            }
            String name = jsonArray.getJSONObject(position).getString("name");

            new AlertDialog.Builder(this).setTitle(R.string.post_on_wall_title)
                    .setMessage(String.format(getString(R.string.post_on_wall), name))
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                      //  @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Bundle params = new Bundle();
                            /*
                             * Source Tag: friend_wall_tag To write on a friend's wall, 
                             * provide friend's UID in the 'to' parameter.
                             * More info on feed dialog:
                             * https://developers.facebook.com/docs/reference/dialogs/feed/
                             */
                      /*      params.putString("to", String.valueOf(friendId));
                            params.putString("caption", getString(R.string.app_name));
                            params.putString("description", getString(R.string.app_desc));
                            params.putString("picture", Utility.HACK_ICON_URL);
                            params.putString("name", getString(R.string.app_action));
                            Utility.mFacebook.dialog(FriendsList.this, "feed", params,
                                    new PostDialogListener());
                        }

                    }).setNegativeButton(R.string.no, null).show();
        } catch (JSONException e) {
            showToast("Error: " + e.getMessage());
        }*/
    } 

    /*
     * Callback after the message has been posted on friend's wall.
     */
  /*  public class PostDialogListener extends BaseDialogListener {
      //  @Override
        public void onComplete(Bundle values) {
            final String postId = values.getString("post_id");
            if (postId != null) {
                showToast("Message posted on the wall.");
            } else {
                showToast("No message posted on the wall.");
            }
        }
    }*/

    public void showToast(final String msg) {
        mHandler.post(new Runnable() {
          //  @Override
            public void run() {
                Toast toast = Toast.makeText(DisplayList.this, msg, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    /**
     * Definition of the list adapter
     */
    public class FriendListAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        DisplayList friendsList;

        public FriendListAdapter(DisplayList friendsList) {
            this.friendsList = friendsList;
           /* if (Utility.model == null) {
                Utility.model = new FriendsGetProfilePics();
            }
            Utility.model.setListener(this); */
            mInflater = LayoutInflater.from(friendsList.getBaseContext());
        }

       // @Override
        public int getCount() {
           // return jsonArray.length();
        	return uid.size();
        }

       // @Override
        public Object getItem(int position) {
            return null;
        }

       // @Override
        public long getItemId(int position) {
            return 0;
        }

       // @Override
        public View getView(int position, View convertView, ViewGroup parent) {
          /*  JSONObject jsonObject = null;
            try {
                jsonObject = jsonArray.getJSONObject(position);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }*/
        	Log.v("getView","position : "+position);
        	
            View hView = convertView;
            if (convertView == null) {
                hView = mInflater.inflate(R.layout.friend_item, null);
                ViewHolder holder = new ViewHolder();
                holder.profile_pic = (ImageView) hView.findViewById(R.id.profile_pic);
                holder.name = (TextView) hView.findViewById(R.id.name);
                holder.info = (TextView) hView.findViewById(R.id.info);
                hView.setTag(holder);
            }

            ViewHolder holder = (ViewHolder) hView.getTag();
          /*  try {
                if (graph_or_fql.equals("graph")) {
                    holder.profile_pic.setImageBitmap(Utility.model.getImage(
                            jsonObject.getString("id"), jsonObject.getString("picture")));
                } else {
                    holder.profile_pic.setImageBitmap(Utility.model.getImage(
                            jsonObject.getString("uid"), jsonObject.getString("pic_square")));
                }
            } catch (JSONException e) {
                holder.name.setText("");
            }*/
            try {
               // holder.name.setText(jsonObject.getString("name"));
            	//if(position < index.size()) {
            		//Integer idx = index.get(position);
            		//int i = idx.intValue();
            		holder.name.setText(name.get(position));
            		holder.info.setText(bday.get(position));
            	//}
            } catch (Exception e) {
                holder.name.setText("");
                Log.v("log","Inside exception");
                Log.v("exc",e.getMessage());
            }
           /* try {
                if (position < index.size()) {
                    holder.info.setText(jsonObject.getJSONObject("location").getString("name"));
                } else {
                    JSONObject location = jsonObject.getJSONObject("current_location");
                    holder.info.setText(location.getString("city") + ", "
                            + location.getString("state"));
                }

            } catch (Exception e) {
                holder.info.setText("");
            }*/
            return hView;
        }

    }

    class ViewHolder {
        ImageView profile_pic;
        TextView name;
        TextView info;
    }
}
