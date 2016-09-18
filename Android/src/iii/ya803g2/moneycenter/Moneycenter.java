package iii.ya803g2.moneycenter;

import iii.ya803g2.casesearchpage.AndroidCasesVO;
import iii.ya803g2.casesearchpage.Case_Search;
import iii.ya803g2.casesearchpage.CasesServer;
import iii.ya803g2.login.Homepage;
import iii.ya803g2.login.LoginServer;
import iii.ya803g2.login.SeverData;
import iii.ya803g2.membercenter.Membercenter;
import iii.ya803g2.memberdata.Memberdata;
import iii.ya803g2.moneycenter.Storage_money;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.example.tao_puzzle.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Moneycenter extends Activity {
	private ListView listView;
	private Button moneyButton;
	private ProgressDialog progressDialog;
	private RetrieveJsonContentTask task;
	private TextView dpshow , dpsamnt , dpsordt , ordsts , membermoney;
	private NullTask nullTask;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.money_center);
		findViews();
	}
	private void findViews() {
		moneyButton = (Button) findViewById(R.id.addwish);
		listView = (ListView) findViewById(R.id.wtflist);
		membermoney = (TextView)findViewById(R.id.membermoney);
		moneyButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent();
				intent.setClass(Moneycenter.this, Storage_money.class);
				startActivity(intent);
				finish();
				
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
			progressDialog = new ProgressDialog(Moneycenter.this);
			progressDialog.setMessage("Loading...");
			progressDialog.show();
		}

		// invoked on the background thread immediately after onPreExecute()
		@Override
		protected List<AndroidCasesVO> doInBackground(String... params) {
			MoneyServer moneyServer = new MoneyServer();
			List<AndroidCasesVO> memberMoneyOrder = moneyServer.getMemeber(params[0]);
			return memberMoneyOrder;
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
		protected void onPostExecute(List<AndroidCasesVO> memberMoneyOrder) {
			progressDialog.cancel();
			if (memberMoneyOrder == null || memberMoneyOrder.isEmpty()) {
				Toast.makeText(Moneycenter.this, "抱歉您沒有儲值紀錄",
						Toast.LENGTH_SHORT).show();
				SharedPreferences pref = getSharedPreferences(
						SeverData.PREF_FILE, MODE_PRIVATE);
				String account = pref.getString("account", "");
				nullTask = new NullTask();
				nullTask.execute(account);
				
			} else {
				membermoney.setText("目前所擁有的拼圖幣 : $"+memberMoneyOrder.get(0).getMembervo().getMoney().toString()+"元");
				
				
			showMoneyListView(memberMoneyOrder);
			}
		}

		private void showMoneyListView(List<AndroidCasesVO> memberMoneyOrder) {
			
			listView.setAdapter(new MoneyListAdapter(Moneycenter.this, memberMoneyOrder));
			listView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View v,
						int position, long id) {
				}
			});
		}
	
	}
	
	private class MoneyListAdapter extends BaseAdapter {
		private LayoutInflater layoutInflater;
		private List<AndroidCasesVO> memberMoneyOrder;

		public MoneyListAdapter(Context context,
				List<AndroidCasesVO> memberMoneyOrder) {
			layoutInflater = LayoutInflater.from(context);
			this.memberMoneyOrder = memberMoneyOrder;
		}

		@Override
		public int getCount() {
			return memberMoneyOrder.size();
		}

		@Override
		public Object getItem(int position) {
			return memberMoneyOrder.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null) {
				convertView = layoutInflater.inflate(R.layout.money_center_list,
						null);
			}
			
			if(position %2 == 0){
				convertView.setBackgroundColor(Color.rgb(255, 255, 255));
			
			}else{
				convertView.setBackgroundColor(Color.rgb(248,248,248));
			}
			
			dpshow = (TextView) convertView.findViewById(R.id.dpshow);
			dpsamnt = (TextView) convertView.findViewById(R.id.dpsamnt);
			dpsordt = (TextView) convertView.findViewById(R.id.dpsordt);
			ordsts = (TextView) convertView.findViewById(R.id.ordsts);
			
			AndroidCasesVO forMoneyEnd = memberMoneyOrder.get(position);
			
			DateFormat df_default = DateFormat.getDateInstance(
					DateFormat.MEDIUM, Locale.getDefault());
			String date = df_default.format(forMoneyEnd.getDpsOrdVO().getDpsordt());
			
			dpshow.setText("儲值方式 : "+forMoneyEnd.getDpsOrdVO().getDpshow());
			dpsamnt.setText("儲值金額 : $"+forMoneyEnd.getDpsOrdVO().getDpsamnt().toString()+"元");
			dpsordt.setText("儲值時間 : "+date);
			ordsts.setText("儲值狀態 : "+forMoneyEnd.getDpsOrdVO().getOrdsts());
			
			return convertView;
		}
	}
	
	
	class NullTask extends AsyncTask<String, Integer, List<AndroidCasesVO>> {
		// invoked on the UI thread immediately after the task is executed.
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(Moneycenter.this);
			progressDialog.setMessage("Loading...");
			progressDialog.show();
		}

		// invoked on the background thread immediately after onPreExecute()
		@Override
		protected List<AndroidCasesVO> doInBackground(String... params) {
			LoginServer loginServer = new LoginServer();
			List<AndroidCasesVO> memberData = loginServer.onlyMember(params[0]);
			return memberData;
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
		protected void onPostExecute(List<AndroidCasesVO> memberData) {
			progressDialog.cancel();
			if (memberData == null || memberData.isEmpty()) {
				
			}else{
				membermoney.setText("目前所擁有的拼圖幣 : "+memberData.get(0).getMembervo().getMoney().toString()+"元");
			}
		}
	}
}