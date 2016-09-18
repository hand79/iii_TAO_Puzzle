package iii.ya803g2.mycase;

import static iii.ya803g2.sever.Oracle.CATEGORIES;
import iii.ya803g2.casesearchpage.Action_Tab_Thing;
import iii.ya803g2.casesearchpage.AndroidCasesVO;
import iii.ya803g2.casesearchpage.Case_Search;
import iii.ya803g2.casesearchpage.CasesServer;
import iii.ya803g2.casesearchpage.Sell_Thing_Page3;
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

public class WishCase extends Activity {
	private ListView caselist;
	private TextView casetitle, caseMin, caseMax, status , total , wishprice;
	private ProgressDialog progressDialog;
	private RetrieveJsonContentTask task;
	private String statusString;
	private Integer helpStatus;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wish_case);
		caselist = (ListView) findViewById(R.id.wtflist);
	}

	@Override
	protected void onStart() {
		super.onStart();
		
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
			progressDialog = new ProgressDialog(WishCase.this);
			progressDialog.setMessage("Loading...");
			progressDialog.show();
		}

		// invoked on the background thread immediately after onPreExecute()
		@Override
		protected List<AndroidCasesVO> doInBackground(String... params) {
			MyCaseServer myCaseServer = new MyCaseServer();
			List<AndroidCasesVO> wishListVO = myCaseServer
					.getWishCase(params[0]);
			return wishListVO;
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
		protected void onPostExecute(List<AndroidCasesVO> wishListVO) {
			progressDialog.cancel();
			if (wishListVO == null || wishListVO.isEmpty()) {
				Toast.makeText(WishCase.this, "抱歉您目前沒有追蹤的合購案",
						Toast.LENGTH_LONG).show();
			} else {
				showJoinOrderListView(wishListVO);
			}
		}

		private void showJoinOrderListView(
				final List<AndroidCasesVO> wishListVO) {

			caselist.setAdapter(new JoinCaseViewAdapter(WishCase.this,
					wishListVO));

			caselist.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View v,
						int position, long id) {
					
					
					
					if (helpStatus == 1 || helpStatus == 2) {
					
					
					
					int caseno = wishListVO.get(position).getCasesvo()
							.getCaseno();
					int memno = wishListVO.get(position).getCasesvo()
							.getMemno();
					Intent intent = new Intent(WishCase.this,
							Action_Tab_Thing.class);
					Bundle bundle = new Bundle();
					bundle.putInt("caseCaseno", caseno);
					bundle.putInt("caseMemno", memno);
					intent.putExtras(bundle);
					startActivity(intent);
					}else {
						Toast.makeText(WishCase.this, "此筆合購案未上架",
								Toast.LENGTH_SHORT).show();
					}
				}

			});

		}
	}

	private class JoinCaseViewAdapter extends BaseAdapter {

		private LayoutInflater layoutInflater;
		private List<AndroidCasesVO> wishListVO;

		public JoinCaseViewAdapter(Context context,
				List<AndroidCasesVO> wishListVO) {
			layoutInflater = LayoutInflater.from(context);
			this.wishListVO = wishListVO;
		}

		@Override
		public int getCount() {
			return wishListVO.size();
		}

		@Override
		public Object getItem(int position) {

			return wishListVO.get(position);
		}

		@Override
		public long getItemId(int position) {
			return wishListVO.get(position).getWishListVO().getCaseno();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null) {
				convertView = layoutInflater.inflate(R.layout.wish_case_list,
						null);
			}
			
			if(position %2 == 0){
				convertView.setBackgroundColor(Color.rgb(255, 255, 255));
			}else{
				
				convertView.setBackgroundColor(Color.rgb(248,248,248));
			}
			
			
//			175, 238, 238  我覺得不錯
			AndroidCasesVO wishcase = wishListVO.get(position);
			
			casetitle = (TextView) convertView.findViewById(R.id.casetitle);
			caseMin = (TextView) convertView.findViewById(R.id.wishcount);
			caseMax = (TextView) convertView.findViewById(R.id.casestatus);
			wishprice = (TextView) convertView.findViewById(R.id.wishprice);
			helpStatus = wishcase.getCasesvo().getStatus();
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
			if(wishcase.getCasesvo().getSpno() == 0){
			String priceend = Integer.toString(wishcase.getCaseproductvo().getUnitprice() - wishcase.getCasesvo().getDiscount());
			wishprice.setText("合購價 : "+priceend+"元");
			}else{
				Integer price = wishcase.getShopproductVO().getUnitprice().intValue();
				String priceend = Integer.toString(price - wishcase.getCasesvo().getDiscount());
				wishprice.setText("合購價 : "+priceend+"元");
			}
				
				
				
			casetitle.setText(wishcase.getCasesvo().getTitle());
			caseMin.setText("目前/成團/上限 數量 : "+wishcase.getTotalOty()+" / "+wishcase.getCasesvo().getMinqty()+" / "+wishcase.getCasesvo().getMaxqty());
			caseMax.setText("合購案狀態 : " + statusString);
			
			return convertView;
		}
	}

}
