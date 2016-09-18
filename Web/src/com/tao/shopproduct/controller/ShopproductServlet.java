package com.tao.shopproduct.controller;

import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.oreilly.servlet.MultipartRequest;
import com.tao.cases.model.CasesService;
import com.tao.cases.model.CasesVO;
import com.tao.jimmy.util.model.ColumnValue;
import com.tao.member.model.MemberService;
import com.tao.member.model.MemberVO;
import com.tao.order.model.OrderService;
import com.tao.order.model.OrderVO;
import com.tao.shop.model.ShopService;
import com.tao.shop.model.ShopVO;
import com.tao.shopproduct.model.ShopproductService;
import com.tao.shopproduct.model.ShopproductVO;

public class ShopproductServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String contentType = req.getContentType();
		String action = null;
		MultipartRequest multi = null;
		if (contentType != null 	&& contentType.startsWith("multipart/form-data")) {
			multi = new MultipartRequest(req, getServletContext().getRealPath(	"/imgTemp"), 5 * 1024 * 1024, "UTF-8");
			action = multi.getParameter("action");
		} else {
			req.setCharacterEncoding("UTF-8");
			action = req.getParameter("action");
		}

		if ("getOne_For_Display".equals(action)) { 
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				Integer spno = new Integer(req.getParameter("spno"));
				Integer shopno = new Integer(req.getParameter("shopno"));
				/*************************** 2.開始查詢資料 *****************************************/
				ShopproductService spSvc = new ShopproductService();
				ShopproductVO spVO = spSvc.getOneShopproduct(spno);
				
				
				ShopService shopSvc = new ShopService();
				ShopVO shopVO = shopSvc.getCheckStatus(shopno);

				Integer memno = shopVO.getMemno();
				MemberService memberSvc = new MemberService();
				MemberVO memberVO = memberSvc.findByPrimaryKeyNoPic(memno);

				List<ShopproductVO> spVOs = (List<ShopproductVO>) spSvc
						.getByshop(shopno);
				List<Integer> spnoList = new ArrayList<Integer>();
				for (ShopproductVO vo : spVOs) {
					spnoList.add(vo.getSpno());
				}
				CasesService casesSvc = new CasesService();
				Set<CasesVO> casesVOSet = casesSvc
						.getByShopProductNums(spnoList
								.toArray(new Integer[spnoList.size()]));

				List<CasesVO> casesVOList = new ArrayList<>();
				for (CasesVO vo : casesVOSet) {
					int i = vo.getStatus();
					if (i == CasesVO.STATUS_PUBLIC) {
						casesVOList.add(vo);
					}
				}
				OrderService ordSvc = new OrderService();
				List<OrderVO> orderList = ordSvc.compositeQuery(new ColumnValue("crate",false), new ColumnValue("cmemno", shopVO.getMemno().toString()));
				
				
				if (!errorMsgs.isEmpty()) {
					res.setContentType("text/html;charset=UTF-8");
					res.getWriter().print("<script>window.onload=function(){alert('此商品已下架 / 刪除');window.location.href=\""+req.getContextPath()+"\";}</script>");
					return;
				}
				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("spVO", spVO); 
				req.setAttribute("shopVO", shopVO);
				req.setAttribute("memberVO", memberVO);
				req.setAttribute("spVOs", spVOs);
				req.setAttribute("orderList", orderList);
				req.setAttribute("casesVOList", casesVOList);
				String url = "/shopproduct/view_shop_product.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得商品資料:" + e.getMessage());
				res.setContentType("text/html;charset=UTF-8");
				res.getWriter().print("<script>window.onload=function(){alert('此商品已下架 / 刪除');window.location.href=\""+req.getContextPath()+"\";}</script>");
			}
		}

		if ("getTemp_Display".equals(action)) { 
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				Integer spno = new Integer(req.getParameter("spno"));
				Integer shopno = new Integer(req.getParameter("shopno"));
				/*************************** 2.開始查詢資料 *****************************************/
				ShopproductService spSvc = new ShopproductService();
				ShopproductVO spVO = spSvc.getOneShopproduct(spno);
				
				
				ShopService shopSvc = new ShopService();
				ShopVO shopVO = shopSvc.getOneShop(shopno);
				
		
				Integer memno = shopVO.getMemno();
				MemberService memberSvc = new MemberService();
				MemberVO memberVO = memberSvc.findByPrimaryKeyNoPic(memno);
		
				List<ShopproductVO> spVOs = (List<ShopproductVO>) spSvc
						.getByshop(shopno);
				List<Integer> spnoList = new ArrayList<Integer>();
				for (ShopproductVO vo : spVOs) {
					spnoList.add(vo.getSpno());
				}
				CasesService casesSvc = new CasesService();
				Set<CasesVO> casesVOSet = casesSvc
						.getByShopProductNums(spnoList
								.toArray(new Integer[spnoList.size()]));
		
				List<CasesVO> casesVOList = new ArrayList<>();
				for (CasesVO vo : casesVOSet) {
					int i = vo.getStatus();
					if (i == CasesVO.STATUS_PUBLIC) {
						casesVOList.add(vo);
					}
				}
				OrderService ordSvc = new OrderService();
				List<OrderVO> orderList = ordSvc.compositeQuery(new ColumnValue("crate",false), new ColumnValue("cmemno", shopVO.getMemno().toString()));
				
				
				if (!errorMsgs.isEmpty()) {
					res.setContentType("text/html;charset=UTF-8");
					res.getWriter().print("<script>window.onload=function(){alert('此商品已下架 / 刪除');window.location.href=\""+req.getContextPath()+"\";}</script>");
					return;
				}
				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("spVO", spVO); 
				req.setAttribute("shopVO", shopVO);
				req.setAttribute("memberVO", memberVO);
				req.setAttribute("spVOs", spVOs);
				req.setAttribute("orderList", orderList);
				req.setAttribute("casesVOList", casesVOList);
				String url = "/shopproduct/preview_shop_product.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				res.setContentType("text/html;charset=UTF-8");
				res.getWriter().print("<script>window.onload=function(){alert('此商品已下架 / 刪除');window.location.href=\""+req.getContextPath()+"\";}</script>");
			}
		}

		if ("getOne_For_Update".equals(action)) { 
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
			try {
				/*************************** 1.接收請求參數 ****************************************/
				Integer spno = new Integer(req.getParameter("spno"));
				/*************************** 2.開始查詢資料 ****************************************/
				ShopproductService spSvc = new ShopproductService();
				ShopproductVO spVO = spSvc.getOneShopproduct(spno);
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("spVO", spVO); 
				String url = "/shopproduct/update_shop_product_front.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
		if ("getOne_For_Create".equals(action)) { 
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
			try {
				/*************************** 1.接收請求參數 ****************************************/
				Integer shopno = new Integer(req.getParameter("shopno"));
				/*************************** 2.開始查詢資料 ****************************************/
				ShopproductService spSvc = new ShopproductService();
				ShopproductVO spVO = spSvc.getOneShopproduct(shopno);
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("spVO", spVO); 
				String url = "/shopproduct/create_shop_product_front.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) { 
			doUpdate(req,res,multi);
		}

		if ("insert".equals(action)) {
			doInsert(req, res, multi);
		}

		if ("delete".equals(action)) { 
			doDelete(req, res, multi);
		}

		
		if ("listShopproduct_ByCompositeQuery".equals(action)) { 
			doListShopproduct_ByCompositeQuery(req, res);
		}
		if ("getPic1".equals(action)) {

			/*************************** 1.接收請求參數 ****************************************/
			Integer spno = new Integer(req.getParameter("spno"));
			/*************************** 2.開始查詢資料 ****************************************/
			ShopproductService spSvc = new ShopproductService();
			ShopproductVO spVO = spSvc.getOneShopproduct(spno);
			
			byte[] pic1 = spVO.getPic1();
			if (pic1==null){
				return;
			}
			res.setContentType(spVO.getPmime1());
			ServletOutputStream out1= res.getOutputStream();
			out1.write(pic1);
		}
		if ("getPic2".equals(action)) {
			/*************************** 1.接收請求參數 ****************************************/
			Integer spno = new Integer(req.getParameter("spno"));
			/*************************** 2.開始查詢資料 ****************************************/
			ShopproductService spSvc = new ShopproductService();
			ShopproductVO spVO = spSvc.getOneShopproduct(spno);
			
			byte[] pic2 = spVO.getPic2();
			if (pic2==null){
				return;
			}
			res.setContentType(spVO.getPmime2());
			ServletOutputStream out2 = res.getOutputStream();
			out2.write(pic2);
		}
		
		if ("getPic3".equals(action)) {
			/*************************** 1.接收請求參數 ****************************************/
			Integer spno = new Integer(req.getParameter("spno"));
			/*************************** 2.開始查詢資料 ****************************************/
			ShopproductService spSvc = new ShopproductService();
			ShopproductVO spVO = spSvc.getOneShopproduct(spno);

			byte[] pic3 = spVO.getPic3();
			if (pic3==null){
				return;
			}
			res.setContentType(spVO.getPmime3());
			ServletOutputStream out3 = res.getOutputStream();
			out3.write(pic3);
		}
	}

	private void doDelete(HttpServletRequest req, HttpServletResponse res,MultipartRequest multi) throws ServletException, IOException {
		List<String> errorMsgs = new LinkedList<String>();
		req.setAttribute("errorMsgs", errorMsgs);

		try {
			/*************************** 1.接收請求參數 ***************************************/
			Integer spno = new Integer(req.getParameter("spno"));
			Integer shopno = new Integer(req.getParameter("shopno"));
			/*************************** 2.開始刪除資料 ***************************************/
			ShopproductService shopproductSvc = new ShopproductService();
			shopproductSvc.deleteShopproduct(spno);
			req.setAttribute("shopVO", new ShopService().getOneShop(shopno));
			/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
			List<ShopproductVO> list  = shopproductSvc.getByshop(shopno);
			req.setAttribute("listShopproduct_ByCompositeQuery", list);
			RequestDispatcher successView = req.getRequestDispatcher("/shopproduct/shop_product_member_center.jsp");
			successView.forward(req, res);
			/*************************** 其他可能的錯誤處理 **********************************/
		} catch (Exception e) {
			errorMsgs.add("刪除資料失敗:" + e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher("/shopproduct/shop_product_member_center.jsp");
			failureView.forward(req, res);
		}
	}

	private void doInsert(HttpServletRequest req, HttpServletResponse res, MultipartRequest multi) throws ServletException, IOException {
		List<String> errorMsgs = new LinkedList<String>();
		req.setAttribute("errorMsgs", errorMsgs);
		try {
			/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
			
			Integer shopno =  null;
			try {
				shopno =  new Integer(multi.getParameter("shopno").trim());
			} catch (NumberFormatException e) {
				errorMsgs.add("請選擇商店編號");
			}
			
			Integer subcatno = null;
			try {
				subcatno = new Integer(multi.getParameter("subcatno").trim());
			} catch (NumberFormatException e) {
				errorMsgs.add("請選擇商店分類");
			}

			Integer isrecomm = null;
			try {
				isrecomm = new Integer(multi.getParameter("isrecomm").trim());
			} catch (NumberFormatException e) {
				errorMsgs.add("請勾選是否推薦此商品");
			}

			Double unitprice = null;
			try {
				unitprice = new Double(multi.getParameter("unitprice").trim());
			} catch (NumberFormatException e) {
				unitprice = 0.0;
				errorMsgs.add("單價請填數字.");
			}
			
			String name = multi.getParameter("name").trim();
			if(name == null || name.length() ==0){
				errorMsgs.add("請輸入商品名稱");
			}				
			String pro_desc = multi.getParameter("pro_desc").trim();
			if(pro_desc == null || pro_desc.length() ==0){
				errorMsgs.add("請輸入商品說明");
			}		
			String spec1 = multi.getParameter("spec1").trim();
			if(spec1 == null || spec1.length() ==0){
				errorMsgs.add("請輸入商品規格");
			}	
			String spec2 = multi.getParameter("spec2").trim();
			String spec3 = multi.getParameter("spec3").trim();

			// shopprdouct pic1
			File currentFile1 = multi.getFile("pic1");
			byte[] pic1=null;
			if (currentFile1!=null){
				InputStream in1 = new FileInputStream(currentFile1);
				pic1 = new byte[in1.available()];
				in1.read(pic1);
				currentFile1.delete();
				in1.close();
			}else{
				errorMsgs.add("請上傳圖片1");
			}
			
			// pic1 type
			String pmime1 = multi.getContentType("pic1");
			
			// shopprdouct pic2
			File currentFile2 = multi.getFile("pic2");
			byte[] pic2=null;
			if (currentFile2!=null){
				InputStream in2 = new FileInputStream(currentFile2);
				pic2= new byte[in2.available()];
				in2.read(pic2);
				currentFile2.delete();
				in2.close();
			}else{
				errorMsgs.add("請上傳圖片2");
			}
			// pic2 type
			String pmime2 = multi.getContentType("pic2");
			
			// shopprdouct pic3
			File currentFile3 = multi.getFile("pic3");
			byte[] pic3=null;
			if (currentFile3!=null){
				InputStream in3 = new FileInputStream(currentFile3);
				pic3= new byte[in3.available()];
				in3.read(pic3);
				currentFile3.delete();
				in3.close();
			}else{
				errorMsgs.add("請上傳圖片3");
			}
			// pic3type
			String pmime3 = multi.getContentType("pic3");
			
			ShopproductVO shopproductVO = new ShopproductVO();
			shopproductVO.setShopno(shopno);
			shopproductVO.setName(name);
			shopproductVO.setUnitprice(unitprice);
			shopproductVO.setPic1(pic1);
			shopproductVO.setPic2(pic2);
			shopproductVO.setPic3(pic3);
			shopproductVO.setPmime1(pmime1);
			shopproductVO.setPmime2(pmime2);
			shopproductVO.setPmime3(pmime3);
			shopproductVO.setPro_desc(pro_desc);
			shopproductVO.setSubcatno(subcatno);
			shopproductVO.setSpec1(spec1);
			shopproductVO.setSpec2(spec2);
			shopproductVO.setSpec3(spec3);
			shopproductVO.setIsrecomm(isrecomm);

			if (!errorMsgs.isEmpty()) {
				req.setAttribute("shopproductVO", shopproductVO); 
				RequestDispatcher failureView = req
						.getRequestDispatcher("/shopproduct/addSP.jsp");
				failureView.forward(req, res);
				return;
			}

			/*************************** 2.開始新增資料 ***************************************/
			ShopproductService shopproductSvc = new ShopproductService();
			shopproductVO = shopproductSvc.addShopproduct(shopno,
					name, unitprice, pic1, pic2, pic3, pmime1, pmime2,
					pmime3, pro_desc, subcatno, spec1, spec2, spec3,
					isrecomm);
			/***************************2.開始複合查詢***************************************/
			ShopproductService spSvc = new ShopproductService();
			List<ShopproductVO> list  = spSvc.getByshop(shopno);
			req.setAttribute("shopVO", new ShopService().getOneShop(shopno));
			/***************************3.查詢完成,準備轉交(Send the Success view)************/
			req.setAttribute("listShopproduct_ByCompositeQuery", list); 
			RequestDispatcher successView = req.getRequestDispatcher("/shopproduct/shop_product_member_center.jsp"); 
			successView.forward(req, res);
			/*************************** 其他可能的錯誤處理 **********************************/
		} catch (Exception e) {
			errorMsgs.add(e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher("/shopproduct/addSP.jsp");
			failureView.forward(req, res);
		}
	}

	private void doListShopproduct_ByCompositeQuery(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException {
		List<String> errorMsgs = new LinkedList<String>();
		req.setAttribute("errorMsgs", errorMsgs);
		try {
			/***************************1.將輸入資料轉為Map**********************************/ 
			HttpSession session = req.getSession();
			Map<String, String[]> map = (Map<String, String[]>)session.getAttribute("map");
			if (req.getParameter("whichPage") == null){
				HashMap<String, String[]> map1 = (HashMap<String, String[]>)req.getParameterMap();
				HashMap<String, String[]> map2 = new HashMap<String, String[]>();
				map2 = (HashMap<String, String[]>)map1.clone();
				session.setAttribute("map",map2);
				map = (HashMap<String, String[]>)req.getParameterMap();
			} 
			/***************************2.開始複合查詢***************************************/
			ShopproductService spSvc = new ShopproductService();
			List<ShopproductVO> list  = spSvc.getAll(map);
			/***************************3.查詢完成,準備轉交(Send the Success view)************/
			String shopnoStr = req.getParameter("shopno");
			Integer shopno = 0;
			if(shopnoStr != null && shopnoStr.matches("\\d{4,}")){
				shopno = new Integer(shopnoStr);
			}
			req.setAttribute("shopVO", new ShopService().getOneShop(shopno));
			req.setAttribute("listShopproduct_ByCompositeQuery", list);
			RequestDispatcher successView = req.getRequestDispatcher("/shopproduct/shop_product_member_center.jsp"); 
			successView.forward(req, res);
			/***************************其他可能的錯誤處理**********************************/
		} catch (Exception e) {
			errorMsgs.add(e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher("/shopproduct/select_page.jsp");
			failureView.forward(req, res);
		}
	}

	private void doUpdate(HttpServletRequest req, HttpServletResponse res,MultipartRequest multi) throws IOException, ServletException {
		List<String> errorMsgs = new LinkedList<String>();
		req.setAttribute("errorMsgs", errorMsgs);
		try {
			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			Integer spno = null;
			try {
				spno = new Integer(multi.getParameter("spno").trim());
			} catch (NumberFormatException e) {
				errorMsgs.add("請選擇商品編號");
			}
			
			Integer subcatno = null;
			try {
				subcatno = new Integer(multi.getParameter("subcatno").trim());
			} catch (NumberFormatException e) {
				errorMsgs.add("請選擇商店分類");
			}

			Integer isrecomm = null;
			try {
				isrecomm = new Integer(multi.getParameter("isrecomm").trim());
			} catch (NumberFormatException e) {
				errorMsgs.add("請勾選是否推薦此商品");
			}

			Double unitprice = null;
			try {
				unitprice = new Double(multi.getParameter("unitprice").trim());
			} catch (NumberFormatException e) {
				unitprice = 0.0;
				errorMsgs.add("單價請填數字.");
			}
			
			String name = multi.getParameter("name").trim();
			if(name == null || name.length() ==0){
				errorMsgs.add("請輸入商品名稱");
			}				
			String pro_desc = multi.getParameter("pro_desc").trim();
			if(pro_desc == null || pro_desc.length() ==0){
				errorMsgs.add("請輸入商品說明");
			}		
			String spec1 = multi.getParameter("spec1").trim();
			if(spec1 == null || spec1.length() ==0){
				errorMsgs.add("請輸入商品規格");
			}	
			String spec2 = multi.getParameter("spec2").trim();
			String spec3 = multi.getParameter("spec3").trim();

			
			// shopprdouct pic1
			
			byte[] pic1 = null;
			File currentFile1 = multi.getFile("pic1");
			if(currentFile1 != null){
				InputStream in1 = new FileInputStream(currentFile1);
				pic1 = new byte[in1.available()];
				in1.read(pic1);					
				currentFile1.delete();		
				in1.close();
			} else {
				// read shop pic1 from DB
//				ShopproductService spSvc = new ShopproductService();
//				ShopproductVO spVO = spSvc.getOneShopproduct(shopno);
//				pic1 = spVO.getPic1();
			}
			// pic1 type
			String pmime1 = multi.getContentType("pic1");
			
			// shopprdouct pic2
			File currentFile2 = multi.getFile("pic2");
			byte[] pic2 = null;
			if(currentFile2 != null){
				InputStream in2 = new FileInputStream(currentFile2);
				pic2 = new byte[in2.available()];
				in2.read(pic2);					
				currentFile2.delete();		
				in2.close();
			} else {
				// read shop pic2 from DB
//				ShopproductService spSvc = new ShopproductService();
//				ShopproductVO spVO = spSvc.getOneShopproduct(shopno);
//				pic2 = spVO.getPic2();
			}
			// pic2 type
			String pmime2 = multi.getContentType("pic2");
			
			
			// shopprdouct pic3
			File currentFile3 = multi.getFile("pic3");
			byte[] pic3 = null;
			if(currentFile3!= null){
				InputStream in3 = new FileInputStream(currentFile3);
				pic3 = new byte[in3.available()];
				in3.read(pic3);					
				currentFile3.delete();		
				in3.close();
			} else {
				// read shop pic3 from DB
//				ShopproductService spSvc = new ShopproductService();
//				ShopproductVO spVO = spSvc.getOneShopproduct(shopno);
//				pic3 = spVO.getPic3();
			}
			// pic3type
			String pmime3 = multi.getContentType("pic3");
			
			ShopproductVO shopproductVO = new ShopproductVO();
			shopproductVO.setSpno(spno);
//			shopproductVO.setShopno(shopno);
			shopproductVO.setName(name);
			shopproductVO.setUnitprice(unitprice);
			shopproductVO.setPic1(pic1);
			shopproductVO.setPic2(pic2);
			shopproductVO.setPic3(pic3);
			shopproductVO.setPmime1(pmime1);
			shopproductVO.setPmime2(pmime2);
			shopproductVO.setPmime3(pmime3);
			shopproductVO.setPro_desc(pro_desc);
			shopproductVO.setSubcatno(subcatno);
			shopproductVO.setSpec1(spec1);
			shopproductVO.setSpec2(spec2);
			shopproductVO.setSpec3(spec3);
			shopproductVO.setIsrecomm(isrecomm);

			req.setAttribute("shopproductVO", shopproductVO); 
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/shopproduct/update_shop_product_front.jsp");
				failureView.forward(req, res);
				return; 
			}
			/*************************** 2.開始修改資料 *****************************************/
			ShopproductService shopproductSvc = new ShopproductService();
			shopproductVO = shopproductSvc.updateShopproduct(spno, /*shopno,*/
					name, unitprice, pic1, pic2, pic3, pmime1, pmime2,
					pmime3, pro_desc, subcatno, spec1, spec2, spec3,
					isrecomm);
			/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
//			ShopproductService spSvc = new ShopproductService();
//			List<ShopproductVO> list  = spSvc.getByshop(shopno);
//			req.setAttribute("shopVO", new ShopService().getOneShop(shopno));
			ShopproductService spSvc = new ShopproductService();
			ShopproductVO spVO = spSvc.getOneShopproduct(spno);
			List<ShopproductVO> list  = spSvc.getByshop(spVO.getShopno());
			req.setAttribute("shopVO", new ShopService().getOneShop(spVO.getShopno()));
			
			/***************************3.查詢完成,準備轉交(Send the Success view)************/
			req.setAttribute("listShopproduct_ByCompositeQuery", list); 
			RequestDispatcher successView = req.getRequestDispatcher("/shopproduct/shop_product_member_center.jsp"); 
			successView.forward(req, res);
			/*************************** 其他可能的錯誤處理 *************************************/
		} catch (Exception e) {
			errorMsgs.add("修改資料失敗:" + e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher("/shopproduct/update_sp_input.jsp");
			failureView.forward(req, res);
		}
	}
}
