package com.tao.jimmy.shop;

import java.io.Serializable;

public class TinyShopProductVO implements Serializable{
	private static final long serialVersionUID = -6820075403512157799L;
	private Integer spno;

	public TinyShopProductVO(Integer spno) {
		super();
		this.spno = spno;
	}

	public TinyShopProductVO() {
		super();
	}

	public Integer getSpno() {
		return spno;
	}

	public void setSpno(Integer spno) {
		this.spno = spno;
	}
	
}
