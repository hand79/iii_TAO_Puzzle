package com.tao.cases.controller;

import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tao.cases.model.CaseProductService;
import com.tao.cases.model.CaseProductVO;
import com.tao.jimmy.util.ImageUtil;

public class CaseProductPictureProvider extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CaseProductService service;

	public CaseProductPictureProvider() {
		super();
		service = new CaseProductService();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String cpno = request.getParameter("cpno");
		String picNum = request.getParameter("pic");
		if (cpno == null || picNum == null || !cpno.matches("\\d{4,}")
				|| !picNum.matches("[1-3]")) {
			return;
		}
		CaseProductVO vo = service.getOneByPrimaryKeyHasPic(new Integer(cpno));
		if(vo == null){
			return;
		}
		byte[] desiredPic = null;
		String picMime = null;
		switch (Integer.parseInt(picNum)) {
		case 1:
			desiredPic = vo.getPic1();
			picMime = vo.getPmime1();
			break;
		case 2:
			desiredPic = vo.getPic2();
			picMime = vo.getPmime2();
			break;
		case 3:
			desiredPic = vo.getPic3();
			picMime = vo.getPmime3();
			break;
		}
		if (desiredPic == null || picMime == null) {
				ServletOutputStream out = response.getOutputStream();
				FileInputStream in = new FileInputStream(getServletContext()
						.getRealPath("/f/res/images/Logo_1.png"));
				byte[] buffer = new byte[in.available()];
				// int length = 0;
				in.read(buffer);
				out.write(buffer);
				in.close();
			return;
		}
		String resizeStr = request.getParameter("resize");
		if(resizeStr != null && resizeStr.matches("\\d+")){
			int resize = Integer.parseInt(resizeStr);
			desiredPic = ImageUtil.shrink(desiredPic, resize);			
		}
		
		response.setContentType(picMime);
		ServletOutputStream out = response.getOutputStream();
		out.write(desiredPic);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
