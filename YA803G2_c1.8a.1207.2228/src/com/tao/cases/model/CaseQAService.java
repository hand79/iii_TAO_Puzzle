package com.tao.cases.model;

import java.sql.Timestamp;
import java.util.List;

public class CaseQAService {
	private CaseQADAO dao;

	public CaseQAService() {
		this.dao = new CaseQADAOImpl();
	}

	public Integer askQuestion(Integer memno, Integer caseno, String question,
			Timestamp qtime) {
		
		CaseQAVO vo = new CaseQAVO();
		
		vo.setMemno(memno);
		vo.setCaseno(caseno);
		vo.setQuestion(question);
		vo.setQtime(qtime);

		Integer genKey = dao.insert(vo);

		return genKey;
	};

//	public int update(Integer qano, Integer memno, Integer caseno, String question, String answer, Timestamp qtime, Timestamp atime) {
//		CaseQAVO vo = new CaseQAVO();
//		vo.setQano(qano);
//		vo.setCaseno(caseno);
//		vo.setQuestion(question);
//		vo.setAnswer(answer);
//		vo.setQtime(qtime);
//		vo.setAtime(atime);
//		int updateCount = dao.update(vo);
//		return updateCount;
//	};

	public int deleteQA(Integer qano) {
		int updateCount = dao.delete(qano);
		return updateCount;
	};

	public List<CaseQAVO> getAll() {
		List<CaseQAVO> list = dao.getAll();
		return list;
	};

	public CaseQAVO findByPrimaryKey(Integer qano) {
		CaseQAVO vo = dao.findByPrimaryKey(qano);
		return vo;
	};
	
	
	public int updateAnswer(Integer qano, String ans) {
		Timestamp atime = new Timestamp(System.currentTimeMillis());
		int updateCount = dao.updateAnswer(qano, ans, atime);
		return updateCount;
	};
	
	public int updateAnswer(Integer qano, String ans, Timestamp atime) {
		int updateCount = dao.updateAnswer(qano, ans, atime);
		return updateCount;
	};

	public List<CaseQAVO> findByCaseNo(Integer caseno) {
		List<CaseQAVO> list = dao.findByCaseNo(caseno);
		return list;
	}

	public List<CaseQAVO> findByCaseNo(Integer caseno, int filter) {
		List<CaseQAVO> list = dao.findByCaseNo(caseno, filter);
		return list;
	};

}
