package com.tao.surfmemberinfo.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.omg.CORBA.PUBLIC_MEMBER;

import com.google.gson.Gson;
import com.tao.cases.model.CasesService;
import com.tao.member.model.MemberService;
import com.tao.member.model.MemberVO;
import com.tao.order.model.OrderService;
import com.tao.order.model.OrderVO;

public class SurfMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action").trim();

		if ("memberView".equals(action)) {
			if ("memcenter".equals(req.getParameter("from"))) {
				if (req.getSession().getAttribute("CurrentUser") == null) {
					req.getSession().setAttribute("location",
							req.getRequestURI() + "?" + req.getQueryString());
					res.sendRedirect(req.getContextPath() + "/Login.jsp");
					return;
				}
			}
			String memnost = req.getParameter("memno").trim();

			if (memnost == null) {
				return;
			}
			Integer memno = null;
			try {
				memno = new Integer(memnost);
			} catch (Exception e) {
				return;
			}
			MemberService memberSvc = new MemberService();
			MemberVO memberVO = memberSvc.findByPrimaryKey(memno);

			if (memberVO == null) {
				return;
			}
			req.setAttribute("querymemberVO", memberVO);
			// res.sendRedirect(req.getContextPath()+"/surfmemberinfo/surfmemberinfo.jsp");
			// return;

			String url = "/surfmemberinfo/surfmemberinfo.jsp";
			if ("memcenter".equals(req.getParameter("from"))) {
				url = "/surfmemberinfo/surfmemberinfo_login.jsp";
			}
			RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllEmp.jsp
			successView.forward(req, res);
			return;
		}
		if ("queryAddData".equals(action)) {
			/*************************** �ШD�Ѽ� *****************************/
			String memnost = req.getParameter("memno").trim();
			String edata = req.getParameter("edata").trim();
			String index = req.getParameter("indexPage").trim();

			if (memnost == null) {
				return;
			}
			Integer memno = null;
			try {
				memno = new Integer(memnost);
			} catch (Exception e) {
				return;
			}

			MemberService memberSvc = new MemberService();
			MemberVO memberVO = memberSvc.findByPrimaryKey(memno);
			if (memberVO == null) {
				return;
			}
			/************************** ��X�|���Ҧ��[�J���ʫ᪺���� *************************************/
			OrderService orderSvc = new OrderService();
			CasesService casesSvc =new CasesService();
			List<OrderVO> listData = orderSvc.findByBuyer(memberVO.getMemno());
			Collections.reverse(listData);
			if (listData.size() <= 0) {
				return;
			}

			for (int i = listData.size() - 1; i >= 0; i--) {
				if (listData.get(i).getBrate() == null) {
					listData.remove(i);
				}
			}

			if (listData.size() <= 0) {
				return;
			}

			if ("goodAddRecord".equals(edata)) {

				for (int i = listData.size() - 1; i >= 0; i--) {

					if (listData.get(i).getBrate() != 2) {

						listData.remove(i);

					}
				}

				if (listData.size() <= 0) {
					return;
				}

			} else if ("normalAddRecord".equals(edata)) {

				for (int i = listData.size() - 1; i >= 0; i--) {
					if (listData.get(i).getBrate() != 1) {
						listData.remove(i);
					}
				}
				if (listData.size() <= 0) {
					return;
				}
			} else if ("badAddRecord".equals(edata)) {

				for (int i = listData.size() - 1; i >= 0; i--) {
					if (listData.get(i).getBrate() != 0) {
						listData.remove(i);
					}

				}
				if (listData.size() <= 0) {
					return;
				}
			}
			/************************************** �����]�w ********************************************************/
			int indexPage = Integer.parseInt(index); // �ШD�n�ĴX��

			int rowdata = 10; // �@��10��

			int pageLength = (int) Math
					.ceil((double) listData.size() / rowdata); // �ݭn��ܪ�����

			int datafirst = 0 + (indexPage - 1) * rowdata; // ��ư_�l

			int datalast = datafirst + rowdata - 1; // ��Ƶ���
			/************** ���ƧP�_ *********************************************************************************/
			if (listData.size() <= datalast) {
				datalast = listData.size() - 1;
			}
			/*****************************************************************************************************/

			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			/*********************************** table create *****************************************/
			out.println("<table class='table table-striped table-bordered table-hover table-condensed table-responsive'>");

			for (int i = datafirst; i <= datalast; i++) {
				out.println("<tr>");
				out.print("<th width='30px'>");
				if (listData.get(i).getBrate() == 2) {
					out.println("<img src='" + req.getContextPath()
							+ "/surfmemberinfo/pointimg/good.gif'>");
				} else if (listData.get(i).getBrate() == 1) {
					out.println("<img src='" + req.getContextPath()
							+ "/surfmemberinfo/pointimg/normal.gif'>");
				} else if (listData.get(i).getBrate() == 0) {
					out.println("<img src='" + req.getContextPath()
							+ "/surfmemberinfo/pointimg/bad.gif'>");
				}
				out.print("<th width='135px' colspan='1'><i class='fa fa-user'></i>�o�_��:");
			
				out.print("<a href='"
						+ req.getContextPath()
						+ "/SurfMemberServlet.do?action=memberView&memno="
						+ memberSvc.findByPrimaryKey(
								listData.get(i).getCmemno()).getMemno()
						+ "'>"
						+ memberSvc.findByPrimaryKey(
								listData.get(i).getCmemno()).getMemid()
						+ "</a></th>");
				out.print("<th width='445px' colspan='3' >�X�ʮ�:");
				out.print("<a href='"+req.getContextPath()+"/cases/cases.do?action=caseDetail&caseno="
				+listData.get(i).getCaseno()				
				+"' >");
				out.print(casesSvc.getByPrimaryKey(listData.get(i).getCaseno()).getShortenedTitle());
				
				out.print("</a></th>");
				
				out.print("<th>�U��ɶ�:" + listData.get(i).getFormatedOrdtime()
						+ "</th>");
				out.print("</tr>");
				out.print("<tr><th colspan='5'><p>");
				out.print("<a href='"
						+ req.getContextPath()
						+ "/SurfMemberServlet.do?action=memberView&memno="
						+ memberSvc.findByPrimaryKey(
								listData.get(i).getCmemno()).getMemno()
						+ "'>"
						+ memberSvc.findByPrimaryKey(
								listData.get(i).getCmemno()).getMemid()
						+ "</a>");
				out.print(" ��  <a href='"
						+ req.getContextPath()
						+ "/SurfMemberServlet.do?action=memberView&memno="
						+ memberSvc.findByPrimaryKey(
								listData.get(i).getBmemno()).getMemno()
						+ "'>"
						+ memberSvc.findByPrimaryKey(
								listData.get(i).getBmemno()).getMemid()
						+ "</a> �������G " + listData.get(i).getBrateDesc()
						+ "</p></th>");
				out.print("<th>	�q����B:" + listData.get(i).getPrice()
						+ "��	</th></tr>");
				out.print("</tr>");
			}

			out.println("</table>");
			out.println("<ul class='pagination'>");

			/******************************* �W�@������ *****************************************/
			if ((indexPage - 1) > 0) {
				out.print("<li>"
						+ "<a class='pagepre' style='cursor:pointer;' data-url='"
						+ "SurfMemberServlet.do?action=queryAddData&edata="
						+ edata + "&memno=" + memberVO.getMemno()
						+ "&indexPage=" + (indexPage - 1)

						+ "' >&laquo;</a></li>");
			} else if ((indexPage - 1) <= 0) {
				out.println("<li class='disabled'><a>&laquo;</a></li>");
			}

			/******************************** ���X ********************************************/

			/********************************* ���X�����]�w ************************************************/
			int page = 0; // ���X�}�l
			int pagecontol = 10; // ���X�����]�w
			int lastpage = pagecontol;
			if (pageLength < pagecontol) {
				lastpage = pageLength;
			}

			if (indexPage < pagecontol && pageLength <= pagecontol) {
				lastpage = pageLength;
			} else if (indexPage > pagecontol && pageLength > pagecontol) {
				int tendigits = indexPage / pagecontol;

				page = tendigits * pagecontol;

				lastpage = page + pagecontol;
			}

			if (lastpage > pageLength) {
				lastpage = pageLength;
			}
			/********************************************************************************************/

			for (int i = page; i < lastpage; i++) {
				if (indexPage != (i + 1)) {
					out.print("<li>"
							+ "<a class='page' style='cursor:pointer;' data-url=\""
							+ "SurfMemberServlet.do?action=queryAddData&edata="
							+ edata + "&memno=" + memberVO.getMemno()
							+ "&indexPage=" + (i + 1) + "\">" + (i + 1)
							+ "</a>" + "</li>");

				} else {
					out.print("<li class='active'><a>" + (i + 1) + "</a></li>");
				}
			}
			/*******************************************************************************/

			/*************************** �U�@������ *************************************/
			if ((indexPage * rowdata) < listData.size()) {
				out.print("<li>"
						+ "<a class='pagenext' style='cursor:pointer;' data-url='"
						+ "SurfMemberServlet.do?action=queryAddData&edata="
						+ edata + "&memno=" + memberVO.getMemno()
						+ "&indexPage=" + (indexPage + 1)

						+ "' >&raquo;</a></li>");
			} else {
				out.println("<li class='disabled'><a>&raquo;</a></li>");
			}

			/*************************************************************************/
			out.println("</ul>");

		}

		if ("querylauData".equals(action)) {
			/*************************** �ШD�Ѽ� *****************************/
			String memnost = req.getParameter("memno").trim();
			String edata = req.getParameter("edata").trim();
			String index = req.getParameter("indexPage").trim();

			if (memnost == null) {
				return;
			}
			Integer memno = null;
			try {
				memno = new Integer(memnost);
			} catch (Exception e) {
				return;
			}

			MemberService memberSvc = new MemberService();
			MemberVO memberVO = memberSvc.findByPrimaryKey(memno);
			/************************** ��X�|���Ҧ��o�_���ʫ᪺���� *************************************/
			OrderService orderSvc = new OrderService();
			CasesService casesSvc=new CasesService();
			List<OrderVO> listData = orderSvc
					.findByCreator(memberVO.getMemno());
			Collections.reverse(listData);
			if (listData.size() <= 0) {
				return;
			}

			for (int i = listData.size() - 1; i >= 0; i--) {
				if (listData.get(i).getCrate() == null) {
					listData.remove(i);
				}
			}

			if (listData.size() <= 0) {
				return;
			}

			if ("goodlauRecord".equals(edata)) {

				for (int i = listData.size() - 1; i >= 0; i--) {

					if (listData.get(i).getCrate() != 2) {

						listData.remove(i);

					}
				}

				if (listData.size() <= 0) {
					return;
				}

			} else if ("normallauRecord".equals(edata)) {

				for (int i = listData.size() - 1; i >= 0; i--) {
					if (listData.get(i).getCrate() != 1) {
						listData.remove(i);
					}
				}
				if (listData.size() <= 0) {
					return;
				}
			} else if ("badlauRecord".equals(edata)) {

				for (int i = listData.size() - 1; i >= 0; i--) {
					if (listData.get(i).getCrate() != 0) {
						listData.remove(i);
					}

				}
				if (listData.size() <= 0) {
					return;
				}
			}
			/**************************************** �����]�w *****************************************************/
			int indexPage = Integer.parseInt(index);// �ШD�n�ĴX��

			int rowdata = 10;// �@��10��

			int pageLength = (int) Math
					.ceil((double) listData.size() / rowdata);// �ݭn��ܪ�����

			int datafirst = 0 + (indexPage - 1) * rowdata;// ��ư_�l

			int datalast = datafirst + rowdata - 1;// ��Ƶ���
			/*************************************** ���ƧP�_ ******************************************************/
			if (listData.size() <= datalast) {
				datalast = listData.size() - 1;
			}

			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			/************************************* table create ****************************************************************/
			out.println("<table class='table table-striped table-bordered table-hover table-condensed table-responsive'>");

			for (int i = datafirst; i <= datalast; i++) {
				out.println("<tr>");
				out.print("<th width='30px'>");
				if (listData.get(i).getCrate() == 2) {
					out.println("<img src='" + req.getContextPath()
							+ "/surfmemberinfo/pointimg/good.gif'>");
				} else if (listData.get(i).getCrate() == 1) {
					out.println("<img src='" + req.getContextPath()
							+ "/surfmemberinfo/pointimg/normal.gif'>");
				} else if (listData.get(i).getCrate() == 0) {
					out.println("<img src='" + req.getContextPath()
							+ "/surfmemberinfo/pointimg/bad.gif'>");
				}
				out.print("<th width='135px' colspan='1'><i class='fa fa-user'></i>�o�_��:");
				
				out.print("<a href='"
						+ req.getContextPath()
						+ "/SurfMemberServlet.do?action=memberView&memno="
						+ memberSvc.findByPrimaryKey(
								listData.get(i).getCmemno()).getMemno()
						+ "'>"
						+ memberSvc.findByPrimaryKey(
								listData.get(i).getCmemno()).getMemid()
						+ "</a></th>");
				out.print("<th width='445px' colspan='3' >�X�ʮ�:");
				
				out.print("<a href='"+req.getContextPath()+"/cases/cases.do?action=caseDetail&caseno="
						+listData.get(i).getCaseno()				
						+"' >");
						out.print(casesSvc.getByPrimaryKey(listData.get(i).getCaseno()).getShortenedTitle());
						
						out.print("</a></th>");
				
				
				
				out.print("<th>�U��ɶ�:" + listData.get(i).getFormatedOrdtime()
						+ "</th>");
				out.print("</tr>");
				out.print("<tr><th colspan='5'><p>");
				out.print("<a href='"
						+ req.getContextPath()
						+ "/SurfMemberServlet.do?action=memberView&memno="
						+ memberSvc.findByPrimaryKey(
								listData.get(i).getBmemno()).getMemno()
						+ "'>"
						+ memberSvc.findByPrimaryKey(
								listData.get(i).getBmemno()).getMemid()
						+ "</a>");
				out.print(" ��  <a href='"
						+ req.getContextPath()
						+ "/SurfMemberServlet.do?action=memberView&memno="
						+ memberSvc.findByPrimaryKey(
								listData.get(i).getCmemno()).getMemno()
						+ "'>"
						+ memberSvc.findByPrimaryKey(
								listData.get(i).getCmemno()).getMemid()
						+ "</a> �������G " + listData.get(i).getCrateDesc()
						+ "</p></th>");
				out.print("<th>	�q����B:" + listData.get(i).getPrice()
						+ "��	</th></tr>");
				out.print("</tr>");
			}

			out.println("</table>");
			out.println("<ul class='pagination'>");

			/******************************* �W�@������ *****************************************/
			if ((indexPage - 1) > 0) {
				out.print("<li>"
						+ "<a class='pagepre' style='cursor:pointer;' data-url='"
						+ "SurfMemberServlet.do?action=querylauData&edata="
						+ edata + "&memno=" + memberVO.getMemno()
						+ "&indexPage=" + (indexPage - 1)

						+ "' >&laquo;</a></li>");
			} else if ((indexPage - 1) <= 0) {
				out.println("<li class='disabled'><a>&laquo;</a></li>");
			}

			/******************************** ���X ********************************************/
			/********************************* ���X���� ************************************************/
			int page = 0; // ���X�}�l
			int pagecontol = 10; // ���X�����]�w
			int lastpage = pagecontol;
			if (pageLength < pagecontol) {
				lastpage = pageLength;
			}

			if (indexPage < pagecontol && pageLength <= pagecontol) {
				lastpage = pageLength;
			} else if (indexPage > pagecontol && pageLength > pagecontol) {
				int tendigits = indexPage / pagecontol;

				page = tendigits * pagecontol;

				lastpage = page + pagecontol;
			}

			if (lastpage > pageLength) {
				lastpage = pageLength;
			}
			/********************************************************************************************/

			for (int i = page; i < lastpage; i++) {

				if (indexPage != (i + 1)) {
					out.print("<li>"
							+ "<a class='page' style='cursor:pointer;' data-url=\""
							+ "SurfMemberServlet.do?action=querylauData&edata="
							+ edata + "&memno=" + memberVO.getMemno()
							+ "&indexPage=" + (i + 1) + "\">" + (i + 1)
							+ "</a>" + "</li>");

				} else {
					out.print("<li class='active'><a>" + (i + 1) + "</a></li>");
				}
			}
			/*******************************************************************************/

			/*************************** �U�@������ *************************************/
			if ((indexPage * rowdata) < listData.size()) {
				out.print("<li>"
						+ "<a class='pagenext' style='cursor:pointer;' data-url='"
						+ "SurfMemberServlet.do?action=querylauData&edata="
						+ edata + "&memno=" + memberVO.getMemno()
						+ "&indexPage=" + (indexPage + 1)

						+ "' >&raquo;</a></li>");
			} else {
				out.println("<li class='disabled'><a>&raquo;</a></li>");
			}

			/*************************************************************************/
			out.println("</ul>");

		}
	}

}
