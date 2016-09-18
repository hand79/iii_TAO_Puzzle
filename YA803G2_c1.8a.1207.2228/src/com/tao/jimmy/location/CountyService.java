package com.tao.jimmy.location;

import java.util.Set;

public class CountyService {
	private LocationDAO dao;

	public CountyService() {
		dao = new LocationDAOImpl();
	}

	public Set<CountyVO> findCounties() {
		return dao.findCounties();
	}
	
}
