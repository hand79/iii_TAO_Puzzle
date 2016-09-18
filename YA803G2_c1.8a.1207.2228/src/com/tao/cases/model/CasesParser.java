package com.tao.cases.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.tao.jimmy.location.LocationService;
import com.tao.jimmy.location.LocationVO;
import com.tao.jimmy.shop.TinyShopProductService;
import com.tao.jimmy.shop.TinyShopProductVO;
import com.tao.jimmy.util.model.ParamParserWrapper;
import com.tao.member.controller.LocnoAreaConverter;

public class CasesParser extends ParamParserWrapper {
	private static final String[] joinCols = { "county", "shopno" };

	public CasesParser() {
		super(new CasesParameterParser(), joinCols);
	}

	@Override
	protected String getJoinColumnName(String joinParam) {
		String re = null;
		switch (joinParam) {
		case "shopno":
			re = "spno";
			break;
		case "county":
			re = "locno";
			break;
		}
		return re;
	}

	@Override
	protected String[] getJoinedValues(String joinParam, String[] values) {
		List<String> list = new ArrayList<>();
		switch (joinParam) {
		case "shopno":
			if (values != null && values.length != 0) {
				String value = values[0];
				TinyShopProductService spService = new TinyShopProductService();
				try {
					Integer shopno = new Integer(value);
					Set<TinyShopProductVO> set = spService.findByShopNo(shopno);
					for (TinyShopProductVO vo : set) {
						list.add(vo.getSpno().toString());
					}
				} catch (NumberFormatException e) {

				}
			}
			break;
		case "county":
			if (values != null && values.length != 0) {
				String value = values[0];
				String[] townBoundry = value.split("-");
				try {
					Integer locnofrom = new Integer(townBoundry[0]);
					Integer locnoto = new Integer(townBoundry[1]);
					LocationService locService = new LocationService();
					Set<LocationVO> set = locService.findByCounty(locnofrom,
							locnoto);
					for (LocationVO vo : set) {
						list.add(vo.getLocno().toString());
					}
				} catch (Exception e) {

				}
			}
			break;
		case "area":
			if (values != null && values.length != 0) {
				String area = values[0];
				try {
					Integer[] locnos = LocnoAreaConverter.areaToLocno(Integer
							.parseInt(area));
					LocationService locService = new LocationService();
					Set<LocationVO> set = locService.findByCounty(locnos[0],
							locnos[1]);
					for (LocationVO vo : set) {
						list.add(vo.getLocno().toString());
					}
				} catch (Exception e) {

				}
			}
			break;
		}// end of switch

		String[] array = list.toArray(new String[list.size()]);
		return array;
	}

}
