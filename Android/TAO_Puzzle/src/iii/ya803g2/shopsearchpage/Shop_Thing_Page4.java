package iii.ya803g2.shopsearchpage;

import iii.ya803g2.casesearchpage.Action_Tab_Thing;
import iii.ya803g2.casesearchpage.AndroidCasesVO;
import iii.ya803g2.casesearchpage.CaseProductVO;
import iii.ya803g2.casesearchpage.Sell_Thing_Page2;
import iii.ya803g2.casesearchpage.Sell_Thing_Page3;
import iii.ya803g2.casesearchpage.Sell_Thing_Page2.Page2ViewAdapter;
import iii.ya803g2.login.Homepage;
import iii.ya803g2.login.SeverData;
import iii.ya803g2.membercenter.Membercenter;
import iii.ya803g2.memberdata.Memberdata;
import iii.ya803g2.moneycenter.Storage_money;
import iii.ya803g2.mycase.MyCaseServer;

import java.util.ArrayList;
import java.util.List;

import com.example.tao_puzzle.R;

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
import android.os.Bundle;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Shop_Thing_Page4 extends android.app.Fragment {

	private TextView forPage3Id;
	private TextView forPage3Title;
	private List<AndroidCasesVO> shopData;
	private ImageView memberimage;
	private Button cancel, delcase;
	private String shopQA;
	private RetrieveJsonContentTask task;
	private ProgressDialog progressDialog;
	private EditText shopQuest;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.shop_data_page4, container, false);

		forPage3Id = (TextView) view.findViewById(R.id.caseprice);
		
		forPage3Title = (TextView) view.findViewById(R.id.gogo2);
		
		delcase = (Button) view.findViewById(R.id.Button03);
		
		cancel = (Button) view.findViewById(R.id.Button02);
		
		memberimage = (ImageView) view.findViewById(R.id.memberimage);
		
		shopQuest = (EditText) view.findViewById(R.id.editText1);
		
		Action_Tab_Shop action_Tab_Shop = (Action_Tab_Shop) this.getActivity();
		shopData = action_Tab_Shop.getHelpPage();
		forPage3Title.setText("店主: "+shopData.get(0).getMembervo().getMemid());
		forPage3Id.setText(shopData.get(0).getShopvo().getTitle());
	
		byte[] memberImageHelp = shopData.get(0).getMembervo().getPhoto();
		if (memberImageHelp != null) {
			Bitmap bitmap = BitmapFactory.decodeByteArray(memberImageHelp, 0,
					memberImageHelp.length);
			memberimage.setImageBitmap(bitmap);
		}
		delclick();
		cancel();
		return view;
	}

	private void cancel() {
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				shopQA = shopQuest.getText().toString().trim();
				if (shopQA == null || shopQA.matches("")) {
					Toast.makeText(Shop_Thing_Page4.this.getActivity(),
							"你還沒填寫問題喔!!", Toast.LENGTH_SHORT).show();
				} else {
					new AlertDialog.Builder(Shop_Thing_Page4.this.getActivity())
							.setTitle(R.string.cleartitle)
							.setIcon(R.drawable.gogo)
							.setMessage(R.string.cleanerstring)
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

											shopQuest.setText("");
										}
									}).setCancelable(false).show();

				}
				
				
				
			}
		});
		
	}

	private void delclick() {

		delcase.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				shopQA = shopQuest.getText().toString().trim();

				if (shopQA == null || shopQA.matches("")) {
					Toast.makeText(Shop_Thing_Page4.this.getActivity(),
							"請輸入您的問題再點選送出!!", Toast.LENGTH_SHORT).show();
				} else {

					new AlertDialog.Builder(Shop_Thing_Page4.this.getActivity())
							.setTitle(R.string.del)
							.setIcon(R.drawable.gogo)
							.setMessage(R.string.delcase)
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

											new AlertDialog.Builder(
													Shop_Thing_Page4.this
															.getActivity())
													.setTitle(R.string.del)
													.setIcon(R.drawable.gogo)
													.setMessage(
															R.string.del2case)
													.setPositiveButton(
															R.string.cancel,
															new DialogInterface.OnClickListener() {

																@Override
																public void onClick(
																		DialogInterface dialog,
																		int which) {
																	dialog.cancel();

																}
															})

													.setNegativeButton(
															R.string.submit,
															new DialogInterface.OnClickListener() {

																@Override
																public void onClick(

																		DialogInterface dialog,
																		int which) {

																	SharedPreferences pref = Shop_Thing_Page4.this
																			.getActivity()
																			.getSharedPreferences(
																					SeverData.PREF_FILE,
																					Context.MODE_PRIVATE);
																	String account = pref
																			.getString(
																					"account",
																					"");

																	int memnoString = Shop_Thing_Page4.this
																			.getActivity()
																			.getIntent()
																			.getExtras()
																			.getInt("caseMemno");
																	String memno = Integer
																			.toString(memnoString);
																	int shopString = Shop_Thing_Page4.this
																			.getActivity()
																			.getIntent()
																			.getExtras()
																			.getInt("shopShopno");
																	String shopno = Integer
																			.toString(shopString);
																	task = new RetrieveJsonContentTask();
																	task.execute(
																			account,
																			shopno,
																			memno,
																			shopQA);

																}
															})
													.setCancelable(false)
													.show();

										}
									}).setCancelable(false).show();
				}
			}
		});

	}

	
	
	
	class RetrieveJsonContentTask extends
			AsyncTask<String, Integer, List<AndroidCasesVO>> {
		// invoked on the UI thread immediately after the task is executed.
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(
					Shop_Thing_Page4.this.getActivity());
			progressDialog.setMessage("Loading...");
			progressDialog.show();
		}

		// invoked on the background thread immediately after onPreExecute()
		@Override
		protected List<AndroidCasesVO> doInBackground(String... params) {
			ShopServer shopServer = new ShopServer();
			List<AndroidCasesVO> delCase = shopServer.delshop(params[0],
					params[1], params[2], params[3]);
			return delCase;
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
		protected void onPostExecute(List<AndroidCasesVO> delCase) {
			progressDialog.cancel();
			if (delCase == null || delCase.isEmpty()) {
				Toast.makeText(Shop_Thing_Page4.this.getActivity(), "請聯絡製作人",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(Shop_Thing_Page4.this.getActivity(),
						"您已經成功檢舉此商店", Toast.LENGTH_SHORT).show();
				
				shopQuest.setText("");
			}
		}

	}

}