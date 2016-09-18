package iii.ya803g2.casesearchpage;

import iii.ya803g2.moneycenter.Money_ATM;

import java.util.List;

import com.example.tao_puzzle.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Case_Search extends Activity {

	private TextView keyword, casecount;
	private ListView list;
	private ProgressDialog progressDialog;
	private RetrieveJsonContentTask task;
	private GetKeyWordSearchTask keyWordSearch;
	private List<AndroidCasesVO> casesList;
	private ImageView searchbt;
	private String word , count;

	

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.case_search);
		list = (ListView) findViewById(R.id.caselist);
		keyword = (TextView) findViewById(R.id.mapword);
		searchbt = (ImageView) findViewById(R.id.mapbt);
		casecount = (TextView) findViewById(R.id.casecount);
		searchclick();
	}

	private void searchclick() {
		searchbt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
					
				
//				if("".equals(keyword.getText().toString().trim())){
//					Toast.makeText(Case_Search.this, "請輸入關鍵字!",
//							Toast.LENGTH_SHORT).show();
//				}else{
				
				word = keyword.getText().toString().trim();
			System.out.println("WTF???"+word);
				keyWordSearch = new GetKeyWordSearchTask();
				keyWordSearch.execute(word);
//				}
			}
		});

	}

	@Override
	protected void onStart() {
		super.onStart();
//		task = new RetrieveJsonContentTask();
//		task.execute();
	}

	class RetrieveJsonContentTask extends
			AsyncTask<String, Integer, List<AndroidCasesVO>> {
		// invoked on the UI thread immediately after the task is executed.
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(Case_Search.this);
			progressDialog.setMessage("Loading...");
			progressDialog.show();
		}

		// invoked on the background thread immediately after onPreExecute()
		@Override
		protected List<AndroidCasesVO> doInBackground(String... params) {
			CasesServer casesServer = new CasesServer();
			casesList = casesServer.getAll();
			return casesList;
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
		protected void onPostExecute(List<AndroidCasesVO> casesList) {
			progressDialog.cancel();
			if (casesList == null || casesList.isEmpty()) {
				Toast.makeText(Case_Search.this, "抱歉目前沒有任何合購案",
						Toast.LENGTH_LONG).show();
			} else {
				if(casesList.get(0).getCount() == null){
					count = "";
					casecount.setText("目前沒有任何合購案上架。");
				}else{
					count = casesList.get(0).getCount().toString();
					casecount.setText("目前架上有\""+count+"\"個合購案。");
				}
					
				
				showCasesListView(casesList);
			}
		}

		private void showCasesListView(final List<AndroidCasesVO> casesList) {

			list.setAdapter(new CasesListAdapter(Case_Search.this, casesList));
			list.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View v,
						int position, long id) {
					int caseno = casesList.get(position).getCasesvo()
							.getCaseno();
					int memno = casesList.get(position).getCasesvo().getMemno();
					Intent intent = new Intent(Case_Search.this,
							Action_Tab_Thing.class);
					Bundle bundle = new Bundle();
					bundle.putInt("caseCaseno", caseno);
					bundle.putInt("caseMemno", memno);
					intent.putExtras(bundle);
					startActivity(intent);
				}
			});
		}

	}

	private class CasesListAdapter extends BaseAdapter {
		private LayoutInflater layoutInflater;
		private List<AndroidCasesVO> cases_list_help;

		public CasesListAdapter(Context context,
				List<AndroidCasesVO> cases_list_help) {
			layoutInflater = LayoutInflater.from(context);
			this.cases_list_help = cases_list_help;
		}

		@Override
		public int getCount() {
			return cases_list_help.size();
		}

		@Override
		public Object getItem(int position) {
			return cases_list_help.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return cases_list_help.get(position).getCasesvo().getCaseno();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null) {
				convertView = layoutInflater.inflate(R.layout.case_search_list,
						null);
			}

			ImageView image1 = (ImageView) convertView
					.findViewById(R.id.mapbt);

			TextView price = (TextView) convertView
					.findViewById(R.id.caseprice);
			TextView title = (TextView) convertView
					.findViewById(R.id.casestitle);

			AndroidCasesVO cases = cases_list_help.get(position);

			byte[] image = cases.getPic1();
			Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0,
					image.length);

			// DateFormat df_default = DateFormat.getDateTimeInstance(
			// DateFormat.MEDIUM, DateFormat.MEDIUM , Locale.getDefault());

			// String stime = df_default.format(cases.getStime());

			image1.setImageBitmap(bitmap);
			title.setText(cases.getCasesvo().getTitle());
			// System.out.println(cases.getCasesvo().getDiscount() *
			// cases.getUnitprice() /100);
			String priceEnd = Integer.toString(cases.getUnitprice()
					- cases.getCasesvo().getDiscount());

			price.setText("合購價:" + priceEnd + "元");
			// time.setText(stime);

			return convertView;
		}

	}

	class GetKeyWordSearchTask extends
			AsyncTask<String, Integer, List<AndroidCasesVO>> {
		// invoked on the UI thread immediately after the task is executed.
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(Case_Search.this);
			progressDialog.setMessage("Loading...");
			progressDialog.show();
		}

		// invoked on the background thread immediately after onPreExecute()
		@Override
		protected List<AndroidCasesVO> doInBackground(String... params) {
			CasesServer casesServer = new CasesServer();
			List<AndroidCasesVO> casesList = casesServer
					.keywordsearch(params[0]);
			return casesList;
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
		protected void onPostExecute(List<AndroidCasesVO> casesList) {
			progressDialog.cancel();
			if (casesList == null || casesList.isEmpty()) {
				Toast.makeText(Case_Search.this, "抱歉目前沒有關於\"" + word + "\"的資訊",
						Toast.LENGTH_LONG).show();
			} else {
				
				if(casesList.get(0).getCount() == null){
					count = "";
					casecount.setText("目前架上沒有關於\""+word+"\"的合購案。");
				}if("".equals(word)){
					count = casesList.get(0).getCount().toString();
					casecount.setText("目前架上有\""+count+"\"個"+word+"合購案。");
				}
					else{
					count = casesList.get(0).getCount().toString();
					casecount.setText("目前架上有"+count+"個關於\""+word+"\"的合購案。");
					
				}
				
				showCasesListView(casesList);
			}
		}

		private void showCasesListView(final List<AndroidCasesVO> casesList) {

			list.setAdapter(new KeyWordListAdapter(Case_Search.this, casesList));
			list.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View v,
						int position, long id) {
					int caseno = casesList.get(position).getCasesvo()
							.getCaseno();
					int memno = casesList.get(position).getCasesvo().getMemno();
					Intent intent = new Intent(Case_Search.this,
							Action_Tab_Thing.class);
					Bundle bundle = new Bundle();
					bundle.putInt("caseCaseno", caseno);
					bundle.putInt("caseMemno", memno);
					intent.putExtras(bundle);
					startActivity(intent);
				}
			});
		}

	}

	private class KeyWordListAdapter extends BaseAdapter {
		private LayoutInflater layoutInflater;
		private List<AndroidCasesVO> cases_list_help;

		public KeyWordListAdapter(Context context,
				List<AndroidCasesVO> cases_list_help) {
			layoutInflater = LayoutInflater.from(context);
			this.cases_list_help = cases_list_help;
		}

		@Override
		public int getCount() {
			return cases_list_help.size();
		}

		@Override
		public Object getItem(int position) {
			return cases_list_help.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return cases_list_help.get(position).getCasesvo().getCaseno();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null) {
				convertView = layoutInflater.inflate(R.layout.case_search_list,
						null);
			}

			ImageView image1 = (ImageView) convertView
					.findViewById(R.id.mapbt);

			TextView price = (TextView) convertView
					.findViewById(R.id.caseprice);
			TextView title = (TextView) convertView
					.findViewById(R.id.casestitle);

			AndroidCasesVO cases = cases_list_help.get(position);

			byte[] image = cases.getPic1();
			Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0,
					image.length);

			// DateFormat df_default = DateFormat.getDateTimeInstance(
			// DateFormat.MEDIUM, DateFormat.MEDIUM , Locale.getDefault());

			// String stime = df_default.format(cases.getStime());

			image1.setImageBitmap(bitmap);
			title.setText(cases.getCasesvo().getTitle());
			// System.out.println(cases.getCasesvo().getDiscount() *
			// cases.getUnitprice() /100);
			String priceEnd = Integer.toString(cases.getUnitprice()
					- cases.getCasesvo().getDiscount());

			price.setText("合購價:" + priceEnd + "元");
			// time.setText(stime);

			return convertView;
		}

	}
}