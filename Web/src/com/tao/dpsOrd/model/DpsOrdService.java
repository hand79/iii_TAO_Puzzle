package com.tao.dpsOrd.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class DpsOrdService {

	private DpsOrdDAO_interface dao;

	public DpsOrdService() {
		dao = new DpsOrdDAO();
	}

	public DpsOrdVO addDpsOrdVO(Timestamp dpsordt, Double dpsamnt,
			Integer memno, String dpshow, String ordsts, String atmac) {
		DpsOrdVO dpsOrdVO = new DpsOrdVO();

		dpsOrdVO.setDpsordt(dpsordt);
		dpsOrdVO.setDpsamnt(dpsamnt);
		dpsOrdVO.setMemno(memno);
		dpsOrdVO.setDpshow(dpshow);
		dpsOrdVO.setOrdsts(ordsts);
		dpsOrdVO.setAtmac(atmac);
		dao.insert(dpsOrdVO);

		return dpsOrdVO;

	}

	public DpsOrdVO updateDpsOrdVO(Integer dpsordno, Timestamp dpsordt,
			Double dpsamnt, Integer memno, String dpshow, String ordsts,
			String atmac) {
		DpsOrdVO dpsOrdVO = new DpsOrdVO();

		dpsOrdVO.setDpsordno(dpsordno);
		dpsOrdVO.setDpsordt(dpsordt);
		dpsOrdVO.setDpsamnt(dpsamnt);
		dpsOrdVO.setMemno(memno);
		dpsOrdVO.setDpshow(dpshow);
		dpsOrdVO.setOrdsts(ordsts);
		dpsOrdVO.setAtmac(atmac);
		dao.update(dpsOrdVO);
		
		return dpsOrdVO;

	}
	
	public void deleteDpsOrd(Integer dpsordno){
		dao.delete(dpsordno);
	}
	
	public DpsOrdVO getOneDpsOrd(Integer dpsordno){
		return dao.findByPrimaryKey(dpsordno);
	}

	public List<DpsOrdVO> getAll(){
		return dao.getAll();
	}
	
	
	public List<DpsOrdVO> getAll(Map<String, String[]> map){
		return dao.getAll(map);
	}


}

