package com.ung.recipetoyou.market.search;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ung.recipetoyou.market.MarketMongoDBData;
import com.ung.recipetoyou.market.SearchDTApriceRepo;
import com.ung.recipetoyou.market.marketresultbeans;
import com.ung.recipetoyou.market.searchDTApriceBeans;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class marketsearchDAO {

	@Autowired
	private SearchDTApriceRepo sdtr;

	@Autowired
	private marketsearchRepo mr;

	@Autowired
	private marketresearchRepo msr;

	public marketsearchBeanslist get() {
		return new marketsearchBeanslist(mr.SQLSELECTER2());
	}

	public String reg(marketsearchBeans mb) {
		try {
			mr.save(mb);
			return "등록성공";
		} catch (Exception e) {
			return "등록실패";
		}
	}

	public void searchdataselect(HttpServletRequest req) {
		List<Object[]> resultList = mr.SQLSELECTER2();
		List<Map<String, Object>> searchData = new ArrayList<>();

		for (Object[] result : resultList) {
			Map<String, Object> map = new HashMap<>();
			map.put("keyword", (String) result[0]);
			map.put("totalSearchCount", ((Number) result[1]).intValue());
			searchData.add(map);
		}
		req.setAttribute("searchData", searchData);
	}

	public void searchdatasave(HttpServletRequest req, marketsearchBeans mb) {
		int counter = 1;

		mb.setSearchCount(counter);
		System.out.println("검색어:" + mb.getMarketsearchingarea() + "+" + mb.getSearchCount());
		mr.save(mb);
	}

	public void analyzDTAsave1(List<marketresultbeans> allsearch, String resultdta, marketresultBeans mrbsa) {
		for (marketresultbeans mrbs : allsearch) {
			// 기존 데이터를 확인하지 않고 새로운 객체를 생성하여 저장
			mrbsa.setRpaschk(resultdta);
			mrbsa.setRpaid(mrbs.getProductid()); // 상품 ID
			mrbsa.setRpatt(mrbs.getTitle()); // 상품 제목
			mrbsa.setRpapr(mrbs.getPrice()); // 가격
			mrbsa.setRpaimg(mrbs.getImg()); // 이미지 URL
			mrbsa.setRpalnk(mrbs.getLink()); // 상품 링크
			// 중복 체크 없이 무조건 새로운 데이터 저장
			msr.save(mrbsa);
			System.out.println("New Data Saved: " + mrbs.getProductid());
		}
	}

	public void priceDTASAVE1(String schkey, searchDTApriceBeans sdtb) {
		List<Object[]> anDTAli = msr.AnalDTASELECTER(schkey);
		for (Object[] result : anDTAli) {
			sdtb.setRmkpschy(result[0].toString());
			sdtb.setRmkptt(result[1].toString());
			sdtb.setRmkpav(new BigDecimal(result[2].toString()).intValue());
			sdtb.setRmkplw(new BigDecimal(result[3].toString()).intValue());
			sdtb.setRmkphg(new BigDecimal(result[4].toString()).intValue());
			sdtb.setRmkppn(new BigDecimal(result[5].toString()).intValue());
			sdtb.setRmkpDCTN(new BigDecimal(result[6].toString()).intValue());
			sdtb.setRmkpdt(new Date());
			sdtr.save(sdtb);
		}
	}

	public void analyzDTAsave(List<marketresultbeans> allsearch, String resultdta, marketresultBeans mrbsa) {
		for (marketresultbeans mrbs : allsearch) {
			// 기존 데이터를 확인하지 않고 새로운 객체를 생성하여 저장
			mrbsa.setRpaschk(resultdta);
			mrbsa.setRpaid(mrbs.getProductid()); // 상품 ID
			mrbsa.setRpatt(mrbs.getTitle()); // 상품 제목
			mrbsa.setRpapr(mrbs.getPrice()); // 가격
			mrbsa.setRpaimg(mrbs.getImg()); // 이미지 URL
			mrbsa.setRpalnk(mrbs.getLink()); // 상품 링크
			// 중복 체크 없이 무조건 새로운 데이터 저장
			msr.save(mrbsa);
			System.out.println("New Data Saved: " + mrbs.getProductid());
		}
	}

	public void analDTAselect(HttpServletRequest req, String userInput) {
	    // 쿼리 실행 결과 가져오기
	    List<Object[]> anDTAli = sdtr.PriceDTaComportprice(userInput);
	    List<Map<String, Object>> analDTA = new ArrayList<>();
	    for (Object[] result : anDTAli) {
	        Map<String, Object> map = new HashMap<>();
	        map.put("inputkey", userInput);

	        // 평균가격: 소수점 둘째 자리까지 반올림
	        map.put("average", roundToTwoDecimalPlaces(((Number) result[0]).doubleValue()));

	        // 최저값: 소수점 둘째 자리까지 반올림
	        map.put("minimum", roundToTwoDecimalPlaces(((Number) result[1]).doubleValue()));

	        // 최고값: 소수점 둘째 자리까지 반올림
	        map.put("maximum", roundToTwoDecimalPlaces(((Number) result[2]).doubleValue()));

	        // 표준편차: 소수점 둘째 자리까지 반올림
	        map.put("rounddev", roundToTwoDecimalPlaces(((Number) result[3]).doubleValue()));

	        // 데이터 개수: 정수로 변환
	        map.put("count", ((Number) result[4]).intValue());

	        analDTA.add(map);
	    }

	    // 결과를 HttpServletRequest에 저장
	    req.setAttribute("analDTA", analDTA);
	}

	// 소수점 둘째 자리까지 반올림하는 메서드
	private double roundToTwoDecimalPlaces(double value) {
	    return Math.round(value * 100.0) / 100.0;
	}


	public void pricecheapersearching(HttpServletRequest req, String cheapInput) {
		List<Object[]> chDTAli = msr.cheapertensearch(cheapInput);
		List<Map<String, Object>> chpDTA = new ArrayList<>();
		for (Object[] ch : chDTAli) {
			Map<String, Object> map = new HashMap<>();
			map.put("ch10_title", (String) ch[0]);
			map.put("ch10_img", (String) ch[1]);
			map.put("ch10_link", (String) ch[2]);
			map.put("ch10_lwp", ((Number) ch[3]).intValue());
			chpDTA.add(map);
		}
		req.setAttribute("chp10DTA", chpDTA);
	}

	public void priceDTASAVE(String schkey, searchDTApriceBeans sdtb) {
		List<Object[]> anDTAli = msr.AnalDTASELECTER(schkey);
		for (Object[] result : anDTAli) {
			sdtb.setRmkpschy(result[0].toString());
			sdtb.setRmkpav(new BigDecimal(result[1].toString()).intValue());
			sdtb.setRmkplw(new BigDecimal(result[2].toString()).intValue());
			sdtb.setRmkphg(new BigDecimal(result[3].toString()).intValue());
			sdtb.setRmkppn(new BigDecimal(result[4].toString()).intValue());
			sdtb.setRmkpDCTN(new BigDecimal(result[5].toString()).intValue());

			sdtb.setRmkpdt(new Date());
			sdtr.save(sdtb);
		}
	}

	public void priceDTAComport(String inputkey, HttpServletRequest req) {
			List<Object[]> pcDTAli = sdtr.PriceDTaComport(inputkey);
			List<Map<String, Object>> pcDTA = new ArrayList<>();
			for (Object[] pcdta : pcDTAli) {
				Map<String, Object> map = new HashMap<>();
				map.put("rmkp_schkey", inputkey);
				map.put("rmkp_Date", (Date) pcdta[0]);
				map.put("rmkp_Aver", (Number) pcdta[1]);
				map.put("rmkp_Low", (Number) pcdta[2]);
				map.put("rmkp_High", (Number) pcdta[3]);
				map.put("rmkp_Pyun", (Number) pcdta[4]);
				map.put("rmkp_DTACT", (Number) pcdta[5]);
				pcDTA.add(map);
			}
			req.setAttribute("priceData", pcDTA);
			System.out.println(pcDTA);
	}
}
