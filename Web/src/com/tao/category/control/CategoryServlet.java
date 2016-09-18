package com.tao.category.control;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tao.category.model.CategoryService;
import com.tao.category.model.SubCategoryService;
import com.tao.category.model.SubCategoryVO;
import com.tao.category.model.UsedSubcategoryCheck;

public class CategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
    	req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		/************************************* �إ�session�M�]�w *********************************/
		HttpSession session = req.getSession();
	

		if ("default".equals(action)) {
			RequestDispatcher successView = req.getRequestDispatcher("/category/Category.jsp"); 
			successView.forward(req, res);
			return;
		}	
		if ("querycatlist".equals(action)) {
			String requestURL = req.getParameter("requestURL").trim();
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z *************************/
				String catnolistnostring = req.getParameter("catnolistno");
				
				SubCategoryService subCategorySvc =new SubCategoryService();
				
				Integer catnolistno = null;
				boolean okcatno = MatcherCategoryData
						.matchNoNumber(catnolistnostring);
				if (okcatno) {
					catnolistno = new Integer(catnolistnostring);
				} else {
					errorMsgs.add("������ƶǿ���~");
				}
				
				if(catnolistno!=0&&subCategorySvc.getAllByCategory(catnolistno).size()<=0){
					errorMsgs.add("�d�L��l�������");
				}
				
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/category/Category.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/*************************** 2.�}�l�d�߸�� ***************************************/

		
				Set<SubCategoryVO> catnolist = null;
				if (subCategorySvc.getAllByCategory(catnolistno) != null) {
					catnolist = subCategorySvc.getAllByCategory(catnolistno);
					session.setAttribute("catnolist", catnolist);
					session.setAttribute("subListSatus",catnolistno);
				}
				
				
				/*************************** 3.�d�ߧ���,�ǳ����(Send the Success view) ***********/

				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);
				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add("�d�ߥ���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}

		if ("addCategory".equals(action)) { 
			String requestURL = req.getParameter("requestURL");
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z *************************/
				String catname = req.getParameter("catname").trim();
				if (catname == null || catname.length() <= 0) {
					errorMsgs.add("���i���ť�");
				} else if (!MatcherCategoryData.matchNameFormat(catname)) {
					errorMsgs.add("�榡���~�A�п�J����");
				}
				if (catname.length() > 6) {
					errorMsgs.add("������׬�6�Ӧr");
				}

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/category/Category.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.�}�l�s�W��� ***************************************/
				CategoryService categorySvc = new CategoryService();
				categorySvc.addCategory(catname);

				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/

				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllEmp.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add("�D�����W�[����:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/category/Category.jsp");
				failureView.forward(req, res);
			}
		}

		if ("addSubCategory".equals(action)) {
			String requestURL = req.getParameter("requestURL");
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z *************************/
				
				String catnostring=req.getParameter("catno").trim();
				String subcatname = req.getParameter("subcatname").trim();
				Integer subListSatus= (Integer) session.getAttribute("subListSatus");
				SubCategoryService subCategorySvc = new SubCategoryService();
				if(subListSatus==null){
					subListSatus=0;
				}
				
					
				if(subcatname==null||subcatname.length()<=0){
					errorMsgs.add("�ж�l�����W��");
				}else if(!MatcherCategoryData.matchNameFormat(subcatname)){
					errorMsgs.add("�榡�п�J����");
				}else if(subCategorySvc.getOneSubCategory(subcatname)!=null){
					errorMsgs.add("�w���o���l����");
				}
				if(subcatname.length()>10){
					errorMsgs.add("������׬�10�r");
				}
								
				Integer catno = null;
				if (MatcherCategoryData.matchNoNumber(catnostring)) {
					catno = new Integer(catnostring);
				} else {
					errorMsgs.add("�D�����s���ǿ���~");
				}

				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher(requestURL);
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.�}�l�s�W��� ***************************************/
				
				SubCategoryVO subCategoryVO = new SubCategoryVO();
				subCategoryVO = subCategorySvc.addSubCategory(subcatname, catno);
				
				if(subListSatus!=0){
					Set<SubCategoryVO> catnolist = subCategorySvc.getAllByCategory(subCategoryVO.getCatno());
					
					session.setAttribute("catnolist", catnolist);
					session.setAttribute("subListSatus",subCategoryVO.getCatno());
				}else{
					Set<SubCategoryVO> catnolist = subCategorySvc.getAll();
					session.setAttribute("catnolist", catnolist);
				}
				

				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/

				req.setAttribute("subcatname", subcatname);

				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllEmp.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add("�l�����W�[����:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/category/Category.jsp");
				failureView.forward(req, res);
			}
		}

		if ("deleteSubCategory".equals(action)) {
			
			String requestURL = req.getParameter("requestURL");
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z *************************/
				String subcatnostring =req.getParameter("subcatno").trim();
				Integer subListSatus= (Integer) session.getAttribute("subListSatus");
				
				if(subListSatus==null){
					subListSatus=0;
				}
				if(subcatnostring.length()<0||subcatnostring==null){
					errorMsgs.add("�l�����ǰe���~");
				}else if(!MatcherCategoryData.matchNoNumber(subcatnostring)){
					errorMsgs.add("�Ǥl�����e�榡���~");					
				}
				Integer subcatno = null;
				if(MatcherCategoryData.matchNoNumber(subcatnostring)){
					subcatno = new Integer(subcatnostring);
				}
				if(UsedSubcategoryCheck.CheckByCase(subcatno)||UsedSubcategoryCheck.CheckByShop(subcatno)){
					errorMsgs.add("���������ӫ~���b�ϥ�");	
				}
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/category/Category.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.�}�l�R����� ***************************************/
				SubCategoryService subCategorySvc = new SubCategoryService();
				SubCategoryVO subCategoryVO = new SubCategoryVO();
				subCategoryVO = subCategorySvc.getOneSubCategory(subcatno);
				subCategorySvc.deleteSubCategory(subcatno);
				
				/*** ��s�ثe(session��)�d�ߪ�list *****/
								
				if(subListSatus!=0){
					Set<SubCategoryVO> catnolist = subCategorySvc.getAllByCategory(subCategoryVO.getCatno());
					session.setAttribute("catnolist", catnolist);
					session.setAttribute("subListSatus",subCategoryVO.getCatno());
				}else{
					Set<SubCategoryVO> catnolist = subCategorySvc.getAll();
					session.setAttribute("catnolist", catnolist);
				}

				/*************************** 3.�R������,�ǳ����(Send the Success view) ***********/

				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllEmp.jsp
				successView.forward(req, res);
				
				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add("�l�����R������:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/category/Category.jsp");
				failureView.forward(req, res);
			}
		}

		if ("deleteCategory".equals(action)) {

			String requestURL = req.getParameter("requestURL");
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {

				/*********************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z *************************/
				String catnostring=req.getParameter("catno").trim();
				CategoryService CategorySvc = new CategoryService();
				SubCategoryService subCategorySvc= new SubCategoryService();
				
				if(catnostring.length()<0||catnostring==null){
					errorMsgs.add("�l�����ǰe���~");
				}else if(!MatcherCategoryData.matchNoNumber(catnostring)){
					errorMsgs.add("�Ǥl�����e�榡���~");					
				}
				
				Integer catno = null;
				if(MatcherCategoryData.matchNoNumber(catnostring)){
					catno = new Integer(catnostring);
				}
				if(subCategorySvc.getAllByCategory(catno).size()!=0){
					errorMsgs.add("�������|���l����");
				}
				
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/category/Category.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.�}�l�R����� ***************************************/
				
				CategorySvc.deleteCategory(catno);
				
				
				/*************************** 3.�R������,�ǳ����(Send the Success view) ***********/
				
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllEmp.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add("�D�����R������" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/Category/Category.jsp");
				failureView.forward(req, res);
			}
		}

		if ("updateSubCategory".equals(action)) {
			String requestURL = req.getParameter("requestURL");
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {

				/*********************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z *************************/
				String catnostring=req.getParameter("catno").trim();
				String subcatnostring=req.getParameter("subcatno").trim();
				String 	subcatname = req.getParameter("subcatname").trim();
				Integer subListSatus= (Integer) session.getAttribute("subListSatus");
				if(subListSatus==null){
					subListSatus=0;
				}
				
				
				if(catnostring.length()<0||catnostring==null){
					errorMsgs.add("�D�����s���ǰe���~");
				}else if(!MatcherCategoryData.matchNoNumber(catnostring)){
					errorMsgs.add("�D�����s���e�榡���~");					
				}
				Integer catno = null;
				if(MatcherCategoryData.matchNoNumber(catnostring)){
					catno = new Integer(catnostring);
				}
				
				if(subcatnostring.length()<0||subcatnostring==null){
					errorMsgs.add("�l�����s���ǰe���~");
				}else if(!MatcherCategoryData.matchNoNumber(subcatnostring)){
					errorMsgs.add("�l�����s���ǰe�榡���~");					
				}
				Integer subcatno = null;
				if(MatcherCategoryData.matchNoNumber(subcatnostring)){
					subcatno = new Integer(subcatnostring);
				}
				
				if(subcatname==null||subcatname.length()<=0){
					errorMsgs.add("�ж�l�����W��");
				}				
				else if(!MatcherCategoryData.matchNameFormat(subcatname)){
					errorMsgs.add("�榡�п�J����");
				}
				
				if(subcatname.length()>10){
					errorMsgs.add("������׬�10�r");
				}
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/category/Category.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.�}�l��s��� **************************************************/
				SubCategoryService subCategorySvc = new SubCategoryService();
				SubCategoryVO subCategoryVO = new SubCategoryVO();
				subCategoryVO = subCategorySvc.updateSubCategory(subcatno,subcatname, catno);
				/********************************** ���A��s *************************************************/
				if(subListSatus!=0){
					Set<SubCategoryVO> catnolist = subCategorySvc.getAllByCategory(subCategoryVO.getCatno());
					session.setAttribute("catnolist", catnolist);
					session.setAttribute("subListSatus",subCategoryVO.getCatno());
				}else{
					Set<SubCategoryVO> catnolist = subCategorySvc.getAll();
					session.setAttribute("catnolist", catnolist);
				}

				/*************************** 3.�R������,�ǳ����(Send the Success view) ***********************/

				req.setAttribute("subcatname", subcatname);

				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add("��s����" + e.getMessage());
				RequestDispatcher failureView = req
					.getRequestDispatcher("/category/Category.jsp");
				failureView.forward(req, res);
			}
		}

	}

}
