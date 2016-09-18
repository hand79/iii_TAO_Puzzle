package iii.ya803g2.moneycenter;

import java.util.ArrayList;
import com.example.tao_puzzle.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class Storage_money extends Activity {
	private Button AtmBt;
	private Button CreditBt;
	private ListView MoneyList;
	private RadioButton RadioButton;
	private int temp = -1;
	private static final String TAG = "Storage_money";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_money_page);
		findViews();
	}

	private void findViews() {
		AtmBt = (Button) findViewById(R.id.addwish);
		CreditBt = (Button) findViewById(R.id.creditbt);
		MoneyList = (ListView) findViewById(R.id.moneylist);	
		RadioButton = (RadioButton)findViewById(R.id.radioButton1);
		
		
		
		
		
		MoneyList.setAdapter(new ImageTextAdapter(this));
		MoneyList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Object caseno = MoneyList.getTag(position);
						System.out.println("這是啥?"+caseno);
				
				
			}
		});
	}

	private class ImageTextAdapter extends BaseAdapter {

		private LayoutInflater layoutInflater;
		private ArrayList<Money_pay_list> moneyList;

		public ImageTextAdapter(Context context) {
			moneyList = new ArrayList<Money_pay_list>();
			moneyList.add(new Money_pay_list("$1000"));
			moneyList.add(new Money_pay_list("$2000"));
			moneyList.add(new Money_pay_list("$3000"));
			moneyList.add(new Money_pay_list("$4000"));
			moneyList.add(new Money_pay_list("$5000"));
			moneyList.add(new Money_pay_list("$6000"));
			moneyList.add(new Money_pay_list("$7000"));
			moneyList.add(new Money_pay_list("$8000"));
			layoutInflater = (LayoutInflater) context
					.getSystemService(LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			return moneyList.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null) {
				convertView = layoutInflater.inflate(R.layout.pay_money_list,
						null);
			}
			RadioButton radioButton = (RadioButton) convertView.findViewById(R.id.radioButton1);
			radioButton.setId(position);  //把position设为radioButton的id
            radioButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    
                    if(isChecked){
                        //這段程式碼來實現單選功能
                        if(temp != -1){
                            RadioButton tempButton = (RadioButton) Storage_money.this.findViewById(temp);
                            if(tempButton != null){
                               tempButton.setChecked(false);
                            }
                            
                        }
                        
                        temp = buttonView.getId();
                        Log.i(TAG,"太神拉!!!  " + isChecked + "   " + temp);
                        
                        
                    }
                }
            });
            
            //这里实现单选框选的回显，解决了单选框移出屏幕范围未选中状态
            if(temp == position){
                radioButton.setChecked(true);
            }
            
            
			Money_pay_list member = moneyList.get(position);

			TextView textView = (TextView) convertView
					.findViewById(R.id.moneyhelp);
			textView.setText(member.getMoney());

			AtmBt.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					
					String atm = String.valueOf("ATM");
					int helpMoney = (Integer.valueOf(temp)+1)*1000;
					
					if(helpMoney == 0){
						Toast.makeText(Storage_money.this, "請選擇儲值的金額!!",
								Toast.LENGTH_SHORT).show();
					}else{
					
					Intent intent = new Intent();
					intent.setClass(Storage_money.this, Money_ATM.class);
					Bundle bundle = new Bundle();
					bundle.putInt("saveMoney", helpMoney);
					bundle.putString("DPSHOW", atm);
					intent.putExtras(bundle);
					startActivity(intent);
					finish();
					}
				}
			});

			CreditBt.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					
					String credit = String.valueOf("CREDIT");
					int helpMoney = (Integer.valueOf(temp)+1)*1000;
					
					if(helpMoney == 0){
						Toast.makeText(Storage_money.this, "請選擇儲值的金額!!",
								Toast.LENGTH_SHORT).show();
					}else{
					
					Intent intent = new Intent();
					intent.setClass(Storage_money.this, Money_Credit_Card.class);
					
					Bundle bundle = new Bundle();
					bundle.putInt("saveMoney", helpMoney);
					bundle.putString("DPSHOW", credit);
					intent.putExtras(bundle);
					
					startActivity(intent);
					finish();
					}
				}
			});

			return convertView;
		}

	}

}
