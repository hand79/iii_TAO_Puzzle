package com.tao.jimmy.location;

import java.io.Serializable;

public class CountyVO implements Serializable, Comparable<CountyVO> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4040497493948056859L;
	private String name;
	private Integer from;
	private Integer to;

	public void setName(String name) {
		this.name = name;
	}

	public Integer getFrom() {
		return from;
	}

	public void setFrom(Integer from) {
		this.from = from;
	}

	public Integer getTo() {
		return to;
	}

	public void setTo(Integer to) {
		this.to = to;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof CountyVO))
			return false;
		return this.getName().equals(((CountyVO) obj).getName());
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name + ":" + from + "-" + to;
	}

	@Override
	public int compareTo(CountyVO o) {

		return getFrom().compareTo(o.getFrom());
	}

	public String getValue() {
		return from.toString() + "-" + to.toString();
	}
}
