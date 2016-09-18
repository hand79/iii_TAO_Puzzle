package iii.ya803g2.moneycenter;

import iii.ya803g2.casesearchpage.AndroidCasesVO;
import iii.ya803g2.casesearchpage.Sell_Thing_Page3;
import iii.ya803g2.login.Homepage;
import iii.ya803g2.login.SeverData;
import iii.ya803g2.membercenter.Membercenter;
import iii.ya803g2.memberdata.Memberdata;
import iii.ya803g2.moneycenter.Money_Credit_Card.RetrieveJsonContentTask;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.example.tao_puzzle.R;
import com.example.tao_puzzle.R.color;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Money_ATM extends Activity {
	private Button cancel;
	private Button submit;
	private TextView title, ordsts;
	private ProgressDialog progressDialog;
	private RetrieveJsonContentTask task;
	private String atmac;
	private EditText bankmb;
	private TextView checktime;
	private static TextView settime;
	private static TextView setdate;
	private static TextView checkdate;
	private static int mYear, mMonth, mDay, mHour, mMinute;
	private ImageView clickData, clickTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_money_atm);

		final Calendar c = Calendar.getInstance();

		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		mHour = c.get(Calendar.HOUR_OF_DAY);
		mMinute = c.get(Calendar.MINUTE);

		findViews();
	}

	// 將指定的日期顯示在TextView上。「mMonth + 1」是因為一月的值是0而非1
	private static void updateDisplay() {
		StringBuilder date = new StringBuilder().append(mYear).append("-")
				.append(pad(mMonth + 1)).append("-").append(pad(mDay));

		

		setdate.setText(date);
	

	}
	
	private static void updateTime() {
		
		StringBuilder daytime = new StringBuilder().append(pad(mHour))
				.append(":").append(pad(mMinute));

	
		settime.setText("    " + daytime);
		
	}

	private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}

	private void findViews() {
		submit = (Button) findViewById(R.id.submit);
		cancel = (Button) findViewById(R.id.del);
		title = (TextView) findViewById(R.id.title);
		ordsts = (TextView) findViewById(R.id.ordsts);
		bankmb = (EditText) findViewById(R.id.bankmb);
		checktime = (TextView) findViewById(R.id.textView6);
		checkdate = (TextView) findViewById(R.id.textView5);
		clickData = (ImageView) findViewById(R.id.mapimage);
		clickTime = (ImageView) findViewById(R.id.imageView2);
		settime = (TextView) findViewById(R.id.jointime);
		setdate = (TextView) findViewById(R.id.setdate);

		int savePrice = this.getIntent().getExtras().getInt("saveMoney");
		title.setText("你所選的儲值金額 : " + savePrice + "元");
		atmac = "";
		for (int i = 0; i <= 12; i++) {
			String helpAtmac = String.valueOf((int) (Math.random() * 10));
			atmac += helpAtmac;
		}

		ordsts.setText("轉帳帳號 : " + atmac);

		clickTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				TimePickerFragment timePickerFragment = new TimePickerFragment();
				FragmentManager tm = getFragmentManager();
				timePickerFragment.show(tm, "timePicker");

			}
		});

		clickData.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				DatePickerFragment datePickerFragment = new DatePickerFragment();
				FragmentManager fm = getFragmentManager();
				datePickerFragment.show(fm, "datePicker");

			}
		});

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent();
				intent.setClass(Money_ATM.this, Moneycenter.class);
				startActivity(intent);
				finish();
			}
		});

		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Timestamp qtime = new Timestamp(System.currentTimeMillis());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date date = sdf.parse(setdate.getText().toString());
				
					if (date.getTime() >= qtime.getTime()-90000000) {
					
					} else {
						
						Toast.makeText(Money_ATM.this, "不可選擇過去的日期",
								Toast.LENGTH_SHORT).show();
						return;
					}

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if ("".equals(setdate.getText().toString().trim())) {
					Toast.makeText(Money_ATM.this, "請輸入預計轉帳的日期!",
							Toast.LENGTH_SHORT).show();
				} else if ("".equals(settime.getText().toString().trim())) {
					Toast.makeText(Money_ATM.this, "請輸入預計轉帳的時間!",
							Toast.LENGTH_SHORT).show();
				} else if ("".equals(bankmb.getText().toString().trim())) {
					Toast.makeText(Money_ATM.this, "請輸入您的銀行後四碼!",
							Toast.LENGTH_SHORT).show();
				} else if (checkdate.getText().toString().trim() != ""
						&& checktime.getText().toString().trim() != ""
						&& bankmb.getText().toString().trim() != "") {

					new AlertDialog.Builder(Money_ATM.this)
							.setTitle(R.string.savemoney)
							.setIcon(R.drawable.gogo)
							.setMessage(R.string.addmoney)
							.setPositiveButton(R.string.cancel,
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.cancel();

										}
									})

							.setNegativeButton(R.string.submit,
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {

											SharedPreferences pref = getSharedPreferences(
													SeverData.PREF_FILE,
													MODE_PRIVATE);
											String account = pref.getString(
													"account", "");
											String dpshow = Money_ATM.this
													.getIntent().getExtras()
													.getString("DPSHOW");
											int savePriceString = Money_ATM.this
													.getIntent().getExtras()
													.getInt("saveMoney");

											String savePrice = Integer
													.toString(savePriceString);

											task = new RetrieveJsonContentTask();
											task.execute(account, dpshow,
													savePrice, atmac);
										}
									}).setCancelable(false).show();

				}
			}
		});

	}

	// 此Fragment為內部類別，必須宣告為static
	public static class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		@Override
		// 改寫此方法以提供Dialog內容
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// 建立DatePickerDialog物件
			// this為OnDateSetListener物件
			// mYear、mMonth、mDay會成為日期挑選器預選的年月日
			DatePickerDialog datePickerDialog = new DatePickerDialog(
					getActivity(), this, mYear, mMonth, mDay);
			return datePickerDialog;
		}

		@Override
		// 日期挑選完成會呼叫此方法，並傳入選取的年月日
		public void onDateSet(DatePicker view, int year, int month, int day) {
			mYear = year;
			mMonth = month;
			mDay = day;
			updateDisplay();
		}
	}

	// 此Fragment為內部類別，必須宣告為static
	public static class TimePickerFragment extends DialogFragment implements
			TimePickerDialog.OnTimeSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// 建立TimePickerDialog物件
			// this為OnTimeSetListener物件
			// mHour、mMinute會成為時間挑選器預選的時與分
			TimePickerDialog timePickerDialog = new TimePickerDialog(
					getActivity(), this, mHour, mMinute, false);
			return timePickerDialog;
		}

		@Override
		// 時間挑選完成會呼叫此方法，並傳入選取的時與分
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			mHour = hourOfDay;
			mMinute = minute;
			updateTime();
		}
	}

	@Override
	protected void onStart() {
		super.onStart();

	}

	class RetrieveJsonContentTask extends
			AsyncTask<String, Integer, List<AndroidCasesVO>> {
		// invoked on the UI thread immediately after the task is executed.
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(Money_ATM.this);
			progressDialog.setMessage("Loading...");
			progressDialog.show();
		}

		// invoked on the background thread immediately after onPreExecute()
		@Override
		protected List<AndroidCasesVO> doInBackground(String... params) {
			MoneyServer moneyServer = new MoneyServer();
			List<AndroidCasesVO> memberMoneyOrder = moneyServer.addDpsord(
					params[0], params[1], params[2], params[3]);
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
				Toast.makeText(Money_ATM.this, "抱歉請聯絡製作人", Toast.LENGTH_SHORT)
						.show();
			} else {

				Toast.makeText(Money_ATM.this, "恭喜你已經儲值成功，請等待客服人員審核",
						Toast.LENGTH_LONG).show();

				Intent intent = new Intent();
				intent.setClass(Money_ATM.this, Moneycenter.class);
				startActivity(intent);
				finish();
				// showMoneyListView(memberMoneyOrder);
			}
		}
	}

}