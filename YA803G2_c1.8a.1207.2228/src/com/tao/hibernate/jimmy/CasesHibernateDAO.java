package com.tao.hibernate.jimmy;

import java.sql.Timestamp;
import java.util.Date;

import org.hibernate.Session;

import com.tao.hibernate.jimmy.CasesVO;
import com.tao.hibernate.util.HibernateUtil;

public class CasesHibernateDAO {

	public boolean deleteCaseByAdmin(Integer caseno) {
		boolean success = false;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			CasesVO cvo = (CasesVO) session.get(CasesVO.class, caseno);
			for (OrderVO ovo : cvo.getOrders()) {
				int oStatus = ovo.getStatus().intValue();
				if (oStatus == OrderVO.STATUS_CREATED) {
					int price = ovo.getPrice();
					ovo.setStatus(OrderVO.STATUS_CANCELED);
					MemberVO bmemvo = ovo.getBmemvo();
					bmemvo.setWithhold(bmemvo.getWithhold() - price);
					bmemvo.setMoney(bmemvo.getMoney() + price);
				}
			}
			cvo.setStatus(CasesVO.STATUS_DELETED);
			session.getTransaction().commit();
			success = true;
		} catch (RuntimeException re) {
			session.getTransaction().rollback();
			success = false;
		}
		return success;

	}

	public boolean cancelCase(Integer caseno) {
		boolean success = false;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			CasesVO cvo = (CasesVO) session.get(CasesVO.class, caseno);
			int status = cvo.getStatus().intValue();
			if (status == CasesVO.STATUS_PRIVATE
					|| status == CasesVO.STATUS_PUBLIC
					|| status == CasesVO.STATUS_OVER) {
				for (OrderVO ovo : cvo.getOrders()) {
					int oStatus = ovo.getStatus().intValue();
					if (oStatus == OrderVO.STATUS_CREATED
							|| oStatus == OrderVO.STATUS_ACHIEVED) {
						int price = ovo.getPrice();
						ovo.setStatus(OrderVO.STATUS_CANCELED);
						MemberVO bmemvo = ovo.getBmemvo();
						bmemvo.setWithhold(bmemvo.getWithhold() - price);
						bmemvo.setMoney(bmemvo.getMoney() + price);
					}// end if
				}// end of for loop
				cvo.setStatus(CasesVO.STATUS_CANCELED);
			}// end if
			session.getTransaction().commit();
			success = true;
		} catch (RuntimeException re) {
			session.getTransaction().rollback();
			success = false;
		}
		return success;
	}

	public boolean overCases(Integer caseno) {
		boolean success = false;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			CasesVO cvo = (CasesVO) session.get(CasesVO.class, caseno);
			for (OrderVO ovo : cvo.getOrders()) {
				ovo.setStatus(OrderVO.STATUS_ACHIEVED);
			}
			cvo.setStatus(CasesVO.STATUS_OVER);
			Date now = new Date();
			if(cvo.getEtime().after(now)){
				cvo.setEtime(new Timestamp(now.getTime()));
			}
			session.getTransaction().commit();
			success = true;
		} catch (RuntimeException re) {
			session.getTransaction().rollback();
		}

		return success;
	}

	public CasesVO findByPrimaryKey(Integer caseno) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		CasesVO cvo = null;
		try {
			session.beginTransaction();
			cvo = (CasesVO) session.get(CasesVO.class, caseno);
			session.getTransaction().commit();
		} catch (RuntimeException re) {
			session.getTransaction().rollback();
		}
		return cvo;
	}

}
