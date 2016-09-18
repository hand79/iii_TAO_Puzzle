package com.tao.hotcasesandshops.model;

import java.util.List;

public class HotCaseService {
	private  HotCaseDAO_interface dao;

	public HotCaseService() {
		dao = new HotCaseDAO();
	}
	
	public HotCaseVO  findByCaseno(Integer caseno){
		return dao.findByCaseno(caseno);
	
	}
	public HotCaseVO findByCasenoPic(Integer caseno){
		return dao.findByCasenoPic(caseno);
	
	}

	/*********************GET_ALL_NO_PIC**************************/
	public List<HotCaseVO>getAllByHits(){
		return dao.getAllByHits();
		
	}
	public List<HotCaseVO>getAllByHitsStatus(){
		return dao.getAllByHitsStatus();
		
	} 
	public List<HotCaseVO>getAllByHitsStatusLocno(Integer locno){
		return dao.getAllByHitsStatusLocno(locno);
	}
	
}
