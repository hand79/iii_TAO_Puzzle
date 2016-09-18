package iii.ya803g2.casesearchpage;

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

public class CasesServer {
	// for Tomcat localhost

	// manifest file add <uses-permission
	// android:name="android.permission.INTERNET" />

	public List<AndroidCasesVO> getAll() {
		String url = Oracle.URL + "CasesServlet";
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		String content = "";
		List<NameValuePair> requestParameters = new ArrayList<NameValuePair>();
		requestParameters
				.add(new BasicNameValuePair("helpList", "getCaseList"));
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

	public List<AndroidCasesVO> findByCaseno(Integer caseno , Integer memno) {
		String url = Oracle.URL + "CasesServlet";
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		List<NameValuePair> requestParameters = new ArrayList<NameValuePair>();
		requestParameters.add(new BasicNameValuePair("helpList", "getCaseno"));
		requestParameters.add(new BasicNameValuePair("caseCaseno", Integer
				.toString(caseno)));
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
		List<AndroidCasesVO> caseNoHelp = gson.fromJson(content, type);
		return caseNoHelp;

	}

	public List<AndroidCasesVO> getOrder(String account, String caseNO) {
		String url = Oracle.URL + "OrderServlet";
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		String content = "";
		List<NameValuePair> requestParameters = new ArrayList<NameValuePair>();
		requestParameters.add(new BasicNameValuePair("helpForOrder", "inOrderPage"));
		requestParameters.add(new BasicNameValuePair("helpforId", account));
		requestParameters.add(new BasicNameValuePair("helpforNo", caseNO));
		
		
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

	public String addOrderData(String caseno , String qty , String ship , String memno) {
		String url = Oracle.URL + "/order/order.do";
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		List<NameValuePair> requestParameters = new ArrayList<NameValuePair>();
		System.out.println("caseno:"+caseno);
		System.out.println("qty:"+qty);
		System.out.println("ship:"+ship);
		System.out.println("memno:"+memno);
		requestParameters.add(new BasicNameValuePair("action", "order"));
		requestParameters.add(new BasicNameValuePair("from", "android"));
		requestParameters.add(new BasicNameValuePair("caseno", caseno));
		requestParameters.add(new BasicNameValuePair("qty", qty));
		requestParameters.add(new BasicNameValuePair("ship", ship));
		requestParameters.add(new BasicNameValuePair("memno", memno));
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
		
		String orderWTF = String.valueOf(content.trim()); 
		
		return orderWTF;

	}
	
	
	
	public List<AndroidCasesVO> addCaseQa(String memno , String caseno , String caseQA , String helpTime) {
		String url = Oracle.URL + "CasesServlet";
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		List<NameValuePair> requestParameters = new ArrayList<NameValuePair>();
		requestParameters.add(new BasicNameValuePair("helpList", "goCaseQA"));
		requestParameters.add(new BasicNameValuePair("memno", memno));
		requestParameters.add(new BasicNameValuePair("caseno", caseno));
		requestParameters.add(new BasicNameValuePair("caseQA", caseQA));
		requestParameters.add(new BasicNameValuePair("helpTime", helpTime));
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
		List<AndroidCasesVO> finalCaseQA = gson.fromJson(content, listType);
		System.out.println(finalCaseQA);
		return finalCaseQA;

	}
	
	public List<AndroidCasesVO> keywordsearch(String word) {
		String url = Oracle.URL + "CasesServlet";
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		List<NameValuePair> requestParameters = new ArrayList<NameValuePair>();
		requestParameters.add(new BasicNameValuePair("helpList", "keywordsearch"));
		requestParameters.add(new BasicNameValuePair("word", word));
		System.out.println("求解?"+word);
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
		List<AndroidCasesVO> finalCaseQA = gson.fromJson(content, listType);
		return finalCaseQA;

	}
	
}