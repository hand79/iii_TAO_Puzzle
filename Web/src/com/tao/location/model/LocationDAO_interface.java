package com.tao.location.model;

import java.util.List;

public interface LocationDAO_interface {
	public LocationVO findByPrimaryKey(Integer locno);
	
	public List<LocationVO> getAll();
	
	public List<String[]> getUniqCounty();
	
	public List<String[]> getMatchedTowns(String countyRange);

}
