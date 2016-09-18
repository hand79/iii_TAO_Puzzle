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
		/************************************* 建立session和設定 *********************************/
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
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String catnolistnostring = req.getParameter("catnolistno");
				
				SubCategoryService subCategorySvc =new SubCategoryService();
				
				Integer catnolistno = null;
				boolean okcatno = MatcherCategoryData
						.matchNoNumber(catnolistnostring);
				if (okcatno) {
					catnolistno = new Integer(catnolistnostring);
				} else {
					errorMsgs.add("分類資料傳輸錯誤");
				}
				
				if(catnolistno!=0&&subCategorySvc.getAllByCategory(catnolistno).size()<=0){
					errorMsgs.add("查無資子分類資料");
				}
				
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/category/Category.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/*************************** 2.開始查詢資料 ***************************************/

		
				Set<SubCategoryVO> catnolist = null;
				if (subCategorySvc.getAllByCategory(catnolistno) != null) {
					catnolist = subCategorySvc.getAllByCategory(catnolistno);
					session.setAttribute("catnolist", catnolist);
					session.setAttribute("subListSatus",catnolistno);
				}
				
				
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ***********/

				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("查詢失敗:" + e.getMessage());
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
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String catname = req.getParameter("catname").trim();
				if (catname == null || catname.length() <= 0) {
					errorMsgs.add("不可為空白");
				} else if (!MatcherCategoryData.matchNameFormat(catname)) {
					errorMsgs.add("格式錯誤，請輸入中文");
				}
				if (catname.length() > 6) {
					errorMsgs.add("限制長度為6個字");
				}

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/category/Category.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				CategoryService categorySvc = new CategoryService();
				categorySvc.addCategory(catname);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/

				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("主分類增加失敗:" + e.getMessage());
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
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				
				String catnostring=req.getParameter("catno").trim();
				String subcatname = req.getParameter("subcatname").trim();
				Integer subListSatus= (Integer) session.getAttribute("subListSatus");
				SubCategoryService subCategorySvc = new SubCategoryService();
				if(subListSatus==null){
					subListSatus=0;
				}
				
					
				if(subcatname==null||subcatname.length()<=0){
					errorMsgs.add("請填子分類名稱");
				}else if(!MatcherCategoryData.matchNameFormat(subcatname)){
					errorMsgs.add("格式請輸入中文");
				}else if(subCategorySvc.getOneSubCategory(subcatname)!=null){
					errorMsgs.add("已有這項子分類");
				}
				if(subcatname.length()>10){
					errorMsgs.add("限制長度為10字");
				}
								
				Integer catno = null;
				if (MatcherCategoryData.matchNoNumber(catnostring)) {
					catno = new Integer(catnostring);
				} else {
					errorMsgs.add("主分類編號傳輸錯誤");
				}

				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher(requestURL);
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				
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
				

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/

				req.setAttribute("subcatname", subcatname);

				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("子分類增加失敗:" + e.getMessage());
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
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String subcatnostring =req.getParameter("subcatno").trim();
				Integer subListSatus= (Integer) session.getAttribute("subListSatus");
				
				if(subListSatus==null){
					subListSatus=0;
				}
				if(subcatnostring.length()<0||subcatnostring==null){
					errorMsgs.add("子分類傳送錯誤");
				}else if(!MatcherCategoryData.matchNoNumber(subcatnostring)){
					errorMsgs.add("傳子分類送格式錯誤");					
				}
				Integer subcatno = null;
				if(MatcherCategoryData.matchNoNumber(subcatnostring)){
					subcatno = new Integer(subcatnostring);
				}
				if(UsedSubcategoryCheck.CheckByCase(subcatno)||UsedSubcategoryCheck.CheckByShop(subcatno)){
					errorMsgs.add("此分類有商品正在使用");	
				}
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/category/Category.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始刪除資料 ***************************************/
				SubCategoryService subCategorySvc = new SubCategoryService();
				SubCategoryVO subCategoryVO = new SubCategoryVO();
				subCategoryVO = subCategorySvc.getOneSubCategory(subcatno);
				subCategorySvc.deleteSubCategory(subcatno);
				
				/*** 更新目前(session中)查詢的list *****/
								
				if(subListSatus!=0){
					Set<SubCategoryVO> catnolist = subCategorySvc.getAllByCategory(subCategoryVO.getCatno());
					session.setAttribute("catnolist", catnolist);
					session.setAttribute("subListSatus",subCategoryVO.getCatno());
				}else{
					Set<SubCategoryVO> catnolist = subCategorySvc.getAll();
					session.setAttribute("catnolist", catnolist);
				}

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/

				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);
				
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("子分類刪除失敗:" + e.getMessage());
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

				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String catnostring=req.getParameter("catno").trim();
				CategoryService CategorySvc = new CategoryService();
				SubCategoryService subCategorySvc= new SubCategoryService();
				
				if(catnostring.length()<0||catnostring==null){
					errorMsgs.add("子分類傳送錯誤");
				}else if(!MatcherCategoryData.matchNoNumber(catnostring)){
					errorMsgs.add("傳子分類送格式錯誤");					
				}
				
				Integer catno = null;
				if(MatcherCategoryData.matchNoNumber(catnostring)){
					catno = new Integer(catnostring);
				}
				if(subCategorySvc.getAllByCategory(catno).size()!=0){
					errorMsgs.add("此分類尚有子分類");
				}
				
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/category/Category.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始刪除資料 ***************************************/
				
				CategorySvc.deleteCategory(catno);
				
				
				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("主分類刪除失敗" + e.getMessage());
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

				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String catnostring=req.getParameter("catno").trim();
				String subcatnostring=req.getParameter("subcatno").trim();
				String 	subcatname = req.getParameter("subcatname").trim();
				Integer subListSatus= (Integer) session.getAttribute("subListSatus");
				if(subListSatus==null){
					subListSatus=0;
				}
				
				
				if(catnostring.length()<0||catnostring==null){
					errorMsgs.add("主分類編號傳送錯誤");
				}else if(!MatcherCategoryData.matchNoNumber(catnostring)){
					errorMsgs.add("主分類編號送格式錯誤");					
				}
				Integer catno = null;
				if(MatcherCategoryData.matchNoNumber(catnostring)){
					catno = new Integer(catnostring);
				}
				
				if(subcatnostring.length()<0||subcatnostring==null){
					errorMsgs.add("子分類編號傳送錯誤");
				}else if(!MatcherCategoryData.matchNoNumber(subcatnostring)){
					errorMsgs.add("子分類編號傳送格式錯誤");					
				}
				Integer subcatno = null;
				if(MatcherCategoryData.matchNoNumber(subcatnostring)){
					subcatno = new Integer(subcatnostring);
				}
				
				if(subcatname==null||subcatname.length()<=0){
					errorMsgs.add("請填子分類名稱");
				}				
				else if(!MatcherCategoryData.matchNameFormat(subcatname)){
					errorMsgs.add("格式請輸入中文");
				}
				
				if(subcatname.length()>10){
					errorMsgs.add("限制長度為10字");
				}
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/category/Category.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始更新資料 **************************************************/
				SubCategoryService subCategorySvc = new SubCategoryService();
				SubCategoryVO subCategoryVO = new SubCategoryVO();
				subCategoryVO = subCategorySvc.updateSubCategory(subcatno,subcatname, catno);
				/********************************** 狀態更新 *************************************************/
				if(subListSatus!=0){
					Set<SubCategoryVO> catnolist = subCategorySvc.getAllByCategory(subCategoryVO.getCatno());
					session.setAttribute("catnolist", catnolist);
					session.setAttribute("subListSatus",subCategoryVO.getCatno());
				}else{
					Set<SubCategoryVO> catnolist = subCategorySvc.getAll();
					session.setAttribute("catnolist", catnolist);
				}

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********************/

				req.setAttribute("subcatname", subcatname);

				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("更新失敗" + e.getMessage());
				RequestDispatcher failureView = req
					.getRequestDispatcher("/category/Category.jsp");
				failureView.forward(req, res);
			}
		}

	}

}
