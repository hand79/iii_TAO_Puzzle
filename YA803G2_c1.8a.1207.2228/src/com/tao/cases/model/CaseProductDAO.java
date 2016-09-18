package com.tao.cases.model;

import java.util.List;

public interface CaseProductDAO {
	public Integer insert(CaseProductVO cpVO);
    public int updateByEdit(CaseProductVO cpVO);
    public int delete(Integer cpno);
    
    public int updateLockflagToLockIt(Integer cpno);
    @Deprecated
    public CaseProductVO findByPrimaryKey(Integer cpno);
	public CaseProductVO findByPrimaryKey(Integer cpno, boolean hasPic);
	@Deprecated
	public List<CaseProductVO> findByOwnerNo(Integer memno);
	public List<CaseProductVO> findByOwnerNo(Integer memno, boolean hasPic);
	
	@Deprecated
	public List<CaseProductVO> getAll();
	public List<CaseProductVO> getAll(boolean hasPic);
	
}
