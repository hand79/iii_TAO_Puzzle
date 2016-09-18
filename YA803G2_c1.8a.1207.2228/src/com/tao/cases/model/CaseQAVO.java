package com.tao.cases.model;

import java.io.Serializable;
import java.sql.Timestamp;

import com.tao.jimmy.util.TimestampFormater;

public class CaseQAVO implements Serializable {
	private static final long serialVersionUID = 1364805082667621072L;
	private Integer qano;
	private Integer memno;
	private Integer caseno;
	private String question;
	private String answer;
	private Timestamp qtime;
	private Timestamp atime;

	public CaseQAVO() {
		super();
	}

	public Integer getQano() {
		return qano;
	}

	public void setQano(Integer qano) {
		this.qano = qano;
	}

	public Integer getMemno() {
		return memno;
	}

	public void setMemno(Integer memno) {
		this.memno = memno;
	}

	public Integer getCaseno() {
		return caseno;
	}

	public void setCaseno(Integer caseno) {
		this.caseno = caseno;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Timestamp getQtime() {
		return qtime;
	}

	public void setQtime(Timestamp qtime) {
		this.qtime = qtime;
	}

	public Timestamp getAtime() {
		return atime;
	}

	public void setAtime(Timestamp atime) {
		this.atime = atime;
	}

	public String getFormatedAtime() {
		return TimestampFormater.format(atime);
	}

	public String getFormatedQtime() {
		return TimestampFormater.format(qtime);
	}

	public String getShortenedQuestion() {
		if (question == null)
			return null;
		String re = question.length() > 20 ? question.substring(0, 20)
				: question;
		return re;
	}
	
}
