package com.roy.tester.gp;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.roy.tester.mytester.R;

public class GpAccountActivity extends Activity implements OnClickListener {

	private AccountManager mAccountManager;
	private Button mButtonGetToken;
	private Button mButtonGetProfile;
	private TextView mTextViewResult;
	private String mAuthToken = "";
	private final static String ACCOUNT_TYPE = "com.google";
	// basic profile
	private final static String TOKEN_TYPE_USER_INFO_PROFILE = "oauth2:https://www.googleapis.com/auth/userinfo.profile";
	// Google plus profile
	private final static String TOKEN_TYPE_KNOW_WHO_YOU_ARE = "Know who you are on Google";

	private final static String URL_PREFIX = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gp_account_layout);
		mAccountManager = AccountManager.get(this);

		mButtonGetToken = (Button) findViewById(R.id.button_get_token);
		mButtonGetToken.setOnClickListener(this);
		mButtonGetProfile = (Button) findViewById(R.id.button_get_user_profile);
		mButtonGetProfile.setOnClickListener(this);
		Button btnGetInfo = (Button) findViewById(R.id.button_get_system_info);
		btnGetInfo.setOnClickListener(this);
		mTextViewResult = (TextView) findViewById(R.id.text_view_user_info);
	}

	private void showLocalAccountInfo(Account[] accounts) {
		for (Account a : accounts) {
			mTextViewResult.append(a.toString() + "\n");
		}
	}

	private boolean isAccountEmpty(Account[] accounts) {
		return accounts == null || accounts.length == 0;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.button_get_token:
				getUserToken();
				break;

			case R.id.button_get_user_profile:
				getUserProfile();
				break;
			case R.id.button_get_system_info:
				getSystemInfo();
				break ;
			default:
				break;
		}
	}

	private void getSystemInfo(){
		String lang = getResources().getConfiguration().locale.getLanguage();
		String langDisplay = getResources().getConfiguration().locale.getDisplayLanguage();
		String country = getResources().getConfiguration().locale.getCountry();
		String countryDisplay = getResources().getConfiguration().locale.getDisplayCountry();

		String text = "  lang = " + lang + ", dispaly lang = " + langDisplay +
					  ", \r\n country = " + country + ", display country = " + countryDisplay;
		mTextViewResult.setText(text);
	}

	private void getUserProfile() {
		new GetProfileTask().execute(URL_PREFIX + mAuthToken);
	}

	private void getUserToken() {
		Account[] accounts = mAccountManager.getAccountsByType(ACCOUNT_TYPE);

		if (!isAccountEmpty(accounts)) {
			showLocalAccountInfo(accounts);
			Account account = accounts[0];
			getAccountToken(account);
		} else {
			Toast.makeText(getApplicationContext(),
					"empty google account at local", Toast.LENGTH_SHORT).show();
		}
	}

	private void getAccountToken(Account account) {
		mAccountManager.getAuthToken(account, TOKEN_TYPE_USER_INFO_PROFILE,
				null, this, new AccountManagerCallback<Bundle>() {

					@Override
					public void run(AccountManagerFuture<Bundle> future) {
						try {
							Bundle bundle = future.getResult();
							mAuthToken = bundle
									.getString(AccountManager.KEY_AUTHTOKEN);
							mTextViewResult.append("auth token get result: "
									+ mAuthToken + "\n");
						} catch (OperationCanceledException e) {
							mTextViewResult
									.setText("OperationCanceledException "
											+ e.toString());
							e.printStackTrace();
						} catch (AuthenticatorException e) {
							mTextViewResult.setText("AuthenticatorException "
									+ e.toString());
							e.printStackTrace();
						} catch (IOException e) {
							mTextViewResult.setText("IOException "
									+ e.toString());
							e.printStackTrace();
						}

					}
				}, null);
	}

	class GetProfileTask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			InputStream is = null;
			int len = 500;
			try {
				URL url = new URL(params[0]);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setReadTimeout(10000);
				conn.setConnectTimeout(15000);
				conn.setRequestMethod("GET");
				conn.setDoInput(true);
				conn.connect();
				int response = conn.getResponseCode();
				Log.d("get profile task", "response code: " + response);
				is = conn.getInputStream();
				String contentAsString = readIt(is, len);
				return contentAsString;
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return "error happend";
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			mTextViewResult.append(result);
		}

		private String readIt(InputStream stream, int len) throws IOException,
				UnsupportedEncodingException {
			Reader reader = null;
			reader = new InputStreamReader(stream, "UTF-8");
			char[] buffer = new char[len];
			reader.read(buffer);
			return new String(buffer);
		}

	}

}
