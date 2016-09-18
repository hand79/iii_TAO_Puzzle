package com.tao.order.model;

import java.sql.Timestamp;
import java.util.List;

import oracle.net.aso.h;

import com.tao.hibernate.jimmy.OrderHibernateDAO;
import com.tao.jimmy.util.model.ColumnValue;
import com.tao.jimmy.util.model.ColumnValueBundle;
import com.tao.jimmy.util.model.Compare;

public class OrderService {
	private OrderDAO dao;
	private OrderHibernateDAO hdao; 

	public OrderService() {
		super();
		dao = new OrderDAOImpl();
		hdao = new OrderHibernateDAO();
	}

	public Integer addOrder(Integer bmemno, Integer cmemno, Integer caseno,
			Integer qty, Integer price, Timestamp ordtime, Integer ship) {
		if(ship != 1 && ship != 2 ){
			throw new IllegalArgumentException("The argument 'ship' should be only 1 or 2");
		}
		
		OrderVO vo = new OrderVO();
		vo.setBmemno(bmemno);
		vo.setCmemno(cmemno);
		vo.setCaseno(caseno);
		vo.setQty(qty);
		vo.setPrice(price);
		vo.setOrdtime(ordtime);
		vo.setShip(ship);

		Integer genKey = dao.insert(vo);
		return genKey;
	};

	public int deleteOrder(Integer ordno) {
		int updateCount = dao.cancelOrder(ordno);
		return updateCount;
	};

	public int updateStatus(Integer ordno, Integer status) {
		int updateCount = hdao.updateStatus(ordno, status);
		return updateCount;
	};

	public int updateBuyerRate(Integer brate, String bratedesc, Integer ordno) {
		int updateCount = dao.updateBRate(brate, bratedesc, ordno);
		return updateCount;
	};

	public int updateCreatorRate(Integer crate, String cratedesc, Integer ordno) {
		int updateCount = dao.updateCRate(crate, cratedesc, ordno);
		return updateCount;
	};

	public OrderVO findByPrimaryKey(Integer ordno) {
		OrderVO vo = dao.findByPrimaryKey(ordno);
		return vo;
	};

	public List<OrderVO> findByBuyer(Integer memno) {
		List<OrderVO> list = dao.findByBuyer(memno, true, null);
		return list;
	};
	
	public List<OrderVO> findByBuyerNotCanceled(Integer memno) {
		List<OrderVO> list = dao.findByBuyer(memno, false, OrderDAO.STATUS_CANCELED);
		return list;
	};

	public List<OrderVO> findByCreator(Integer cemno) {
		List<OrderVO> list = dao.findByCreator(cemno);
		return list;
	};

	public List<OrderVO> findByCase(Integer caseno) {
		List<OrderVO> list = dao.findByCase(caseno);
		return list;
	};

	public List<OrderVO> findByCase(Integer caseno, Integer status) {
		List<OrderVO> list = dao.findByCase(caseno, status ,true);
		return list;
	};
	public List<OrderVO> findByCaseExcludeStatus(Integer caseno, Integer status) {
		List<OrderVO> list = dao.findByCase(caseno, status, false);
		return list;
	};

	public List<OrderVO> findByTimeInterval(Timestamp from) {
		List<OrderVO> list = dao.findByTimeInterval(from);
		return list;
	};

	public List<OrderVO> findByTimeInterval(Timestamp from, Timestamp to) {
		List<OrderVO> list = dao.findByTimeInterval(from, to);
		return list;
	};

	public List<OrderVO> findByStatus(Integer status) {
		List<OrderVO> list = dao.findByStatus(status);

		return list;
	};

	public List<OrderVO> compositeQuery(ColumnValue... whereArgs) {
		List<OrderVO> list = dao.findByWhere(whereArgs);
		return list;
	}

	public List<OrderVO> getAll() {
		List<OrderVO> list = dao.getAll();
		return list;
	};

	public List<OrderVO> findByCompositeQuery(ColumnValueBundle... whereArgs) {
		List<OrderVO> list = dao.findByCompositeQuery(whereArgs);
		return list;
	}
	public List<OrderVO> findByCompositeQuery(boolean matchCondition, ColumnValueBundle... whereArgs) {
		List<OrderVO> list = dao.findByCompositeQuery(matchCondition, whereArgs);
		return list;
	}
	public Integer getTotalOrderQty(Integer caseno){
		return dao.getTotalOrderQty(caseno, OrderDAO.STATUS_CANCELED);
	}
	
	public int cancelOrder(Integer ordno){
		return hdao.cancelOrder(ordno);
	}
	
	public int finishOrder(List<OrderVO> list){
		return dao.finishOrders(list);
	}
	
}
