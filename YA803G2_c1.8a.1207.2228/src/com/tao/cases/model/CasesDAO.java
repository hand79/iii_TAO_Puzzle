package com.tao.cases.model;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Set;

import com.tao.jimmy.util.model.ColumnValue;
import com.tao.jimmy.util.model.ColumnValueBundle;

public interface CasesDAO {
//	public static final int STATUS_CREATED = 0;
//	public static final int STATUS_PUBLIC = 1;
//	public static final int STATUS_PRIVATE = 2;
//	public static final int STATUS_OVER = 3;
//	public static final int STATUS_COMPLETED = 4;
//	public static final int STATUS_DELETED = 5;

	public Integer insert(CasesVO vo);
	public int updateByEdit(CasesVO vo);
	
//	public int delete(Integer caseno);

	public int updateStatus(Integer caseno, Integer status);
	public int openCases(Integer caseno, Timestamp stime, Timestamp etime, Boolean isPrivate);
	public boolean overCases(Integer caseno);
	public boolean cancelCase(Integer caseno);
	public int updateHits(Map<Integer, Integer> contextHitsRecord);
//	public int updateByEdit(CaseVO vo);
	public Set<CasesVO> findByAreaSearch(Integer locnoFrom, Integer locnoTo,
			Integer subcatno);
	public CasesVO findByPrimaryKey(Integer caseno);
	public Set<CasesVO> getAll();

	public Set<CasesVO> findByCreator(Integer memno);
	public Set<CasesVO> findByCreator(Integer memno, boolean reverse);
	public Set<CasesVO> findByCaseProductNums(Integer... cpnos);
	public Set<CasesVO> findByShopProductNums(Integer... spnos);
	
	public Set<CasesVO> findByLocations(Integer... locnos);
	public Set<CasesVO> findByStatuses(Integer... statuses);
	public Set<CasesVO> findExcludeStatuses(Integer... statuses);
	public Set<CasesVO> findByTitleKeyword(String keyword, Integer locnoFrom, Integer locnoTo);
	public Set<CasesVO> findByTimeInterval(Timestamp stimefrom, Timestamp stimeto, Timestamp etimefrom, Timestamp etimeto);
	
	public Set<CasesVO> findByWhere(ColumnValue... whereArgs);
	public Set<CasesVO> findByWhere(boolean useAnd, ColumnValue... whereArgs);
	public Set<CasesVO> findByCompositeQuery(ColumnValueBundle... whereArgsBundle);
	public Set<CasesVO> findByCompositeQuery(boolean matchCondition, ColumnValueBundle... whereArgsBundle);

	
	
}
