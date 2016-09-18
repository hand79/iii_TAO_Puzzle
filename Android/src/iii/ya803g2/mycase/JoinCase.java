package iii.ya803g2.mycase;

import static iii.ya803g2.sever.Oracle.CATEGORIES;
import iii.ya803g2.casesearchpage.Action_Tab_Thing;
import iii.ya803g2.casesearchpage.AndroidCasesVO;
import iii.ya803g2.casesearchpage.Case_Search;
import iii.ya803g2.casesearchpage.CasesServer;
import iii.ya803g2.login.SeverData;
import iii.ya803g2.memberdata.Memberdata;
import iii.ya803g2.sever.Category;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.example.tao_puzzle.R;

import android.R.color;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class JoinCase extends Activity {
	private ListView caselist;
	private TextView caseno, orderqty, ordership, status, orderTime , orderTitle , orderprice , bdesc , sdesc;
	private ProgressDialog progressDialog;
	private RetrieveJsonContentTask task;
	private String statusString , ship;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.join_case);

	}

	@Override
	protected void onStart() {
		super.onStart();
		caselist = (ListView) findViewById(R.id.wtflist);
		SharedPreferences pref = getSharedPreferences(SeverData.PREF_FILE,
				MODE_PRIVATE);
		String account = pref.getString("account", "");
		task = new RetrieveJsonContentTask();
		task.execute(account);
	}

	class RetrieveJsonContentTask extends
			AsyncTask<String, Integer, List<AndroidCasesVO>> {
		// invoked on the UI thread immediately after the task is executed.
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(JoinCase.this);
			progressDialog.setMessage("Loading...");
			progressDialog.show();
		}

		// invoked on the background thread immediately after onPreExecute()
		@Override
		protected List<AndroidCasesVO> doInBackground(String... params) {
			MyCaseServer myCaseServer = new MyCaseServer();
			List<AndroidCasesVO> joincaseData = myCaseServer
					.getJoinCase(params[0]);
			return joincaseData;
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
		protected void onPostExecute(List<AndroidCasesVO> joincaseData) {
			progressDialog.cancel();
			if (joincaseData == null || joincaseData.isEmpty()) {
				Toast.makeText(JoinCase.this, "抱歉您目前沒有參與的合購案",
						Toast.LENGTH_LONG).show();
			} else {
				showJoinOrderListView(joincaseData);
			}
		}

		private void showJoinOrderListView(
				final List<AndroidCasesVO> joincaseData) {

			caselist.setAdapter(new JoinCaseViewAdapter(JoinCase.this,
					joincaseData));

			caselist.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View v,
						int position, long id) {

				}

			});

		}
	}

	private class JoinCaseViewAdapter extends BaseAdapter {

		private LayoutInflater layoutInflater;
		private List<AndroidCasesVO> joincaseData;

		public JoinCaseViewAdapter(Context context,
				List<AndroidCasesVO> joincaseData) {
			layoutInflater = LayoutInflater.from(context);
			this.joincaseData = joincaseData;
		}

		@Override
		public int getCount() {
			return joincaseData.size();
		}

		@Override
		public Object getItem(int position) {

			return joincaseData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return joincaseData.get(position).getCasesvo().getCaseno();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null) {
				convertView = layoutInflater.inflate(R.layout.join_case_list,
						null);
			}
			
//			if(position %2 == 0){
//				convertView.setBackgroundColor(Color.rgb(red, green, blue));
//			}else{
//				convertView.setBackgroundColor(Color.RED);
//			}
			
			if(position %2 == 0){
		
				convertView.setBackgroundColor(Color.rgb(255, 255, 255));
			}else{
				convertView.setBackgroundColor(Color.rgb(248,248,248));
			}
			
			
			AndroidCasesVO joincases = joincaseData.get(position);

			
			
			
			status = (TextView) convertView.findViewById(R.id.joinstatus);
			orderTime = (TextView) convertView.findViewById(R.id.jointime);
			orderTitle = (TextView) convertView.findViewById(R.id.ordertitle);
			orderprice = (TextView) convertView.findViewById(R.id.joinprice);
			orderqty = (TextView) convertView.findViewById(R.id.joincount);
			Integer helpStatus = joincases.getOrderVO().getStatus();
			if (helpStatus == 0) {
				statusString = "待成團";
			}
			if (helpStatus == 1) {
				statusString = "已成團";
			}
			if (helpStatus == 2) {
				statusString = "買方確認";
			}
			if (helpStatus == 3) {
				statusString = "賣方確認";
			}
			if (helpStatus == 4) {
				statusString = "糾紛處理";
			}
			if (helpStatus == 5) {
				statusString = "已完成";
			}
			if (helpStatus == 6) {
				statusString = "待撥款(雙方確認)";
			}
			if (helpStatus == 9) {
				statusString = "已取消";
			}
			
			
			orderTitle.setText(joincases.getCasesvo().getTitle());
			
			
			DateFormat df_default = DateFormat.getDateInstance(
					DateFormat.MEDIUM, Locale.getDefault());
			
			String date = df_default.format(joincases.getOrderVO().getOrdtime());
			
			orderTime.setText("下單時間 :"+date);
			status.setText("訂單狀態 :"+statusString);
			orderqty.setText("購買數量 :"+joincases.getOrderVO().getQty());
			Integer helpShip = joincases.getOrderVO().getShip();
			
			if(helpShip == 1){
				ship = joincases.getCasesvo().getShip1();
				
			}if(helpShip == 2){
				ship = joincases.getCasesvo().getShip2();
			}
			
			orderprice.setText("總共金額 : "+joincases.getOrderVO().getPrice()+"元(含運費)"+"\n交貨方式 : "+ship);
			
			return convertView;
		}
	}

}
