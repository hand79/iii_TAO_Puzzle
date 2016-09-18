package com.tao.hotcasesandshops.control;

import java.io.*;
import java.sql.*;

import javax.naming.Context;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;

import com.tao.cases.model.CaseProductService;
import com.tao.cases.model.CaseProductVO;
import com.tao.cases.model.CasesService;
import com.tao.cases.model.CasesVO;
import com.tao.hotcasesandshops.model.HotCaseService;
import com.tao.hotcasesandshops.model.HotCaseVO;
import com.tao.shopproduct.model.ShopproductService;
import com.tao.shopproduct.model.ShopproductVO;
import com.tao.util.model.DataSourceHolder;

public class HotCaseImage extends HttpServlet {

	private static DataSource ds = DataSourceHolder.getDataSource();

	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("UTF-8");

		String caseno = req.getParameter("caseno");
		if (caseno == null) {
			return;
		}
		Integer number = null;
		boolean success = true;
		
		try {
			number = new Integer(caseno);
		} catch (Exception e) {
			success = false;
		}
		byte[] pic = null;
		String mime = null;
		CasesVO cvo = new CasesService().getByPrimaryKey(number);
		if(cvo == null){
			success= false;
		}else{
			if(cvo.getCpno() != 0 ){
				CaseProductVO cpvo = new CaseProductService().getOneByPrimaryKeyHasPic(cvo.getCpno());
				pic = cpvo.getPic1();
				mime = cpvo.getPmime1();
			}	
			if(cvo.getSpno() != 0 ){
				ShopproductVO spvo = new ShopproductService().getOneShopproduct(cvo.getSpno());
				pic = spvo.getPic1();
				mime = spvo.getPmime1();
			}
			if(pic == null){
				success = false;
			}else if(mime == null){
				mime = "image/*";
			}
		}
		
		if(success){
			res.setContentType(mime);
			res.getOutputStream().write(pic);
		}else{
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
