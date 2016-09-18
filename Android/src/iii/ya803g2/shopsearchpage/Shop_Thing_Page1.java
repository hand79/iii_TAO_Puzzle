package iii.ya803g2.shopsearchpage;

import iii.ya803g2.casesearchpage.Action_Tab_Thing;
import iii.ya803g2.casesearchpage.AndroidCasesVO;
import iii.ya803g2.casesearchpage.CasesServer;
import iii.ya803g2.casesearchpage.Sell_Thing_Page1;
import iii.ya803g2.login.Homepage;
import iii.ya803g2.membercenter.Membercenter;
import iii.ya803g2.memberdata.Memberdata;
import iii.ya803g2.moneycenter.Storage_money;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.example.tao_puzzle.R;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Shop_Thing_Page1 extends android.app.Fragment {
	private GetShopTask getShopTask;
	private List<AndroidCasesVO> shopnoHelp;
	private ImageView memberImage, image;
	private ListView shoplist;
	private WebView webView;
	private TextView memid, title;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.shop_data_page1, container, false);
		memberImage = (ImageView) view.findViewById(R.id.memberimage);
		shoplist = (ListView) view.findViewById(R.id.caselistview);
		memid = (TextView) view.findViewById(R.id.caseprice);
		title = (TextView) view.findViewById(R.id.gogo2);
		webView = (WebView) view.findViewById(R.id.casedesc);
		image = (ImageView) view.findViewById(R.id.shoppic);

		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		// SharedPreferences pref =
		// this.getActivity().getSharedPreferences(SeverData.PREF_FILE,Context.MODE_PRIVATE);
		int shopno = this.getActivity().getIntent().getExtras()
				.getInt("shopShopno");

		int memno = this.getActivity().getIntent().getExtras()
				.getInt("shopMemno");

		getShopTask = new GetShopTask();
		// params will be passed to AsyncTask.doInBackground(Integer... params)
		getShopTask.execute(shopno, memno);
	}

	class GetShopTask extends AsyncTask<Integer, Integer, List<AndroidCasesVO>> {
		private ProgressDialog progressDialog;

		// invoked on the UI thread immediately after the task is executed.
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(
					Shop_Thing_Page1.this.getActivity());
			progressDialog.setMessage("Loading...");
			progressDialog.show();
		}

		@Override
		protected List<AndroidCasesVO> doInBackground(Integer... shopno) {
			ShopServer shopServer = new ShopServer();
			shopnoHelp = shopServer.findByShopno(shopno[0], shopno[1]);
			return shopnoHelp;
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
		protected void onPostExecute(List<AndroidCasesVO> shopnoHelp) {
			progressDialog.cancel();
			if (shopnoHelp == null) {
				Toast.makeText(Shop_Thing_Page1.this.getActivity(),
						"抱歉找不到該筆資訊", Toast.LENGTH_SHORT).show();
			} else {

				byte[] memberImageHelp = shopnoHelp.get(0).getMembervo()
						.getPhoto();

				if (memberImageHelp != null) {
					Bitmap bitmap = BitmapFactory.decodeByteArray(
							memberImageHelp, 0, memberImageHelp.length);
					memberImage.setImageBitmap(bitmap);
				}

				showDetail(shopnoHelp);
			}
		}

		private void showDetail(List<AndroidCasesVO> shopnoHelp) {
			String description = shopnoHelp.get(0).getShopvo().getShop_desc();
			webView.loadDataWithBaseURL(null, description, "text/html",
					"utf-8", null);
			
			title.setText("店主: "+shopnoHelp.get(0).getMembervo().getMemid());
			memid.setText(shopnoHelp.get(0).getShopvo().getTitle());
			
			// shoplist.setAdapter(new DetailListAdapter(Shop_Thing_Page1.this
			// .getActivity(), shopnoHelp));
			// shoplist.setOnItemClickListener(new OnItemClickListener() {
			// public void onItemClick(AdapterView<?> parent, View v,
			// int position, long id) {
			//
			// }
			// });

		}

	}

	// private class DetailListAdapter extends BaseAdapter {
	// private LayoutInflater layoutInflater;
	// private List<AndroidCasesVO> shopDetail;
	//
	// public DetailListAdapter(Context context,
	// List<AndroidCasesVO> shopDetail) {
	// layoutInflater = LayoutInflater.from(context);
	// this.shopDetail = shopDetail;
	// }
	//
	// @Override
	// public int getCount() {
	// return shopDetail.size();
	// }
	//
	// @Override
	// public Object getItem(int position) {
	// return shopDetail.get(position);
	// }
	//
	// @Override
	// public long getItemId(int position) {
	// // TODO Auto-generated method stub
	// return shopDetail.get(position).getCasesvo().getCaseno();
	// }
	//
	// @Override
	// public View getView(int position, View convertView, ViewGroup parent) {
	// if (convertView == null) {
	// convertView = layoutInflater.inflate(
	// R.layout.shop_data_page1_list, null);
	// }
	//
	// image = (ImageView) convertView
	// .findViewById(R.id.shoppic);
	// webView = (WebView) convertView.findViewById(R.id.webView1);
	//
	// AndroidCasesVO shopFinalDetail = shopDetail.get(position);
	//
	// memid.setText(shopFinalDetail.getMembervo().getMemid());
	// title.setText(shopFinalDetail.getShopvo().getTitle());
	// System.out.println("叫我稱號"+title);
	// byte[] imageForData = shopFinalDetail.getShopvo().getPic();
	// if (imageForData != null) {
	// Bitmap bitmap1 = BitmapFactory.decodeByteArray(imageForData, 0,
	// imageForData.length);
	// image.setImageBitmap(bitmap1);
	// }else{
	// image.setVisibility(View.GONE);
	// }
	// String description =
	// String.valueOf(shopFinalDetail.getShopvo().getShop_desc());
	// System.out.println("叫我敘述唷"+description);
	//
	//
	// webView.loadData("<html><body>You scored <b>192</b> points.</body></html>",
	// "text/html", "utf-8");
	//
	// return convertView;
	// }
	// }

	@Override
	public void onPause() {

		super.onPause();
		Action_Tab_Shop action_Tab_Shop = (Action_Tab_Shop) this.getActivity();
		action_Tab_Shop.setHelpPage(shopnoHelp);
	}
}
