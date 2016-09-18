package iii.ya803g2.moneycenter;

import iii.ya803g2.casesearchpage.AndroidCasesVO;
import iii.ya803g2.sever.Oracle;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MoneyServer {
	// for Tomcat localhost

	// manifest file add <uses-permission
	// android:name="android.permission.INTERNET" />

	public List<AndroidCasesVO> getMemeber(String memid) {
		String url = Oracle.URL + "DpsOrdServletAndroid";
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		String content = "";
		List<NameValuePair> requestParameters = new ArrayList<NameValuePair>();
		requestParameters.add(new BasicNameValuePair("helpMoney",
				"getMoneyOrder"));
		requestParameters.add(new BasicNameValuePair("member", memid));
		try {
			post.setEntity(new UrlEncodedFormEntity(requestParameters));
		} catch (UnsupportedEncodingException e) {
			Log.e("exception", e.toString());
		}
		try {
			HttpResponse response = client.execute(post);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				content = EntityUtils.toString(entity);
			} else {
				Log.d("statusCode", Integer.toString(statusCode));
			}
		} catch (Exception e) {
			Log.e("exception", e.toString());
		}

		// decode JSON to List<News>
		Gson gson = new Gson();
		Type listType = new TypeToken<List<AndroidCasesVO>>() {
		}.getType();
		List<AndroidCasesVO> casesList = gson.fromJson(content, listType);
		return casesList;
	}

	public List<AndroidCasesVO> addDpsord(String memid, String dpshow,
			String saveMoney, String atmAC) {
		String url = Oracle.URL + "DpsOrdServletAndroid";
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		List<NameValuePair> requestParameters = new ArrayList<NameValuePair>();
		requestParameters.add(new BasicNameValuePair("helpMoney", "addDpsord"));
		requestParameters.add(new BasicNameValuePair("memid", memid));
		requestParameters.add(new BasicNameValuePair("dpshow", dpshow));
		requestParameters.add(new BasicNameValuePair("dpsamnt", saveMoney));
		requestParameters.add(new BasicNameValuePair("atmac", atmAC));
		try {
			post.setEntity(new UrlEncodedFormEntity(requestParameters));
		} catch (UnsupportedEncodingException e) {
			Log.e("exception", e.toString());
		}

		String content = null;
		try {
			HttpResponse response = client.execute(post);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				content = EntityUtils.toString(entity);
			} else {
				Log.d("statusCode", Integer.toString(statusCode));
			}
		} catch (Exception e) {
			Log.e("exception", e.toString());
		}

		// decode JSON to List<News>
		Gson gson = new Gson();
		Type type = new TypeToken<List<AndroidCasesVO>>() {
		}.getType();
		List<AndroidCasesVO> caseNoHelp = gson.fromJson(content, type);
		return caseNoHelp;

	}
}