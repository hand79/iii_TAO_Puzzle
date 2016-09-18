package com.tao.runningad.model;

import java.sql.Date;
import java.util.List;

public interface RunningAdDAO_interface {
	public int insert(RunningAdVO runningAdVO);
	public int update(RunningAdVO runningAdVO);
	public int delete(Integer adno);
	public RunningAdVO findByPrimaryKey(Integer adno);
	public List<RunningAdVO> getAll();
	
	public int insertFromUser(RunningAdVO runningAdVO);
	
	public int updateToManage(RunningAdVO runningAdVO);
	
	public List<RunningAdVO> getAllByMemno(Integer memno);
	
	public List<RunningAdVO> getAllActiveAds();
	
	public List<RunningAdVO> getAllByStatus(Integer tst);
	
	public int RemoveExpiredAds(Integer adno);
	
	public Date getSysDate();
}
