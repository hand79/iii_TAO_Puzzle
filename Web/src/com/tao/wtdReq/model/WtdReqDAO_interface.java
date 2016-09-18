package com.tao.wtdReq.model;

import java.util.*;



public interface WtdReqDAO_interface {
	public void insert(WtdReqVO wtdReqVO);
	public void update(WtdReqVO wtdReqVO);
	public void delete(Integer wtdreqno);
	public WtdReqVO findByPrimaryKey(Integer wtdreqno);
	public List<WtdReqVO> getAll();
	
	public List<WtdReqVO> getAll(Map<String, String[]>map);
}
