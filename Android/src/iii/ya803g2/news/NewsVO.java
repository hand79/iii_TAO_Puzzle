package iii.ya803g2.news;


import java.sql.Timestamp;

import com.tao.cathy.util.DateFormater;

public class NewsVO implements java.io.Serializable{
	private Integer newsno;
	private String title;
	private String text;
	private Timestamp pubtime;
	public Integer getNewsno() {
		return newsno;
	}
	public void setNewsno(Integer newsno) {
		this.newsno = newsno;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Timestamp getPubtime() {
		return pubtime;
	}
	public void setPubtime(Timestamp pubtime) {
		this.pubtime = pubtime;
	}
	public String getFormatPubtime() {
		return DateFormater.formatTimestamp(pubtime);
		
		
	}

}
