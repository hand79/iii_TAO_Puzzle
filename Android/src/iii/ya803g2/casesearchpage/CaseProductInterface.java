package iii.ya803g2.casesearchpage;

import java.util.List;

public interface CaseProductInterface {
	public Integer insert(CaseProductVO cpVO);
//	public int update(CaseProductVO cpVO);
    public int updateByEdit(CaseProductVO cpVO);
    public int delete(Integer cpno);
    
    public CaseProductVO findByPrimaryKey(Integer cpno);
	public CaseProductVO findByPrimaryKey(Integer cpno, boolean hasPic);
	public List<CaseProductVO> findByOwnerNo(Integer memno);
	public List<CaseProductVO> findByOwnerNo(Integer memno, boolean hasPic);
	
	public List<CaseProductVO> getAll();
	public List<CaseProductVO> getAll(boolean hasPic);
}
