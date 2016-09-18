package com.tao.runningad.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tao.runningad.model.RunningAdService;
import com.tao.runningad.model.RunningAdVO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.Part;

public class RunningAdServlet extends HttpServlet {
			
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
	
		String contentType = req.getContentType(); 
		
		
		if (contentType.startsWith("multipart") && contentType !=null) {
			
			 MultipartRequest multi =
		        	  new MultipartRequest(req, getServletContext().getRealPath("images") , 5 * 1024 * 1024, "UTF-8");	
			 
			 String action = multi.getParameter("action");
			 
			 
			 
			 if ("insertFromUser".equals(action)) { 
					
					List<String> errorMsgs = new LinkedList<String>();
					req.setAttribute("errorMsgs", errorMsgs);
		
					try {     
						
						/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
	
						Integer tst = new Integer(multi.getParameter("tst").trim());
						Integer memno = new Integer(multi.getParameter("memno").trim());
						Integer caseno = new Integer(multi.getParameter("caseno").trim());
						
						File photoFile = multi.getFile("adpic");
						FileInputStream in = null;
						byte[] photo = null;
						 
						try {
							in = new FileInputStream(photoFile);
							photo = new byte[in.available()];
							in.read(photo); 
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						RunningAdVO runningAdVO = new RunningAdVO();
						runningAdVO.setMemno(memno);
						runningAdVO.setCaseno(caseno);
						runningAdVO.setTst(tst);
						runningAdVO.setAdpic(photo);

						// Send the user back to the form, if there were errors
						if (!errorMsgs.isEmpty()) {
							req.setAttribute("runningAdVO", runningAdVO); 
							RequestDispatcher failureView = req
									.getRequestDispatcher("/member/front/signupAlexV5.jsp"); // ** 須改為insert頁面
							failureView.forward(req, res);
							return;
						}
						
						/***************************2.開始新增資料***************************************/
						RunningAdService runAdSvc = new RunningAdService();
						runAdSvc.insertFromUser(runningAdVO);
						
						/***************************3.新增完成,準備轉交(Send the Success view)***********/
						String url = "/member/front/signupSuccessAlexV4.jsp"; 
						RequestDispatcher successView = req.getRequestDispatcher(url); // ** 須改為insert成功頁面
						successView.forward(req, res);				
						
						
						/***************************其他可能的錯誤處理**********************************/
					} catch (Exception e) {
						errorMsgs.add(e.getMessage());
						RequestDispatcher failureView = req
								.getRequestDispatcher("/member/front/signupAlexV5.jsp"); // ** 須改為insert頁面
						failureView.forward(req, res);
					}
				} // end of insert statement
			 
			 
		} else {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
			
			 if ("update_For_Approve".equals(action)) { // 來自update_mem_input.jsp的請求
					
					List<String> errorMsgs = new LinkedList<String>();
					// Store this set in the request scope, in case we need to
					// send the ErrorPage view.
					req.setAttribute("errorMsgs", errorMsgs);
					
					
					try {
						/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
		
						Integer adno = new Integer(req.getParameter("adno").trim());
						Integer tst = 1;
						
						String requestURL = req.getParameter("requestURL").trim();
						String whichPage = req.getParameter("whichPage").trim();
						
						RunningAdVO runningAdVO = new RunningAdVO();
						runningAdVO.setAdno(adno);
						runningAdVO.setTst(tst);
		
						// Send the use back to the form, if there were errors
						if (!errorMsgs.isEmpty()) {
							req.setAttribute("runningAdVO", runningAdVO); 
							RequestDispatcher failureView = req
									.getRequestDispatcher("/runningAd/back/backRunningAdListAll.jsp"); // ** 須改為update頁面
							failureView.forward(req, res);
							return; //程式中斷
						}
						
						/***************************2.開始修改資料*****************************************/
		
						RunningAdService runAdSvc = new RunningAdService();
						runAdSvc.updateToManage(runningAdVO);
						
						/***************************3.修改完成,準備轉交(Send the Success view)*************/
						req.setAttribute("runningAdVO", runningAdVO); 
						String url = "/runningAd/back/backRunningAdListAll.jsp?whichPage="+whichPage+"&adno="+adno+"&requestURL="+requestURL;
						RequestDispatcher successView = req.getRequestDispatcher(url); // ** 須改為update頁面
						successView.forward(req, res);
		
						/***************************其他可能的錯誤處理*************************************/
				} catch (Exception e) {
						errorMsgs.add("修改資料失敗:"+e.getMessage());
						RequestDispatcher failureView = req
								.getRequestDispatcher("/runningAd/back/backRunningAdListAll.jsp"); // ** 須改為update頁面
						failureView.forward(req, res);
					}
				} // end of update_For_Approve statement
			 
			 if ("update_For_Reject".equals(action)) { // 來自update_mem_input.jsp的請求
					
					List<String> errorMsgs = new LinkedList<String>();
					// Store this set in the request scope, in case we need to
					// send the ErrorPage view.
					req.setAttribute("errorMsgs", errorMsgs);
					
					
					try {
						/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
		
						Integer adno = new Integer(req.getParameter("adno").trim());
						Integer tst = 3;
						
						String requestURL = req.getParameter("requestURL").trim();
						String whichPage = req.getParameter("whichPage").trim();
						
						RunningAdVO runningAdVO = new RunningAdVO();
						runningAdVO.setAdno(adno);
						runningAdVO.setTst(tst);
		
						// Send the use back to the form, if there were errors
						if (!errorMsgs.isEmpty()) {
							req.setAttribute("runningAdVO", runningAdVO); 
							RequestDispatcher failureView = req
									.getRequestDispatcher("/runningAd/back/backRunningAdListAll.jsp"); // ** 須改為update頁面
							failureView.forward(req, res);
							return; //程式中斷
						}
						
						/***************************2.開始修改資料*****************************************/
		
						RunningAdService runAdSvc = new RunningAdService();
						runAdSvc.updateToManage(runningAdVO);
						
						/***************************3.修改完成,準備轉交(Send the Success view)*************/
						req.setAttribute("runningAdVO", runningAdVO); 
						String url = "/runningAd/back/backRunningAdListAll.jsp?whichPage="+whichPage+"&adno="+adno+"&requestURL="+requestURL;
						RequestDispatcher successView = req.getRequestDispatcher(url); // ** 須改為update頁面
						successView.forward(req, res);
		
						/***************************其他可能的錯誤處理*************************************/
				} catch (Exception e) {
						errorMsgs.add("修改資料失敗:"+e.getMessage());
						RequestDispatcher failureView = req
								.getRequestDispatcher("/runningAd/back/backRunningAdListAll.jsp"); // ** 須改為update頁面
						failureView.forward(req, res);
					}
				} // end of update_For_Reject statement
			 
			 if ("backSearchByTst".equals(action)) { // 來自update_mem_input.jsp的請求
					
					List<String> errorMsgs = new LinkedList<String>();
					// Store this set in the request scope, in case we need to
					// send the ErrorPage view.
					req.setAttribute("errorMsgs", errorMsgs);
					
					
					try {
						/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
		
						//Integer tst = new Integer(req.getParameter("tst").trim());
												
						String tstStr = req.getParameter("tst");
						Integer tst = null;
						
						if (tstStr == null || (tstStr.trim()).length() == 0) {
							errorMsgs.add("請選擇搜尋狀態");
						} else {
							tst = new Integer(tstStr);
							if (tst >=0 || tst <4){
								
							}else{
								errorMsgs.add("搜尋狀態錯誤");
							}
						}
						
						//String requestURL = req.getParameter("requestURL").trim();
						//String whichPage = req.getParameter("whichPage").trim();
						
		
						// Send the use back to the form, if there were errors
						if (!errorMsgs.isEmpty()) {
							RequestDispatcher failureView = req
									.getRequestDispatcher("/runningAd/back/backRunningAdListAll.jsp"); // ** 須改為update頁面
							failureView.forward(req, res);
							return; //程式中斷
						}
						
						/***************************2.開始修改資料*****************************************/
		
						RunningAdService runAdSvc = new RunningAdService();
						List<RunningAdVO> runlist = runAdSvc.getAllByStatus(tst);
						
						/***************************3.修改完成,準備轉交(Send the Success view)*************/
						if ((runlist != null) || (runlist.size() >0)){
							req.getSession().setAttribute("runlist", runlist);	
							req.setAttribute("oldTst", tst);
						}
						 
						RequestDispatcher successView = req.getRequestDispatcher("/runningAd/back/backRunningAdListAll.jsp"); // ** 須改為update頁面
						successView.forward(req, res);
		
						/***************************其他可能的錯誤處理*************************************/
				} catch (Exception e) {
						errorMsgs.add("修改資料失敗:"+e.getMessage());
						RequestDispatcher failureView = req
								.getRequestDispatcher("/runningAd/back/backRunningAdListAll.jsp"); // ** 須改為update頁面
						failureView.forward(req, res);
					}
				} // end of backSearchByTst statement
			 
		} // end of content if statement
	} // end of doPost statement
	
} // end of MemberServlet statement
