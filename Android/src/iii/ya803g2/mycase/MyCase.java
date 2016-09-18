package iii.ya803g2.mycase;

import static iii.ya803g2.sever.Oracle.CATEGORIES;
import iii.ya803g2.casesearchpage.Action_Tab_Thing;
import iii.ya803g2.casesearchpage.AndroidCasesVO;
import iii.ya803g2.casesearchpage.Case_Search;
import iii.ya803g2.casesearchpage.CasesServer;
import iii.ya803g2.login.SeverData;
import iii.ya803g2.memberdata.Memberdata;
import iii.ya803g2.sever.Category;

import java.util.ArrayList;
import java.util.List;

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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MyCase extends Activity {
	private ListView caselist;
	private TextView casetitle, caseMin, caseMax, status , total;
	private ProgressDialog progressDialog;
	private RetrieveJsonContentTask task;
	private String statusString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_case);

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
			progressDialog = new ProgressDialog(MyCase.this);
			progressDialog.setMessage("Loading...");
			progressDialog.show();
		}

		// invoked on the background thread immediately after onPreExecute()
		@Override
		protected List<AndroidCasesVO> doInBackground(String... params) {
			MyCaseServer myCaseServer = new MyCaseServer();
			List<AndroidCasesVO> mycaseData = myCaseServer
					.getAllMyCase(params[0]);
			return mycaseData;
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
		protected void onPostExecute(List<AndroidCasesVO> mycaseData) {
			progressDialog.cancel();
			if (mycaseData == null || mycaseData.isEmpty()) {
				Toast.makeText(MyCase.this, "抱歉您目前沒有發起的合購案",
						Toast.LENGTH_LONG).show();
			} else {
				showCasesListView(mycaseData);
			}
		}

		private void showCasesListView(final List<AndroidCasesVO> mycaseData) {

			caselist.setAdapter(new CaseViewAdapter(MyCase.this,
					mycaseData));

			caselist.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View v,
						int position, long id) {
					
					
					if(mycaseData.get(position).getCasesvo().getStatus() == 1 || mycaseData.get(position).getCasesvo().getStatus() == 2){
					int caseno = mycaseData.get(position).getCasesvo()
							.getCaseno();
					int memno = mycaseData.get(position).getCasesvo()
							.getMemno();
					Intent intent = new Intent(MyCase.this,
							Action_Tab_Thing.class);
					Bundle bundle = new Bundle();
					bundle.putInt("caseCaseno", caseno);
					bundle.putInt("caseMemno", memno);
					intent.putExtras(bundle);
					startActivity(intent);
					}else{
						Toast.makeText(MyCase.this, "此筆合購案未上架",
								Toast.LENGTH_SHORT).show();
					}
					
				}
			});

		}

	}

	private class CaseViewAdapter extends BaseAdapter {

		private LayoutInflater layoutInflater;
		private List<AndroidCasesVO> mycases_list;

		public CaseViewAdapter(Context context,
				List<AndroidCasesVO> mycases_list) {
			layoutInflater = LayoutInflater.from(context);
			this.mycases_list = mycases_list;
		}

		@Override
		public int getCount() {
			return mycases_list.size();
		}

		@Override
		public Object getItem(int position) {

			return mycases_list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return mycases_list.get(position).getCasesvo().getCaseno();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null) {
				convertView = layoutInflater.inflate(
						R.layout.my_case_list, null);
			}
			
			if(position %2 == 0){
				
				convertView.setBackgroundColor(Color.rgb(255, 255, 255));
			}else{
				
				convertView.setBackgroundColor(Color.rgb(248,248,248));
			}
			
			
			AndroidCasesVO mycases = mycases_list.get(position);

			casetitle = (TextView) convertView.findViewById(R.id.casetitle);
			caseMin = (TextView) convertView.findViewById(R.id.casepeople);
			caseMax = (TextView) convertView.findViewById(R.id.casestatus);
			
			Integer helpStatus = mycases.getCasesvo().getStatus();
			if (helpStatus == 0) {
				statusString = "已下架";
			}
			if (helpStatus == 1) {
				statusString = "上架中";
			}
			if (helpStatus == 2) {
				statusString = "上架中(隱密)";
			}
			if (helpStatus == 3) {
				statusString = "已結案";
			}
			if (helpStatus == 4) {
				statusString = "已完成 (開始交貨)";
			}
			if (helpStatus == 5) {
				statusString = "已刪除";
			}
			if (helpStatus == 6) {
				statusString = "已取消";
			}
			
			casetitle.setText(mycases.getCasesvo().getTitle());
			caseMin.setText("目前/成團/上限 數量 : "+mycases.getTotalOty()+" / "+mycases.getCasesvo().getMinqty()+" / "+ mycases.getCasesvo().getMaxqty());
			caseMax.setText("合購案狀態 : " + statusString);

			return convertView;
		}
	}

}
