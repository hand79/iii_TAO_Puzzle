package com.tao.hotcasesandshops.model;

import java.util.List;

public interface HotCaseDAO_interface {
	public HotCaseVO findByCaseno(Integer caseno);
	public List<HotCaseVO>getAllByHits(); 
	public List<HotCaseVO>getAllByHitsStatus(); 
	public List<HotCaseVO>getAllByHitsStatusLocno(Integer locno);
	public HotCaseVO findByCasenoPic(Integer caseno);
	
}
