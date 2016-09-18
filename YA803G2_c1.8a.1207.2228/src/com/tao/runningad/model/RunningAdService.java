package com.tao.runningad.model;
import java.sql.Date;
import java.util.List;


public class RunningAdService {
	private RunningAdDAO_interface dao;
	
	public RunningAdService() {
		dao = new RunningAdDAO();
	}
	
	public int insert(RunningAdVO runningAdVO){
		return dao.insert(runningAdVO);
	}
	
	public int update(RunningAdVO runningAdVO){
		return dao.update(runningAdVO);
	}
	
	public int delete(Integer adno){
		return dao.delete(adno);
	}
	
	public RunningAdVO findByPrimaryKey(Integer adno){
		return dao.findByPrimaryKey(adno);
	}
	
	public List<RunningAdVO> getAll(){
		return dao.getAll();
	}
	
	public int insertFromUser(RunningAdVO runningAdVO){
		return dao.insertFromUser(runningAdVO);
	}
	
	public int updateToManage(RunningAdVO runningAdVO){
		return dao.updateToManage(runningAdVO);
	}
	
	public List<RunningAdVO> getAllByMemno(Integer memno){
		return dao.getAllByMemno(memno);
	}
	
	public List<RunningAdVO> getAllActiveAds(){
		return dao.getAllActiveAds();
	}
	
	public int RemoveExpiredAds(Integer adno){
		return dao.RemoveExpiredAds(adno);
	}
	
	public Date getSysDate(){
		return dao.getSysDate();
	}
	
	public List<RunningAdVO> getAllByStatus(Integer tst){
		return dao.getAllByStatus(tst);
	}
}
