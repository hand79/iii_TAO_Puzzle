package iii.ya803g2.casesearchpage;

import iii.ya803g2.casesearchpage.Sell_Thing_Page1.RetrieveJsonContentTask;
import iii.ya803g2.casesearchpage.Sell_Thing_Page1.TimeGo;
import iii.ya803g2.login.Homepage;
import iii.ya803g2.login.SeverData;
import iii.ya803g2.membercenter.Membercenter;
import iii.ya803g2.memberdata.Memberdata;
import iii.ya803g2.moneycenter.Storage_money;
import iii.ya803g2.mycase.MyCaseServer;
import iii.ya803g2.shopsearchpage.Action_Tab_Shop;

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
import android.os.Handler;
import android.app.Fragment;
import android.util.DisplayMetrics;
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
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class Sell_Thing_Page2 extends Fragment {
	private TextView Time;
	private ListView page2listView;
	private List<AndroidCasesVO> helpPage2;
	private TextView ship1;
	private TextView ship2;
	private TextView casedescription, minqty, maxqty;
	private TextView thingpage2member;
	private Action_Tab_Thing Helplist;
	private List<AndroidCasesVO> omg;
	private TimeGo timeHelp;
	private Button goOrder, addwish;
	private ImageView memberImage;
	private TextView page2Title;
	private List<AndroidCasesVO> shopData;
	private RetrieveJsonContentTask task;
	private ProgressDialog progressDialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.thing_data_page2, container,
				false);

		Time = (TextView) view.findViewById(R.id.page2starttime);
		page2Title = (TextView) view.findViewById(R.id.thingpage2title);
		thingpage2member = (TextView) view.findViewById(R.id.thingpage2member);
		page2listView = (ListView) view.findViewById(R.id.page2listView);
		goOrder = (Button) view.findViewById(R.id.Button02);
		memberImage = (ImageView) view.findViewById(R.id.memberimage);
		addwish = (Button) view.findViewById(R.id.addwish);
		Action_Tab_Thing action_Tab_Thing = (Action_Tab_Thing) this
				.getActivity();
		omg = action_Tab_Thing.getHelpPage2();
		page2Title.setText("主購:"+omg.get(0).getMembervo().getMemid()+"，目前/成團/上限 數量: "+omg.get(0).getTotalOty()+" / "+omg.get(0).getCasesvo().getMinqty()+" / "+omg.get(0).getCasesvo().getMaxqty());
		
		long helpTime = omg.get(0).getCasesvo().getEtime().getTime()
				- System.currentTimeMillis();
		
		
		if (omg.get(0).getCasesvo().getStatus().intValue() == 0) {
			addwish.setVisibility(View.INVISIBLE);
			goOrder.setVisibility(View.INVISIBLE);
		}
		if (omg.get(0).getCasesvo().getStatus().intValue() == 3) {
			addwish.setVisibility(View.INVISIBLE);
			goOrder.setVisibility(View.INVISIBLE);
		}
		if (omg.get(0).getCasesvo().getStatus().intValue() == 4) {
			addwish.setVisibility(View.INVISIBLE);
			goOrder.setVisibility(View.INVISIBLE);
		}
		if (omg.get(0).getCasesvo().getStatus().intValue() == 5) {
			addwish.setVisibility(View.INVISIBLE);
			goOrder.setVisibility(View.INVISIBLE);
		}
		if (omg.get(0).getCasesvo().getStatus().intValue() == 6) {
			addwish.setVisibility(View.INVISIBLE);
			goOrder.setVisibility(View.INVISIBLE);
		}
	
		
		
		if (timeHelp == null) {
			timeHelp = new TimeGo(helpTime, 1000);
		}
		
		
		
		
		
		
		
		
		
		timeHelp.start();
		showpage2();

		byte[] memberImageHelp = omg.get(0).getMembervo().getPhoto();
		if (memberImageHelp != null) {
			Bitmap bitmap = BitmapFactory.decodeByteArray(memberImageHelp, 0,
					memberImageHelp.length);
			memberImage.setImageBitmap(bitmap);
		}

		
		if(omg.get(0).getCasesvo().getCpno() == 0){
			thingpage2member.setText(omg.get(0).getShopproductVO().getName());
			
		}else{
		
		thingpage2member.setText(omg.get(0).getCaseproductvo().getName());
		}
		wishclick();
		clickorder();
		
		SharedPreferences pref = this.getActivity().getSharedPreferences(
				SeverData.PREF_FILE, Context.MODE_PRIVATE);

		Integer membertype = pref.getInt("membertype", 0);
		if (membertype == 1) {
			addwish.setVisibility(View.INVISIBLE);
			goOrder.setVisibility(View.INVISIBLE);
		}
		
		
		
		
		return view;
	}

	private void wishclick() {
		addwish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(Sell_Thing_Page2.this.getActivity())
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

										SharedPreferences pref = Sell_Thing_Page2.this
												.getActivity()
												.getSharedPreferences(
														SeverData.PREF_FILE,
														Context.MODE_PRIVATE);
										String account = pref.getString(
												"account", "");

										int casenoString = Sell_Thing_Page2.this
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
		goOrder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();

				intent.setClass(Sell_Thing_Page2.this.getActivity(),
						OrderPage.class);

				Bundle bundle = new Bundle();
				bundle.putString("helpForTitle", omg.get(0).getCasesvo()
						.getTitle());

				bundle.putString("helpForShip1", omg.get(0).getCasesvo()
						.getShip1());
				bundle.putInt("helpForShipcost1", omg.get(0).getCasesvo()
						.getShipcost1());

				bundle.putString("helpForShip2", omg.get(0).getCasesvo()
						.getShip2());
				bundle.putInt("helpForShipcost2", omg.get(0).getCasesvo()
						.getShipcost2());

				bundle.putInt("helpForCaseno", omg.get(0).getCasesvo()
						.getCaseno());
				bundle.putInt("helpForDiscount", omg.get(0).getCasesvo()
						.getDiscount());
				
				
				
				if(omg.get(0).getCasesvo().getCpno() == 0){
					Integer	price = omg.get(0).getShopproductVO().getUnitprice().intValue();
					bundle.putInt("helpForUnitprice", price);
				}else{
				bundle.putInt("helpForUnitprice", omg.get(0).getCaseproductvo()
						.getUnitprice());
				}
				
				
				bundle.putInt("helpForMaxqty", omg.get(0).getCasesvo()
						.getMaxqty());
				bundle.putInt("helpForCaseno", omg.get(0).getCasesvo()
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
			addwish.setVisibility(View.INVISIBLE);
			goOrder.setVisibility(View.INVISIBLE);
			Time.setText("合購案已結束");
		}

	}

	private void showpage2() {

		page2listView.setAdapter(new Page2ViewAdapter(Sell_Thing_Page2.this
				.getActivity(), omg));
		// 點選景點(spot)後將該景點ID取出並傳至下個Activity
		page2listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
			}
		});

	}

	public class Page2ViewAdapter extends BaseAdapter {

		private LayoutInflater layoutInflater;
		private List<AndroidCasesVO> omg;

		public Page2ViewAdapter(Context context, List<AndroidCasesVO> omg) {
			layoutInflater = LayoutInflater.from(context);

			this.omg = omg;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return omg.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return omg.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = layoutInflater.inflate(
						R.layout.thing_data_page2_list, null);
			}
			AndroidCasesVO casesFinalDetail = omg.get(position);
			ship1 = (TextView) convertView.findViewById(R.id.ship1page2);
			ship2 = (TextView) convertView.findViewById(R.id.ship2page2);
			minqty = (TextView) convertView.findViewById(R.id.minqty);
			maxqty = (TextView) convertView.findViewById(R.id.maxqty);
			casedescription = (TextView) convertView
					.findViewById(R.id.casedescriptionpag2);

			minqty.setVisibility(View.GONE);
			maxqty.setVisibility(View.GONE);

			ship1.setText(casesFinalDetail.getCasesvo().getShip1() 	+ "，運費:" + casesFinalDetail.getCasesvo().getShipcost1()
					+ "元    ");

			ship2.setText(casesFinalDetail.getCasesvo().getShip2() 
					+ "，運費:" + casesFinalDetail.getCasesvo().getShipcost1()
					+ "元");
			if (casesFinalDetail.getCasesvo().getShip2() == null) {
				ship2.setVisibility(View.GONE);
			}
			casedescription
					.setText(casesFinalDetail.getCasesvo().getCasedesc());
			return convertView;
		}

	}

	class RetrieveJsonContentTask extends
			AsyncTask<String, Integer, List<AndroidCasesVO>> {
		// invoked on the UI thread immediately after the task is executed.
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(
					Sell_Thing_Page2.this.getActivity());
			progressDialog.setMessage("Loading...");
			progressDialog.show();
		}

		// invoked on the background thread immediately after onPreExecute()
		@Override
		protected List<AndroidCasesVO> doInBackground(String... params) {
			MyCaseServer myCaseServer = new MyCaseServer();
			List<AndroidCasesVO> wishListVO = myCaseServer
					.addwish(params[0],
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
				Toast.makeText(Sell_Thing_Page2.this.getActivity(),
						"抱歉您已經追蹤過此合購案", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(Sell_Thing_Page2.this.getActivity(),
						"您已經成功追蹤此合購案", Toast.LENGTH_SHORT).show();
			}
		}

	}

}
