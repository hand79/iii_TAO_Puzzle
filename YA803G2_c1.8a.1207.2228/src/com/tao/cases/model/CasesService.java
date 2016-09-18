package com.tao.cases.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tao.hibernate.jimmy.CasesHibernateDAO;
import com.tao.jimmy.util.model.ColumnValue;
import com.tao.jimmy.util.model.ColumnValueBundle;
import com.tao.jimmy.util.model.Compare;
import com.tao.order.model.OrderVO;

public class CasesService {
	private CasesDAO dao;
	private CasesHibernateDAO hdao;

	public CasesService() {
		super();
		dao = new CasesDAOImpl();
		hdao = new CasesHibernateDAO();
	}

	public Integer addCase(String title, Integer memno, Integer cpno,
			Integer spno, Integer locno, Integer discount, Integer minqty,
			Integer maxqty, String casedesc, String ship1, Integer shipcost1,
			String ship2, Integer shipcost2, Integer threshold) {

		CasesVO vo = new CasesVO();
		vo.setTitle(title);
		vo.setMemno(memno);
		vo.setCpno(cpno);
		vo.setSpno(spno);
		vo.setLocno(locno);
		vo.setDiscount(discount);
		vo.setMinqty(minqty);
		vo.setMaxqty(maxqty);
		vo.setCasedesc(casedesc);
		vo.setShip1(ship1);
		vo.setShipcost1(shipcost1);
		vo.setShip2(ship2);
		vo.setShipcost2(shipcost2);
		vo.setThreshold(threshold);
		Integer genKey = dao.insert(vo);
		return genKey;
	};

	public int editCase(Integer caseno, String title, Integer locno,
			Integer discount, Integer minqty, Integer maxqty, String casedesc,
			String ship1, Integer shipcost1, String ship2, Integer shipcost2,
			Integer threshold) {
		CasesVO vo = new CasesVO();
		vo.setCaseno(caseno);
		vo.setTitle(title);
		vo.setLocno(locno);
		vo.setDiscount(discount);
		vo.setMinqty(minqty);
		vo.setMaxqty(maxqty);
		vo.setCasedesc(casedesc);
		vo.setShip1(ship1);
		vo.setShipcost1(shipcost1);
		vo.setShip2(ship2);
		vo.setShipcost2(shipcost2);
		vo.setThreshold(threshold);
		int updateCount = dao.updateByEdit(vo);
		return updateCount;
	};

	public int updateCaseStatus(Integer caseno, Integer status) {
		if (caseno == null)
			caseno = new Integer(0);
		if (status == null) {
			throw new IllegalArgumentException(
					"Invalid status: should not be null");
		} else {
			switch (status) {
			case CasesVO.STATUS_CREATED:
			case CasesVO.STATUS_PRIVATE:
			case CasesVO.STATUS_PUBLIC:
			case CasesVO.STATUS_OVER:
			case CasesVO.STATUS_COMPLETED:
			case CasesVO.STATUS_CANCELED:
			case CasesVO.STATUS_DELETED:
				break;
			default:
				throw new IllegalArgumentException(
						"Invalid status: try use constants defined in CasesVO");
			}
		}
		int updateCount = dao.updateStatus(caseno, status);
		return updateCount;
	};

	public CasesVO getByPrimaryKey(Integer caseno) {
		CasesVO vo = dao.findByPrimaryKey(caseno);
		return vo;
	};

	public Set<CasesVO> getAll() {
		Set<CasesVO> set = dao.getAll();
		return set;
	};

	@Deprecated
	public Set<CasesVO> getByCreator(Integer memno) {
		Set<CasesVO> set = dao.findByCreator(memno);
		return set;
	};

	public Set<CasesVO> getByCreator(Integer memno, boolean includeCanceled,
			boolean includeDeleted) {
		ColumnValue[] cvs = new ColumnValue[3];
		cvs[0] = new ColumnValue("memno", memno.toString());

		if (!includeCanceled) {
			cvs[1] = new ColumnValue("status",
					Integer.toString(CasesVO.STATUS_CANCELED), Compare.NotEqual);
		}
		if (!includeDeleted) {
			cvs[2] = new ColumnValue("status",
					Integer.toString(CasesVO.STATUS_DELETED), Compare.NotEqual);
		}
		Set<CasesVO> set = dao.findByWhere(cvs);
		return set;
	};

	public Set<CasesVO> getByCaseProductNums(Integer cpnos) {
		Set<CasesVO> set = dao.findByCaseProductNums(cpnos);
		return set;
	}

	public Set<CasesVO> getByShopProductNums(Integer... spnos) {
		Set<CasesVO> set = dao.findByShopProductNums(spnos);
		return set;
	};

	public Set<CasesVO> getByLocations(Integer... locnos) {
		Set<CasesVO> set = dao.findByLocations(locnos);
		return set;
	}

	public Set<CasesVO> getByStatuses(Integer... statuses) {
		Set<CasesVO> set = dao.findByStatuses(statuses);
		return set;
	}

	public Set<CasesVO> getExcpetStatuses(Integer... statuses) {
		Set<CasesVO> set = dao.findExcludeStatuses(statuses);
		return set;
	}

	public Set<CasesVO> getByTitleKeyword(String keyword, Integer locnoFrom, Integer locnoTo) {
		Set<CasesVO> set = dao.findByTitleKeyword(keyword, locnoFrom, locnoTo);
		return set;
	}

	public Set<CasesVO> getByTimeInterval(Timestamp stimefrom,
			Timestamp stimeto, Timestamp etimefrom, Timestamp etimeto) {
		Set<CasesVO> set = dao.findByTimeInterval(stimefrom, stimeto,
				etimefrom, etimeto);
		return set;
	}

	public Set<CasesVO> compositeQuery(ColumnValue... whereArgs) {
		Set<CasesVO> Set = dao.findByWhere(whereArgs);
		return Set;
	}

	//
	// public Set<CasesVO> compositeQuery(boolean useAnd, ColumnValue...
	// whereArgs) {
	// Set<CasesVO> Set = dao.findByWhere(useAnd, whereArgs);
	// return Set;
	// }

	public Set<CasesVO> getAllByOrderList(List<OrderVO> list) {

		List<ColumnValue> whereArgList = new ArrayList<>();
		for (OrderVO vo : list) {
			whereArgList.add(new ColumnValue("caseno", vo.getCaseno()
					.toString()));
		}

		Set<CasesVO> set = dao.findByWhere(false,
				whereArgList.toArray(new ColumnValue[whereArgList.size()]));
		return set;
	}

	public Set<CasesVO> compositeQuery(ColumnValueBundle[] whereArgsBundle) {
		Set<CasesVO> set = dao.findByCompositeQuery(whereArgsBundle);
		return set;
	}

	public Set<CasesVO> compositeQuery(boolean matchCondition,
			ColumnValueBundle[] whereArgsBundle) {
		Set<CasesVO> Set = dao.findByCompositeQuery(matchCondition,
				whereArgsBundle);
		return Set;
	}

	public com.tao.hibernate.jimmy.CasesVO getByPKAssociated(Integer caseno) {
		return hdao.findByPrimaryKey(caseno);
	}

	// key = caseno, value = hit change
	public int updateCasesHits(Map<Integer, Integer> contextHitsRecord) {
		return dao.updateHits(contextHitsRecord);
	}

	public boolean overCase(Integer caseno) {
		return hdao.overCases(caseno);
	}

	public boolean cancelCase(Integer caseno) {
		return hdao.cancelCase(caseno);
	}

	public int openCases(Integer caseno, Timestamp etime, boolean isPrivate) {
		return dao.openCases(caseno, new Timestamp(System.currentTimeMillis()),
				etime, isPrivate);
	}
	
	public Set<CasesVO> findByAreaSearch(Integer locnoFrom, Integer locnoTo,
			Integer subcatno){
		return dao.findByAreaSearch(locnoFrom, locnoTo, subcatno);
	}
}
