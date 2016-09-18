package iii.ya803g2.news;



import iii.ya803g2.casesearchpage.AndroidCasesVO;
import iii.ya803g2.sever.Oracle;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;




public class NewsServer  {
	// for Tomcat localhost
	

	// manifest file add <uses-permission
	// android:name="android.permission.INTERNET" />

	public List<AndroidCasesVO> getAll() {
		String url = Oracle.URL + "NewsServletAndroid";
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		String content = "";
		List<NameValuePair> requestParameters = new ArrayList<NameValuePair>();
		requestParameters.add(new BasicNameValuePair("helpNews","getNews"));
		
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
		Type listType = new TypeToken<List<AndroidCasesVO>>(){}.getType();
		List<AndroidCasesVO> newsList = gson.fromJson(content, listType);
		
		
		
		return newsList;
		
	}

	
}