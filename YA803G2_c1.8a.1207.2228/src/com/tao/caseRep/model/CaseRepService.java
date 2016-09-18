package com.tao.caseRep.model;

import java.util.List;
import java.util.Map;

import com.tao.wtdReq.model.WtdReqVO;

public class CaseRepService {

	private CaseRepDAO_interface dao;
	
	public CaseRepService(){
		dao = new CaseRepDAO();
	}
	
	public CaseRepVO addCaseRep (Integer repno, Integer susno, Integer repcaseno, String creprsn){
		
		CaseRepVO caseRepVO = new CaseRepVO();
		
		 caseRepVO.setRepno(repno);
		 caseRepVO.setSusno(susno);
		 caseRepVO.setRepcaseno(repcaseno);
		 caseRepVO.setCreprsn(creprsn);
		 dao.insert(caseRepVO);
		 
		 return caseRepVO;
	}
	
	public CaseRepVO updateCaseRep (Integer crepno,Integer repno, Integer susno, Integer repcaseno, String creprsn){
		
		CaseRepVO caseRepVO = new CaseRepVO();
		
		 caseRepVO.setCrepno(crepno);
		 caseRepVO.setRepno(repno);
		 caseRepVO.setSusno(susno);
		 caseRepVO.setRepcaseno(repcaseno);
		 caseRepVO.setCreprsn(creprsn);
		 dao.update(caseRepVO);
		 
		 return caseRepVO;
	}
	
	public void deleteCaseRep(Integer crepno){
		dao.delete(crepno);
	}
	
	public CaseRepVO getOneCaseRep(Integer crepno){
		return dao.findByPrimaryKey(crepno);
	}
	
	public List<CaseRepVO> getAll(){
		return dao.getAll();
	}
	
	public List<CaseRepVO> getAll(Map<String,String[]> map){
		return dao.getAll(map);
	}
	
}
