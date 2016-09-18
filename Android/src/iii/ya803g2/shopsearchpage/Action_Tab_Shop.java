package iii.ya803g2.shopsearchpage;

import iii.ya803g2.casesearchpage.AndroidCasesVO;

import java.util.List;

import com.example.tao_puzzle.R;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.Toast;

public class Action_Tab_Shop extends Activity {

	private List<AndroidCasesVO> helpPage;

	public List<AndroidCasesVO> getHelpPage() {
		return helpPage;
	}

	public void setHelpPage(List<AndroidCasesVO> helpPage) {
		this.helpPage = helpPage;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		// actionBar.setDisplayShowHomeEnabled(false);
		// actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.addTab(actionBar
				.newTab()

				.setText("商店資訊")
				.setTabListener(
						new MyTabListener<Shop_Thing_Page1>(this, "商店資訊",
								Shop_Thing_Page1.class)));

		actionBar.addTab(actionBar
				.newTab()
				.setText("商店商品")
				.setTabListener(
						new MyTabListener<Shop_Thing_Page2>(this, "商店商品",
								Shop_Thing_Page2.class)));

		actionBar.addTab(actionBar
				.newTab()
				.setText("商店位置")
				.setTabListener(
						new MyTabListener<Shop_Thing_Page3>(this, "商店位置",
								Shop_Thing_Page3.class)));
		
		actionBar.addTab(actionBar
				.newTab()
				.setText("檢舉商店")
				.setTabListener(
						new MyTabListener<Shop_Thing_Page4>(this, "檢舉商店",
								Shop_Thing_Page4.class)));
	}

	public class MyTabListener<T extends Fragment> implements
			ActionBar.TabListener {
		private final Activity activity;
		private final String tag;
		private final Class<T> clz;
		private final Bundle bundle;
		private Fragment fragment;

		public MyTabListener(Activity activity, String tag, Class<T> clz) {
			this(activity, tag, clz, null);
		}

		public MyTabListener(Activity activity, String tag, Class<T> clz,
				Bundle bundle) {
			this.activity = activity;
			this.tag = tag;
			this.clz = clz;
			this.bundle = bundle;
		}

		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			if (fragment == null) {
				fragment = Fragment
						.instantiate(activity, clz.getName(), bundle);
				ft.add(android.R.id.content, fragment, tag);
			} else {
				ft.attach(fragment);
			}
		}

		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			if (fragment != null) {
				ft.detach(fragment);
			}
		}

		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			Toast.makeText(activity, "已經在當前頁面!!", Toast.LENGTH_SHORT).show();
		}
	}
}