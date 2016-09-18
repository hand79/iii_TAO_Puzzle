package iii.ya803g2.casesearchpage;

import iii.ya803g2.login.SeverData;
import iii.ya803g2.moneycenter.Moneycenter;
import iii.ya803g2.moneycenter.Storage_money;

import java.util.ArrayList;
import java.util.List;

import com.example.tao_puzzle.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class OrderPage extends Activity {

	private TextView helpForTitle;

	private ProgressDialog progressDialog;
	private RetrieveJsonContentTask task;
	private AddOrderJsonContentTask Ordertask;
	private TextView memberMoney;
	private TextView castMoney;
	private TextView helpForTotal;
	private Spinner shipEnd, moneyCount;
	private EditText helpMoneyCount;
	private List<AndroidCasesVO> orderOneData;
	private Integer priceEnd;
	private Integer sososoHard;
	private Button gogoOrder;
	private TextView nextTimeForYou;
	private TextView showShip;
	private String orderValue;
	private CheckBox forMemberPaper;
	private String helpPager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.thing_data_order);

		helpForTitle = (TextView) findViewById(R.id.address);
		gogoOrder = (Button) findViewById(R.id.Button02);
		shipEnd = (Spinner) findViewById(R.id.spinner1);
		castMoney = (TextView) findViewById(R.id.textView6);
		helpMoneyCount = (EditText) findViewById(R.id.editText1);
		moneyCount = (Spinner) findViewById(R.id.spinner2);
		nextTimeForYou = (TextView) findViewById(R.id.textView7);

		showShip = (TextView) findViewById(R.id.textView9);
		forMemberPaper =(CheckBox) findViewById(R.id.checkBox1);
		
		Bundle bundle = this.getIntent().getExtras();
		String orderTitle = new String(bundle.getString("helpForTitle"));
		String ship1 = new String(bundle.getString("helpForShip1"));
		String ForShipcost1 = String.valueOf(bundle.getInt("helpForShipcost1"));
		
		
		
		String ship2 = bundle.getString("helpForShip2");
		String ForShipcost2 = String.valueOf(bundle.getInt("helpForShipcost2"));
		
		Integer Discount = Integer.valueOf(bundle.getInt("helpForDiscount"));
		Integer Unitprice = Integer.valueOf(bundle.getInt("helpForUnitprice"));

		priceEnd = Integer.valueOf( Unitprice - Discount );

		helpForTitle.setText(orderTitle);

		
		
		
		String[] shipHelp = new String[ship2 == null ? 1:2];
		shipHelp[0] = ship1 + " , 運費: " + ForShipcost1 + "元";
		if(ship2 != null){
			shipHelp[1] = ship2 + " , 運費: " + ForShipcost2 + "元";
		}
			
		
		ArrayAdapter<String> adaptership = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, shipHelp);
		adaptership
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		shipEnd.setAdapter(adaptership);
		shipEnd.setOnItemSelectedListener(shipgogo);
		
		
		forMemberPaper.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() { 
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		        if (isChecked) {
		         helpPager = String.valueOf(isChecked).trim();
		         
		         
		         
		        } else {
		        	helpPager = String.valueOf(isChecked).trim();
		        	
		        }
		    }
		});
		
		
		
		clickFinalOrder();

	}

	
		
	
	Spinner.OnItemSelectedListener shipgogo = new Spinner.OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int pos,
				long id) {

			showShip.setText(parent.getItemAtPosition(pos).toString());
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {

			

		}
	};

	
	
	
	
	
	private void clickFinalOrder() {
		gogoOrder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				
				

				
				
				
				if (helpPager == "false" || helpPager == null){
					
					Toast.makeText(OrderPage.this, "你必須勾選同意會員條款", Toast.LENGTH_SHORT)
					.show();
					
					
					
				}if(helpPager == "true"){
					
					
					new AlertDialog.Builder(OrderPage.this)
					.setTitle(R.string.ordertitle)
					.setIcon(R.drawable.gogo)
					.setMessage(R.string.addorder)
					.setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
					
						@Override
						public void onClick(DialogInterface dialog, int which) {
								dialog.cancel();
							
								
								
								
						}
					})
					
					.setNegativeButton(R.string.submit,new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							
							
							
							Bundle bundle = OrderPage.this.getIntent().getExtras();

							Integer intcaseno = Integer.valueOf(bundle
									.getInt("helpForCaseno"));

							String caseno = String.valueOf(intcaseno);
							System.out.println(caseno);

							String ship = Integer.toString(shipEnd
									.getSelectedItemPosition()+1);
							
							String getqty = Integer.toString(moneyCount
									.getSelectedItemPosition()+1);
							
							
							Integer intmemno = Integer.valueOf(orderOneData.get(0)
									.getMembervo().getMemno());
							String memno = String.valueOf(intmemno);

							Ordertask = new AddOrderJsonContentTask();
							Ordertask.execute(caseno, getqty, ship, memno);
						}
					})
					.setCancelable(false).show();
					
					
					
					
					
				}
				
				

				
				
			}
		});

	}

	@Override
	protected void onStart() {
		super.onStart();
		SharedPreferences pref = getSharedPreferences(SeverData.PREF_FILE,
				MODE_PRIVATE);

		String account = pref.getString("account", "");
		Bundle bundle = this.getIntent().getExtras();
		String caseNO = Integer.toString(bundle.getInt("helpForCaseno"));
		task = new RetrieveJsonContentTask();
		task.execute(account, caseNO);
	}

	class RetrieveJsonContentTask extends
			AsyncTask<String, Integer, List<AndroidCasesVO>> {
		// invoked on the UI thread immediately after the task is executed.
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(OrderPage.this);
			progressDialog.setMessage("Loading...");
			progressDialog.show();
		}

		// invoked on the background thread immediately after onPreExecute()
		@Override
		protected List<AndroidCasesVO> doInBackground(String... params) {
			CasesServer casesServer = new CasesServer();

			orderOneData = casesServer.getOrder(params[0], params[1]);

			return orderOneData;
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
		protected void onPostExecute(List<AndroidCasesVO> orderOneData) {
			progressDialog.cancel();

			if (orderOneData == null || orderOneData.isEmpty()) {
				Toast.makeText(OrderPage.this, "抱歉請聯絡製作人", Toast.LENGTH_SHORT)
						.show();
			} else {
				viewPage(orderOneData);
			}
			Bundle bundle = OrderPage.this.getIntent().getExtras();
			Integer Maxqty = Integer.valueOf(bundle.getInt("helpForMaxqty"));

			sososoHard = Integer.valueOf(Maxqty
					- orderOneData.get(0).getTotalOty());
			System.out.println(sososoHard);
			List<String> conut = new ArrayList<String>();
			for (int i = 1; i <= sososoHard; i++) {

				// String[] shipHelp = { };

				conut.add(Integer.toString(i));
			}

			String[] ohoho = conut.toArray(new String[conut.size()]);

			ArrayAdapter<String> adaptermoney = new ArrayAdapter<String>(
					OrderPage.this, android.R.layout.simple_spinner_item, ohoho);

			adaptermoney
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			moneyCount.setAdapter(adaptermoney);
			moneyCount.setOnItemSelectedListener(moneylistener);

		}

		Spinner.OnItemSelectedListener moneylistener = new Spinner.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {

				Integer soHard = Integer.parseInt((String) parent
						.getItemAtPosition(pos));

				castMoney.setText("數量金額 : " + priceEnd + " X " + soHard + " = "
						+ priceEnd * soHard+" 元");
		
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

				Toast.makeText(OrderPage.this, "你還未選擇數量", Toast.LENGTH_SHORT)
						.show();

			}
		};

		private void viewPage(List<AndroidCasesVO> orderOneData) {

			memberMoney = (TextView) findViewById(R.id.ordsts);

			helpForTotal = (TextView) findViewById(R.id.textView8);
			AndroidCasesVO helpDetail = orderOneData.get(0);

			memberMoney.setText("目前可用餘額 : "
					+ helpDetail.getMembervo().getMoney().toString()+"元");

			Bundle bundle = OrderPage.this.getIntent().getExtras();
			Integer Maxqty = Integer.valueOf(bundle.getInt("helpForMaxqty"));

			helpForTotal.setText("目前剩餘 " + (Maxqty - helpDetail.getTotalOty())
					+ " 份可購買");

		}

	}

	class AddOrderJsonContentTask extends
			AsyncTask<String, Integer, String> {
		// invoked on the UI thread immediately after the task is executed.
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(OrderPage.this);
			progressDialog.setMessage("Loading...");
			progressDialog.show();
		}

		// invoked on the background thread immediately after onPreExecute()
		@Override
		protected String doInBackground(String... params) {
			CasesServer casesServer = new CasesServer();

			 orderValue = casesServer.addOrderData(params[0], params[1],
					params[2], params[3]);

			return orderValue;
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
		protected void onPostExecute(String orderValue) {
			progressDialog.cancel();
			
			
			
			
			if (orderValue == null || orderValue.isEmpty()) {
				Toast.makeText(OrderPage.this, "抱歉請聯絡製作人", Toast.LENGTH_SHORT)
						.show();
			}
			
			if(orderValue != null){
			Integer numberGo = Integer.valueOf(orderValue);
			
			if(numberGo == 1){
				Toast.makeText(OrderPage.this, "恭喜您已經合購成功，想觀看詳細資訊請至會員中心。", Toast.LENGTH_LONG)
				.show();
				finish();
			}
			
			if(numberGo == 2){
				Toast.makeText(OrderPage.this, "你輸入的參數有誤", Toast.LENGTH_SHORT)
				.show();
				
			}
			
			if(numberGo == 3){
				Toast.makeText(OrderPage.this, "您的餘額不足", Toast.LENGTH_LONG)
				.show();
				
				Intent intent = new Intent();
				intent.setClass(OrderPage.this, Storage_money.class);
				startActivity(intent);
				finish();
			}
			
			if(numberGo == 4){
				Toast.makeText(OrderPage.this, "抱歉，此合購案已截止", Toast.LENGTH_LONG)
				.show();
				finish();
			}
			
			
			}		
		}

	}

}