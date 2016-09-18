package com.tao.wtdReq.model;

import java.sql.Timestamp;
import java.util.*;


public class WtdReqService {

	private WtdReqDAO_interface dao;

	public WtdReqService() {
		dao = new WtdReqDAO();
	}

	public WtdReqVO addWtdReq(Timestamp wtdreqt, Integer memno,
			Double wtdamnt, String wtdac, String reqsts) {

		WtdReqVO wtdReqVO = new WtdReqVO();

		wtdReqVO.setWtdreqt(wtdreqt);
		wtdReqVO.setWtdamnt(wtdamnt);
		wtdReqVO.setMemno(memno);
		wtdReqVO.setWtdac(wtdac);
		wtdReqVO.setReqsts(reqsts);
		dao.insert(wtdReqVO);

		return wtdReqVO;
	}
	
	public WtdReqVO updateWtdReq(Integer wtdreqno, Timestamp wtdreqt, Integer memno,
			Double wtdamnt, String wtdac, String reqsts){
		
		WtdReqVO wtdReqVO = new WtdReqVO();
		
		wtdReqVO.setWtdreqno(wtdreqno);
		wtdReqVO.setWtdreqt(wtdreqt);
		wtdReqVO.setWtdamnt(wtdamnt);
		wtdReqVO.setMemno(memno);
		wtdReqVO.setWtdac(wtdac);
		wtdReqVO.setReqsts(reqsts);
		dao.update(wtdReqVO);
		
		return wtdReqVO;
	}
	
	public void deleteWtdReq(Integer wtdreqno){
		dao.delete(wtdreqno);
	}
	
	public WtdReqVO getOneWtdReq(Integer wtdreqno){
		return dao.findByPrimaryKey(wtdreqno);
	}
	
	public List<WtdReqVO> getAll(){
		return dao.getAll();
	}
	
	public List<WtdReqVO> getAll(Map<String,String[]> map){
		return dao.getAll(map);
	}
}
