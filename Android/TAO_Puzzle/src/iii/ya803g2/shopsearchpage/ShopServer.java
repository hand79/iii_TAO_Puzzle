package iii.ya803g2.shopsearchpage;

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

public class ShopServer {
	// for Tomcat localhost

	// manifest file add <uses-permission
	// android:name="android.permission.INTERNET" />

	public List<AndroidCasesVO> getAll() {
		String url = Oracle.URL + "ShopServletAndroid";
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		String content = "";
		List<NameValuePair> requestParameters = new ArrayList<NameValuePair>();
		requestParameters
				.add(new BasicNameValuePair("helpShopList", "getShopList"));
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
		Type listType = new TypeToken<List<AndroidCasesVO>>() {}.getType();
		List<AndroidCasesVO> shopList = gson.fromJson(content, listType);
		return shopList;
	}

	public List<AndroidCasesVO> findByShopno(Integer shopno , Integer memno) {
		String url = Oracle.URL + "ShopServletAndroid";
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		List<NameValuePair> requestParameters = new ArrayList<NameValuePair>();
		requestParameters.add(new BasicNameValuePair("helpShopList", "getShopno"));
		requestParameters.add(new BasicNameValuePair("shopShopno", Integer
				.toString(shopno)));
		requestParameters.add(new BasicNameValuePair("helpforMember", Integer
				.toString(memno)));
		
		
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
		List<AndroidCasesVO> shopnoHelp = gson.fromJson(content, type);
		return shopnoHelp;

	}

	public List<AndroidCasesVO> findByShopProduct(Integer shopno) {
		String url = Oracle.URL + "ShopServletAndroid";
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		String content = "";
		List<NameValuePair> requestParameters = new ArrayList<NameValuePair>();
		requestParameters.add(new BasicNameValuePair("helpShopList", "getShopProduct"));
		requestParameters.add(new BasicNameValuePair("shopShopno", Integer.toString(shopno).trim()));
		System.out.println("Android的資料"+shopno);
		
		
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
		List<AndroidCasesVO> shopListData = gson.fromJson(content, listType);
		return shopListData;
	}
	
	public List<AndroidCasesVO> getShopcase(String spno) {
		String url = Oracle.URL + "ShopServletAndroid";
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		List<NameValuePair> requestParameters = new ArrayList<NameValuePair>();

		
		requestParameters.add(new BasicNameValuePair("helpShopList", "getShopcase"));
		requestParameters.add(new BasicNameValuePair("spno", spno));
		
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
//		Gson gson = new Gson();
//		Type type = new TypeToken<List<AndroidCasesVO>>() {
//		}.getType();
//		List<AndroidCasesVO> orderHelp = gson.fromJson(content, type);
//		Boolean isMember = Boolean.valueOf(content.trim());
		
		
		
		Gson gson = new Gson();
		Type listType = new TypeToken<List<AndroidCasesVO>>() {
		}.getType();
		List<AndroidCasesVO> finalCaseQA = gson.fromJson(content, listType);
		return finalCaseQA;

	}
	
	public List<AndroidCasesVO> delshop(String memid , String shopno , String memno ,String shopQA) {
		String url = Oracle.URL + "ShopServletAndroid";
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		List<NameValuePair> requestParameters = new ArrayList<NameValuePair>();

		
		requestParameters.add(new BasicNameValuePair("helpShopList", "delshop"));
		requestParameters.add(new BasicNameValuePair("memid", memid));
		requestParameters.add(new BasicNameValuePair("shopno", shopno));
		requestParameters.add(new BasicNameValuePair("memno", memno));
		requestParameters.add(new BasicNameValuePair("shopQA", shopQA));
		
		try {
			post.setEntity(new UrlEncodedFormEntity(requestParameters,
					HTTP.UTF_8));
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


		
		
		
		Gson gson = new Gson();
		Type listType = new TypeToken<List<AndroidCasesVO>>() {
		}.getType();
		List<AndroidCasesVO> delShopQA = gson.fromJson(content, listType);
		return delShopQA;

	}
	
	
	public List<AndroidCasesVO> keywordsearch(String word) {
		String url = Oracle.URL + "ShopServletAndroid";
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		List<NameValuePair> requestParameters = new ArrayList<NameValuePair>();

		
		requestParameters.add(new BasicNameValuePair("helpShopList", "keywordsearch"));
		requestParameters.add(new BasicNameValuePair("word", word));
	
		
		try {
			post.setEntity(new UrlEncodedFormEntity(requestParameters,
					HTTP.UTF_8));
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


		
		
		
		Gson gson = new Gson();
		Type listType = new TypeToken<List<AndroidCasesVO>>() {
		}.getType();
		List<AndroidCasesVO> keywordVO = gson.fromJson(content, listType);
		return keywordVO;

	}
}