package com.tao.news.model;

import java.util.List;
import java.util.Map;

import com.tao.shopproduct.model.ShopproductVO;


public class NewsService {

	private NewsDAO_interface dao;

	public NewsService() {
		dao = new NewsDAO();
	}

	public NewsVO addNews(String title, String text, java.sql.Timestamp pubtime) {

		NewsVO newsVO = new NewsVO();

		newsVO.setTitle(title);
		newsVO.setText(text);
		newsVO.setPubtime(pubtime);
		dao.insert(newsVO);

		return newsVO;
	}

	public NewsVO updateNews(Integer newsno, String title, String text,java.sql.Timestamp pubtime) {

		NewsVO newsVO = new NewsVO();

		newsVO.setNewsno(newsno);
		newsVO.setTitle(title);
		newsVO.setText(text);
		newsVO.setPubtime(pubtime);
		dao.update(newsVO);

		return newsVO;
	}

	public void deleteNews(Integer newsno) {
		dao.delete(newsno);
	}

	public NewsVO getOneNews(Integer newsno) {
		return dao.findByPrimaryKey(newsno);
	}

	public List<NewsVO> getAll() {
		return dao.getAll();
	}
	public List<NewsVO> getAll(Map<String, String[]> map) {
		return dao.getAll(map);
	}
	 public List<NewsVO> getByNews(Integer newsno){
		 return dao.getByNews(newsno);
	 }
}

