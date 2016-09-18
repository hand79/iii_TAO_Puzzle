package com.tao.searchresult.controller;



import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tao.shop.model.ShopService;
import com.tao.shop.model.ShopVO;

public class DBShopImgReader extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		res.setContentType("image/jpeg");
		ServletOutputStream out = res.getOutputStream();

		Integer shopno = Integer.parseInt(req.getParameter("shopno"));
		
		ShopService ss = new ShopService();
		ShopVO sVO = ss.getOneShop(shopno);
		out.write(sVO.getPic());
	}	
		
}
