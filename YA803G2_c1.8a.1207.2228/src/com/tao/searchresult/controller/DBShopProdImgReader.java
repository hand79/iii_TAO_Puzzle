package com.tao.searchresult.controller;



import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tao.shopproduct.model.ShopproductService;
import com.tao.shopproduct.model.ShopproductVO;

public class DBShopProdImgReader extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		res.setContentType("image/jpeg");
		ServletOutputStream out = res.getOutputStream();

		Integer spno = Integer.parseInt(req.getParameter("spno"));
		
		ShopproductService sps = new ShopproductService();
		ShopproductVO spVO = sps.getOneShopproduct(spno);
		out.write(spVO.getPic1());
	}	
		
}
