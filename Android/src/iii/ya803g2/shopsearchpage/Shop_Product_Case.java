package iii.ya803g2.shopsearchpage;

import iii.ya803g2.casesearchpage.Action_Tab_Thing;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Shop_Product_Case extends Activity {

	
	private ListView list;
	private ProgressDialog progressDialog;
	private RetrieveJsonContentTask task;
	private List<AndroidCasesVO> cases_list_help;
	private TextView shopcasetitle , shopcaseqty , shopcaseprice ;
	private ImageView shopcaseimage;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shop_data_page2_list_to_shopcase);
		list = (ListView) findViewById(R.id.listView1);
		
	}

	@Override
	protected void onStart() {
		super.onStart();
		Bundle bundle = this.getIntent().getExtras();
		String spno = Integer.toString(bundle.getInt("caseSpno"));
		task = new RetrieveJsonContentTask();
		task.execute(spno);
	}

	class RetrieveJsonContentTask extends
			AsyncTask<String, Integer, List<AndroidCasesVO>> {
		// invoked on the UI thread immediately after the task is executed.
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(Shop_Product_Case.this);
			progressDialog.setMessage("Loading...");
			progressDialog.show();
		}

		// invoked on the background thread immediately after onPreExecute()
		@Override
		protected List<AndroidCasesVO> doInBackground(String... params) {
			ShopServer shopServer = new ShopServer();
			List<AndroidCasesVO> shopCaseList = shopServer.getShopcase(params[0]);
			return shopCaseList;
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
		protected void onPostExecute(List<AndroidCasesVO> shopCaseList) {
			progressDialog.cancel();
			if (shopCaseList == null || shopCaseList.isEmpty()) {
				Toast.makeText(Shop_Product_Case.this, "抱歉目前該商品沒有合購案",
						Toast.LENGTH_SHORT).show();
			} else {
				showShopCaseListView(shopCaseList);
			}
		}

		private void showShopCaseListView(final List<AndroidCasesVO> shopCaseList) {
			list.setAdapter(new ShopCasesListAdapter(Shop_Product_Case.this, shopCaseList));
			list.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View v,
						int position, long id) {
					
					
					int caseno = shopCaseList.get(position).getCasesvo()
							.getCaseno();
					
				
					
					int memno = shopCaseList.get(position).getCasesvo()
							.getMemno();
			
					Intent intent = new Intent(Shop_Product_Case.this,
							Action_Tab_Thing.class);
					Bundle bundle = new Bundle();
					bundle.putInt("caseCaseno", caseno);
					bundle.putInt("caseMemno", memno);
					intent.putExtras(bundle);
					startActivity(intent);
					
				}
			});
		}

	}

	private class ShopCasesListAdapter extends BaseAdapter {
		private LayoutInflater layoutInflater;
		private List<AndroidCasesVO> shopCaseList;

		public ShopCasesListAdapter(Context context,
				List<AndroidCasesVO> shopCaseList) {
			layoutInflater = LayoutInflater.from(context);
			this.shopCaseList = shopCaseList;
		}

		@Override
		public int getCount() {
			return shopCaseList.size();
		}

		@Override
		public Object getItem(int position) {
			return shopCaseList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return shopCaseList.get(position).getCasesvo().getCaseno();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null) {
				convertView = layoutInflater.inflate(R.layout.shop_data_page2_list_to_shopcase_list,
						null);
			}
			if(position %2 == 0){
				
				convertView.setBackgroundColor(Color.rgb(255, 255, 255));
			}else{
				convertView.setBackgroundColor(Color.rgb(248,248,248));
			}
			
			
			shopcasetitle = (TextView) convertView.findViewById(R.id.protitle);
			shopcaseprice = (TextView) convertView.findViewById(R.id.proprice);
			shopcaseqty = (TextView) convertView.findViewById(R.id.proqty);
			shopcaseimage = (ImageView) convertView.findViewById(R.id.shopcaseimage);
			
			

			AndroidCasesVO shopCaseData = shopCaseList.get(position);
			
			
			
			Bundle bundle = Shop_Product_Case.this.getIntent().getExtras();
			Integer shopPrice = Integer.valueOf((int) bundle.getDouble("shopPrice"));
			byte[] shopImage = bundle.getByteArray("shopImage");			
			Double  test = Double.valueOf(bundle.getDouble("shopPrice"));
		
			
			
			if (shopImage != null) {
				Bitmap bitmap1 = BitmapFactory.decodeByteArray(shopImage, 0,
						shopImage.length);
				shopcaseimage.setImageBitmap(bitmap1);
			} 
			
			
			shopcasetitle.setText(shopCaseData.getCasesvo().getTitle());
			shopcaseqty.setText("目前/成團/上限 數量: "+shopCaseData.getTotalOty()+" / "+shopCaseData.getCasesvo().getMinqty()+" / "+shopCaseData.getCasesvo().getMaxqty());

		Integer priceend = shopPrice - shopCaseData.getCasesvo().getDiscount();
			
			
			shopcaseprice.setText("合購價 :"+priceend+"元");
		
				
			


			return convertView;
		}

	}

	
}