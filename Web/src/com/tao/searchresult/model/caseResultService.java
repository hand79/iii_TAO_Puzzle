package com.tao.searchresult.model;

import java.util.List;

public class caseResultService {
	private caseResultVO_interface dao;
	
	public caseResultService(){
		dao = new caseResultDAO();
	}
	
	public List<caseResultVO> findCaseByTitleKey(String keyword){
		return dao.findCaseByTitleKey(keyword);
	}
	
	public List<caseResultVO> findCaseBySubCat(Integer subcatno, Integer area){
		return dao.findCaseBySubCat(subcatno, area);
	}

}
