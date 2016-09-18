package com.tao.cases.model;

import java.sql.Timestamp;
import java.util.List;

public interface CaseQADAO {
	public static final int ALL = 0;
	public static final int ANSWERED = 1;
	public static final int UNANSWERED = 2;
	
	public Integer insert(CaseQAVO qavo);
//	public int update(CaseQAVO qavo);
	public int delete(Integer qano);
	public List<CaseQAVO> getAll();
	
	public CaseQAVO findByPrimaryKey(Integer qano);
	public int updateAnswer(Integer qano, String ans, Timestamp atime);
	public List<CaseQAVO> findByCaseNo(Integer caseno);
	public List<CaseQAVO> findByCaseNo(Integer caseno, int filter);
}
