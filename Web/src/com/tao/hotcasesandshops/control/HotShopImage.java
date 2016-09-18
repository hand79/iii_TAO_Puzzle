package com.tao.hotcasesandshops.control;

import java.io.*;
import java.sql.*;

import javax.naming.Context;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;

import com.tao.hotcasesandshops.model.HotCaseService;
import com.tao.hotcasesandshops.model.HotShopService;
import com.tao.hotcasesandshops.model.HotShopVO;
import com.tao.util.model.DataSourceHolder;

public class HotShopImage extends HttpServlet {

	private static DataSource ds = DataSourceHolder.getDataSource();

	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		req.setCharacterEncoding("UTF-8");
		String shopno = req.getParameter("shopno");

		if (shopno == null) {
			return;
		}
		Integer number = null;
		try {
			number = new Integer(shopno);
		} catch (Exception e) {
			return;
		}
		HotShopService hotShopSvc = new HotShopService();
		HotShopVO hotShopVO = hotShopSvc.findByIsrecommPic(number);

		if (hotShopVO != null) {
			if (hotShopVO.getMime() != null) {
				res.setContentType(hotShopVO.getMime());
				ServletOutputStream out = res.getOutputStream();
				byte[] pic = hotShopVO.getPic();
				if (pic != null) {
					out.write(pic);
				}
			} 
		}else {
			ServletOutputStream out = res.getOutputStream();
			FileInputStream in = new FileInputStream(getServletContext()
					.getRealPath("/f/res/images/Logo_1.png"));
			byte[] buffer = new byte[in.available()];
			// int length = 0;
			in.read(buffer);
			out.write(buffer);
			in.close();
		}

	}
}
