package homework02.h02;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * TaskFragment class is used in landscape mode. It shows screen in split panes.
 * If the screen is large then split pane will be shown in both landscape and
 * potrait mode
 */
public class TaskFragment extends Fragment {

	private String mDetail = "This is a task"; // Default

	/**
	 * Called when Fragment is created
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// Inflate the layout for this fragment and set the color
		View v = inflater.inflate(R.layout.detail_layout, container, false);
		setDescription(mDetail);
		return v;
	}

	/**
	 * Sets the description of a task
	 * 
	 * @param color
	 */
	public void setDescription(String detail) {
		mDetail = "This is " + detail;
		if (this.getView() != null) {
			TextView text = (TextView) getView().findViewById(R.id.textView1);
			text.setText(mDetail);

		}

	}

}
