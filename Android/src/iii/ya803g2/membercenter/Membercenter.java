package iii.ya803g2.membercenter;

import static iii.ya803g2.sever.Oracle.CATEGORIES;
import iii.ya803g2.memberdata.Memberdata;
import iii.ya803g2.sever.Category;

import java.util.ArrayList;

import com.example.tao_puzzle.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Membercenter extends Activity {
	private ListView listView;
	private TextView membercentertitle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.member_center);
		findViews();
	}

	private void findViews() {
		listView = (ListView) findViewById(R.id.membercenterlist);
		listView.setAdapter(new ImageTextAdapter(this));
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Class<? extends Activity> activityClass = CATEGORIES[position]
						.getFirstActivity();
				Intent intent = new Intent(getBaseContext(), activityClass);
				startActivity(intent);
			}
		});
	}

	private class ImageTextAdapter extends BaseAdapter {

		private LayoutInflater layoutInflater;

		public ImageTextAdapter(Context context) {

			layoutInflater = (LayoutInflater) context
					.getSystemService(LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			return CATEGORIES.length;
		}

		@Override
		public Object getItem(int position) {

			return CATEGORIES[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null) {
				convertView = layoutInflater.inflate(
						R.layout.member_center_list, null);
			}
			Category forTitle = CATEGORIES[position];
			
			
			membercentertitle = (TextView) convertView.findViewById(R.id.casecount);
			membercentertitle.setText(forTitle.getTitle());
			
			
			return convertView;
		}
	}
}
