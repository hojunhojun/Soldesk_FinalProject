package com.ung.recipetoyou.market;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ung.recipetoyou.market.search.marketresultBeans;
import com.ung.recipetoyou.market.search.marketsearchBeans;
import com.ung.recipetoyou.market.search.marketsearchDAO;
import com.ung.recipetoyou.market.search.marketsearchRepo;
import com.ung.recipetoyou.market.utils.Navershopsearch;
import com.ung.recipetoyou.recipe.MainRecipeRepo;
import com.ung.recipetoyou.recipe.Recipe;
import com.ung.recipetoyou.recipe.SubRecipe;
import com.ung.recipetoyou.recipe.SubRecipeRepo;
import com.ung.recipetoyou.recipe.TodayRecipe;
import com.ung.recipetoyou.recipe.TodayRecipeRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class MarketDAO {

	@Autowired
	private marketsearchDAO msDAO;

	@Autowired
	private Navershopsearch naverShopSearch;

	@Autowired
	private SubRecipeRepo srr;

	@Autowired
	private MainRecipeRepo mrr;

	@Autowired
	private TodayRecipeRepository trr;

	@Autowired
	private MarketMongoRepo mgr;

	public void getChallengeCommunityPost(HttpServletRequest req) {
		TodayRecipe existingRecipe = trr.findFirstByOrderByNameAsc();
		Recipe today = mrr.findByRcpId(existingRecipe.getRno());
		req.setAttribute("todayRcp", today);

		List<SubRecipe> sc = srr.findByRcpId(today.getRcpId());
		req.setAttribute("stRcp", sc);
		req.setAttribute("trcpma", "market/todaymarketrecipe");
	}

	public void markethomecate(HttpServletRequest req) {
		int startN = 1;
		List<marketBeans>[] mainsearch = new List[7];
		String[] strArr = { "농산물", "수산물", "축산물", "조미료", "제과/제빵재료", "음료", "유제품" };

		for (int i = 0; i < 7; i++) {
			mainsearch[i] = new ArrayList<>();
			String searchResult = naverShopSearch.cheapsearch(strArr[i], startN);
			List<marketBeans> resultSearch = naverShopSearch.fromJSONtoItems(searchResult);
			mainsearch[i].addAll(resultSearch);
		}
		req.setAttribute("cheapresultfarm", mainsearch[0]); // 농산물
		req.setAttribute("cheapresultseafood", mainsearch[1]); // 수산물
		req.setAttribute("cheapresultmeat", mainsearch[2]); // 축산물
		req.setAttribute("cheapresultscn", mainsearch[3]); // 조미료
		req.setAttribute("cheapresultbak", mainsearch[4]); // 제과제빵
		req.setAttribute("cheapresultdrink", mainsearch[5]); // 음료
		req.setAttribute("cheapresultmilk", mainsearch[6]); // 유제품
	}

	public void marketgraphpageshow(HttpServletRequest req) {
		req.setAttribute("contentPage", "market/market");
		req.setAttribute("loginPage", "member/login");
		req.setAttribute("searchingarea", "market/searchresult1");
		req.setAttribute("sidebarcate", "market/sidebar_category");
		req.setAttribute("adesctable", "market/adesctable");
		req.setAttribute("marketsearchresult2", "market/markethome");
		req.setAttribute("cheaper", "market/graph.html");
	}

	public void marketdefaultpageshow(HttpServletRequest req) {
		req.setAttribute("contentPage", "market/market");
		req.setAttribute("loginPage", "member/login");
		req.setAttribute("searchingarea", "market/searchresult1");
		req.setAttribute("sidebarcate", "market/sidebar_category");
		req.setAttribute("adesctable", "market/adesctable");
		req.setAttribute("marketsearchresult2", "market/markethome");
		req.setAttribute("cheaper", "market/cheapercategory");
	}

	public void marketgraphshow(HttpServletRequest req) {
		req.setAttribute("contentPage", "market/market");
		req.setAttribute("loginPage", "member/login");
		req.setAttribute("searchingarea", "market/searchresult1");
		req.setAttribute("sidebarcate", "market/sidebar_category");
		req.setAttribute("adesctable", "market/adesctable");
		req.setAttribute("marketsearchresult2", "market/marketpricepage");
		req.setAttribute("cheaper", "market/cheapercategory");
	}

	public void searchpageshow(HttpServletRequest req) {
		req.setAttribute("marketsearchresult2", "market/searchresult");
		req.setAttribute("showingpage_bar", "market/searchresult_bar");
		req.setAttribute("showingpage_pan", "market/searchresult_pan");
	}

	private MarketMongoDBData convertToMongoData(marketBeans marketBean, String query) {
		MarketMongoDBData mdbd = new MarketMongoDBData();
		mdbd.setRtymid(marketBean.getProductid());
		mdbd.setRtymtt(marketBean.getTitle());
		mdbd.setRtymlnk(marketBean.getLink());
		mdbd.setRtymimg(marketBean.getImg());
		mdbd.setRtymprce(marketBean.getPrice());
		mdbd.setRtymmn(marketBean.getMallname());
		mdbd.setRtymbd(marketBean.getBrand());
		mdbd.setRtymmk(marketBean.getMaker());
		mdbd.setRtymct1(marketBean.getCate1());
		mdbd.setRtymct2(marketBean.getCate2());
		mdbd.setRtymct3(marketBean.getCate3());
		mdbd.setRtymct4(marketBean.getCate4());
		mdbd.setRtymqk(query);
		return mdbd;
	}

	public void marketdtasave(String query) {
		int startN;
		List<marketBeans> allsearch = new ArrayList<>();
		for (startN = 1; startN <= 1000; startN += 100) {
			String searchresult = naverShopSearch.search(query, startN);
			List<marketBeans> resultSearch = naverShopSearch.fromJSONtoItems(searchresult);
			allsearch.addAll(resultSearch);
		}

		List<MarketMongoDBData> dataToSave = new ArrayList<>();
		for (marketBeans marketBean : allsearch) {
			MarketMongoDBData mdbd = convertToMongoData(marketBean, query);
			dataToSave.add(mdbd);
		}
		saveOrUpdateData(dataToSave);
	}

	public void marketdtasavesort(String query, String sortN) {
		int startN;
		List<marketBeans> allsearch = new ArrayList<>();
		for (startN = 1; startN <= 1000; startN += 100) {
			String searchresult = naverShopSearch.adescsearch(query, startN, sortN);
			List<marketBeans> resultSearch = naverShopSearch.fromJSONtoItems(searchresult);
			allsearch.addAll(resultSearch);
		}

		List<MarketMongoDBData> dataToSave = new ArrayList<>();
		for (marketBeans marketBean : allsearch) {
			MarketMongoDBData mdbd = convertToMongoData(marketBean, query);
			dataToSave.add(mdbd);
		}

		saveOrUpdateData(dataToSave);
	}

	private void saveOrUpdateData(List<MarketMongoDBData> dataToSave) {
		try {
			List<MarketMongoDBData> updatedData = new ArrayList<>();
			for (MarketMongoDBData data : dataToSave) {
				MarketMongoDBData existingData = mgr.findByRtymid(data.getRtymid());
				if (existingData != null) {
					existingData.updateFrom(data);
					updatedData.add(existingData);
				} else {
					updatedData.add(data);
				}
			}
			mgr.saveAll(updatedData);
			System.out.println(updatedData.size() + "개의 데이터 저장/업데이트 완료");
		} catch (Exception e) {
			System.err.println("데이터 저장 중 오류 발생: " + e.getMessage());
			e.printStackTrace();
		}
	}

	// 정확도 검색 메소드
	public List<MarketMongoDBData> findPaginatedData(String query, int skip, int limit) {
		Pageable pageable = PageRequest.of(skip / limit, limit);
		Page<MarketMongoDBData> page = mgr.findByRtymqkContainingAndPriceBetweenOrderByPriceAsc(query, pageable);
		return page.getContent();
	}

	// 가격 내림차순
	public List<MarketMongoDBData> findPaginatedDatapdsc(String query, int skip, int limit) {
		Pageable pageable = PageRequest.of(skip / limit, limit);
		Page<MarketMongoDBData> page = mgr.findByRtymqkContainingAndPriceBetweenOrderByRtymprceDesc(query, pageable);
		return page.getContent();
	}

	// 가격 오름차순
	public List<MarketMongoDBData> findPaginatedDatapasc(String query, int skip, int limit) {
		Pageable pageable = PageRequest.of(skip / limit, limit);
		Page<MarketMongoDBData> page = mgr.findByRtymqkContainingAndPriceGreaterThanEqualOrderByRtymprceAsc(query,
				pageable);
		return page.getContent();
	}

	// 등록일자순
	public List<MarketMongoDBData> findPaginatedDataoidesc(String query, int skip, int limit) {
		Pageable pageable = PageRequest.of(skip / limit, limit);
		Page<MarketMongoDBData> page = mgr.findByRtymqkContainingIgnoreCaseOrderByRtymidDesc(query, pageable);
		return page.getContent();
	}

	// 카테고리 검색 메소드
	public List<MarketMongoDBData> findPaginatedDataoicate(String query, int skip, int limit) {
		Pageable pageable = PageRequest.of(skip / limit, limit);
		Page<MarketMongoDBData> page = mgr.findByRtymqkAndRtymct3OrRtymct2ContainingAndPriceLessThanEqual(query,
				pageable);
		return page.getContent();
	}

	public long countByQuery(String query) {
		return mgr.countByRtymttContainingIgnoreCase(query);
	}
}
