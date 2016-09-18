package com.tao.searchresult.model;

import java.util.List;

public interface caseResultVO_interface {
	public List<caseResultVO> findCaseByTitleKey(String keyword);
	
	public List<caseResultVO> findCaseBySubCat(Integer subcatno, Integer area);

}
