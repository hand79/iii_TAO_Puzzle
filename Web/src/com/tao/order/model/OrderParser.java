package com.tao.order.model;

import java.util.Set;

import com.tao.jimmy.member.TinyMemberService;
import com.tao.jimmy.member.TinyMemberVO;
import com.tao.jimmy.util.model.ParamParserWrapper;

public class OrderParser extends ParamParserWrapper {
	private static final String[] joinParams = { "bmem", "cmem" };
	private TinyMemberService tmemSvc = new TinyMemberService();

	public OrderParser() {
		super(new OrderParameterParser(), joinParams);

	}

	@Override
	protected String getJoinColumnName(String joinParam) {
		String re = null;
		if ("bmem".equals(joinParam))
			re = "bmemno";
		if ("cmem".equals(joinParam))
			re = "cmemno";
		return re;
	}

	@Override
	protected String[] getJoinedValues(String joinParam, String[] value) {
		String[] re = null;
		if ("bmem".equals(joinParam) || "cmem".equals(joinParam)) {
			String v = value[0].trim();
			if (isMemNo(v)) {
				TinyMemberVO vo = tmemSvc.getOneByPK(new Integer(v));
				if (vo != null) {
					re = new String[] { vo.getMemno().toString() };
				} else {
					re = new String[] { "0" };
				}
			} else {
				Set<TinyMemberVO> voset = tmemSvc.getSetByIdKeyword(v);
				re = processSet(voset);
			}
		}
		return re;
	}

	private boolean isMemNo(String input) {
		return input.matches("\\d+");
	}

	private String[] processSet(Set<TinyMemberVO> set) {
		String[] re = new String[set.size()];
		int counter = 0;
		for (TinyMemberVO vo : set) {
			re[counter++] = vo.getMemno().toString();
		}
		if (re.length == 0) {
			re = new String[] { "0" };
		}
		return re;
	}
}
