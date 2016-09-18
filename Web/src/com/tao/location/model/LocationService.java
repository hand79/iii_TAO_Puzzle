package com.tao.location.model;

import java.util.List;

public class LocationService {
	private LocationDAO_interface dao;
	
	public LocationService() {
		dao = new LocationDAO();
	}

	
	public LocationVO findByPrimaryKey(Integer locno){
		return dao.findByPrimaryKey(locno);
	}
	
	public List<LocationVO> getAll(){
		return dao.getAll();
	}
	
	public List<String[]> getUniqCounty(){
		return dao.getUniqCounty();
	}
	
	public List<String[]> getMatchedTowns(String countyRange){
		return dao.getMatchedTowns(countyRange);
	}
}
