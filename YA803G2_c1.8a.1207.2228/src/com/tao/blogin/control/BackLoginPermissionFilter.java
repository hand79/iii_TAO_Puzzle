package com.tao.blogin.control;

import java.io.IOException;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tao.acc.model.AccountVO;
import com.tao.acc.model.PerListService;
import com.tao.acc.model.PerListVO;

public class BackLoginPermissionFilter implements Filter {

//	private FilterConfig config;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
//		this.config = filterConfig;
	}

	public void destroy() {
		// TODO Auto-generated method stub
//		config = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();


		Set<PerListVO> perList = (Set<PerListVO>) session
				.getAttribute("bAccountPerlist");

		boolean[] permissionFlagArray = { false, false, false, false, false,
				false, false };
		for (PerListVO vo : perList) {
			// index starts from 0, while permission num starts form 1
			permissionFlagArray[vo.getPerno() - 1] = true;
		}
		session.setAttribute("permissionFlagArray", permissionFlagArray);

		boolean success = true;

		if ((req.getRequestURI().endsWith("/category/Category.jsp"))
				&& !permissionFlagArray[1 - 1]) {
			success = false;
		}
		if ((req.getRequestURI().endsWith("/account/Account.jsp"))
				&& !permissionFlagArray[4 - 1]) {
			success = false;
		}
		if ((req.getRequestURI().endsWith("/order/ManageOrders.jsp") || req
				.getRequestURI().endsWith("/cases/ManageCases.jsp"))
				&& !permissionFlagArray[7 - 1]) {
			success = false;
		}

		if (!success) {
			res.sendRedirect(req.getContextPath() + "/b/backindex.jsp");
			return;
		} else {
			chain.doFilter(request, response);
		}

	}

}
