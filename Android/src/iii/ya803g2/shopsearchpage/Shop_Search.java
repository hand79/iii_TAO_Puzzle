package iii.ya803g2.shopsearchpage;

import iii.ya803g2.casesearchpage.AndroidCasesVO;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Shop_Search extends Activity {

	private TextView keyword, casecount;
	private ListView list;
	private ProgressDialog progressDialog;
	private RetrieveJsonContentTask task;
	private GetKeyWordSearchTask keyWordSearch;
	private List<AndroidCasesVO> cases_list_help;
	private ImageView searchbt;
	private String word, count;

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

				word = keyword.getText().toString().trim();

				keyWordSearch = new GetKeyWordSearchTask();
				keyWordSearch.execute(word);

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
			progressDialog = new ProgressDialog(Shop_Search.this);
			progressDialog.setMessage("Loading...");
			progressDialog.show();
		}

		// invoked on the background thread immediately after onPreExecute()
		@Override
		protected List<AndroidCasesVO> doInBackground(String... params) {
			ShopServer shopServer = new ShopServer();
			List<AndroidCasesVO> shopList = shopServer.getAll();
			return shopList;
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
		protected void onPostExecute(List<AndroidCasesVO> shopList) {
			progressDialog.cancel();
			if (shopList == null || shopList.isEmpty()) {
				Toast.makeText(Shop_Search.this, "抱歉目前沒有上架商店",
						Toast.LENGTH_SHORT).show();
			} else {

				if (shopList.get(0).getCount() == null) {
					count = "";
					casecount.setText("目前沒有任何商店上架。");
				} else {
					count = shopList.get(0).getCount().toString();
					casecount.setText("目前架上有\"" + count + "\"個商店。");
				}

				showShopListView(shopList);
			}
		}

		private void showShopListView(final List<AndroidCasesVO> shopList) {
			list.setAdapter(new CasesListAdapter(Shop_Search.this, shopList));
			list.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View v,
						int position, long id) {
					int shopno = shopList.get(position).getShopvo().getShopno();
					int memno = shopList.get(position).getShopvo().getMemno();
					Intent intent = new Intent(Shop_Search.this,
							Action_Tab_Shop.class);
					Bundle bundle = new Bundle();
					bundle.putInt("shopShopno", shopno);
					bundle.putInt("shopMemno", memno);
					intent.putExtras(bundle);
					startActivity(intent);
				}
			});
		}

	}

	private class CasesListAdapter extends BaseAdapter {
		private LayoutInflater layoutInflater;
		private List<AndroidCasesVO> shop_list_help;

		public CasesListAdapter(Context context,
				List<AndroidCasesVO> cases_list_help) {
			layoutInflater = LayoutInflater.from(context);
			this.shop_list_help = cases_list_help;
		}

		@Override
		public int getCount() {
			return shop_list_help.size();
		}

		@Override
		public Object getItem(int position) {
			return shop_list_help.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return shop_list_help.get(position).getShopvo().getMemno();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null) {
				convertView = layoutInflater.inflate(R.layout.shop_search_list,
						null);
			}

			ImageView image1 = (ImageView) convertView.findViewById(R.id.mapbt);

			TextView phone = (TextView) convertView
					.findViewById(R.id.caseprice);
			TextView title = (TextView) convertView
					.findViewById(R.id.casestitle);

			AndroidCasesVO shopData = shop_list_help.get(position);
			
			
			
	
			byte[] image = shopData.getShopvo().getPic();
			if (image != null) {
				Bitmap bitmap1 = BitmapFactory.decodeByteArray(image, 0,
						image.length);
				image1.setImageBitmap(bitmap1);
			}

			// DateFormat df_default = DateFormat.getDateTimeInstance(
			// DateFormat.MEDIUM, DateFormat.MEDIUM , Locale.getDefault());

			// String stime = df_default.format(cases.getStime());

			title.setText(shopData.getShopvo().getTitle());
			// System.out.println(cases.getCasesvo().getDiscount() *
			// cases.getUnitprice() /100);

			phone.setText("店家電話:" + shopData.getShopvo().getPhone());
			// time.setText(stime);

			return convertView;
		}

	}

	class GetKeyWordSearchTask extends
			AsyncTask<String, Integer, List<AndroidCasesVO>> {
		// invoked on the UI thread immediately after the task is executed.
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(Shop_Search.this);
			progressDialog.setMessage("Loading...");
			progressDialog.show();
		}

		// invoked on the background thread immediately after onPreExecute()
		@Override
		protected List<AndroidCasesVO> doInBackground(String... params) {
			ShopServer shopServer = new ShopServer();
			List<AndroidCasesVO> shopList = shopServer.keywordsearch(params[0]);
			return shopList;
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
		protected void onPostExecute(List<AndroidCasesVO> shopList) {
			progressDialog.cancel();
			if (shopList == null || shopList.isEmpty()) {
				Toast.makeText(Shop_Search.this, "抱歉目前沒有關於\"" + word + "\"的商店",
						Toast.LENGTH_SHORT).show();
			} else {

				if (shopList.get(0).getCount() == null) {
					count = "";
					casecount.setText("目前架上沒有關於\"" + word + "\"的商店。");
				}
				if ("".equals(word)) {
					count = shopList.get(0).getCount().toString();
					casecount.setText("目前架上有\"" + count + "\"個" + word + "商店。");
				} else {
					count = shopList.get(0).getCount().toString();
					casecount.setText("目前架上有" + count + "個關於\"" + word
							+ "\"的商店。");

				}

				ShowShopListView(shopList);
			}
		}

		private void ShowShopListView(final List<AndroidCasesVO> shopList) {
			list.setAdapter(new ShowShopListView(Shop_Search.this, shopList));
			list.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View v,
						int position, long id) {
					int shopno = shopList.get(position).getShopvo().getShopno();
					int memno = shopList.get(position).getShopvo().getMemno();
					Intent intent = new Intent(Shop_Search.this,
							Action_Tab_Shop.class);
					Bundle bundle = new Bundle();
					bundle.putInt("shopShopno", shopno);
					bundle.putInt("shopMemno", memno);
					intent.putExtras(bundle);
					startActivity(intent);
				}
			});
		}

	}

	private class ShowShopListView extends BaseAdapter {
		private LayoutInflater layoutInflater;
		private List<AndroidCasesVO> shop_list_help;

		public ShowShopListView(Context context,
				List<AndroidCasesVO> cases_list_help) {
			layoutInflater = LayoutInflater.from(context);
			this.shop_list_help = cases_list_help;
		}

		@Override
		public int getCount() {
			return shop_list_help.size();
		}

		@Override
		public Object getItem(int position) {
			return shop_list_help.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return shop_list_help.get(position).getShopvo().getMemno();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

		
			if (convertView == null) {
				convertView = layoutInflater.inflate(R.layout.shop_search_list,
						null);
			}
		ImageView image1 = (ImageView) convertView.findViewById(R.id.mapbt);

			TextView phone = (TextView) convertView
					.findViewById(R.id.caseprice);
			TextView title = (TextView) convertView
					.findViewById(R.id.casestitle);
			AndroidCasesVO shopData = shop_list_help.get(position);
			
			byte[] image = shopData.getShopvo().getPic();
			if (image != null) {
				Bitmap bitmap1 = BitmapFactory.decodeByteArray(image, 0,
						image.length);
				image1.setImageBitmap(bitmap1);
			}

			// DateFormat df_default = DateFormat.getDateTimeInstance(
			// DateFormat.MEDIUM, DateFormat.MEDIUM , Locale.getDefault());

			// String stime = df_default.format(cases.getStime());

			title.setText(shopData.getShopvo().getTitle());
			// System.out.println(cases.getCasesvo().getDiscount() *
			// cases.getUnitprice() /100);

			phone.setText("店家電話:" + shopData.getShopvo().getPhone());
			// time.setText(stime);

			return convertView;
		}

	}

}