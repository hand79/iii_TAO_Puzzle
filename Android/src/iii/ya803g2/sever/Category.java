package iii.ya803g2.sever;

import android.app.Activity;

// 一個Category物件代表一個功能分類
public class Category {
	private int id;
	// 分類的標題
	private String title;
	// 分類的首頁面屬於哪個Activity
	private Class<? extends Activity> firstActivity;

	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Category(int id, String title, 
			Class<? extends Activity> firstActivity) {
		super();
		this.id = id;
		this.title = title;
		this.firstActivity = firstActivity;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	

	public Class<? extends Activity> getFirstActivity() {
		return firstActivity;
	}

	public void setFirstActivity(Class<? extends Activity> firstActivity) {
		this.firstActivity = firstActivity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}