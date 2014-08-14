package com.quickblox.sample.chat.ui.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.quickblox.core.QBCallback;
import com.quickblox.core.QBSettings;
import com.quickblox.core.result.Result;
import com.quickblox.module.auth.QBAuth;
import com.quickblox.module.chat.QBChatService;
import com.quickblox.module.chat.listeners.SessionCallback;
import com.quickblox.module.chat.smack.SmackAndroid;
import com.quickblox.module.users.QBUsers;
import com.quickblox.module.users.model.QBUser;
import com.quickblox.sample.chat.ChatManager;
import com.quickblox.sample.chat.OSChatIteractions;
import com.quickblox.sample.chat.R;

public class SplashActivity extends Activity {

	private static final String TAG = SplashActivity.class.getSimpleName();

	private static final int UNAUTHORIZED = 401;//nauthorized

	private static final String APP_ID = "13174";
	private static final String AUTH_KEY = "tGAmNfQOaw-ewJj";
	private static final String AUTH_SECRET = "nqk3c6KT5DeeyE4";

	private ProgressBar progressBar;
	private SmackAndroid smackAndroid;
	private QBUser user; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		smackAndroid = SmackAndroid.init(this);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		user = OSChatIteractions.createQBUser();

		QBSettings.getInstance().fastConfigInit(APP_ID, AUTH_KEY, AUTH_SECRET);
		QBAuth.createSession(new AuthenticationCallback(this));
	}


	public void onClickGetUser(View view) {
		QBCallback qbCallback = new QBCallback() {
			 

			@Override
			public void onComplete(Result result) {
				if (result.isSuccess()) {
					logOnChat();
				} else if(result.getStatusCode() == UNAUTHORIZED) {
					startRegistrationTask();
				}
			}
			
			@Override
			public void onComplete(Result result, Object arg1) {}
		};
		QBUsers.signIn(user, qbCallback);
	}

	private class AuthenticationCallback implements QBCallback {

		private Context context;

		public AuthenticationCallback(Context context) {
			this.context = context;
		}
		@Override
		public void onComplete(Result result) {
			progressBar.setVisibility(View.GONE);

			if (result.isSuccess()) {
				Toast.makeText(SplashActivity.this, "Authentication OK", Toast.LENGTH_SHORT).show();

			} else {
				AlertDialog.Builder dialog = new AlertDialog.Builder(context);
				dialog.setMessage("Error(s) occurred. Look into DDMS log for details, " +
						"please. Errors: " + result.getErrors()).create().show();
			}
		}

		@Override
		public void onComplete(Result result, Object context) {
			Log.i("CARLINHOS VELHO", "**** CARLINHOS VELHO");
		}
	} 

	private void startLoginTask() {
		QBUsers.signIn(user, new LoginCallBack());
	}    
	
	private class LoginCallBack implements QBCallback {
		@Override
		public void onComplete(Result result) {
			if (result.isSuccess()) {
				logOnChat();
			} else {
				AlertDialog.Builder dialog = new AlertDialog.Builder(SplashActivity.this);
				dialog.setMessage("Error(s) occurred. Look into DDMS log for details, " +
						"please. Errors: " + result.getErrors()).create().show();
			}
		}

		@Override
		public void onComplete(Result result, Object context) {
		}
	}

	
	private void logOnChat() {
		ChatManager.getInstance().setQbUser(user);

        if (QBChatService.getInstance().isLoggedIn()) {

            QBChatService.getInstance().logout();
        }

		QBChatService.getInstance().loginWithUser(user, new SessionCallback() {
			@Override
			public void onLoginSuccess() {
				Log.i(TAG, "success when login");
				goToChatActivity();
			}

			@Override
			public void onLoginError(String error) {
				Log.i(TAG, "error when login");
			}

		});
	}

	private void startRegistrationTask() {
		Log.i(TAG, "Starting registration process");
		QBUsers.signUpSignInTask(user, new RegistrationCallback());
	}

	private class RegistrationCallback implements QBCallback {
		@Override
		public void onComplete(Result result) {
			if (result.isSuccess()) {
				logOnChat();
			} else {
				AlertDialog.Builder dialog = new AlertDialog.Builder(SplashActivity.this);
				dialog.setMessage("Error(s) occurred. Look into DDMS log for details, " +
						"please. Errors: " + result.getErrors()).create().show();
			}
		}

		@Override
		public void onComplete(Result result, Object context) {
		}
	}

	private void goToChatActivity() {
		Intent intent = new Intent(this, getNextActivity());
		startActivity(intent);
		finish();
	}

	public Class<? extends Activity> getNextActivity() {
		return MainChatActivity.class;
	}	 
	@Override
	protected void onDestroy() {
		smackAndroid.onDestroy();
		super.onDestroy();
	}
}