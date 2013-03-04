package homework02.h02;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
/**
 * This class shows the details of a selected task when in potrait mode
 * @author Anshu
 *
 */
public class TaskActivity extends Activity {

    public static final String TAG = "TaskActivity";

    /**
     * Called first when the activity is created
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.detail_layout);

        // Turn off animations
        getWindow().setWindowAnimations(android.R.style.Animation);
        
        // Enable the up affordance
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        Bundle b = getIntent().getExtras();
        String detailString = b.getString("detail");
        if (detailString != null){
        	detailString = "This is " + detailString;
        	//Sets the textview to show the details of a selected task
           	 TextView text = (TextView) this.findViewById(R.id.textView1);
           	 text.setText(detailString);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected()");
        
        // Handle the up affordance
        if (item.getItemId() == android.R.id.home)
            finish();
        
        return super.onOptionsItemSelected(item);
    }

    
    
}
