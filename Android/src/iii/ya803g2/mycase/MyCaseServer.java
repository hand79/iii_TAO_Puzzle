package iii.ya803g2.mycase;

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

public class MyCaseServer {
	// for Tomcat localhost

	// manifest file add <uses-permission
	// android:name="android.permission.INTERNET" />

	public List<AndroidCasesVO> getAllMyCase(String memid) {
		String url = Oracle.URL + "OrderServlet";
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		String content = "";
		List<NameValuePair> requestParameters = new ArrayList<NameValuePair>();
		requestParameters.add(new BasicNameValuePair("helpForOrder",
				"getMyCase"));
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

	public List<AndroidCasesVO> getJoinCase(String memid) {
		String url = Oracle.URL + "OrderServlet";
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		List<NameValuePair> requestParameters = new ArrayList<NameValuePair>();
		requestParameters.add(new BasicNameValuePair("helpForOrder",
				"getJoinCase"));
		requestParameters.add(new BasicNameValuePair("member", memid));

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

	public List<AndroidCasesVO> getWishCase(String memid) {
		String url = Oracle.URL + "OrderServlet";
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		String content = "";
		List<NameValuePair> requestParameters = new ArrayList<NameValuePair>();
		requestParameters.add(new BasicNameValuePair("helpForOrder", "getWishCase"));
		requestParameters.add(new BasicNameValuePair("memid", memid));

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
		List<AndroidCasesVO> orderData = gson.fromJson(content, listType);
		return orderData;
	}

	public List<AndroidCasesVO> addwish(String memid , String caseno) {
		String url = Oracle.URL + "OrderServlet";
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		String content = "";
		List<NameValuePair> requestParameters = new ArrayList<NameValuePair>();
		requestParameters.add(new BasicNameValuePair("helpForOrder", "addWish"));
	
		requestParameters.add(new BasicNameValuePair("memid", memid));
		requestParameters.add(new BasicNameValuePair("caseno", caseno));
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
		List<AndroidCasesVO> orderData = gson.fromJson(content, listType);
//		Boolean isMember = Boolean.valueOf(orderData.get(0).getResult().trim());
		return orderData;
	}
	
	public List<AndroidCasesVO> delcase(String memid , String caseno , String memno ,String caseQA) {
		String url = Oracle.URL + "OrderServlet";
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		String content = "";
		List<NameValuePair> requestParameters = new ArrayList<NameValuePair>();
		requestParameters.add(new BasicNameValuePair("helpForOrder", "delcase"));
		System.out.println("TMD"+memid);
		System.out.println("TMDTMDTMD"+caseno);
		requestParameters.add(new BasicNameValuePair("memid", memid));
		requestParameters.add(new BasicNameValuePair("caseno", caseno));
		requestParameters.add(new BasicNameValuePair("memno", memno));
		requestParameters.add(new BasicNameValuePair("caseQA", caseQA));
		try {
			post.setEntity(new UrlEncodedFormEntity(requestParameters,
					HTTP.UTF_8));
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
		List<AndroidCasesVO> orderData = gson.fromJson(content, listType);
//		Boolean isMember = Boolean.valueOf(orderData.get(0).getResult().trim());
		return orderData;
	}
	

}