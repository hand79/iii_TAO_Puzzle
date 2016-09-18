package iii.ya803g2.news;



import iii.ya803g2.casesearchpage.AndroidCasesVO;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.example.tao_puzzle.R;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class NewsPage extends ListActivity {
	private List<AndroidCasesVO> newsList;
	private boolean[] newsExpanded;
	private ProgressDialog progressDialog;
	private RetrieveJsonContentTask task;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	protected void onStart() {
		super.onStart();
		task = new RetrieveJsonContentTask();
		task.execute();
	}


	class RetrieveJsonContentTask extends
			AsyncTask<String, Integer, List<AndroidCasesVO>> {
		// invoked on the UI thread immediately after the task is executed.
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(NewsPage.this);
			progressDialog.setMessage("Loading...");
			progressDialog.show();
		}

		// invoked on the background thread immediately after onPreExecute()
		@Override
		protected List<AndroidCasesVO> doInBackground(String... params) {
			NewsServer newsServer = new NewsServer();
			List<AndroidCasesVO> newsList = newsServer.getAll();
			return newsList;
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
		protected void onPostExecute(List<AndroidCasesVO> newsList) {
			progressDialog.cancel();
			if (newsList == null || newsList.isEmpty()) {
				Toast.makeText(NewsPage.this, "抱歉目前沒有最新資訊",
						Toast.LENGTH_SHORT).show();
			} else {
				NewsPage.this.newsList = newsList;
				newsExpanded = new boolean[newsList.size()];
				showNewsListView();
			}
		}
	}

	public void showNewsListView() {
		// Use our own list adapter
		setListAdapter(new NewsListAdapter(this));
		ListView listView = getListView();
		listView.setTextFilterEnabled(true);
		// Tell the list view to show one checked/activated item at a time.
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		// Start with first item activated.
		// Make the newly clicked item the currently selected one.
		listView.setItemChecked(0, true);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		((NewsListAdapter) getListAdapter()).expand(position);
		getListView().setItemChecked(position, true);
	}

	private class NewsListAdapter extends BaseAdapter {
		private LayoutInflater layoutInflater;

		public NewsListAdapter(Context context) {
			layoutInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return newsList.size();
		}

		@Override
		public Object getItem(int position) {
			return newsList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return newsList.get(position).getNewsVO().getNewsno();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = layoutInflater.inflate(
						R.layout.news_page, null);
			}
			TextView newsTitle = (TextView) convertView
					.findViewById(R.id.title);
			TextView newsDetail = (TextView) convertView
					.findViewById(R.id.text);
			AndroidCasesVO news = newsList.get(position);
			// 依照Android裝置的區域設定來格式化欲輸出的日期
			
		
			
			DateFormat df_default = DateFormat.getDateInstance(
					DateFormat.MEDIUM, Locale.getDefault());
			String date = df_default.format(news.getNewsVO().getPubtime());

			newsTitle.setText(date + "\n" + news.getNewsVO().getTitle());
			newsDetail.setText(news.getNewsVO().getText());
			// 依據messageExpanded的值決定該訊息是否展開detail
			// GONE會隱藏而且不佔空間；INVISIBLE也會隱藏但會佔空間
			newsDetail.setVisibility(newsExpanded[position] ? View.VISIBLE
					: View.GONE);
			return convertView;
		}

		public void expand(int position) {
			// 被點擊的資料列才會彈出內容，其他資料列的內容會自動縮起來
			// for (int i=0; i<newsExpanded.length; i++) {
			// newsExpanded[i] = false;
			// }
			// newsExpanded[position] = true;

			newsExpanded[position] = !newsExpanded[position];
			notifyDataSetChanged();
		}

	}

	@Override
	public void onPause() {
		// AsyncTask執行下載資料時，user切換至其他Tab會發生執行錯誤，呼叫cancel()以避免此錯誤
		if (task != null) {
			task.cancel(true);
		}
		super.onPause();
	}

}