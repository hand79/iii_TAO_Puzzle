package com.tao.caseRep.model;

import java.util.List;
import java.util.Map;



public interface CaseRepDAO_interface {
	public void insert(CaseRepVO caseRepVO);
	public void update(CaseRepVO caseRepVO);
	public void delete(Integer crepno);
	public CaseRepVO findByPrimaryKey(Integer crepno);
	public List<CaseRepVO> getAll();
	
	public List<CaseRepVO> getAll(Map<String, String[]>map);
}
