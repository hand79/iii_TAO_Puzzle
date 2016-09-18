package iii.ya803g2.casesearchpage;

import android.app.Fragment;
import iii.ya803g2.login.Homepage;
import iii.ya803g2.login.LoginPage;
import iii.ya803g2.login.SeverData;
import iii.ya803g2.mycase.MyCaseServer;
import iii.ya803g2.mycase.WishCase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.example.tao_puzzle.R;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class Sell_Thing_Page1 extends Fragment {
	private TextView Time;
	private List<AndroidCasesVO> caseNoHelp;
	private GetCaseTask getCaseTask;
	private ListView casedetailforlist;
	private ImageView memberImage;
	private TextView thingmember;
	private TextView thingname;
	private TextView sprice;
	private TextView eprice;
	private TextView people, helpItem;
	private TimeGo timeHelp;
	private Button startorder, addwish;
	private RetrieveJsonContentTask task;
	private ProgressDialog progressDialog;
	private WebView webView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.thing_data_page1, container,
				false);
		memberImage = (ImageView) view.findViewById(R.id.memberimage);
		thingmember = (TextView) view.findViewById(R.id.thingmember);
		thingname = (TextView) view.findViewById(R.id.thingname);
		sprice = (TextView) view.findViewById(R.id.sprice);
		eprice = (TextView) view.findViewById(R.id.eprice);
		people = (TextView) view.findViewById(R.id.people);
		Time = (TextView) view.findViewById(R.id.starttime);
		casedetailforlist = (ListView) view.findViewById(R.id.casedetaillist);
		startorder = (Button) view.findViewById(R.id.Button02);
		helpItem = (TextView) view.findViewById(R.id.ordertitle);
		addwish = (Button) view.findViewById(R.id.addwish);
		helpItem.setVisibility(view.INVISIBLE);
		clickorder();
		wishclick();

		SharedPreferences pref = this.getActivity().getSharedPreferences(
				SeverData.PREF_FILE, Context.MODE_PRIVATE);

		Integer membertype = pref.getInt("membertype", 0);
		if (membertype == 1) {
			addwish.setVisibility(View.INVISIBLE);
			startorder.setVisibility(View.INVISIBLE);
		}

		return view;
	}

	private void wishclick() {
		addwish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(Sell_Thing_Page1.this.getActivity())
						.setTitle(R.string.wish)
						.setIcon(R.drawable.gogo)
						.setMessage(R.string.addwish)
						.setPositiveButton(R.string.cancel,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.cancel();

									}
								})

						.setNegativeButton(R.string.submit,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

										SharedPreferences pref = Sell_Thing_Page1.this
												.getActivity()
												.getSharedPreferences(
														SeverData.PREF_FILE,
														Context.MODE_PRIVATE);
										String account = pref.getString(
												"account", "");

										int casenoString = Sell_Thing_Page1.this
												.getActivity().getIntent()
												.getExtras()
												.getInt("caseCaseno");
										String caseno = Integer
												.toString(casenoString);

										task = new RetrieveJsonContentTask();
										task.execute(account, caseno);

									}
								}).setCancelable(false).show();

			}
		});

	}

	private void clickorder() {
		startorder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();

				intent.setClass(Sell_Thing_Page1.this.getActivity(),
						OrderPage.class);

				Bundle bundle = new Bundle();
				bundle.putString("helpForTitle", caseNoHelp.get(0).getCasesvo()
						.getTitle());

				bundle.putString("helpForShip1", caseNoHelp.get(0).getCasesvo()
						.getShip1());
				bundle.putInt("helpForShipcost1", caseNoHelp.get(0)
						.getCasesvo().getShipcost1());

				bundle.putString("helpForShip2", caseNoHelp.get(0).getCasesvo()
						.getShip2());
				bundle.putInt("helpForShipcost2", caseNoHelp.get(0)
						.getCasesvo().getShipcost2());

				bundle.putInt("helpForCaseno", caseNoHelp.get(0).getCasesvo()
						.getCaseno());
				bundle.putInt("helpForDiscount", caseNoHelp.get(0).getCasesvo()
						.getDiscount());
				
				if(caseNoHelp.get(0).getCasesvo().getCpno() == 0){
					Integer	price = caseNoHelp.get(0).getShopproductVO().getUnitprice().intValue();
					bundle.putInt("helpForUnitprice", price);
				}else{
				bundle.putInt("helpForUnitprice", caseNoHelp.get(0).getCaseproductvo()
						.getUnitprice());
				}
				bundle.putInt("helpForMaxqty", caseNoHelp.get(0).getCasesvo()
						.getMaxqty());
				bundle.putInt("helpForCaseno", caseNoHelp.get(0).getCasesvo()
						.getCaseno());
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});

	}

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	public class TimeGo extends CountDownTimer {

		public TimeGo(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			// TODO Auto-generated constructor stub
		}

		@SuppressLint("NewApi")
		@TargetApi(Build.VERSION_CODES.GINGERBREAD)
		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub
			long millis = millisUntilFinished;
			String gogo = String.format(
					"%02d天%02d時%02d分%02d秒",
					TimeUnit.MILLISECONDS.toDays(millis),
					TimeUnit.MILLISECONDS.toHours(millis)
							- TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS
									.toDays(millis)),
					TimeUnit.MILLISECONDS.toMinutes(millis)
							- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
									.toHours(millis)),
					TimeUnit.MILLISECONDS.toSeconds(millis)
							- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
									.toMinutes(millis)));

			Time.setText(gogo);
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			
		
				addwish.setVisibility(View.INVISIBLE);
				startorder.setVisibility(View.INVISIBLE);
			
			
			Time.setText("合購案已結束");
			
		}

	}

	@Override
	public void onStart() {
		super.onStart();
		// SharedPreferences pref =
		// this.getActivity().getSharedPreferences(SeverData.PREF_FILE,Context.MODE_PRIVATE);
		int caseno = this.getActivity().getIntent().getExtras()
				.getInt("caseCaseno");

		int memno = this.getActivity().getIntent().getExtras()
				.getInt("caseMemno");

		getCaseTask = new GetCaseTask();
		// params will be passed to AsyncTask.doInBackground(Integer... params)
		getCaseTask.execute(caseno, memno);
	}

	class GetCaseTask extends AsyncTask<Integer, Integer, List<AndroidCasesVO>> {
		private ProgressDialog progressDialog;

		// invoked on the UI thread immediately after the task is executed.
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(
					Sell_Thing_Page1.this.getActivity());
			progressDialog.setMessage("Loading...");
			progressDialog.show();
		}

		@Override
		protected List<AndroidCasesVO> doInBackground(Integer... caseno) {
			CasesServer casesServer = new CasesServer();
			caseNoHelp = casesServer.findByCaseno(caseno[0], caseno[1]);
			return caseNoHelp;
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
		protected void onPostExecute(List<AndroidCasesVO> caseNoHelp) {
			progressDialog.cancel();
			if (caseNoHelp == null) {
				Toast.makeText(Sell_Thing_Page1.this.getActivity(),
						"抱歉找不到該筆資訊", Toast.LENGTH_SHORT).show();
			} else {
				
				
				if (caseNoHelp.get(0).getCasesvo().getStatus().intValue() == 0) {
					addwish.setVisibility(View.INVISIBLE);
					startorder.setVisibility(View.INVISIBLE);
				}
				if (caseNoHelp.get(0).getCasesvo().getStatus().intValue() == 3) {
					addwish.setVisibility(View.INVISIBLE);
					startorder.setVisibility(View.INVISIBLE);
				}
				if (caseNoHelp.get(0).getCasesvo().getStatus().intValue() == 4) {
					addwish.setVisibility(View.INVISIBLE);
					startorder.setVisibility(View.INVISIBLE);
				}
				if (caseNoHelp.get(0).getCasesvo().getStatus().intValue() == 5) {
					addwish.setVisibility(View.INVISIBLE);
					startorder.setVisibility(View.INVISIBLE);
				}
				if (caseNoHelp.get(0).getCasesvo().getStatus().intValue() == 6) {
					addwish.setVisibility(View.INVISIBLE);
					startorder.setVisibility(View.INVISIBLE);
				}
				
				
				byte[] memberImageHelp = caseNoHelp.get(0).getMembervo()
						.getPhoto();

				if (memberImageHelp != null) {
					Bitmap bitmap = BitmapFactory.decodeByteArray(
							memberImageHelp, 0, memberImageHelp.length);
					memberImage.setImageBitmap(bitmap);
				}

				showDetail(caseNoHelp);
			}
		}

		public void showDetail(final List<AndroidCasesVO> caseNoHelp) {

			casedetailforlist.setAdapter(new DetailListAdapter(
					Sell_Thing_Page1.this.getActivity(), caseNoHelp));
			casedetailforlist.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View v,
						int position, long id) {

				}
			});
		}
	}

	private class DetailListAdapter extends BaseAdapter {
		private LayoutInflater layoutInflater;
		private List<AndroidCasesVO> caseDetail;

		public DetailListAdapter(Context context,
				List<AndroidCasesVO> caseDetail) {
			layoutInflater = LayoutInflater.from(context);
			this.caseDetail = caseDetail;
		}

		@Override
		public int getCount() {
			return caseDetail.size();
		}

		@Override
		public Object getItem(int position) {
			return caseDetail.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return caseDetail.get(position).getCasesvo().getCaseno();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = layoutInflater.inflate(
						R.layout.thing_data_page1_list, null);
			}

			TextView title = (TextView) convertView
					.findViewById(R.id.helpfortitle);
			webView = (WebView) convertView
					.findViewById(R.id.casedesc);
			ImageView image1 = (ImageView) convertView
					.findViewById(R.id.mapimage);
			ImageView image2 = (ImageView) convertView
					.findViewById(R.id.imageView2);
			ImageView image3 = (ImageView) convertView
					.findViewById(R.id.imageView3);
			TextView shopdesc = (TextView) convertView.findViewById(R.id.forshopdesc);
			AndroidCasesVO casesFinalDetail = caseDetail.get(position);

			title.setText("\n    "
					+ casesFinalDetail.getCasesvo().getTitle());
			
			if(casesFinalDetail.getCasesvo().getCpno() == 0){
				shopdesc.setText ("    "+casesFinalDetail.getShopproductVO().getPro_desc());
				webView.setVisibility(View.GONE);
				
				byte[] image11 = casesFinalDetail.getShopproductVO().getPic1();
				if (image11 != null) {
					Bitmap bitmap1 = BitmapFactory.decodeByteArray(image11, 0,
							image11.length);
					image1.setImageBitmap(bitmap1);
				} else {
					image1.setVisibility(View.GONE);
				}

				byte[] image12 = casesFinalDetail.getShopproductVO().getPic2();
				if (image12 != null) {
					Bitmap bitmap2 = BitmapFactory.decodeByteArray(image12, 0,
							image12.length);
					image2.setImageBitmap(bitmap2);
				} else {
					image2.setVisibility(View.GONE);
				}

				byte[] image13 = casesFinalDetail.getShopproductVO().getPic3();
				if (image13 != null) {
					Bitmap bitmap3 = BitmapFactory.decodeByteArray(image13, 0,
							image13.length);

					image3.setImageBitmap(bitmap3);
				} else {
					image3.setVisibility(View.GONE);
				}			
				
				
				
				thingmember.setText(casesFinalDetail.getShopproductVO().getName());
				
				
				sprice.setText("原價:"
						+ casesFinalDetail.getShopproductVO().getUnitprice()
								.toString() + "元");
			Integer	price = casesFinalDetail.getShopproductVO().getUnitprice().intValue();
				String priceEnd = Integer.toString(price
						- casesFinalDetail.getCasesvo().getDiscount());

				people.setText("合購價:" + priceEnd + "元");
				
				
			}else{
			String description = "    "+casesFinalDetail.getCaseproductvo().getCpdesc();
			
			webView.loadDataWithBaseURL(null, description, "text/html",
					"utf-8", null);
			shopdesc.setVisibility(View.GONE);
			
			
			byte[] image11 = casesFinalDetail.getCaseproductvo().getPic1();
			if (image11 != null) {
				Bitmap bitmap1 = BitmapFactory.decodeByteArray(image11, 0,
						image11.length);
				image1.setImageBitmap(bitmap1);
			} else {
				image1.setVisibility(View.GONE);
			}

			byte[] image12 = casesFinalDetail.getCaseproductvo().getPic2();
			if (image12 != null) {
				Bitmap bitmap2 = BitmapFactory.decodeByteArray(image12, 0,
						image12.length);
				image2.setImageBitmap(bitmap2);
			} else {
				image2.setVisibility(View.GONE);
			}

			byte[] image13 = casesFinalDetail.getCaseproductvo().getPic3();
			if (image13 != null) {
				Bitmap bitmap3 = BitmapFactory.decodeByteArray(image13, 0,
						image13.length);

				image3.setImageBitmap(bitmap3);
			} else {
				image3.setVisibility(View.GONE);
			}
			
			thingmember.setText(casesFinalDetail.getCaseproductvo().getName());
			
			sprice.setText("原價:"
					+ casesFinalDetail.getCaseproductvo().getUnitprice()
							.toString() + "元");
			
			String priceEnd = Integer.toString(casesFinalDetail
					.getCaseproductvo().getUnitprice()
					- casesFinalDetail.getCasesvo().getDiscount());
			
			people.setText("合購價:" + priceEnd + "元");
			
			}
			

			thingname.setText("主購:"+casesFinalDetail.getMembervo().getMemid()+"，目前/成團/上限 數量: "+casesFinalDetail.getTotalOty()+" / "+casesFinalDetail.getCasesvo().getMinqty()+" / "+casesFinalDetail.getCasesvo().getMaxqty());
		
			

			

			
			eprice.setVisibility(View.INVISIBLE);

//			Integer nowTotal = Integer.valueOf(casesFinalDetail.getTotalOty());

			

			long helpTime = casesFinalDetail.getCasesvo().getEtime().getTime()
					- System.currentTimeMillis();

			// final TimeGo TimeHelp = new TimeGo(helpTime, 1000);
			if (timeHelp == null) {
				timeHelp = new TimeGo(helpTime, 1000);
			}
			timeHelp.start();
			return convertView;
		}
	}

	class RetrieveJsonContentTask extends
			AsyncTask<String, Integer, List<AndroidCasesVO>> {
		// invoked on the UI thread immediately after the task is executed.
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(
					Sell_Thing_Page1.this.getActivity());
			progressDialog.setMessage("Loading...");
			progressDialog.show();
		}

		// invoked on the background thread immediately after onPreExecute()
		@Override
		protected List<AndroidCasesVO> doInBackground(String... params) {
			MyCaseServer myCaseServer = new MyCaseServer();
			List<AndroidCasesVO> wishListVO = myCaseServer.addwish(params[0],
					params[1]);
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
				Toast.makeText(Sell_Thing_Page1.this.getActivity(),
						"抱歉您已經追蹤過此合購案", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(Sell_Thing_Page1.this.getActivity(),
						"您已經成功追蹤此合購案", Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Action_Tab_Thing action_Tab_Thing = (Action_Tab_Thing) this
				.getActivity();
		action_Tab_Thing.setHelpPage2(caseNoHelp);
	}

}