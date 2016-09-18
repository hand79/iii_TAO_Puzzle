package com.tao.memberrunning.control;

import java.io.*;
import java.sql.*;

import javax.naming.Context;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;

import com.tao.runningad.model.RunningAdService;
import com.tao.runningad.model.RunningAdVO;
import com.tao.util.model.DataSourceHolder;

public class RunningImage extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		ServletOutputStream out = res.getOutputStream();
		String adnost = req.getParameter("adno");
		
		if(adnost==null){
			return;
		}
		Integer adno=null;
		
		try {
			adno = new Integer(adnost);
		} catch (Exception e) {
			return;
		}
		
		RunningAdService adService = new RunningAdService();
		RunningAdVO adVO = new RunningAdVO();
		adVO = adService.findByPrimaryKey(adno);
		if(adVO==null){
			return;
		}
		res.setContentType(adVO.getMime());
		
		if(adVO.getAdpic()==null){
			return;
		}else {
			out.write(adVO.getAdpic());
		}
		
	}
	


}
