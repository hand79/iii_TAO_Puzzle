package com.tao.runningad.controller;



import java.io.*;
import java.sql.*;

import javax.naming.Context;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;

import com.tao.runningad.model.RunningAdService;
import com.tao.runningad.model.RunningAdVO;
import com.tao.util.model.DataSourceHolder;

public class DBRunningAdImgReader extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		

		Integer adno = Integer.parseInt(req.getParameter("adno"));
		
		RunningAdService ras = new RunningAdService();
		RunningAdVO raVO = ras.findByPrimaryKey(adno);
		req.setCharacterEncoding("UTF-8");
		res.setContentType(raVO.getMime());
		ServletOutputStream out = res.getOutputStream();
		out.write(raVO.getAdpic());
	}	
		
}
