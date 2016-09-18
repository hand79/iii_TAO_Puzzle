package com.tao.order.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import com.tao.jimmy.util.model.ColumnValue;
import com.tao.jimmy.util.model.ColumnValueBundle;
import com.tao.jimmy.util.model.Compare;

public interface OrderDAO {
	public static final int STATUS_CREATED = 0;
	public static final int STATUS_ACHIEVED = 1;
	public static final int STATUS_BUYER_COMFIRMED = 2;
	public static final int STATUS_CREATOR_COMFIRMED = 3;
	public static final int STATUS_CONFLICT = 4;
	public static final int STATUS_COMPLETED = 5;
	public static final int STATUS_CANCELED = 9;
	public static final int STATUS_BOTH_COMFIRMED = 6;
	
	public Integer insert(OrderVO ordvo);
//	public int update(OrderVO ordvo);
	public int cancelOrder(Integer ordno);
	
	public int updateStatus(Integer ordno, Integer status);
	public int updateBRate(Integer brate, String bratedesc, Integer ordno);
	public int updateCRate(Integer crate, String cratedesc, Integer ordno);
	public int finishOrders(List<OrderVO> list);
	
	public void updateStatusByCaseOver(Integer caseno, Connection con) throws SQLException;
	public void cancelOrderByCaseCancel(Integer caseno, Connection con) throws SQLException;
	public OrderVO findByPrimaryKey(Integer ordno);
	
	public List<OrderVO> findByBuyer(Integer memno, boolean match ,int... filter);
	public List<OrderVO> findByCreator(Integer cemno);
	
	public List<OrderVO> findByCase(Integer caseno);
	public List<OrderVO> findByCase(Integer caseno, Integer status, boolean matchStatus);
	
	public List<OrderVO> findByTimeInterval(Timestamp from);
	public List<OrderVO> findByTimeInterval(Timestamp from, Timestamp to);
	
	public List<OrderVO> findByStatus(Integer status);
	
	public List<OrderVO> findByPrice(Integer price, Compare c);/**** Added *****/
	public List<OrderVO> findByWhere(ColumnValue... whereArgs);
	public List<OrderVO> findByCompositeQuery(ColumnValueBundle... whereArgs);
	public List<OrderVO> findByCompositeQuery(boolean matchCondition, ColumnValueBundle... whereArgs);
	public Integer getTotalOrderQty(Integer caseno, Integer excludedStatus);
	
	public List<OrderVO> getAll();
}
