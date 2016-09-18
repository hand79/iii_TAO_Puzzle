package iii.ya803g2.mapsearch;

import iii.ya803g2.casesearchpage.AndroidCasesVO;
import iii.ya803g2.shopsearchpage.Action_Tab_Shop;
import iii.ya803g2.shopsearchpage.ShopServer;
import iii.ya803g2.shopsearchpage.Shop_Search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tao_puzzle.R;
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

public class Map_Search extends FragmentActivity {
	private GoogleMap map;
	private LatLng taiwan;
	private TextView maptitle, mapadd, keyword;
	private ImageView mapimage;
	private ProgressDialog progressDialog;
	private RetrieveJsonContentTask task;
	private GetKeyWordSearchTask keyWordSearch;
	private LatLng helpLat;
	private ImageView searchbt;
	Map<Marker, AndroidCasesVO> markers;
	private AndroidCasesVO vo;
	private String word , count;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_search);
		searchbt = (ImageView) findViewById(R.id.mapbt);
		keyword = (TextView) findViewById(R.id.mapword);
		initPoints();
		initMap();
		searchclick();

	}

	private void searchclick() {
		searchbt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				word = keyword.getText().toString().trim();

				keyWordSearch = new GetKeyWordSearchTask();
				keyWordSearch.execute(word);

			}
		});

	}

	class GetKeyWordSearchTask extends
			AsyncTask<String, Integer, List<AndroidCasesVO>> {
		// invoked on the UI thread immediately after the task is executed.
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(Map_Search.this);
			progressDialog.setMessage("Loading...");
			progressDialog.show();
		}

		// invoked on the background thread immediately after onPreExecute()
		@Override
		protected List<AndroidCasesVO> doInBackground(String... params) {
			ShopServer shopServer = new ShopServer();
			List<AndroidCasesVO> shopList = shopServer.keywordsearch(params[0]);
			return shopList;
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
		protected void onPostExecute(List<AndroidCasesVO> shopList) {
			progressDialog.cancel();
			if (shopList == null || shopList.isEmpty()) {
				Toast.makeText(Map_Search.this, "抱歉目前沒有關於\"" + word + "\"的商店",
						Toast.LENGTH_SHORT).show();
			}else{
				if(shopList.get(0).getCount() == null){
					count = "";
					Toast.makeText(Map_Search.this, "目前沒有任何商店上架。", Toast.LENGTH_SHORT)
					.show();
				
				}else{
					count = shopList.get(0).getCount().toString();
					Toast.makeText(Map_Search.this, "目前架上有\""+count+"\"個"+word+"商店。", Toast.LENGTH_SHORT)
					.show();
				
				}
				map.clear();
				
				keyword(shopList);
			}
		}

		private void keyword(List<AndroidCasesVO> shopList) {
			
			markers = new HashMap<Marker, AndroidCasesVO>();

			for (AndroidCasesVO shopMaker : shopList) {

				LatLng helpLat = new LatLng(shopMaker.getShopvo().getLat(),
						shopMaker.getShopvo().getLng());

				Marker m = map.addMarker(new MarkerOptions().position(helpLat)
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.shopicon)));

				markers.put(m, shopMaker);

			}
			
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
//		task = new RetrieveJsonContentTask();
//		task.execute();
	}

	class RetrieveJsonContentTask extends
			AsyncTask<String, Integer, List<AndroidCasesVO>> {
		// invoked on the UI thread immediately after the task is executed.
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(Map_Search.this);
			progressDialog.setMessage("Loading...");
			progressDialog.show();
		}

		// invoked on the background thread immediately after onPreExecute()
		@Override
		protected List<AndroidCasesVO> doInBackground(String... params) {
			ShopServer shopServer = new ShopServer();
			List<AndroidCasesVO> shopList = shopServer.getAll();
			return shopList;
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
		protected void onPostExecute(List<AndroidCasesVO> shopList) {
			progressDialog.cancel();
			if (shopList == null || shopList.isEmpty()) {
				Toast.makeText(Map_Search.this, "抱歉目前上架商店", Toast.LENGTH_SHORT)
						.show();
			} else {
				
				if(shopList.get(0).getCount() == null){
					count = "";
					Toast.makeText(Map_Search.this, "目前沒有任何商店上架。", Toast.LENGTH_SHORT)
					.show();
				
				}else{
					count = shopList.get(0).getCount().toString();
					Toast.makeText(Map_Search.this, "目前架上有\""+count+"\"個商店。", Toast.LENGTH_SHORT)
					.show();
				
				}
				
				addMarkersToMap(shopList);

			}
		}

		private void addMarkersToMap(List<AndroidCasesVO> shopList) {

			markers = new HashMap<Marker, AndroidCasesVO>();

			for (AndroidCasesVO shopMaker : shopList) {

				LatLng helpLat = new LatLng(shopMaker.getShopvo().getLat(),
						shopMaker.getShopvo().getLng());

				Marker m = map.addMarker(new MarkerOptions().position(helpLat)
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.shopicon)));

				markers.put(m, shopMaker);

			}

		}
	}

	private void initPoints() {

		taiwan = new LatLng(23.69781, 120.96051499999999);

	}

	// 完成地圖相關設定
	private void initMap() {
		// 檢查GoogleMap物件是否存在
		if (map == null) {
			// 從SupportMapFragment取得GoogleMap物件
			map = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.fmMap)).getMap();
			if (map != null) {
				setUpMap();
			}
		}

	}

	private void setUpMap() {

		map.setMyLocationEnabled(true);
		CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(taiwan).zoom(7).build();

		CameraUpdate cameraUpdate = CameraUpdateFactory
				.newCameraPosition(cameraPosition);

		map.animateCamera(cameraUpdate);

		// 如果不套用自訂InfoWindowAdapter會自動套用預設訊息視窗
		map.setInfoWindowAdapter(new MyInfoWindowAdapter());

		MyMarkerListener myMarkerListener = new MyMarkerListener();
		// 註冊OnMarkerClickListener，當標記被點擊時會自動呼叫該Listener的方法
		map.setOnMarkerClickListener(myMarkerListener);
		// 註冊OnInfoWindowClickListener，當標記訊息視窗被點擊時會自動呼叫該Listener的方法
		map.setOnInfoWindowClickListener(myMarkerListener);

	}

	// 實作與標記相關的監聽器方法
	private class MyMarkerListener implements OnMarkerClickListener,
			OnInfoWindowClickListener {
		@Override
		// 點擊地圖上的標記
		public boolean onMarkerClick(Marker marker) {
			return false;
		}

		@Override
		// 點擊標記的訊息視窗
		public void onInfoWindowClick(Marker marker) {

			int shopno = vo.getShopvo().getShopno();

			int memno = vo.getShopvo().getMemno();

			Intent intent = new Intent(Map_Search.this, Action_Tab_Shop.class);
			Bundle bundle = new Bundle();
			bundle.putInt("shopShopno", shopno);
			bundle.putInt("shopMemno", memno);
			intent.putExtras(bundle);
			startActivity(intent);
		}

	}

	// 自訂InfoWindowAdapter，當點擊標記時會跳出自訂風格的訊息視窗
	private class MyInfoWindowAdapter implements InfoWindowAdapter {
		private final View infoWindow;

		MyInfoWindowAdapter() {
			// 取得指定layout檔，方便標記訊息視窗套用
			infoWindow = getLayoutInflater().inflate(R.layout.map_gogo, null);
		}

		@Override
		public View getInfoWindow(Marker marker) {
			vo = markers.get(marker);

			maptitle = (TextView) infoWindow.findViewById(R.id.maptitle);

			mapadd = (TextView) infoWindow.findViewById(R.id.mapadd);
			mapimage = (ImageView) infoWindow.findViewById(R.id.mapforimage);

			

			byte[] image = vo.getShopvo().getPic();
			if (image != null) {
			Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0,
					image.length);
			mapimage.setImageBitmap(bitmap);
			} else {
				mapimage.setVisibility(View.INVISIBLE);
			}
			
			
			maptitle.setText(vo.getShopvo().getTitle());
			mapadd.setText(vo.getShopvo().getAddr());

			return infoWindow;

		}

		@Override
		// 當getInfoWindow(Marker)回傳null時才會呼叫此方法
		// 此方法如果再回傳null，代表套用預設視窗樣式
		public View getInfoContents(Marker marker) {
			return null;
		}
	}

	private boolean isMapReady() {
		if (map == null) {
			Toast.makeText(this, "到底?", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		initMap();
	}

}