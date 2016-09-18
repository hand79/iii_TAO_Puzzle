package iii.ya803g2.sever;



import iii.ya803g2.membercenter.Membercenter;
import iii.ya803g2.memberdata.Memberdata;
import iii.ya803g2.mycase.JoinCase;
import iii.ya803g2.mycase.MyCase;
import iii.ya803g2.mycase.WishCase;

public class Oracle {
	// 模擬器連Tomcat
		public static String URL = "http://10.120.37.2:8081/YA803G2_c1.8a/";
//		public static String URL = "http://192.168.1.101:8081/TAO_Puzzle_Web/";
		
//		home : AIzaSyBU7w4LTqZYvvUEHoQf89Jbr6sOWkwN-xw
//		school: AIzaSyC1iONEjhnnLFcUReg3O-CvB7YBUQodpoE
//      final:AIzaSyDExalOzY_vq2AC-IFlVofk_l9tBu2JLwc
		// 偏好設定檔案名稱
		public final static String PREF_FILE = "pref";
		
		public final static Category[] CATEGORIES = {
			new Category(0, "會員資訊及評價",  Memberdata.class),
			new Category(1, "查看發起的合購",  MyCase.class),
			new Category(2, "查看訂單",  JoinCase.class),
			new Category(3, "查看追蹤合購案",  WishCase.class)};
		
		
}