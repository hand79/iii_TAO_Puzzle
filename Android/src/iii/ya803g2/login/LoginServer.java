package iii.ya803g2.login;






import iii.ya803g2.casesearchpage.AndroidCasesVO;
import iii.ya803g2.casesearchpage.OrderVO;
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





public class LoginServer  {
	// for Tomcat localhost
	// manifest file add <uses-permission
	// android:name="android.permission.INTERNET" />
	
	

	public Boolean isMember(String memberid , String memberpw) {
		String url = Oracle.URL + "MemberServletAndroid";
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		List<NameValuePair> requestParameters = new ArrayList<NameValuePair>();
		requestParameters.add(new BasicNameValuePair("helpForMember","getLoginData"));
		requestParameters.add(new BasicNameValuePair("helpforid", memberid ));
		requestParameters.add(new BasicNameValuePair("helpforpw", memberpw ));
		
		
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
//		Type type = new TypeToken<List<MemberVO>>() {}.getType();
//		List<MemberVO> memberData = gson.fromJson(content,type);
		// android
		Gson gson = new Gson();
		Type listType = new TypeToken<List<AndroidCasesVO>>() {
		}.getType();
		List<AndroidCasesVO> casesList = gson.fromJson(content, listType);
		Boolean isMember = Boolean.valueOf(casesList.get(0).getResult().trim());
		
		return isMember;
}
	
	public List<AndroidCasesVO> getRate(String memid) {
		String url = Oracle.URL + "MemberServletAndroid";
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		String content = "";
		List<NameValuePair> requestParameters = new ArrayList<NameValuePair>();
		requestParameters.add(new BasicNameValuePair("helpForMember","getMyMemberData"));
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
		List<AndroidCasesVO> rateList = gson.fromJson(content, listType);
		return rateList;
	}
	
	
	public List<AndroidCasesVO> onlyMember(String memid) {
		String url = Oracle.URL + "MemberServletAndroid";
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		String content = "";
		List<NameValuePair> requestParameters = new ArrayList<NameValuePair>();
		requestParameters.add(new BasicNameValuePair("helpForMember","getOnlyMember"));
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
		List<AndroidCasesVO> rateList = gson.fromJson(content, listType);
		return rateList;
	}
	
	public List<AndroidCasesVO> changeImage(String memid) {
		String url = Oracle.URL + "MemberServletAndroid";
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		String content = "";
		List<NameValuePair> requestParameters = new ArrayList<NameValuePair>();
		requestParameters.add(new BasicNameValuePair("helpForMember","changeImage"));
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
		List<AndroidCasesVO> rateList = gson.fromJson(content, listType);
		return rateList;
	}
	
	
	public List<AndroidCasesVO> getNews(String memid) {
		String url = Oracle.URL + "MemberServletAndroid";
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		String content = "";
		List<NameValuePair> requestParameters = new ArrayList<NameValuePair>();
		requestParameters.add(new BasicNameValuePair("helpForMember","getNews"));
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
		List<AndroidCasesVO> rateList = gson.fromJson(content, listType);
		return rateList;
	}
	
	
}