package iii.ya803g2.login;

import java.text.DateFormat;
import java.util.List;
import java.util.Locale;

import iii.ya803g2.casesearchpage.Action_Tab_Thing;
import iii.ya803g2.casesearchpage.AndroidCasesVO;
import iii.ya803g2.casesearchpage.Case_Search;
import iii.ya803g2.login.LoginPage.RetrieveJsonContentTask;
import iii.ya803g2.mapsearch.Map_Search;
import iii.ya803g2.membercenter.Membercenter;
import iii.ya803g2.moneycenter.MoneyServer;
import iii.ya803g2.moneycenter.Money_ATM;
import iii.ya803g2.moneycenter.Moneycenter;
import iii.ya803g2.news.NewsPage;
import iii.ya803g2.shopsearchpage.Shop_Search;

import com.example.tao_puzzle.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Homepage extends Activity {
	
	private ImageView MemberCenter;
	private ImageView ThingSearch;
	private ImageView MapSearch;
	private ImageView Money;
	private ImageView Date;
	private ImageView ShopSearch;
	private TextView News;
	private ProgressDialog progressDialog;
	private RetrieveJsonContentTask task;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_page);
		News = (TextView) this.findViewById(R.id.description);
		findViews();
		News.setSelected(true);
	}

	private void findViews() {
		MemberCenter = (ImageView) findViewById(R.id.membercenter);
		ThingSearch = (ImageView) findViewById(R.id.thingsearch);
		MapSearch = (ImageView) findViewById(R.id.mapsearch);
		Money = (ImageView) findViewById(R.id.money);
		Date = (ImageView) findViewById(R.id.date);
		ShopSearch = (ImageView) findViewById(R.id.shopsearch);
		News = (TextView) findViewById(R.id.description);
		
		
		
		
		MemberCenter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(Homepage.this, Membercenter.class);
				startActivity(intent);

			}
		});

		ThingSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(Homepage.this, Case_Search.class);
				startActivity(intent);

			}
		});
		
		MapSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(Homepage.this, Map_Search.class);
				startActivity(intent);

			}
		});
		
		Money.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(Homepage.this, Moneycenter.class);
				startActivity(intent);

			}
		});
		
		
		
		ShopSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(Homepage.this, Shop_Search.class);
				startActivity(intent);

			}
		});
		
		News.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(Homepage.this, NewsPage.class);
				startActivity(intent);

			}
		});
	}
	
	
	
	@Override
	protected void onStart() {
		super.onStart();
		SharedPreferences pref = getSharedPreferences(SeverData.PREF_FILE,MODE_PRIVATE);
		String account = pref.getString("account", "");
		System.out.println(account);
		task = new RetrieveJsonContentTask();
		task.execute(account);
	}

	class RetrieveJsonContentTask extends
			AsyncTask<String, Integer, List<AndroidCasesVO>> {
		// invoked on the UI thread immediately after the task is executed.
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(Homepage.this);
			progressDialog.setMessage("Loading...");
			progressDialog.show();
		}

		// invoked on the background thread immediately after onPreExecute()
		@Override
		protected List<AndroidCasesVO> doInBackground(String... params) {
			LoginServer loginServer = new LoginServer();
			List<AndroidCasesVO> newsData = loginServer.getNews(
					params[0]);
			return newsData;
		}

		/*
		 * This method is used to display any form of progress in the user
		 * interface while the background computation is still executing
		 */
		@Override
		protected void onProgressUpdate(Integer... progress) {

		}

		/*
		 * invoked on the UI thread after the background computation finishes.
		 * The result of the background computation is passed to this step as a
		 * parameter.
		 */
		@Override
		protected void onPostExecute(List<AndroidCasesVO> newsData) {
			progressDialog.cancel();
			if (newsData == null || newsData.isEmpty()) {
				Toast.makeText(Homepage.this, "抱歉請聯絡製作人",
						Toast.LENGTH_SHORT).show();
			}else{
				
			
				
			
				SharedPreferences pref = getSharedPreferences(SeverData.PREF_FILE,
						MODE_PRIVATE);
				pref.edit().putInt("membertype", newsData.get(0).getMembervo().getType()).commit();
					
					
					String newslist = "                                    點擊可觀看詳細最新消息!!    "+newsData.get(0).getNewstitle();
					
					
				
				News.setText(newslist);
				Toast.makeText(Homepage.this, "歡迎來到"
						+ "饕飽拼圖APP!!",
						Toast.LENGTH_SHORT).show();
			}
		}
	}
}
