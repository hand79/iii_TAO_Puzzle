package com.tao.surfmemberinfo.control;

import java.io.*;
import java.sql.*;

import javax.naming.Context;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;

import com.tao.member.model.MemberService;
import com.tao.member.model.MemberVO;
import com.tao.util.model.DataSourceHolder;

public class MemberInfoImage extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		req.setCharacterEncoding("UTF-8");
		
		ServletOutputStream out = res.getOutputStream();
		String memnost = req.getParameter("memno");
		if(memnost==null){
			return;
		}
		Integer memno =null;
		try {
			memno = new Integer(memnost);
		} catch (Exception e) {
			// TODO: handle exception
			return;
		}
		
		MemberService memberSvc = new MemberService();
		MemberVO memberVO = memberSvc.findByPrimaryKey(memno);
//		res.setContentType(memberVO.getMime());
		
		if(memberVO.getMime() != null){
			res.setContentType(memberVO.getMime());
			out.write(memberVO.getPhoto());
		}else{
			res.setContentType("image/jpeg");
//			String path = memberVO.getType() == 0 ? getServletContext().getRealPath("/f/res/images/member/n_default.jpg") : getServletContext().getRealPath("/f/res/images/member/s_default.jpg") ;
			String path = memberVO.getType() == 0 ? "/f/res/images/member/n_default.jpg" : "/f/res/images/member/s_default.jpg" ;
			req.getRequestDispatcher(path).forward(req, res);
			
//			System.out.println(path);
//			FileInputStream in = new FileInputStream(path);
//			byte[] buffer = new byte[in.available()];
//			in.read(buffer);
//			out.write(buffer);
//			in.close();
		}
		
	}
	
}
