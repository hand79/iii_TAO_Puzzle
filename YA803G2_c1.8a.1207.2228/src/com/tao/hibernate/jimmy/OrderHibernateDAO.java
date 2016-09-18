package com.tao.hibernate.jimmy;

import org.hibernate.Session;

import com.tao.hibernate.util.HibernateUtil;

public class OrderHibernateDAO {

	public int updateStatus(Integer ordno, Integer status) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		int updateCount = 0;
		try {
			session.beginTransaction();
			OrderVO ovo = (OrderVO) session.get(OrderVO.class, ordno);
			int ordSts = ovo.getStatus();
			if ((status == OrderVO.STATUS_BUYER_COMFIRMED && ordSts == OrderVO.STATUS_CREATOR_COMFIRMED)
					|| (ordSts == OrderVO.STATUS_BUYER_COMFIRMED && status == OrderVO.STATUS_CREATOR_COMFIRMED)) {
				status = OrderVO.STATUS_BOTH_COMFIRMED;
				CasesVO cvo = ovo.getCasevo();
				if (cvo.getStatus() == CasesVO.STATUS_OVER) {
					cvo.setStatus(CasesVO.STATUS_COMPLETED);
				}
			}
			ovo.setStatus(status);
			session.getTransaction().commit();
			updateCount = 1;
		} catch (RuntimeException re) {
			session.getTransaction().rollback();
		}

		return updateCount;
	}

	public int cancelOrder(Integer ordno) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		int updateCount = 0;
		try {
			session.beginTransaction();

			OrderVO ovo = (OrderVO) session.get(OrderVO.class, ordno);
			if (ovo.getStatus() != OrderVO.STATUS_CREATED
					&& ovo.getStatus() != OrderVO.STATUS_CONFLICT) {
				System.out
						.println("cancel order failed, status is neither STATUS_CREATED nor STATUS_CONFLICT");
				throw new RuntimeException();
			}
			MemberVO memvo = ovo.getBmemvo();
			int price = ovo.getPrice();
			memvo.setMoney(memvo.getMoney() + price);
			memvo.setWithhold(memvo.getWithhold() - price);
			ovo.setStatus(OrderVO.STATUS_CANCELED);
			session.getTransaction().commit();
			updateCount = 1;
		} catch (RuntimeException re) {
			session.getTransaction().rollback();
		}

		return updateCount;

	}
	/*
	public int finishOrders() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		int updateCount = 0;
		try {
			session.beginTransaction();
			Query query = session.createQuery("FROM OrderVO WHERE status=?");
			query.setParameter(1, OrderVO.STATUS_BOTH_COMFIRMED);
			List<OrderVO> list = query.list();
			for (OrderVO vo : list) {
				int price = vo.getPrice();
				MemberVO cmemvo = vo.getCmemvo();
				MemberVO bmemvo = vo.getBmemvo();
				cmemvo.setMoney(cmemvo.getMoney() + price);
				bmemvo.setWithhold(bmemvo.getWithhold() - price);
				vo.setStatus(OrderVO.STATUS_COMPLETED);
			}
			session.getTransaction().commit();
			updateCount = 1;
		} catch (RuntimeException re) {
			session.getTransaction().rollback();
		}

		return updateCount;

	}*/
}
