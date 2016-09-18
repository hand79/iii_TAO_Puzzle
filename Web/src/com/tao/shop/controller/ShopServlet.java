package com.tao.shop.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.oreilly.servlet.MultipartRequest;
import com.tao.cases.model.CasesService;
import com.tao.cases.model.CasesVO;
import com.tao.jimmy.location.CountyService;
import com.tao.jimmy.location.CountyVO;
import com.tao.jimmy.location.LocationService;
import com.tao.jimmy.location.LocationVO;
import com.tao.jimmy.member.TinyMemberService;
import com.tao.jimmy.member.TinyMemberVO;
import com.tao.jimmy.util.model.ColumnValue;
import com.tao.member.model.MemberService;
import com.tao.member.model.MemberVO;
import com.tao.news.model.NewsService;
import com.tao.news.model.NewsVO;
import com.tao.order.model.OrderService;
import com.tao.order.model.OrderVO;
import com.tao.shop.model.ShopService;
import com.tao.shop.model.ShopVO;
import com.tao.shopRep.model.ShopRepService;
import com.tao.shopRep.model.ShopRepVO;
import com.tao.shopproduct.model.ShopproductService;
import com.tao.shopproduct.model.ShopproductVO;

public class ShopServlet extends HttpServlet {

	private static String index = "/index.jsp";

	@Override
	public void init() throws ServletException {
		super.init();
		index = getServletContext().getContextPath() + index;
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String contentType = req.getContentType();
		String action = null;
		MultipartRequest multi = null;
		if (contentType != null
				&& contentType.startsWith("multipart/form-data")) {

			multi = new MultipartRequest(req, getServletContext().getRealPath(
					"/imgTemp"), 5 * 1024 * 1024, "UTF-8");
			action = multi.getParameter("action");
		} else {
			req.setCharacterEncoding("UTF-8");
			action = req.getParameter("action");
		}


		if ("search".equals(action)) {
			RequestDispatcher successView = req
					.getRequestDispatcher("/shop/index_back.jsp");
			successView.forward(req, res);
			return;
		}

		if ("approve".equals(action)) {
			RequestDispatcher successView = req
					.getRequestDispatcher("/shop/approve_shop_back.jsp");
			successView.forward(req, res);
			return;
		}

		if ("shoprepAdd".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				Integer shopno = new Integer(req.getParameter("shopno"));
				String sreprsn = req.getParameter("sreprsn").trim();
				Integer repno = new Integer(req.getParameter("repno"));
				ShopRepVO shopRepVO = new ShopRepVO();
				shopRepVO.setShopno(shopno);
				shopRepVO.setRepno(repno);
				shopRepVO.setSreprsn(sreprsn);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("shopRepVO", shopRepVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/shop/index_fornt.jsp");
					failureView.forward(req, res);
					return;
				}
				ShopRepService shopSvc = new ShopRepService();
				shopRepVO = shopSvc.addShopRep(shopno, repno, sreprsn);
				doDisplay(req, res);

			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/shop/index_fornt.jsp");
				failureView.forward(req, res);
			}

		}

		if ("getOne_For_Display".equals(action)) {
			doDisplay(req, res);
		}

		if ("getTemp_Display".equals(action)) { // for back admin to display
			if(req.getServletPath().startsWith("/back/")){
				req.setAttribute("i_am_admin", new Object());
			}
			doAllDisplay(req, res);
			
			return;
		}

		if ("getOne_For_Update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
			try {
				/*************************** 1.接收請求參數 ****************************************/
				Integer shopno = new Integer(req.getParameter("shopno"));
				/*************************** 2.開始查詢資料 ****************************************/
				ShopService shopSvc = new ShopService();
				ShopVO shopVO = shopSvc.getOneShop(shopno);
				int locno = shopVO.getLocno();
				CountyService countyService = new CountyService();
				Set<CountyVO> countSet = countyService.findCounties();
				CountyVO countyVO = null;
				for (CountyVO vo : countSet) {
					if (vo.getFrom() <= locno && vo.getTo() >= locno) {
						countyVO = vo;
						break;
					}
				}
				LocationService locationService = new LocationService();
				Set<LocationVO> towns = locationService.findByCounty(
						countyVO.getFrom(), countyVO.getTo());
				LocationVO locvo = new LocationVO();
				locvo.setLocno(shopVO.getLocno());
				req.setAttribute("countyVO", countyVO);
				req.setAttribute("towns", towns);// for select drop-down town
				req.setAttribute("locvo", locvo);// for select drop-down town
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("shopVO", shopVO);
				String url = "/shop/edit_shop_front.jsp";
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

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				Integer shopno = new Integer(multi.getParameter("shopno")
						.trim());
				Integer hits = new Integer(multi.getParameter("hits").trim());
				String fax = multi.getParameter("fax").trim();
				String ship_desc = multi.getParameter("ship_desc").trim();
				String other_desc = multi.getParameter("other_desc").trim();
				String shop_desc = multi.getParameter("shop_desc").trim();

				Integer memno = null;
				try {
					memno = new Integer(multi.getParameter("memno").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("請選擇商店會員帳號");
				}

				Integer locno = null;
				try {
					locno = new Integer(multi.getParameter("locno").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("請輸入地區編號");
				}

				Integer status = null;
				try {
					status = new Integer(multi.getParameter("status").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("請選擇商店狀態");
				}

				String title = multi.getParameter("title").trim();
				if (title == null || title.length() == 0) {
					errorMsgs.add("請輸入商店名稱");
				}

				String addr = multi.getParameter("addr").trim();
				if (addr == null || addr.length() == 0) {
					errorMsgs.add("請輸入商店地址");
				}

				String phone = multi.getParameter("phone").trim();
				if (phone == null || phone.length() == 0) {
					errorMsgs.add("請輸入商店電話");
				}

				String email = multi.getParameter("email").trim();
				if (email == null || email.length() == 0) {
					errorMsgs.add("請輸入商店信箱");
				}

				Double lng = null;
				try {
					lng = new Double(multi.getParameter("lng").trim());
				} catch (NumberFormatException e) {
					lng = 0.0;
					errorMsgs.add("緯度請填數字");
				}

				Double lat = null;
				try {
					lat = new Double(multi.getParameter("lat").trim());
				} catch (NumberFormatException e) {
					lat = 0.0;
					errorMsgs.add("經度請填數字");
				}

				// shop pic
				// Enumeration files = multi.getFileNames();
				// String name = (String) files.nextElement();
//				String filename = multi.getFilesystemName("pic");
				File currentFile = multi.getFile("pic");
				byte[] pic = null;
				if (currentFile != null) {
					InputStream in = new FileInputStream(currentFile);
					pic = new byte[in.available()];
					in.read(pic);
					currentFile.delete();
					in.close();
				} 
				// pictype
				String mime = multi.getContentType("pic");
//				System.out.println("pic mime " + mime);
				ShopVO shopVO = new ShopVO();
				shopVO.setShopno(shopno);
				shopVO.setMemno(memno);
				shopVO.setTitle(title);
				shopVO.setShop_desc(shop_desc);
				shopVO.setPic(pic);
				shopVO.setMime(mime);
				shopVO.setLocno(locno);
				shopVO.setAddr(addr);
				shopVO.setLng(lng);
				shopVO.setLat(lat);
				shopVO.setPhone(phone);
				shopVO.setFax(fax);
				shopVO.setEmail(email);
				shopVO.setShip_desc(ship_desc);
				shopVO.setOther_desc(other_desc);
				shopVO.setHits(hits);
				shopVO.setStatus(status);
				req.setAttribute("shopVO", shopVO);
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/shop/edit_shop_front.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始修改資料 *****************************************/
				ShopService shopSvc = new ShopService();
				shopVO = shopSvc.updateShop(shopno, memno, title, shop_desc,
						pic, mime, locno, addr, lng, lat, phone, fax, email,
						ship_desc, other_desc, hits, status);

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				// HttpSession session = req.getSession();
				// Map<String, String[]> map = (Map<String,
				// String[]>)session.getAttribute("map");
				// List<ShopVO> list = shopSvc.getAll(map);
				// req.setAttribute("listShop_ByCompositeQuery",list);

				req.setAttribute("shopVO", shopVO);
				RequestDispatcher successView = req
						.getRequestDispatcher("/shop/shop_member_center.jsp");
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/shop/edit_shop_front.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				Integer hits = new Integer(multi.getParameter("hits").trim());
				String fax = multi.getParameter("fax").trim();
				String ship_desc = multi.getParameter("ship_desc").trim();
				String other_desc = multi.getParameter("other_desc").trim();
				String shop_desc = multi.getParameter("shop_desc").trim();

				Integer memno = null;
				try {
					memno = new Integer(multi.getParameter("memno").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("請輸入商店會員帳號");
				}

				Integer locno = null;
				try {
					locno = new Integer(multi.getParameter("locno").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("請輸入地區編號");
				}

				Integer status = null;
				try {
					status = new Integer(multi.getParameter("status").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("請輸入商店狀態");
				}

				String title = multi.getParameter("title").trim();
				if (title == null || title.length() == 0) {
					errorMsgs.add("請輸入商店名稱");
				}

				String addr = multi.getParameter("addr").trim();
				if (addr == null || addr.length() == 0) {
					errorMsgs.add("請輸入商店地址");
				}

				String phone = multi.getParameter("phone").trim();
				if (phone == null || phone.length() == 0) {
					errorMsgs.add("請輸入商店電話");
				}

				String email = multi.getParameter("email").trim();
				if (email == null || email.length() == 0) {
					errorMsgs.add("請輸入商店信箱");
				}

				Double lng = null;
				try {
					lng = new Double(multi.getParameter("lng").trim());
				} catch (NumberFormatException e) {
					lng = 0.0;
					errorMsgs.add("緯度請填數字");
				}

				Double lat = null;
				try {
					lat = new Double(multi.getParameter("lat").trim());
				} catch (NumberFormatException e) {
					lat = 0.0;
					errorMsgs.add("經度請填數字");
				}

				// shop pic
				// Enumeration files = multi.getFileNames();
				// String name = (String) files.nextElement();
				File currentFile = multi.getFile("pic");
				byte[] pic = null;
				if (currentFile != null) {
					InputStream in = new FileInputStream(currentFile);
					pic = new byte[in.available()];
					in.read(pic);
					currentFile.delete();
					in.close();
				} else {
					errorMsgs.add("請上傳圖片");
				}

				// pictype
				String mime = multi.getContentType("pic");

				ShopVO shopVO = new ShopVO();
				shopVO.setMemno(memno);
				shopVO.setTitle(title);
				shopVO.setShop_desc(shop_desc);
				shopVO.setPic(pic);
				shopVO.setMime(mime);
				shopVO.setLocno(locno);
				shopVO.setAddr(addr);
				shopVO.setLng(lng);
				shopVO.setLat(lat);
				shopVO.setPhone(phone);
				shopVO.setFax(fax);
				shopVO.setEmail(email);
				shopVO.setShip_desc(ship_desc);
				shopVO.setOther_desc(other_desc);
				shopVO.setHits(hits);
				shopVO.setStatus(status);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("shopVO", shopVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/shop/shop_member_center.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.開始新增資料 ***************************************/
				ShopService shopSvc = new ShopService();
				shopVO = shopSvc.addShop(memno, title, shop_desc, pic, mime,
						locno, addr, lng, lat, phone, fax, email, ship_desc,
						other_desc, hits, status);
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				RequestDispatcher successView = req
						.getRequestDispatcher("/shop/shop_member_center.jsp");
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("新增資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/shop/shop_member_center.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
			String whichPage = req.getParameter("whichPage");
			try {
				/*************************** 1.接收請求參數 ***************************************/
				Integer shopno = new Integer(req.getParameter("shopno"));
				/*************************** 2.開始刪除資料 ***************************************/
				ShopService shopSvc = new ShopService();
				shopSvc.deleteShop(shopno);
				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = requestURL + "?whichPage=" + whichPage;
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}

		if ("getPic".equals(action)) {
			/*************************** 1.接收請求參數 ****************************************/
			Integer shopno = new Integer(req.getParameter("shopno"));
			/*************************** 2.開始查詢資料 ****************************************/
			ShopService shopSvc = new ShopService();
			ShopVO shopVO = shopSvc.getOneShop(shopno);

			byte[] shoppic = shopVO.getPic();
			if (shoppic == null) {
				return;
			}
			res.setContentType(shopVO.getMime());
			ServletOutputStream out = res.getOutputStream();
			out.write(shoppic);
			return;
		}

		if ("update_stauts".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
			String whichPage = req.getParameter("whichPage");
			try {
			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			Integer shopno = new Integer(req.getParameter("shopno"));
			Integer status = new Integer(req.getParameter("status"));
			/*************************** 2.開始修改資料 *****************************************/
			ShopService shopSvc = new ShopService();
			shopSvc.update_statusShop(status, shopno);
			/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
			if (requestURL.equals("/shop/search_shop_page_back.jsp")) {
				HttpSession session = req.getSession();
				Map<String, String[]> map = (Map<String, String[]>) session
						.getAttribute("map");
				List<ShopVO> list = shopSvc.getAll(map);
				req.setAttribute("listShop_ByCompositeQuery", list);
			}

			req.setAttribute("shopno", shopno);

			Map<String, AsyncContext> onlineUsers = (Map<String, AsyncContext>) getServletContext()
					.getAttribute("onlineShopUsers");
			
			if (req.getServletPath().startsWith("/back") && onlineUsers != null) {
				Integer memno = shopSvc.getOneShop(shopno).getMemno();
				if (memno != null) {
					AsyncContext actx = onlineUsers.get(memno.toString());
					if (actx != null) {
						PrintWriter out = actx.getResponse().getWriter(); 
						out.print("success");
						onlineUsers.remove(memno.toString());
						getServletContext().setAttribute("onlineShopUsers",onlineUsers);
						actx.complete();
					}
				}
			}
			String url = requestURL + "?whichPage=" + whichPage;
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);

			/*************************** 其他可能的錯誤處理 *************************************/
			 } catch (Exception e) {
			 errorMsgs.add("修改資料失敗:" + e.getMessage());
			 RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
			 failureView.forward(req, res);
			 }
		}
		
		if ("push".equals(action)) {
			System.out.println("push");
			MemberVO memvo = (MemberVO) req.getSession().getAttribute(
					"CurrentUser");
			if (memvo == null || memvo.getType() != 1) {
				return;
			}

			ServletContext application = getServletContext();
			Map<String, AsyncContext> onlineUsers = (Map<String, AsyncContext>) application
					.getAttribute("onlineShopUsers");
			if (onlineUsers == null) {
				onlineUsers = new HashMap<String, AsyncContext>();
			}
			AsyncContext actx = req.startAsync(req, res);
			actx.setTimeout(900000000);
			AsyncContext oldActx = onlineUsers.get(memvo.getMemno().toString());
			if (oldActx != null) {
				oldActx.complete();
				onlineUsers.remove(memvo.getMemno().toString());
			}
			onlineUsers.put(memvo.getMemno().toString(), actx);
			application.setAttribute("onlineShopUsers", onlineUsers);
			System.out.println("added");
			return;
		}
		
		if ("listShop_ByCompositeQuery".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.將輸入資料轉為Map **********************************/
				HttpSession session = req.getSession();
				Map<String, String[]> map = (Map<String, String[]>) session
						.getAttribute("map");
				if (req.getParameter("whichPage") == null) {
					HashMap<String, String[]> map1 = (HashMap<String, String[]>) req
							.getParameterMap();
					HashMap<String, String[]> map2 = new HashMap<String, String[]>();
					map2 = (HashMap<String, String[]>) map1.clone();
					session.setAttribute("map", map2);
					map = (HashMap<String, String[]>) req.getParameterMap();
				}
				/*************************** 2.開始複合查詢 ***************************************/
				ShopService shopSvc = new ShopService();
				List<ShopVO> list = shopSvc.getAll(map);
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("listShop_ByCompositeQuery", list);
				RequestDispatcher successView = req
						.getRequestDispatcher("/shop/search_shop_page_back.jsp");
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/shop/search_shop_page_back.jsp");
				failureView.forward(req, res);
			}
		}

		if ("queryAll".equals(action)) {
			NewsService newsSvc = new NewsService();
			List<NewsVO> queryAll = newsSvc.getAll();
			req.setAttribute("queryAll", queryAll);
			RequestDispatcher successView = req
					.getRequestDispatcher("/shop/index_back.jsp");
			successView.forward(req, res);
		}
	}

	private void doAllDisplay(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		System.out.println("doAllDisplay");
		List<String> errorMsgs = new LinkedList<String>();
		req.setAttribute("errorMsgs", errorMsgs);
		try {
			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			String str = req.getParameter("shopno");
			if (str == null || (str.trim()).length() == 0) {
				errorMsgs.add("此商店不存在");
			}

			Integer shopno = new Integer(str);

			/*************************** 2.開始查詢資料 *****************************************/
			ShopService shopSvc = new ShopService();
			ShopVO shopVO = shopSvc.getOneShop(shopno);

			Integer memno = shopVO.getMemno();
			MemberService memberSvc = new MemberService();
			MemberVO memberVO = memberSvc.findByPrimaryKeyNoPic(memno);

			ShopproductService spSvc = new ShopproductService();
			List<ShopproductVO> spVOs = (List<ShopproductVO>) spSvc
					.getByshop(shopno);
			List<Integer> spnoList = new ArrayList<Integer>();
			for (ShopproductVO vo : spVOs) {
				spnoList.add(vo.getSpno());
			}
			CasesService casesSvc = new CasesService();
			Set<CasesVO> casesVOSet = casesSvc.getByShopProductNums(spnoList
					.toArray(new Integer[spnoList.size()]));

			List<CasesVO> casesVOList = new ArrayList<>();
			for (CasesVO vo : casesVOSet) {
				int i = vo.getStatus();
				if (i == CasesVO.STATUS_PUBLIC) {
					casesVOList.add(vo);
				}
			}
			OrderService ordSvc = new OrderService();
			List<OrderVO> orderList = ordSvc.compositeQuery(new ColumnValue(
					"crate", false), new ColumnValue("cmemno", shopVO
					.getMemno().toString()));
			if (!errorMsgs.isEmpty()) {
				res.setContentType("text/html;charset=UTF-8");
				res.getWriter().print("<script>window.onload=function(){alert('此商店已下架 / 刪除');window.location.href=\""+ req.getContextPath() + "\";}</script>");
				return;
			}
			/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
			req.setAttribute("shopVO", shopVO);
			req.setAttribute("memberVO", memberVO);
			req.setAttribute("spVOs", spVOs);
			req.setAttribute("orderList", orderList);
			req.setAttribute("casesVOList", casesVOList);
			String url = "/shop/preview_index_front.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);

			/*************************** 其他可能的錯誤處理 *************************************/
		} catch (Exception e) {
			errorMsgs.add("無法取得資料:" + e.getMessage());
			res.setContentType("text/html;charset=UTF-8");
			res.getWriter().print("<script>window.onload=function(){alert('此商店已下架 / 刪除');window.location.href=\""+ req.getContextPath() + "\";}</script>");
		}
	}

	private void doDisplay(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		List<String> errorMsgs = new LinkedList<String>();
		req.setAttribute("errorMsgs", errorMsgs);
		try {
			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			String str = req.getParameter("shopno");
			if (str == null || (str.trim()).length() == 0) {
				errorMsgs.add("此商店不存在");
			}
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher(index);
				failureView.forward(req, res);
				return;
			}

			Integer shopno = new Integer(str);

			/*************************** 2.開始查詢資料 *****************************************/
			ShopService shopSvc = new ShopService();
			ShopVO shopVO = shopSvc.getCheckStatus(shopno);

			Integer memno = shopVO.getMemno();
			MemberService memberSvc = new MemberService();
			MemberVO memberVO = memberSvc.findByPrimaryKeyNoPic(memno);

			ShopproductService spSvc = new ShopproductService();
			List<ShopproductVO> spVOs = (List<ShopproductVO>) spSvc
					.getByshop(shopno);
			List<Integer> spnoList = new ArrayList<Integer>();
			for (ShopproductVO vo : spVOs) {
				spnoList.add(vo.getSpno());
			}
			CasesService casesSvc = new CasesService();
			Set<CasesVO> casesVOSet = casesSvc.getByShopProductNums(spnoList
					.toArray(new Integer[spnoList.size()]));

			List<CasesVO> casesVOList = new ArrayList<>();
			List<CasesVO> overedCasesVOList = new ArrayList<>();

			for (CasesVO vo : casesVOSet) {
				int i = vo.getStatus();
				if (i == CasesVO.STATUS_PUBLIC) {
					casesVOList.add(vo);
				} else if (i == CasesVO.STATUS_COMPLETED
						|| i == CasesVO.STATUS_COMPLETED) {
					overedCasesVOList.add(vo);
				}
			}
			OrderService ordSvc = new OrderService();
			List<OrderVO> orderList = ordSvc.compositeQuery(new ColumnValue(
					"crate", false), new ColumnValue("cmemno", shopVO
					.getMemno().toString()));
			int Goodcrate = 0, Normalcrate = 0, BadCount = 0;
			for (OrderVO o : orderList) {
				switch (o.getCrate()) {
				case 0:
					BadCount++;
					break;
				case 1:
					Normalcrate++;
					break;
				case 2:
					Goodcrate++;
					break;
				default:
					break;
				}
			}
			req.setAttribute("Goodcrate", Goodcrate);
			req.setAttribute("Normalcrate", Normalcrate);
			req.setAttribute("BadCount", BadCount);
			if (!errorMsgs.isEmpty()) {
				res.setContentType("text/html;charset=UTF-8");
				res.getWriter().print(
						"<script>window.onload=function(){alert('此商店已下架 / 刪除');window.location.href=\""
								+ req.getContextPath() + "\";}</script>");
				return;
			}
			/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
			req.setAttribute("shopVO", shopVO);
			req.setAttribute("memberVO", memberVO);
			req.setAttribute("spVOs", spVOs);
			req.setAttribute("orderList", orderList);
			req.setAttribute("casesVOList", casesVOList);
			req.setAttribute("overedCasesVOList", overedCasesVOList);
			String url = "/shop/index_front.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);

			/*************************** 其他可能的錯誤處理 *************************************/
		} catch (Exception e) {
			errorMsgs.add("無法商店取得資料:" + e.getMessage());
			res.setContentType("text/html;charset=UTF-8");
			res.getWriter().print("<script>window.onload=function(){alert('此商店已下架 / 刪除');window.location.href=\""+ req.getContextPath() + "\";}</script>");

		}

	}
}
