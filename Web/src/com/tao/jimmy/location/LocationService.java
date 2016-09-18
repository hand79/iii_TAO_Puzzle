package com.tao.jimmy.location;

import java.util.Set;

public class LocationService {
	private LocationDAO dao;

	public LocationService() {
		dao = new LocationDAOImpl();
	}

	public Set<CountyVO> findCounties() {
		return dao.findCounties();
	};

	public Set<LocationVO> getAll() {
		return dao.getAll();
	};

	public Set<LocationVO> findByCounty(Integer from, Integer to) {
		CountyVO vo = new CountyVO();
		vo.setFrom(from);
		vo.setTo(to);
		return dao.findByCounty(vo);
	};

	public LocationVO findByPrimaryKey(Integer locno) {
		return dao.findByPrimaryKey(locno);
	}
}
