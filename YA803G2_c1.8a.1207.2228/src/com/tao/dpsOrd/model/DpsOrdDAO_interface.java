package com.tao.dpsOrd.model;

import java.util.*;



public interface DpsOrdDAO_interface {
	public void insert(DpsOrdVO dpsOrdVO);
	public void update(DpsOrdVO dpsOrdVO);
	public void delete(Integer dpsordno);
	public DpsOrdVO findByPrimaryKey(Integer dpsordno);
	public List<DpsOrdVO> getAll();
	public List<DpsOrdVO> getAll(Map<String, String[]> map);
	
}
