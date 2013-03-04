/**
 * File: LoginActivity.java
 */
package aad.homework01;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Login Activity
 * This activity is the launcher activity. It launches the login form containing user name and password text fields
 * with a sign in button. On click of button values in text field are validated and action taken accordingly.  
 * @author Anshu Priyadarshini
 * @date 1/29/2013
 */
public class LoginActivity extends Activity {

	// Email, password EditText
    EditText userEmail, userPassword;
    
   /**
    * This method set the view and handles the actions that takes place in that view
    * @param: Bundle
    * @return: void
    */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Sets the layout
        setContentView(R.layout.activity_login);
        // Get the instance of button from the view
        Button loginButton = (Button) findViewById(R.id.signin_button);
        // Listening to register new account link
        loginButton.setOnClickListener(new View.OnClickListener() {
        	// This method responds to the onClick event of the button
            public void onClick(View v) {
                // Switching to another activity by implicitly calling LoginSuccess activity
                Intent i = new Intent(getApplicationContext(), LoginSuccess.class);
                // Email, Password input text
                userEmail = (EditText) findViewById(R.id.user_email);
                userPassword = (EditText) findViewById(R.id.user_password);
                // Getting and storing string values from EditText
                String input_email = userEmail.getText().toString();
                String input_password = userPassword.getText().toString();
                         
               // Validating the input values
                if(validation(userEmail,userPassword))
                {
                	// Bundle to carry values of Email and password field to other activity
                	Bundle bundle = new Bundle();
    				bundle.putString("Email", input_email);
    				bundle.putString("Password", input_password);
    				// Adding bundle to the intent
    				i.putExtras(bundle);
    				// Launching the second activity
                    startActivity(i);
                }                
            }
        });
    }
    //End of onCreate()
    
    /**
     * This method validates the edit text values entered in as email and password
     * @param email
     * @param password
     * @return boolean
     */
    public boolean validation(EditText email, EditText password)
    {
    	// The boolean variable will be set to false if any of the validations fail
    	boolean valid = true;
    	// Checking for empty email value    	
    	if(TextUtils.isEmpty(email.getText().toString()))
    	{
    		//Known bug: Tried using View.setError(), View.requestFocus() but it doesn't work. Issue with text display in 'LIGHT' theme.
    		// Show the error message
    		Toast.makeText(LoginActivity.this, getString(R.string.error_email_required), 
    		         Toast.LENGTH_SHORT).show();
    		// Set boolean to false for invalid case
    		valid = false;
    	}
    	// Checking for empty password value
    	else if(TextUtils.isEmpty(password.getText().toString()))
    	{
    		// Show the error message
    		Toast.makeText(LoginActivity.this, getString(R.string.error_password_required), 
   		         Toast.LENGTH_SHORT).show();
    		valid = false;
    	}
    	// Checking for valid email format
    	else
    	{
    		Pattern pattern = Pattern.compile(".+@.+\\.[a-z]+");
    	    Matcher matcher = pattern.matcher(email.getText().toString());
    	    valid = matcher.matches();
    	    // Show error message if email is in incorrect format
    	    if(valid == false)
    	    {
    	       Toast.makeText(LoginActivity.this, getString(R.string.error_invalid_email), 
      		         Toast.LENGTH_SHORT).show();
    	    }
    	}
    	return valid;
    }
    
       
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_login, menu);
        return true;
    }
}
