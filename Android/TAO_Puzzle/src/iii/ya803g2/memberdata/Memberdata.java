package iii.ya803g2.memberdata;


import iii.ya803g2.casesearchpage.AndroidCasesVO;
import iii.ya803g2.casesearchpage.OrderVO;
import iii.ya803g2.login.Homepage;
import iii.ya803g2.login.LoginPage;
import iii.ya803g2.login.LoginServer;
import iii.ya803g2.login.MemberVO;
import iii.ya803g2.login.SeverData;
import iii.ya803g2.moneycenter.Money_ATM;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import com.example.tao_puzzle.R;
import com.google.gson.Gson;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Memberdata extends Activity {
	private ListView listView;
	private Button changeImage, ok, notok;
	private ImageView memberImage, tempimage;
	private Bitmap picture;
	private static final int REQUEST_TAKE_PICTURE = 0;
	private ProgressDialog progressDialog;
	private RetrieveJsonContentTask task;
	private UpdateImageTask imageTask;
	private NullTask nullTask;
	private TextView ratetitle, rate, ratedesc, memberStatus, memid;
	private String stringrate, stringType;
	File out;

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.logout_menu, menu);
	

//		menu.add(0 , 0, 0, casesList.get(0).getCasesvo().getTitle());
//		menu.add(0 , 0, 1, casesList.get(0).getCasesvo().getTitle());
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		String message = "";
		switch (item.getItemId()) {
		case 1:
			message = "123";
			break;
		case 2:
			message = "321";
			break;
		case R.id.exit:
			
			new AlertDialog.Builder(Memberdata.this)
			.setTitle(R.string.logout)
			.setIcon(R.drawable.gogo)
			.setMessage(R.string.logoutdesc)
			.setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
			
				@Override
				public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						
						
				}
			})
			
			.setNegativeButton(R.string.submit,new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					SharedPreferences pref = getSharedPreferences(SeverData.PREF_FILE,
							MODE_PRIVATE);
					pref.edit().putString("account" , "").commit();
					
					Intent intent = new Intent();
					intent.setClass(Memberdata.this, LoginPage.class);
					startActivity(intent);
					finish();
				
				}
			})
			.setCancelable(false).show();
			
			
			
//			System.exit(0);
		default:
			return super.onOptionsItemSelected(item);
		}
		
		return true;
	}
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.member_data);
		changeImage = (Button) findViewById(R.id.addwish);
		memberImage = (ImageView) findViewById(R.id.memberimage);
		listView = (ListView) findViewById(R.id.memberdata_list);
		memid = (TextView) findViewById(R.id.memidid);
		ok = (Button) findViewById(R.id.Button01);
		notok = (Button) findViewById(R.id.Button02);
		tempimage = (ImageView) findViewById(R.id.tempimage);
		
		
		tempimage.setVisibility(View.INVISIBLE);
		ok.setVisibility(View.INVISIBLE);
		notok.setVisibility(View.INVISIBLE);
		okclick();
		notokclick();
		
	}



	private void notokclick() {
		notok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(Memberdata.this)
				.setTitle(R.string.change)
				.setIcon(R.drawable.gogo)
				.setMessage(R.string.changeimage)
				.setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
				
					@Override
					public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
							
							
					}
				})
				
				.setNegativeButton(R.string.submit,new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						ok.setVisibility(View.INVISIBLE);
						notok.setVisibility(View.INVISIBLE);
						tempimage.setVisibility(View.INVISIBLE);
						memberImage.setVisibility(View.VISIBLE);
						changeImage.setVisibility(View.VISIBLE);
					
					}
				})
				.setCancelable(false).show();
				
			}
		});
		
	}

	private void okclick() {
			
		ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				new AlertDialog.Builder(Memberdata.this)
				.setTitle(R.string.change)
				.setIcon(R.drawable.gogo)
				.setMessage(R.string.changeimage)
				.setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
				
					@Override
					public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
							
							
					}
				})
				
				.setNegativeButton(R.string.submit,new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						SharedPreferences pref = getSharedPreferences(SeverData.PREF_FILE,
								MODE_PRIVATE);
						String account = pref.getString("account", "");
						
						ByteArrayOutputStream out = new ByteArrayOutputStream();
						picture.compress(Bitmap.CompressFormat.JPEG, 100, out);
						byte[] imageString = out.toByteArray();
						MemberVO qqq = new MemberVO(account , imageString);
						Gson gson = new Gson();
						String jsonStr = gson.toJson(qqq);
//						String image = String.valueOf(imageString);
						
						imageTask = new UpdateImageTask();
						imageTask.execute(jsonStr);
					
					}
				})
				.setCancelable(false).show();
				
				
				
			}
		});
				
	

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
			progressDialog = new ProgressDialog(Memberdata.this);
			progressDialog.setMessage("Loading...");
			progressDialog.show();
		}

		// invoked on the background thread immediately after onPreExecute()
		@Override
		protected List<AndroidCasesVO> doInBackground(String... params) {
			LoginServer loginServer = new LoginServer();
			List<AndroidCasesVO> memberData = loginServer.getRate(params[0]);
			return memberData;
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
		protected void onPostExecute(List<AndroidCasesVO> memberData) {
			progressDialog.cancel();
			if (memberData == null || memberData.isEmpty()) {
				Toast.makeText(Memberdata.this, "您目前沒有任何的評價。",
						Toast.LENGTH_SHORT).show();

				SharedPreferences pref = getSharedPreferences(
						SeverData.PREF_FILE, MODE_PRIVATE);
				String account = pref.getString("account", "");
				nullTask = new NullTask();
				nullTask.execute(account);

			} else {

				byte[] image = memberData.get(0).getMembervo().getPhoto();
				if (image != null) {
					Bitmap bitmap1 = BitmapFactory.decodeByteArray(image, 0,
							image.length);

					memberImage.setImageBitmap(bitmap1);

				}

				Integer helpType = memberData.get(0).getMembervo().getType();

				if (helpType == 0) {
					stringrate = "一般會員";
				}
				if (helpType == 1) {
					stringrate = "商店會員";
				}

			
				memid.setText("會員帳號 : "
						+ memberData.get(0).getMembervo().getMemid()+"("+memberData.get(0).getMembervo().getPoint()+")"+"        帳號類型 : " + stringrate);
				showRateListView(memberData);
			}
		}

		private void showRateListView(List<AndroidCasesVO> memberData) {

			listView.setAdapter(new RateListAdapter(Memberdata.this, memberData));
			listView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View v,
						int position, long id) {

				}
			});

		}

	}

	private class RateListAdapter extends BaseAdapter {
		private LayoutInflater layoutInflater;
		private List<AndroidCasesVO> memberData;

		public RateListAdapter(Context context, List<AndroidCasesVO> memberData) {
			layoutInflater = LayoutInflater.from(context);
			this.memberData = memberData;
		}

		@Override
		public int getCount() {
			return memberData.size();
		}

		@Override
		public Object getItem(int position) {
			return memberData.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return memberData.get(position).getMembervo().getMemno();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null) {
				convertView = layoutInflater.inflate(R.layout.member_data_list,
						null);
			}
			
			if(position %2 == 0){
				convertView.setBackgroundColor(Color.rgb(255, 255, 255));
			}else{
				
				convertView.setBackgroundColor(Color.rgb(248,248,248));
			}

//			if(position %2 == 0){
//			convertView.setBackgroundColor(Color.rgb(red, green, blue));
//		}else{
//			convertView.setBackgroundColor(Color.RED);
//		}
			
			
			ratetitle = (TextView) convertView.findViewById(R.id.ratetitle);
			rate = (TextView) convertView.findViewById(R.id.rate);
			ratedesc = (TextView) convertView.findViewById(R.id.ratedesc);
			AndroidCasesVO ratelist = memberData.get(position);
			Integer helpRate = ratelist.getOrderVO().getBrate();

			if (helpRate == 0) {
				stringrate = "壞評";
			}
			if (helpRate == 1) {
				stringrate = "普評";
			}
			if (helpRate == 2) {
				stringrate = "好評";
			}
			ratetitle.setText("合購案名稱 : " + ratelist.getCasesvo().getTitle());

			rate.setText("評價 : " + stringrate);

			System.out.println("BBB" + ratelist.getOrderVO().getBmemno());
			System.out.println("CCC" + ratelist.getOrderVO().getCmemno());

			if (ratelist.getOrderVO().getBmemno().intValue() == ratelist
					.getMembervo().getMemno()) {
				ratedesc.setText("賣家給您的評價敘述 : "
						+ ratelist.getOrderVO().getBrateDesc());
				
			}
			if (ratelist.getOrderVO().getCmemno().intValue() == ratelist
					.getMembervo().getMemno()) {
				ratedesc.setText("買家給您的評價敘述 : "
						+ ratelist.getOrderVO().getCrateDesc());
				
			}

			return convertView;
		}

	}

	@Override
	// 螢幕轉向之前，先將資料儲存至Bundle，方便轉向完畢後取出
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (picture != null) {
			outState.putParcelable("picture", picture);
		}
	}

	@Override
	// 螢幕轉向完畢後，將轉向之前存的資料取出
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		Bitmap picture = savedInstanceState.getParcelable("picture");
		if (picture != null) {
			this.picture = picture;
			tempimage.setImageBitmap(picture);
		}
	}

	// 點擊ImageView會拍照
	public void onTakePicture(View view) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// 指定存檔路徑
		out = Environment.getExternalStorageDirectory();
		out = new File(out, "photo.jpg");
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out));
		if (isIntentAvailable(this, intent)) {
			startActivityForResult(intent, REQUEST_TAKE_PICTURE);
		} else {
			Toast.makeText(this, R.string.msgNoCameraApp, Toast.LENGTH_SHORT)
					.show();
		}
	}

	public boolean isIntentAvailable(Context context, Intent intent) {
		PackageManager packageManager = context.getPackageManager();
		List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
				PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			// 手機拍照App拍照完成後可以取得照片圖檔
			case REQUEST_TAKE_PICTURE:
				// picture = (Bitmap) data.getExtras().get("data"); //只取得小圖
				picture = downSize(out.getPath());
				tempimage.setVisibility(View.VISIBLE);
				tempimage.setImageBitmap(picture);
				memberImage.setVisibility(View.INVISIBLE);
				changeImage.setVisibility(View.INVISIBLE);
				ok.setVisibility(View.VISIBLE);
				notok.setVisibility(View.VISIBLE);

				

				break;
			}
		}
	}

	private Bitmap downSize(String path) {
		Bitmap picture = BitmapFactory.decodeFile(path);
		// 設定寬度不超過width，並利用Options.inSampleSize來縮圖
		int width = 512;
		if (picture.getWidth() > width) {
			Options options = new Options();
			// 若原始照片寬度無法整除width，則inSampleSize + 1
			options.inSampleSize = picture.getWidth() % width == 0 ? picture
					.getWidth() / width : picture.getWidth() / width + 1;
			picture = BitmapFactory.decodeFile(out.getPath(), options);
			System.gc();
		}
		String text = picture.getWidth() + "x" + picture.getHeight();
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
		return picture;
	}

	class NullTask extends AsyncTask<String, Integer, List<AndroidCasesVO>> {
		// invoked on the UI thread immediately after the task is executed.
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(Memberdata.this);
			progressDialog.setMessage("Loading...");
			progressDialog.show();
		}

		// invoked on the background thread immediately after onPreExecute()
		@Override
		protected List<AndroidCasesVO> doInBackground(String... params) {
			LoginServer loginServer = new LoginServer();
			List<AndroidCasesVO> memberData = loginServer.onlyMember(params[0]);
			return memberData;
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
		protected void onPostExecute(List<AndroidCasesVO> memberData) {
			progressDialog.cancel();
			if (memberData == null || memberData.isEmpty()) {

			} else {
				byte[] image = memberData.get(0).getMembervo().getPhoto();
				if (image != null) {
					Bitmap bitmap1 = BitmapFactory.decodeByteArray(image, 0,
							image.length);

					memberImage.setImageBitmap(bitmap1);

				}

				Integer helpType = memberData.get(0).getMembervo().getType();

				if (helpType == 0) {
					stringrate = "一般會員";
				}
				if (helpType == 1) {
					stringrate = "商店會員";
				}

				memid.setText("會員帳號 : "
						+ memberData.get(0).getMembervo().getMemid()
						+"("+memberData.get(0).getMembervo().getPoint()+")"
						+"        帳號類型 : " + stringrate);

			}
		}
	}

	class UpdateImageTask extends
			AsyncTask<String, Integer, List<AndroidCasesVO>> {
		// invoked on the UI thread immediately after the task is executed.
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(Memberdata.this);
			progressDialog.setMessage("Loading...");
			progressDialog.show();
		}

		// invoked on the background thread immediately after onPreExecute()
		@Override
		protected List<AndroidCasesVO> doInBackground(String... params) {
			LoginServer loginServer = new LoginServer();
			List<AndroidCasesVO> memberData = loginServer.changeImage(params[0]);
			return memberData;
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
		protected void onPostExecute(List<AndroidCasesVO> memberData) {
			progressDialog.cancel();
			if (memberData == null || memberData.isEmpty()) {
				Toast.makeText(Memberdata.this, "您目前沒有任何的評價。",
						Toast.LENGTH_SHORT).show();
				
			
			}else{
				
				ok.setVisibility(View.INVISIBLE);
				notok.setVisibility(View.INVISIBLE);
				
				memberImage.setVisibility(View.INVISIBLE);
				changeImage.setVisibility(View.VISIBLE);
				
			}
		}
	}

}
