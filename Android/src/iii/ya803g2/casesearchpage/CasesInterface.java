package iii.ya803g2.casesearchpage;



import java.sql.Timestamp;
import java.util.Set;
 

 
public interface CasesInterface {
//  public static final int STATUS_CREATED = 0;
//  public static final int STATUS_PUBLIC = 1;
//  public static final int STATUS_PRIVATE = 2;
//  public static final int STATUS_OVER = 3;
//  public static final int STATUS_COMPLETED = 4;
//  public static final int STATUS_DELETED = 5;
 
    public Integer insert(CasesVO vo);
    public int update(CasesVO vo);
    public int delete(Integer caseno);
 
    public int updateStatus(Integer caseno, Integer status);
//  public int updateByEdit(CaseVO vo);
 
    public CasesVO findByPrimaryKey(Integer caseno);
    public Set<CasesVO> getAll();
 
    public Set<CasesVO> findByCreator(Integer memno);
    public Set<CasesVO> findByCaseProductNums(Integer... cpnos);
    public Set<CasesVO> findByShopProductNums(Integer... spnos);
     
    public Set<CasesVO> findByLocations(Integer... locnos);
    public Set<CasesVO> findByStatuses(Integer... statuses);
    public Set<CasesVO> findExcludeStatuses(Integer... statuses);
    public Set<CasesVO> findByTitleKeyword(String keyword);
    public Set<CasesVO> findByTimeInterval(Timestamp stimefrom, Timestamp stimeto, Timestamp etimefrom, Timestamp etimeto);
     
   
     
}