package iii.ya803g2.shopsearchpage;

import iii.ya803g2.casesearchpage.Action_Tab_Thing;
import iii.ya803g2.casesearchpage.AndroidCasesVO;
import iii.ya803g2.casesearchpage.Case_Search;

import java.util.List;

import com.example.tao_puzzle.R;

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

public class Shop_Thing_Page2 extends android.app.Fragment {

	private GetShopTask getShopTask;
	private List<AndroidCasesVO> shopnoHelp, shopData;
	private ImageView memberImage, pic1, pic2, pic3;
	private ListView shoplist;
	private TextView productdesc, spec1, spec2, spec3, price, id, title , truedesc;
	private AndroidCasesVO shopFinalDetail;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.shop_data_page2, container, false);

		memberImage = (ImageView) view.findViewById(R.id.memberimage);
		shoplist = (ListView) view.findViewById(R.id.caselistview);
		id = (TextView) view.findViewById(R.id.shoppage2member);
		title = (TextView) view.findViewById(R.id.gogo2);
		Action_Tab_Shop action_Tab_Shop = (Action_Tab_Shop) this.getActivity();
		shopData = action_Tab_Shop.getHelpPage();
		
		title.setText("店主: "+shopData.get(0).getMembervo().getMemid());
		id.setText(shopData.get(0).getShopvo().getTitle());

		byte[] memberImageHelp = shopData.get(0).getMembervo().getPhoto();

		if (memberImageHelp != null) {
			Bitmap bitmap = BitmapFactory.decodeByteArray(memberImageHelp, 0,
					memberImageHelp.length);
			memberImage.setImageBitmap(bitmap);
		}

		return view;
	}

	@Override
	public void onStart() {
		super.onStart();

		Action_Tab_Shop action_Tab_Shop = (Action_Tab_Shop) this.getActivity();
		shopData = action_Tab_Shop.getHelpPage();
		Integer shopno = Integer.valueOf(shopData.get(0).getShopvo()
				.getShopno());
		getShopTask = new GetShopTask();
		// params will be passed to AsyncTask.doInBackground(Integer... params)
		getShopTask.execute(shopno);
	}

	class GetShopTask extends AsyncTask<Integer, Integer, List<AndroidCasesVO>> {
		private ProgressDialog progressDialog;

		// invoked on the UI thread immediately after the task is executed.
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(
					Shop_Thing_Page2.this.getActivity());
			progressDialog.setMessage("Loading...");
			progressDialog.show();
		}

		@Override
		protected List<AndroidCasesVO> doInBackground(Integer... shopno) {
			ShopServer shopServer = new ShopServer();
			shopnoHelp = shopServer.findByShopProduct(shopno[0]);
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
				Toast.makeText(Shop_Thing_Page2.this.getActivity(),
						"抱歉找不到該筆資訊", Toast.LENGTH_SHORT).show();
			} else {

				showDetail(shopnoHelp);
			}
		}

		private void showDetail(final List<AndroidCasesVO> shopnoHelp) {

			shoplist.setTextFilterEnabled(true);
			shoplist.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			shoplist.setItemChecked(0, true);

			shoplist.setAdapter(new DetailListAdapter(Shop_Thing_Page2.this
					.getActivity(), shopnoHelp));

			shoplist.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View v,
						int position, long id) {
						
					
					
					double shopprice = shopnoHelp.get(position).getShopproductVO().getUnitprice();
					int getSpno = shopnoHelp.get(position).getShopproductVO().getSpno();
					byte[] pic1Help = shopFinalDetail.getShopproductVO().getPic1();
					
					
					
					Intent intent = new Intent(Shop_Thing_Page2.this.getActivity(),
							Shop_Product_Case.class);
					Bundle bundle = new Bundle();
					bundle.putInt("caseSpno", getSpno);
					bundle.putDouble("shopPrice", shopprice);
					bundle.putByteArray("shopImage", pic1Help);
					intent.putExtras(bundle);
					startActivity(intent);
					
					
				}

			});
		}

	}

	private class DetailListAdapter extends BaseAdapter {
		private LayoutInflater layoutInflater;
		private List<AndroidCasesVO> shopDetail;

		public DetailListAdapter(Context context,
				List<AndroidCasesVO> shopDetail) {
			layoutInflater = LayoutInflater.from(context);
			this.shopDetail = shopDetail;
		}

		@Override
		public int getCount() {
			return shopDetail.size();
		}

		@Override
		public Object getItem(int position) {
			return shopDetail.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return shopDetail.get(position).getShopproductVO().getSpno();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = layoutInflater.inflate(
						R.layout.shop_data_page2_list, null);
			}
			
			
			if(position %2 == 0){
				convertView.setBackgroundColor(Color.rgb(255, 255, 255));
			}else{
				convertView.setBackgroundColor(Color.rgb(248,248,248));
			}

			productdesc = (TextView) convertView.findViewById(R.id.productdesc);
			truedesc  = (TextView) convertView.findViewById(R.id.truedesc);
			spec1 = (TextView) convertView.findViewById(R.id.dpshow);
			spec2 = (TextView) convertView.findViewById(R.id.dpsamnt);
			spec3 = (TextView) convertView.findViewById(R.id.dpsordt);
			price = (TextView) convertView.findViewById(R.id.ordsts);
			pic1 = (ImageView) convertView.findViewById(R.id.pic11);
			pic2 = (ImageView) convertView.findViewById(R.id.pic22);
			pic3 = (ImageView) convertView.findViewById(R.id.pic33);
			
			shopFinalDetail = shopDetail.get(position);

			byte[] pic1Help = shopFinalDetail.getShopproductVO().getPic1();
			if (pic1Help != null) {
				Bitmap bitmap1 = BitmapFactory.decodeByteArray(pic1Help, 0,
						pic1Help.length);
				pic1.setImageBitmap(bitmap1);
			}

			byte[] pic2Help = shopFinalDetail.getShopproductVO().getPic2();
			if (pic2Help != null) {
				Bitmap bitmap1 = BitmapFactory.decodeByteArray(pic2Help, 0,
						pic2Help.length);
				pic2.setImageBitmap(bitmap1);
			} else {
				pic2.setVisibility(View.GONE);
			}

			byte[] pic3Help = shopFinalDetail.getShopproductVO().getPic3();
			if (pic3Help != null) {
				Bitmap bitmap1 = BitmapFactory.decodeByteArray(pic3Help, 0,
						pic3Help.length);
				pic3.setImageBitmap(bitmap1);
			} else {
				pic3.setVisibility(View.GONE);
			}

			productdesc.setText("商品名稱 : "
					+ shopFinalDetail.getShopproductVO().getName());
			truedesc.setText("商品說明: \n\n"+shopFinalDetail.getShopproductVO().getPro_desc());
			String helpspec1 = shopFinalDetail.getShopproductVO().getSpec1();
			String helpspec2 = shopFinalDetail.getShopproductVO().getSpec2();
			String helpspec3 = shopFinalDetail.getShopproductVO().getSpec3();
			spec1.setText("規格1 : " + helpspec1);
			if (helpspec1 == null) {
				spec1.setVisibility(View.INVISIBLE);
			}
			spec2.setText("規格2 : " + helpspec2);
			if (helpspec2 == null) {
				spec2.setVisibility(View.INVISIBLE);
			}
			spec3.setText("規格3 : " + helpspec3);
			if (helpspec3 == null) {
				spec3.setVisibility(View.INVISIBLE);
			}
			price.setText("商品價格 : "
					+ shopFinalDetail.getShopproductVO().getUnitprice() + "元");

			return convertView;
		}

	}

}
