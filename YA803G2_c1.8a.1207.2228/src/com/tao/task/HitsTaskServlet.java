package com.tao.task;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.tao.cases.model.CasesService;

public class HitsTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HashMap<Integer, Integer> caseHitsMap;
	HashMap<Integer, Integer> shopHitsMap;
	Timer timer;   
   
    public HitsTaskServlet() {
        super();
        caseHitsMap = new HashMap<>();
		shopHitsMap = new HashMap<>();
    }

	
	public void init(ServletConfig config) throws ServletException {
		super.init();
		ServletContext context = config.getServletContext();
		if ("off".equals(config.getInitParameter("EnableHitsTask"))) {
			return;
		}
		context.setAttribute("CaseHitsMap", caseHitsMap);
		context.setAttribute("ShopHitsMap", shopHitsMap);

		GregorianCalendar calendar = new GregorianCalendar();

		TimerTask timerTask = new TimerTask() {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				HashMap<Integer, Integer> cMap = (HashMap<Integer, Integer>) caseHitsMap.clone();
				HashMap<Integer, Integer> sMap = (HashMap<Integer, Integer>) shopHitsMap.clone();
				caseHitsMap.clear();
				shopHitsMap.clear();
				CasesService casesService = new CasesService();
				HitsShops hitsShops = new HitsShops();
				int count = casesService.updateCasesHits(cMap);
				System.out.println("HitsTask: update case hits, update count: "  + count);
				int count2 = hitsShops.updateHits(sMap);
				System.out.println("HitsTask: update shop hits, update count: "  + count2);
			}
		};
		timer = new Timer();
		timer.scheduleAtFixedRate(timerTask, calendar.getTime(), 1000 * 60 * 15);
		System.out.println("HitsTaskServlet: task scheduled at fixed rate: 15 minutes");
	}

	
	public void destroy() {
		super.destroy();
		CasesService casesService = new CasesService();
		HitsShops hitsShops = new HitsShops();
		casesService.updateCasesHits(caseHitsMap);
		hitsShops.updateHits(shopHitsMap);
		if(timer != null){
			timer.cancel();
			System.out.println("HitsTaskServlet: task canceled");
		}
		
	}

}
