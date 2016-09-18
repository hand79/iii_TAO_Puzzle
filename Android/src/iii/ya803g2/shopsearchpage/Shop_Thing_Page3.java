package iii.ya803g2.shopsearchpage;

import iii.ya803g2.casesearchpage.Action_Tab_Thing;
import iii.ya803g2.casesearchpage.AndroidCasesVO;
import iii.ya803g2.casesearchpage.CaseProductVO;
import iii.ya803g2.casesearchpage.Sell_Thing_Page2;
import iii.ya803g2.casesearchpage.Sell_Thing_Page2.Page2ViewAdapter;
import iii.ya803g2.login.Homepage;
import iii.ya803g2.membercenter.Membercenter;
import iii.ya803g2.memberdata.Memberdata;
import iii.ya803g2.moneycenter.Storage_money;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import com.example.tao_puzzle.R;
import com.google.android.gms.maps.SupportMapFragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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

public class Shop_Thing_Page3 extends android.app.Fragment{
	
	private TextView address , phone , fax , email , ship , otherdesc , id , title;
	private List<AndroidCasesVO> shopData;
	private ListView page3list;
	private ImageView memberImage;
	private GoogleMap map;
	@Override
	public View onCreateView(LayoutInflater inflater , ViewGroup container ,  Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.shop_data_page3, container , false);
		
		
		id = (TextView) view.findViewById(R.id.caseprice);
		title = (TextView) view.findViewById(R.id.gogo2);
		page3list = (ListView) view.findViewById(R.id.caselistview);
		memberImage = (ImageView) view.findViewById(R.id.memberimage);
		Action_Tab_Shop action_Tab_Shop = (Action_Tab_Shop)this.getActivity();
		shopData = action_Tab_Shop.getHelpPage();
		
		title.setText("店主: "+shopData.get(0).getMembervo().getMemid());
		id.setText(shopData.get(0).getShopvo().getTitle());
		
		byte[] memberImageHelp = shopData.get(0).getMembervo().getPhoto();
		if (memberImageHelp != null) {
			Bitmap bitmap = BitmapFactory.decodeByteArray(memberImageHelp, 0,
					memberImageHelp.length);
			memberImage.setImageBitmap(bitmap);		
		}
		
	
			
		
		showpage3();
		
		return view;
	}

	private void showpage3() {
		page3list.setAdapter(new Page3ViewAdapter(Shop_Thing_Page3.this
				.getActivity(), shopData));
		
		page3list.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
			}
		});
		
	}
	
	public class Page3ViewAdapter extends BaseAdapter {

		private LayoutInflater layoutInflater;
		private List<AndroidCasesVO> shopData;

		public Page3ViewAdapter(Context context, List<AndroidCasesVO> shopData) {
			layoutInflater = LayoutInflater.from(context);

			this.shopData = shopData;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return shopData.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return shopData.get(position);
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
						R.layout.shop_data_page3_list, null);
			}
			address = (TextView) convertView.findViewById(R.id.address);
			phone  = (TextView) convertView.findViewById(R.id.phone);
			fax = (TextView) convertView.findViewById(R.id.fax);
			email = (TextView) convertView.findViewById(R.id.email);
			ship = (TextView) convertView.findViewById(R.id.ship);
			otherdesc = (TextView) convertView.findViewById(R.id.otherdesc);


	
			AndroidCasesVO casesFinalDetail = shopData.get(position);
			
			address.setText("地址 : "+casesFinalDetail.getLocationVO().getCounty()+casesFinalDetail.getLocationVO().getTown()+casesFinalDetail.getShopvo().getAddr());
			phone.setText("電話 : "+casesFinalDetail.getShopvo().getPhone());
			
			if(casesFinalDetail.getShopvo().getFax() == null){
				fax.setText("傳真 : 無");
			}else{
			fax.setText("傳真 : "+casesFinalDetail.getShopvo().getFax());
			}
			email.setText("信箱 : "+casesFinalDetail.getShopvo().getEmail());
			if(casesFinalDetail.getShopvo().getShip_desc() == null || casesFinalDetail.getShopvo().getShip_desc().trim().length() == 0){
				ship.setText("運費說明 :\n無");
			}else{
				ship.setText("運費說明 :\n"+casesFinalDetail.getShopvo().getShip_desc());
			}
			if(casesFinalDetail.getShopvo().getOther_desc() == null || casesFinalDetail.getShopvo().getOther_desc().trim().length() == 0){
				otherdesc.setText("額外說明 :\n無");
			}else{
				otherdesc.setText("額外說明 :\n"+casesFinalDetail.getShopvo().getOther_desc());
			}
			
			
			
			return convertView;
		}

	
	}

}