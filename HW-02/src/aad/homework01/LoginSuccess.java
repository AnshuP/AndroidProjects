/**
 * @File: LoginSuccess.java
 */
package aad.homework01;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Login Success
 * This activity is launched by the main activity. It launches the success page displaying the values entered in 
 * previous activity 
 * @author Anshu Priyadarshini
 * @date 1/29/2013
 */
public class LoginSuccess extends Activity{

		/**
	    * This method set the view and handles the actions that takes place in that view
	    * @param: Bundle
	    * @return: void
	    */
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        // Set View to register.xml
	        setContentView(R.layout.success);
	        

	        // pull the greeting from the passed bundle
			Bundle bundle = this.getIntent().getExtras(); 
			String userEmail = bundle.getString("Email");
			String userPassword = bundle.getString("Password");
		
			 // pull in textview definition 
	        TextView email = (TextView)findViewById(R.id.email);
	        TextView password = (TextView)findViewById(R.id.password);
	        
	        // set text to passed value
	        email.setText(" Email : "+ userEmail);
	        password.setText(" Password : "+ userPassword);
	 }
}
