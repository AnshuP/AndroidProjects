package homework02.h02;

import homework02.h02.CommentsDataSource.ListItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.app.FragmentManager;

/**
 * This is the main launcher class. 
 * Fragments are added or removed in this class
 * @author Anshu
 *
 */
public class MainActivity extends Activity implements ActionBar.TabListener {

	public static final String TAG = "MainActivity";

	private ActionBar mActionBar;
	private boolean mDualPane = true;
	private ListFragment mListFragment;
	private TaskFragment mTaskFragment;
	private CommentsDataSource datasource;
	public static int counter = 1;

	/**
	 * Called when the activity is first created
	 * 
	 * 
	 */


	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate()");

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Turn off animations
		getWindow().setWindowAnimations(android.R.style.Animation);

		View fragmentContainer = this.findViewById(R.id.fragmentContainer);
		mDualPane = fragmentContainer != null
				&& fragmentContainer.getVisibility() == View.VISIBLE;

		//Instantiate and open the database 
		datasource = new CommentsDataSource(this);
		datasource.open();

		final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// Fill our list panel
		final ArrayList<ListItem> items = (ArrayList<ListItem>) datasource
				.getAllComments();
		ArrayAdapter<ListItem> itemsAdapter = new ArrayAdapter<ListItem>(this,
				android.R.layout.list_content, items) {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {

				View row;

				if (convertView == null) {
					row = inflater.inflate(android.R.layout.simple_list_item_2,
							null);
				} else {
					row = convertView;
				}

				TextView tv1 = (TextView) row.findViewById(android.R.id.text1);
				tv1.setText("");

				TextView tv2 = (TextView) row.findViewById(android.R.id.text2);
				tv2.setText(getItem(position).getComment());

				return row;
			}

		};

		//Set the fragment
		FragmentManager fragmentManager = getFragmentManager();
		ListFragment listFragment = (ListFragment) fragmentManager
				.findFragmentById(R.id.listFragment);
		listFragment.getListView().setBackgroundColor(
				Color.parseColor("#333333"));
		listFragment.setListAdapter(itemsAdapter);
		listFragment.getListView().setOnItemClickListener(
				new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> av, View view,
							int position, long id) {

						TextView tv = (TextView) view
								.findViewById(android.R.id.text2);
						showDetail(position, tv.getText().toString());
					}

				});

		mListFragment = (ListFragment) getFragmentManager().findFragmentById(
				R.id.listFragment);
		mTaskFragment = new TaskFragment();
		

		// Setup the Action Bar
		mActionBar = this.getActionBar();
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		Tab t1 = mActionBar.newTab();
		t1.setText("");
		t1.setTabListener(this);

		Tab t2 = mActionBar.newTab();
		t2.setText("");
		t2.setTabListener(this);

		mActionBar.addTab(t1);
		mActionBar.addTab(t2);

	}

	/**
	 * Creates the options menu that contains add and delete options
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options, menu);
		return true;
	}

	/** Show either a fragment or activity in the window */
	void showDetail(int index, String detailString) {
		Log.d(TAG, "showDetail() index:" + index + " detailString: "
				+ detailString);

		if (mDualPane) {

			mTaskFragment.setDescription(detailString);

		} else {

			Intent intent = new Intent();
			intent.setClass(this, TaskActivity.class);
			intent.putExtra("index", index);
			intent.putExtra("detail", detailString);
			startActivity(intent);
		}
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// action when tab is reselected
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		int position = tab.getPosition();
		switch (position) {
		case 0:
			if (mDualPane)
				ft.add(R.id.fragmentContainer, mTaskFragment);
			break;
		case 1:
			break;
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		int position = tab.getPosition();
		switch (position) {
		case 0:
			if (mDualPane)
				ft.remove(mTaskFragment);
			break;
		case 1:
			ft.show(mListFragment);
			break;
		}
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		ArrayAdapter<ListItem> adapter = (ArrayAdapter<ListItem>) mListFragment
				.getListAdapter();
		ListItem comment = null;
		//Switch based on the options menu selected
		switch (item.getItemId()) {

		case R.id.item1:
			//Flow when add is selected
			if (mDualPane)
				getFragmentManager().beginTransaction().add(
						R.id.fragmentContainer, mTaskFragment);

			String comments = "Task" + counter;
			int nextInt = new Random().nextInt(3);
			// Save the new comment to the database
			comment = datasource.createComment(comments);
			adapter.add(comment);
			counter++;
			//notify the adapter of the changes
			adapter.notifyDataSetChanged();
			return true;
			

		case R.id.item2:
			//Flow when delete is selected
			if (mListFragment.getListAdapter().getCount() > 0) {
				comment = (ListItem) mListFragment.getListAdapter().getItem(0);
				datasource.deleteComment(comment);
				adapter.remove(comment);
			}
			//notify the adapter of the changes
			adapter.notifyDataSetChanged();
			return true;
			
		}
		return super.onOptionsItemSelected(item);
	}

}