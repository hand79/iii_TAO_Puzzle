package iii.ya803g2.login;


import iii.ya803g2.memberdata.Memberdata;

import java.util.List;

import com.example.tao_puzzle.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginPage extends Activity {

	private EditText Account;
	private EditText Password;
	private Button Button;
	private ProgressDialog progressDialog;
	private RetrieveJsonContentTask task;
	private List<MemberVO> memberList;
	private Boolean isMember;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_page);
	
		findViews();
		
		setResult(RESULT_CANCELED);
	}
	
	
	@Override
	protected void onStart() {
		super.onStart();
		SharedPreferences pref = getSharedPreferences(SeverData.PREF_FILE,MODE_PRIVATE);
//		String string1 = pref.getString("account" ,  "");
//		String string2 = pref.getString("account" ,  "");
//		System.out.println("救命啊"+string1);
//		System.out.println("救命啊ㄚㄚㄚㄚ"+string2);
		boolean login = pref.getBoolean("login", false);
		
		
		if (login) {
			String account = pref.getString("account", "");
			String password = pref.getString("password", "");
			
			task = new RetrieveJsonContentTask();
			task.execute(account,password);
			
	}
		
	
		
//		SharedPreferences pref = getSharedPreferences(SeverData.PREF_FILE,
//				MODE_PRIVATE);
//		boolean login = pref.getBoolean("login", false);
//		if (login) {
//			String name = pref.getString("name", "");
//			String password = pref.getString("password", "");
//			if (isUserValid(name, password)) {
//			setResult(RESULT_OK);
//				// finish();
//			} else {
//				Toast.makeText(LoginPage.this, R.string.msgErrorUserOrPassword,
//						Toast.LENGTH_SHORT).show();
//			}
//		}
	}

	class RetrieveJsonContentTask extends
			AsyncTask<String, Integer, Boolean> {
		// invoked on the UI thread immediately after the task is executed.
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(LoginPage.this);
			progressDialog.setMessage("Loading...");
			progressDialog.show();
		}

		// invoked on the background thread immediately after onPreExecute()
		@Override
		protected Boolean doInBackground(String... memberhelp) {
			
			LoginServer memberDAO = new LoginServer();
//			memberList = memberDAO.findByMemberID(memberhelp[0]);
			Boolean isMember = memberDAO.isMember(memberhelp[0],memberhelp[1]);
//			System.out.println("不要阿"+memberhelp[0]);
//			System.out.println("不要ㄚㄚㄚㄚ"+memberhelp[1]);
			if(isMember){
				
				
			
				
				SharedPreferences pref = getSharedPreferences(SeverData.PREF_FILE,
						MODE_PRIVATE);
				pref.edit().putBoolean("login", true)
							.putString("account", memberhelp[0])
							.putString("password", memberhelp[1]).commit();
			
				
			}		
			
			
			return isMember;
		}

		/*
		 * This method is used to display any form of progress in the user
		 * interface while the background computation is still executing
		 */
		@Override
		protected void onProgressUpdate(Integer... progress) {

		}

		@Override
		protected void onPostExecute(Boolean isMember) {
			progressDialog.cancel();
			if (!isMember) {
				Toast.makeText(LoginPage.this, "請輸入正確的帳號或密碼", Toast.LENGTH_SHORT)
						.show();
			} else {
				
				setResult(RESULT_OK);
//				findViews(memberList);
				// 進入主頁面
				Intent intent = new Intent();
				intent.setClass(LoginPage.this, Homepage.class);
				startActivity(intent);
				finish();
				
			}
		}
	}

	private void findViews() {
		Account = (EditText) findViewById(R.id.Account);
		Password = (EditText) findViewById(R.id.Password);
		Button = (Button) findViewById(R.id.addwish);
		
		Button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String account = Account.getText().toString().trim();
				String password = Password.getText().toString().trim();
				
				
				
				if (account.length() <= 0 || password.length() <= 0) {
					Toast.makeText(LoginPage.this,
							R.string.msgErrorUserOrPassword, Toast.LENGTH_SHORT)
							.show();
					return;
				}
				
				
				System.out.println("Android"+account);
				
				task = new RetrieveJsonContentTask();
				task.execute(account,password);
//				if (isUserValid(account, password)) {
//					SharedPreferences pref = getSharedPreferences(
//							SeverData.PREF_FILE, MODE_PRIVATE);
//					pref.edit().putBoolean("login", true)
//							.putString("name", account)
//							.putString("password", password).commit();
//					setResult(RESULT_OK);
//					Intent intent = new Intent();
//					intent.setClass(LoginPage.this, Homepage.class);
//					startActivity(intent);
//					finish();
//
//				} else {
//					Toast.makeText(LoginPage.this,
//							R.string.msgErrorUserOrPassword, Toast.LENGTH_SHORT)
//							.show();
//				}

			}
		});

	}

	

//	private boolean isUserValid(String name, String password) {
//	
//		System.out.println(isMember);
//	    
//		
//		
//		
//		
////		if(memberList != null){
////		
////		String namedie = memberList.get(0).getMemid();
////		String passdie = memberList.get(0).getMempw();
////		idgo = name.equals(namedie) && password.equals(passdie);
////		}	
//		
//		
//		return isMember;
//	}

}
