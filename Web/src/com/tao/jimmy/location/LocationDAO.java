package com.tao.jimmy.location;

import java.util.Set;

public interface LocationDAO {
	public Set<CountyVO> findCounties();
	public Set<LocationVO> getAll();
	public Set<LocationVO> findByCounty(CountyVO vo);
	public LocationVO findByPrimaryKey(Integer locno);
	
}
