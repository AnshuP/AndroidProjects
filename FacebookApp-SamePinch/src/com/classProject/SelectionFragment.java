package com.classProject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.R;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class SelectionFragment extends Fragment{

	private static final String TAG = "SelectionFragment";
	private ProfilePictureView profilePictureView;
	private TextView userNameView;
	private UiLifecycleHelper uiHelper;
	private static final int REAUTH_ACTIVITY_CODE = 100;
	private Button birthdayButton; //added
	public static Facebook mFacebook; //added
	//public static final String APP_ID = "523618874324530"; //Added
	String[] permissions = { "offline_access", "user_photos", 
			"friends_birthday", "user_birthday" }; //added
	
	private static GraphUser supuser; //added

	@Override
	public View onCreateView(LayoutInflater inflater, 
			ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.selection, 
				container, false);

		// mFacebook = new Facebook(APP_ID); //added

		// Find the user's profile picture custom view
		profilePictureView = (ProfilePictureView) view.findViewById(R.id.profilePicture);
		profilePictureView.setCropped(true);

		// Find the user's name view
		userNameView = (TextView) view.findViewById(R.id.greeting);

				
		// Check for an open session
		Session session = Session.getActiveSession();
		if (session != null && session.isOpened()) {
			// Get the user's data

			makeMeRequest(session);

			//added


			//end added
		}

		birthdayButton = (Button) view.findViewById(R.id.birthday);
		birthdayButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {

				friendPicker();

			}

		});

		return view;
	}

	private void makeMeRequest(final Session session) {
		
		
		// Make an API call to get user data and define a 
		// new callback to handle the response.
		Request request = Request.newMeRequest(session, 
				new Request.GraphUserCallback() {

			@Override
			public void onCompleted(GraphUser user, Response response) {

				// If the response is successful
				if (session == Session.getActiveSession()) {
					if (user != null) {
						// Set the id for the ProfilePictureView
						// view that in turn displays the profile picture.
						profilePictureView.setProfileId(user.getId());
						// Set the Textview's text to the user's name.
						userNameView.setText(getString(R.string.hello_user, user.getFirstName()));
						supuser = user;
					}
				}
				if (response.getError() != null) {
					// Handle errors, will do so later.
				}


			}
		});
		request.executeAsync();
	} 

	private void friendPicker() {

		Session session = Session.getActiveSession();
		if (session != null && session.isOpened()) {
			Log.v("log","Inside if");

			Bundle parameters = new Bundle();
			parameters.putString("fields", "name,birthday");
			parameters.putString("access_token", session.getAccessToken());
			String response = null;

			Request request = new Request(session, "me/friends", parameters, HttpMethod.GET, new Request.Callback() {

				@Override
				public void onCompleted(Response response) {

					if (response.getError() != null) {
						Log.e("response error",response.getError().getErrorMessage());
						return;
						// Handle errors, will do so later.
					}
					ArrayList<String> frenid = new ArrayList<String>();
					ArrayList<String> frenname = new ArrayList<String>();
					ArrayList<String> frenbday = new ArrayList<String>();
					ArrayList<Integer> index = new ArrayList<Integer>();

					Log.v("response",response.toString());
					JSONObject json = new JSONObject();
					json = response.getGraphObject().getInnerJSONObject();
					JSONArray jsonArray = new JSONArray();
					try {
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
						
						Log.v("user","user before matching");
		            	if(supuser != null) {
		            	Log.v("userbday",supuser.getBirthday());
		            	index = matchingBdayMonth(supuser.getBirthday(),frenbday);
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
						Intent myIntent = new Intent(getActivity().getApplicationContext(), DisplayList.class);
						//myIntent.setClass(getActivity(), DisplayList.class);
						myIntent.putStringArrayListExtra("name", selectedFrenName);
						myIntent.putStringArrayListExtra("bday", selectedFrenBday);
						myIntent.putIntegerArrayListExtra("index", index);
						myIntent.putStringArrayListExtra("uid", selectedFrenId);

						startActivity(myIntent);

						//End added


					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			request.executeAsync();

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

	private void onSessionStateChange(final Session session, SessionState state, Exception exception) {
		if (session != null && session.isOpened()) {
			// Get the user's data.
			makeMeRequest(session);
		}
	}

	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(final Session session, final SessionState state, final Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(getActivity(), callback);
		uiHelper.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REAUTH_ACTIVITY_CODE) {
			uiHelper.onActivityResult(requestCode, resultCode, data);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		uiHelper.onResume();
	}

	@Override
	public void onSaveInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);
		uiHelper.onSaveInstanceState(bundle);
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}



/*	private class ActionListAdapter extends ArrayAdapter<BaseListElement> {
		private List<BaseListElement> listElements;

		public ActionListAdapter(Context context, int resourceId, 
				List<BaseListElement> listElements) {
			super(context, resourceId, listElements);
			this.listElements = listElements;
			// Set up as an observer for list item changes to
			// refresh the view.
			for (int i = 0; i < listElements.size(); i++) {
				listElements.get(i).setAdapter(this);
			}
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			if (view == null) {
				LayoutInflater inflater =
						(LayoutInflater) getActivity()
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.layout.listitem, null);
			}

			BaseListElement listElement = listElements.get(position);
			if (listElement != null) {
				view.setOnClickListener(listElement.getOnClickListener());
				ImageView icon = (ImageView) view.findViewById(R.id.icon);
				TextView text1 = (TextView) view.findViewById(R.id.text1);
				TextView text2 = (TextView) view.findViewById(R.id.text2);
				if (icon != null) {
					icon.setImageDrawable(listElement.getIcon());
				}
				if (text1 != null) {
					text1.setText(listElement.getText1());
				}
				if (text2 != null) {
					text2.setText(listElement.getText2());
				}
			}
			return view;
		}

	}*/

}


