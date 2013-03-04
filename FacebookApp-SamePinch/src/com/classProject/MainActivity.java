package com.classProject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
//import com.facebook.FacebookActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookOperationCanceledException;
import com.facebook.Request;
import com.facebook.Request.Callback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionLoginBehavior;
import com.facebook.SessionState;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.R;
import com.facebook.android.Util;
import com.facebook.model.GraphUser;
import com.facebook.widget.FriendPickerFragment;
import com.facebook.widget.LoginButton;
import com.facebook.widget.PickerFragment;
import com.facebook.widget.PlacePickerFragment;
import com.facebook.widget.ProfilePictureView;

public class MainActivity extends Activity {

	 @SuppressWarnings("serial")
	   // private static final List<String> PERMISSIONS = new ArrayList<String>() {{
	    //    add("publish_actions");
	   // }};

	    public static final String APP_ID = "523618874324530";
	    
	    private final int REAUTHORIZE_ACTIVITY = 3;
	    private final String PENDING_ACTION_BUNDLE_KEY = "com.facebook.samples.hellofacebook:PendingAction";

	    private Button postStatusUpdateButton;
	    private Button postPhotoButton;
	    private Button pickFriendsButton;
	    private Button pickPlaceButton;
	    private LoginButton loginButton;
	    private ProfilePictureView profilePictureView;
	    private TextView greeting;
	    private PendingAction pendingAction = PendingAction.NONE;
	    private ViewGroup controlsContainer;
	    String[] permissions = { "offline_access", "user_photos", 
        "friends_birthday", "user_birthday" };
	    public static Facebook mFacebook;
	    public static Session session;
	    final static int AUTHORIZE_ACTIVITY_RESULT_CODE = 0;
	   

	    private final Location SEATTLE_LOCATION = new Location("") {
	        {
	            setLatitude(47.6097);
	            setLongitude(-122.3331);
	        }
	    };
	    private GraphUser user;

	    private enum PendingAction {
	        NONE,
	        POST_PHOTO,
	        POST_STATUS_UPDATE
	    }

	    /**
	     * Called when the activity is first created.
	     */
	    @SuppressWarnings("deprecation")
		@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);

	        mFacebook = new Facebook(APP_ID);
	        session = Session.getActiveSession();
	        if (session == null) {
	        	session = new Session(this.getApplicationContext());
	        }
	        mFacebook.setAccessToken(session.getAccessToken());
	        Date now = new Date();
	        mFacebook.setTokenFromCache(session.getAccessToken(), session.getExpirationDate().getTime(), now.getTime());
	        mFacebook.authorize(this, permissions, new Facebook.DialogListener() {
				
				public void onFacebookError(FacebookError e) {
					// TODO Auto-generated method stub
					
				}
				
				public void onError(DialogError e) {
					// TODO Auto-generated method stub
					
				}
				
				public void onComplete(Bundle values) {
					// TODO Auto-generated method stub
					
				}
				
				public void onCancel() {
					// TODO Auto-generated method stub
					
				}
			});
	        
	        
	        loginButton = (LoginButton) findViewById(R.id.login_button);
	        loginButton.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
	           // @Override
	         public void onUserInfoFetched(GraphUser user) {
	            	MainActivity.this.user = user;
	            	
	                updateUI();
	               
	                // It's possible that we were waiting for this.user to be populated in order to post a
	                // status update.
	               // handlePendingAction();
	            }
	        });

	        profilePictureView = (ProfilePictureView) findViewById(R.id.profilePicture);
	        greeting = (TextView) findViewById(R.id.greeting);
	        postStatusUpdateButton = (Button) findViewById(R.id.postStatusUpdateButton);
	        postStatusUpdateButton.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View view) {
	               // onClickPostStatusUpdate();
	            	//Facebook mFacebook = new Facebook(APP_ID);
	            	  
	            	ArrayList<String> frenid = new ArrayList<String>();
					ArrayList<String> frenname = new ArrayList<String>();
					ArrayList<String> frenbday = new ArrayList<String>();
					ArrayList<Integer> index = new ArrayList<Integer>();
	           // 	if(mFacebook.isSessionValid()) {
	            		Log.v("log","Inside if");
	            		Bundle parameters = new Bundle();
	            		parameters.putString("fields", "name,birthday");
	            		parameters.putString("access_token", mFacebook.getAccessToken());
	            		String response = null;
	            		
	            		try {
							response = mFacebook.request("me/friends", parameters);
							//Added
							Log.v("log", "Inside try");
		            		JSONObject json = new JSONObject();
		            		json = new JSONObject(response);
		            		JSONArray jsonArray = new JSONArray();
		            		jsonArray = json.getJSONArray("data");
		            		Log.v("log", "before for");
		            		for (int i = 0; i < jsonArray.length(); i++) {
		            			Log.v("log", "3");
		                        JSONObject jObjFren = jsonArray.getJSONObject(i);
		                        Log.v("log", "4");
		                        int count =0;
		                        Iterator it = jObjFren.keys();
		                        while (it.hasNext()) {
		                        	count++;
		                            String s = (String) it.next();
		                            Log.v("KEY",s);
		                            String svalue = jObjFren.getString(s);
		                            if (s.equals("id")) {
		                            	frenid.add(svalue);
		                            } else if (s.equals("name")) {
		                            	frenname.add(svalue);
		                            } else if (s.equals("birthday")) {
		                            	frenbday.add(svalue);
		                            }

		                        }
		                           
		                            if (count == 2) {
		                            	frenbday.add("00/");
		                            } 
		                           
		                           Log.v("fren", "id="+frenid.get(i)+"  name="+frenname.get(i)+"  bday="+frenbday.get(i));
		            		}
		            		//End added
		            		
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	            		Log.v("response", response);
	            		
	            		
	           // 	}
	            	Log.v("user","user before matching");
	            	if(user != null) {
	            	Log.v("userbday",user.getBirthday());
	            	index = matchingBdayMonth(user.getBirthday(),frenbday);
	            	}
	            	Log.v("index",index.toString());
	            	ArrayList<String> selectedFrenName = new ArrayList<String>();
	            	ArrayList<String> selectedFrenId = new ArrayList<String>();
	            	ArrayList<String> selectedFrenBday = new ArrayList<String>();
	            		            	
	            	for (int i = 0; i < index.size(); i++) {
	            		int idx = index.get(i).intValue();
	            		selectedFrenName.add(frenname.get(idx));
	            		selectedFrenId.add(frenid.get(idx));
	            		selectedFrenBday.add(frenbday.get(idx));
	            	}
	            	//frenWithMatchingBday(index,frenid,frenname);
	            	 Intent myIntent = new Intent(getApplicationContext(), DisplayList.class);
	                 myIntent.putStringArrayListExtra("name", selectedFrenName);
	                 myIntent.putStringArrayListExtra("bday", selectedFrenBday);
	                 myIntent.putIntegerArrayListExtra("index", index);
	                 myIntent.putStringArrayListExtra("uid", selectedFrenId);
	                 
	                 startActivity(myIntent);
	            	
	            	Log.v("user",user.toString());
	            	
	            }
	            
	        });
	       // postStatusUpdateButton.setAdapter(new FriendListAdapter(this));

	        postPhotoButton = (Button) findViewById(R.id.postPhotoButton);
	        postPhotoButton.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View view) {
	               // onClickPostPhoto();
	            }
	        });

	        pickFriendsButton = (Button) findViewById(R.id.pickFriendsButton);
	        pickFriendsButton.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View view) {
	               // onClickPickFriends();
	            }
	        });

	        pickPlaceButton = (Button) findViewById(R.id.pickPlaceButton);
	        pickPlaceButton.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View view) {
	               // onClickPickPlace();
	            }
	        });

	        controlsContainer = (ViewGroup) findViewById(R.id.main_ui_container);

	      //  final FragmentManager fm = getSupportFragmentManager();
	        final FragmentManager fm = null ;//getFragmentManager();
	        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
	        if (fragment != null) {
	            // If we're being re-created and have a fragment, we need to a) hide the main UI controls and
	            // b) hook up its listeners again.
	            controlsContainer.setVisibility(View.GONE);
	            if (fragment instanceof FriendPickerFragment) {
	                setFriendPickerListeners((FriendPickerFragment) fragment);
	            } else if (fragment instanceof PlacePickerFragment) {
	               // setPlacePickerListeners((PlacePickerFragment) fragment);
	            }
	        }

	        // Listen for changes in the back stack so we know if a fragment got popped off because the user
	        // clicked the back button.
	        fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
	          //  @Override
	            public void onBackStackChanged() {
	                if (fm.getBackStackEntryCount() == 0) {
	                    // We need to re-show our UI.
	                    controlsContainer.setVisibility(View.VISIBLE);
	                }
	            }
	        });
	    }

	   
	    
	    private void setFriendPickerListeners(final FriendPickerFragment fragment) {
	        fragment.setOnDoneButtonClickedListener(new FriendPickerFragment.OnDoneButtonClickedListener() {
	           // @Override
	            public void onDoneButtonClicked(PickerFragment<?> pickerFragment) {
	               // onFriendPickerDone(fragment);
	            }
	        });
	    }
	    
	    @Override
	    protected void onStart() {
	        super.onStart();

	        updateUI();
	    }

	    @Override
	    protected void onSaveInstanceState(Bundle outState) {
	        super.onSaveInstanceState(outState);

	        outState.putInt(PENDING_ACTION_BUNDLE_KEY, pendingAction.ordinal());
	    }

	    @Override
	    protected void onRestoreInstanceState(Bundle savedInstanceState) {
	        super.onRestoreInstanceState(savedInstanceState);

	        int ordinal = savedInstanceState.getInt(PENDING_ACTION_BUNDLE_KEY, 0);
	        pendingAction = PendingAction.values()[ordinal];
	    }

	   // @Override
	    protected void onSessionStateChange(SessionState state, Exception exception) {
	       // super.onSessionStateChange(state, exception);
	        if (pendingAction != PendingAction.NONE &&
	                exception instanceof FacebookOperationCanceledException) {
	            new AlertDialog.Builder(this)
	                    .setTitle(getString(R.string.cancelled))
	                    .setMessage(getString(R.string.permission_not_granted))
	                    .setPositiveButton(getString(R.string.ok), null)
	                    .show();
	            pendingAction = PendingAction.NONE;
	        } else if (state == SessionState.OPENED_TOKEN_UPDATED) {
	           // handlePendingAction();
	        }

	        updateUI();
	    }

	    private void updateUI() {
	        boolean enableButtons = Session.getActiveSession() != null &&
	                Session.getActiveSession().getState().isOpened();

	        postStatusUpdateButton.setEnabled(enableButtons);
	        postPhotoButton.setEnabled(enableButtons);
	        pickFriendsButton.setEnabled(enableButtons);
	        pickPlaceButton.setEnabled(enableButtons);

	        if (enableButtons && user != null) {
	            profilePictureView.setProfileId(user.getId());
	            greeting.setText(getString(R.string.hello_user, user.getFirstName()));
	        } else {
	            profilePictureView.setProfileId(null);
	            greeting.setText(null);
	        }
	    }
	
	    private ArrayList<Integer> matchingBdayMonth(String userBday, ArrayList frenBdays) {
	    	//Log.v("frenbday", frenBdays.get(0).toString());
	    	ArrayList<Integer> index = new ArrayList<Integer>();
	    	Log.v("userbday", userBday.substring(0, 2));
	    	for(int i = 0; i < frenBdays.size(); i++ ) {
	    		Log.v("frenbday :"+i, frenBdays.get(i).toString().substring(0, 2));
	    		String fbday = frenBdays.get(i).toString().substring(0, 2);
	    		if(fbday.equals(userBday.substring(0, 2)) || (fbday == userBday.substring(0, 2))) {
	    			Log.v("frenbday :2 ", frenBdays.get(i).toString().substring(0, 2));
	    			index.add(i);
	    		}
	    	}
	    	return index;
	    }
	    
	    private void frenWithMatchingBday(ArrayList index, ArrayList id, ArrayList name) {
	    	//TextView selection;
	    	Log.v("log","frenWithMatchingBday");
	    	for(int i = 0; i < index.size(); i++) {
	    		Log.v("frenWithMatchingBday","i : "+i);
	    		int k = (Integer)index.get(i);
	    		//Log.v("frenWithMatchingBday","k : "+k);
	    		System.out.println(id.get(k));
	    		System.out.println(name.get(k));
	    		
	    	}
	    	
	    	ArrayAdapter adapter = new ArrayAdapter<String>(this.getApplicationContext(), 
    		        android.R.layout.simple_list_item_1, name);
    		Log.v("frenWithMatchingBday","new adapter ");
    		ListView listView = (ListView) findViewById(R.id.list);
    		listView.setAdapter(adapter);
    		//setListAdapter(adapter);
    		Log.v("frenWithMatchingBday","After setAdapter");
    		//selection=(TextView)findViewById(R.id.entry); 
	    	/*listView.setOnItemClickListener(new OnItemClickListener() {
	    		 // @Override
	    		  public void onItemClick(AdapterView<?> parent, View view,
	    		    int position, long id) {
	    			Log.v("log","Inside onItemClick");
	    			  String item = (String)parent.getItemAtPosition(position);
	    			  Log.v("log","Inside onItemClick :1");
	    		    Toast.makeText(getApplicationContext(),
	    		      item, Toast.LENGTH_LONG)
	    		      .show();
	    		    Log.v("log","Inside onItemClick :2");
	    		  }
	    		}); */
	    	Log.v("frenWithMatchingBday"," outside for ");
	    }
	    
	    
	    
	    
	    
	//Added
	    private void onClickPostStatusUpdate(){//(AdapterView<?> arg0, View v, int position, long arg3) {
	       // performPublish(PendingAction.POST_STATUS_UPDATE);
	      /* Log.v("onclick","1"); 
	    	Session session = Session.getActiveSession();
	    	if (session == null) {
	    		Log.v("onclick","2"); 
	    	    session = new Session(this.getApplicationContext());
	    	}
	    	if (session != null && session.isOpened()) {
	    		Log.v("onclick","3"); 
	    	    Request req = Request.newGraphPathRequest(session, "/me/friends?fields=id,name,birthday",
	    	            new Callback() {
	    	                //@Override
	    	                public void onCompleted(Response response) {
	    	                            Log.v("FriendsListFragment.Facebook.onComplete", response.toString());
	    	                }
	    	            });
	    	    Log.v("onclick","4"); 
	    	    Request.executeAndWait(req);
	    	    Log.v("onclick","5"); 
	    	}*/
	    	
	        //added
	      /*  try {
	            final long friendId;
	            if (graph_or_fql.equals("graph")) {
	                friendId = jsonArray.getJSONObject(position).getLong("id");
	            } else {
	                friendId = jsonArray.getJSONObject(position).getLong("uid");
	            }
	            String name = jsonArray.getJSONObject(position).getString("name");

	            Log.v("log",friendId + "--" +name);
	        } catch (JSONException e) {
	            Log.v("Error: ", e.getMessage());
	        }*/
	        //end
	    }
	    
	    
	//End  Added
	
	
   /* @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.openSession();
    }

    @Override
    protected void onSessionStateChange(SessionState state, Exception exception) {
      // user has either logged in or not ...
      if (state.isOpened()) {
        // make request to the /me API
        Request request = Request.newMeRequest(
          this.getSession(),
          new Request.GraphUserCallback() {
            // callback after Graph API response with user object
           // @Override
            public void onCompleted(GraphUser user, Response response) {
              if (user != null) {
                TextView welcome = (TextView) findViewById(R.id.welcome);
                welcome.setText("Hello " + user.getName() + "!");
              }
            }
          }
        );
        Request.executeBatchAsync(request);
      }
    }*/
}
