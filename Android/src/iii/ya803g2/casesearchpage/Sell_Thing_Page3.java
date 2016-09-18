package iii.ya803g2.casesearchpage;

import iii.ya803g2.casesearchpage.Sell_Thing_Page2.RetrieveJsonContentTask;
import iii.ya803g2.login.SeverData;
import iii.ya803g2.mycase.MyCaseServer;

import java.util.List;
import java.util.regex.Pattern;

import com.example.tao_puzzle.R;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Sell_Thing_Page3 extends Fragment {
	private Button cancel, submit, delcase;
	private TextView forPage3Id;
	private TextView forPage3Title;
	private GetCaseTaskQA getCaseTaskQA;
	private EditText caseQuest;
	private Action_Tab_Thing Helplist;
	private List<AndroidCasesVO> omg;
	private List<AndroidCasesVO> caseQAWTF;
	private ImageView memberimage;
	private String caseQA;
	private RetrieveJsonContentTask task;
	private ProgressDialog progressDialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.thing_data_page3, container,
				false);
		forPage3Id = (TextView) view.findViewById(R.id.caseprice);
		forPage3Title = (TextView) view.findViewById(R.id.gogo2);
		submit = (Button) view.findViewById(R.id.Button01);
		cancel = (Button) view.findViewById(R.id.Button02);
		caseQuest = (EditText) view.findViewById(R.id.editText1);
		memberimage = (ImageView) view.findViewById(R.id.memberimage);
		delcase = (Button) view.findViewById(R.id.Button03);
		Helplist = (Action_Tab_Thing) this.getActivity();
		omg = Helplist.getHelpPage2();
		
		
		
		
		
		
		
		Action_Tab_Thing action_Tab_Thing = (Action_Tab_Thing) this
				.getActivity();
		forPage3Title.setText("主購:"+omg.get(0).getMembervo().getMemid()+"，目前/成團/上限 數量: "+omg.get(0).getTotalOty()+" / "+omg.get(0).getCasesvo().getMinqty()+" / "+omg.get(0).getCasesvo().getMaxqty());
		if(omg.get(0).getCasesvo().getCpno() == 0){
			forPage3Id.setText(omg.get(0).getShopproductVO().getName());
		}else{
		forPage3Id.setText(omg.get(0).getCaseproductvo().getName());
		}
		byte[] memberImageHelp = omg.get(0).getMembervo().getPhoto();
		if (memberImageHelp != null) {
			Bitmap bitmap = BitmapFactory.decodeByteArray(memberImageHelp, 0,
					memberImageHelp.length);
			memberimage.setImageBitmap(bitmap);
		}
		delclick();
		clickCaseQA();
		return view;
	}

	private void delclick() {
		delcase.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				caseQA = caseQuest.getText().toString().trim();
				
				
				if (omg.get(0).getCasesvo().getStatus().intValue() == 0) {
					Toast.makeText(Sell_Thing_Page3.this.getActivity(),
							"抱歉，此合購案已下架", Toast.LENGTH_SHORT).show();
				}
				else if (omg.get(0).getCasesvo().getStatus().intValue() == 3) {
					Toast.makeText(Sell_Thing_Page3.this.getActivity(),
							"抱歉，此合購案已結案", Toast.LENGTH_SHORT).show();
				}
				else if (omg.get(0).getCasesvo().getStatus().intValue() == 4) {
					Toast.makeText(Sell_Thing_Page3.this.getActivity(),
							"抱歉，此合購案已進入交貨階段", Toast.LENGTH_SHORT).show();
				}
				else if (omg.get(0).getCasesvo().getStatus().intValue() == 5) {
					Toast.makeText(Sell_Thing_Page3.this.getActivity(),
							"抱歉，此合購案已刪除", Toast.LENGTH_SHORT).show();
				}
				else if (omg.get(0).getCasesvo().getStatus().intValue() == 6) {
					Toast.makeText(Sell_Thing_Page3.this.getActivity(),
							"抱歉，此合購案已取消", Toast.LENGTH_SHORT).show();
				}
				else if (caseQA == null || caseQA.matches("")) {
					Toast.makeText(Sell_Thing_Page3.this.getActivity(),
							"請輸入您的問題再點選送出!!", Toast.LENGTH_SHORT).show();
				}else{

				new AlertDialog.Builder(Sell_Thing_Page3.this.getActivity())
						.setTitle(R.string.del)
						.setIcon(R.drawable.gogo)
						.setMessage(R.string.delcase)
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
									
										
										

										new AlertDialog.Builder(
												Sell_Thing_Page3.this
														.getActivity())
												.setTitle(R.string.del)
												.setIcon(R.drawable.gogo)
												.setMessage(R.string.del2case)
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

																SharedPreferences pref = Sell_Thing_Page3.this
																		.getActivity()
																		.getSharedPreferences(
																				SeverData.PREF_FILE,
																				Context.MODE_PRIVATE);
																String account = pref
																		.getString(
																				"account",
																				"");

																int memnoString = Sell_Thing_Page3.this
																		.getActivity()
																		.getIntent()
																		.getExtras()
																		.getInt("caseMemno");
																String memno = Integer
																		.toString(memnoString);
																int casenoString = Sell_Thing_Page3.this
																		.getActivity().getIntent()
																		.getExtras()
																		.getInt("caseCaseno");
																String caseno = Integer
																		.toString(casenoString);
																task = new RetrieveJsonContentTask();
																task.execute(
																		account,
																		caseno,memno,caseQA);

															}
														}).setCancelable(false)
												.show();

									}
								}).setCancelable(false).show();
			}
			}
		});

	}

	private void clickCaseQA() {
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				caseQA = caseQuest.getText().toString().trim();

				if (caseQA == null || caseQA.matches("")) {
					Toast.makeText(Sell_Thing_Page3.this.getActivity(),
							"請輸入您的問題再點選送出!!", Toast.LENGTH_SHORT).show();
				} else {

					new AlertDialog.Builder(Sell_Thing_Page3.this.getActivity())
							.setTitle(R.string.qatitle)
							.setIcon(R.drawable.gogo)
							.setMessage(R.string.qadesc)
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

											String memno = Integer.toString(omg
													.get(0).getMembervo()
													.getMemno());

											String caseno = Integer
													.toString(omg.get(0)
															.getCasesvo()
															.getCaseno());

											long helpTime = System
													.currentTimeMillis();

											String getTime = String
													.valueOf(helpTime);

											getCaseTaskQA = new GetCaseTaskQA();
											getCaseTaskQA.execute(memno,
													caseno, caseQA, getTime);

//											Sell_Thing_Page3.this.getActivity()
//													.finish();
										}
									}).setCancelable(false).show();

				}
			}
		});

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				caseQA = caseQuest.getText().toString().trim();
				if (caseQA == null || caseQA.matches("")) {
					Toast.makeText(Sell_Thing_Page3.this.getActivity(),
							"你還沒填寫問題喔!!", Toast.LENGTH_SHORT).show();
				} else {
					new AlertDialog.Builder(Sell_Thing_Page3.this.getActivity())
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

											caseQuest.setText("");
										}
									}).setCancelable(false).show();

				}
			}
		});
	}

	@Override
	public void onStart() {
		super.onStart();

		// getCaseTaskQA = new GetCaseTaskQA();
		// getCaseTaskQA.execute();
	}

	class GetCaseTaskQA extends
			AsyncTask<String, Integer, List<AndroidCasesVO>> {
		private ProgressDialog progressDialog;

		// invoked on the UI thread immediately after the task is executed.
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(
					Sell_Thing_Page3.this.getActivity());
			progressDialog.setMessage("Loading...");
			progressDialog.show();
		}

		@Override
		protected List<AndroidCasesVO> doInBackground(String... caseQA) {
			CasesServer casesServer = new CasesServer();
			caseQAWTF = casesServer.addCaseQa(caseQA[0], caseQA[1], caseQA[2],
					caseQA[3]);
			return caseQAWTF;
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
		protected void onPostExecute(List<AndroidCasesVO> caseQAWTF) {
			progressDialog.cancel();
			if (caseQAWTF == null) {
				Toast.makeText(Sell_Thing_Page3.this.getActivity(),
						"抱歉、您輸入的文字長度過多", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(Sell_Thing_Page3.this.getActivity(),
						"您已經成功提出問題!", Toast.LENGTH_LONG).show();
				
				caseQuest.setText("");
				
			}
		}

	}

	class RetrieveJsonContentTask extends
			AsyncTask<String, Integer, List<AndroidCasesVO>> {
		// invoked on the UI thread immediately after the task is executed.
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(
					Sell_Thing_Page3.this.getActivity());
			progressDialog.setMessage("Loading...");
			progressDialog.show();
		}

		// invoked on the background thread immediately after onPreExecute()
		@Override
		protected List<AndroidCasesVO> doInBackground(String... params) {
			MyCaseServer myCaseServer = new MyCaseServer();
			List<AndroidCasesVO> delCase = myCaseServer
					.delcase(params[0],params[1],params[2],params[3]);
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
				Toast.makeText(Sell_Thing_Page3.this.getActivity(), "請聯絡製作人",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(Sell_Thing_Page3.this.getActivity(),
						"您已經成功檢舉此合購案", Toast.LENGTH_SHORT).show();
				
				caseQuest.setText("");
			}
		}

	}

}