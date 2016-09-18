package com.tao.member.controller;



import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.tao.member.model.MemberService;
import com.tao.member.model.MemberVO;

public class DBImgReader extends HttpServlet {


	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		
		Integer memno = Integer.parseInt(req.getParameter("memno"));

		MemberService ms = new MemberService();
		MemberVO memVO = ms.findByPrimaryKey(memno);
//		req.setCharacterEncoding("UTF-8");
		
		ServletOutputStream out = res.getOutputStream();
		if(memVO.getPhoto() != null){
			String mime = memVO.getMime();
			if(mime == null){
				mime = "image/*";
			}
			res.setContentType(mime);
			out.write(memVO.getPhoto());
		}else{
			res.setContentType("image/jpeg");
//			String path = memberVO.getType() == 0 ? getServletContext().getRealPath("/f/res/images/member/n_default.jpg") : getServletContext().getRealPath("/f/res/images/member/s_default.jpg") ;
			String path = memVO.getType() == 0 ? "/f/res/images/member/n_default.jpg" : "/f/res/images/member/s_default.jpg" ;
			req.getRequestDispatcher(path).forward(req, res);
//			res.setContentType("image/jpeg");
//			String path = memVO.getType() == 0 ? getServletContext().getRealPath("/f/res/images/member/member.jpg") : getServletContext().getRealPath("/f/res/images/member/shopMember.jpg") ;
////			System.out.println(path);
//			FileInputStream in = new FileInputStream(path);
//			byte[] buffer = new byte[in.available()];
//			in.read(buffer);
//			out.write(buffer);
//			in.close();
		}
	}
}
