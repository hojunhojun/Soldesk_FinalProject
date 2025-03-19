package com.ung.recipetoyou.market;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ung.recipetoyou.market.search.marketresearchRepo;
import com.ung.recipetoyou.market.search.marketresultBeans;
import com.ung.recipetoyou.market.search.marketsearchDAO;
import com.ung.recipetoyou.market.search.marketsearchRepo;
import com.ung.recipetoyou.market.utils.Navershopsearch;
import com.ung.recipetoyou.member.MemberDAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
// <canvas id="priceChart"></canvas>
@Controller
public class SearchRestController {

	private static final Logger logger = LoggerFactory.getLogger(SearchRestController.class);

	@Autowired
	private MemberDAO mbDAO;

	@Autowired
	private MarketDAO mDAO;

	@Autowired
	private marketresearchRepo msr;

	@Autowired
	private marketsearchDAO msDAO;

	@Autowired
	private Navershopsearch naverShopSearch;

	@Autowired
	private SearchDTApriceRepo sdtr;

	@Autowired
	private MarketMongoRepo MMr;
	
	@Autowired
	private PRepo pRepo;
	
	@Autowired
	private P2Repo p2Repo;

	@GetMapping("/marketgraph.go")
	public String marketGRPH(@RequestParam(name = "searchkeyaa") String resultdata, HttpServletRequest req) {
		mDAO.marketgraphshow(req);
		msDAO.priceDTAComport(resultdata, req);
		mbDAO.isLogined(req);
		return "index";
	}
	
	private PBEANS convertToPBEANS(marketBeans mb) {
	    PBEANS pb = new PBEANS();
	    pb.setProductId(mb.getProductid());
	    pb.setPrice(mb.getPrice());
	    pb.setTitle(mb.getTitle());
	    pb.setLink(mb.getLink());
	    pb.setImage(mb.getImg());
	    pb.setMallName(mb.getMallname());
	    pb.setBrand(mb.getBrand());
	    pb.setMaker(mb.getMaker());
	    pb.setCategory1(mb.getCate1());
	    pb.setCategory2(mb.getCate2());
	    pb.setCategory3(mb.getCate3());
	    pb.setCategory4(mb.getCate4());
	    pb.setRegisteredDate(new Date()); // 현재 날짜로 설정
	    return pb;
	}
	
	@Scheduled(cron = "0 0 0 * * *")
	public void autosearch() throws InterruptedException {
		List<String> foodKeywords = Arrays.asList(
	            "농산물", "수산물", "축산물", "조미료", "제과제빵", "음료", "유제품",
	            "견과류", "채소", "과일", "잡곡/혼합곡", "쌀", "건과류", "냉동과일",
	            "김/해초", "건어물", "생선", "해산물/어패류", "젓갈/장류", "수산가공식품",
	            "축산가공식품", "쇠고기", "닭고기", "돼지고기", "오리고기", "계란", "양고기", "기타육류",
	            "기타조미료", "소금", "식초", "설탕", "후추", "물엿/올리고당", "고춧가루", "천연감미료", 
	            "액젓", "고추냉이", "맛가루/후리카케", "겨자",
	            "기타/제빵재료", "제과/제빵믹스", "베이킹파우더", "호떡믹스",
	            "차", "커피", "탄산", "주스", "건강/기능성", "생수", "전통차", "우유/요거트", "두유", "탄산수", "코코아",
	            "치즈", "버터", "휘핑크림", "생크림", "연유", "마가린", "파우더/스무디",
	            "스낵", "냉동/간편조리식품", "다이어트식품", "통조림/캔", "밀키트", "반찬", "라면/면류", "식용유/오일"
	        );
		
		int startN = 1;
		List<marketBeans> allsearch = new ArrayList<>();
		for (String keywords : foodKeywords) {
				String searchresult = naverShopSearch.searchsim2(keywords, startN);
				List<marketBeans> resultSearch = naverShopSearch.fromJSONtoItems(searchresult);
				allsearch.addAll(resultSearch); // 리스트에 저장
				Thread.sleep(200);
		}
		for (marketBeans mb : allsearch) {
	        PBEANS pb = convertToPBEANS(mb);
	        pRepo.save(pb);
	    }
		pRepo.insertCurrentPriceHistory();
		System.out.println("history save");
	}
	
	@GetMapping("dataresultch")
	public String DTAresultch(HttpServletRequest req) {
		//기본 필수 반환 요소
		mDAO.markethomecate(req);
		mDAO.getChallengeCommunityPost(req);
		mDAO.marketgraphshow(req); //그래프 페이지 할당
		mbDAO.isLogined(req);
		
		List<P2BEANS> resultSearch = p2Repo.findAll(); //PRICE_HISTORY 테이블의 값을 가져와서 리스트로 저장?
		//[P2BEANS(productId=10604243896, priceDate=2025-03-17, lprice=2000.0, hprice=62820.0, avgprice=17182.4, category2=농산물, category3=잡곡/혼합곡, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=48948343921, priceDate=2025-03-17, lprice=11160.0, hprice=98900.0, avgprice=34376.98, category2=축산물, category3=기타육류, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=19127130621, priceDate=2025-03-17, lprice=1900.0, hprice=34000.0, avgprice=10619.8, category2=제과/제빵재료, category3=호떡믹스, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=12653421063, priceDate=2025-03-17, lprice=2800.0, hprice=109900.0, avgprice=15916.8, category2=유가공품, category3=휘핑크림, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=52611019899, priceDate=2025-03-17, lprice=1300.0, hprice=81500.0, avgprice=23933.8, category2=유가공품, category3=연유, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=51929177907, priceDate=2025-03-17, lprice=26580.0, hprice=26580.0, avgprice=26580.0, category2=다이어트식품, category3=카테킨, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=51929031056, priceDate=2025-03-17, lprice=12600.0, hprice=32600.0, avgprice=22600.0, category2=다이어트식품, category3=가르시니아, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=35688250537, priceDate=2025-03-17, lprice=5900.0, hprice=44000.0, avgprice=15751.54, category2=반찬, category3=장아찌, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=82643974264, priceDate=2025-03-17, lprice=6100.0, hprice=17500.0, avgprice=11800.0, category2=반찬, category3=단무지, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=11545019968, priceDate=2025-03-17, lprice=2000.0, hprice=74900.0, avgprice=21131.9, category2=수산물, category3=해산물/어패류, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=10561748546, priceDate=2025-03-17, lprice=6480.0, hprice=104000.0, avgprice=24465.49, category2=수산물, category3=생선, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=22581026814, priceDate=2025-03-17, lprice=630.0, hprice=21400.0, avgprice=11015.0, category2=음료, category3=건강/기능성음료, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=10658713309, priceDate=2025-03-17, lprice=1000.0, hprice=85900.0, avgprice=24519.8, category2=농산물, category3=견과류, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=11731591817, priceDate=2025-03-17, lprice=19750.0, hprice=75170.0, avgprice=39186.6, category2=농산물, category3=쌀, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=10075922649, priceDate=2025-03-17, lprice=4900.0, hprice=98000.0, avgprice=21702.0, category2=수산물, category3=젓갈/장류, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=10090513956, priceDate=2025-03-17, lprice=6700.0, hprice=149950.0, avgprice=32011.6, category2=축산물, category3=쇠고기, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=10139459611, priceDate=2025-03-17, lprice=740.0, hprice=75000.0, avgprice=15115.8, category2=조미료, category3=고추냉이, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=10001473034, priceDate=2025-03-17, lprice=2560.0, hprice=135000.0, avgprice=24430.4, category2=음료, category3=차류, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=25071984902, priceDate=2025-03-17, lprice=2540.0, hprice=32900.0, avgprice=11599.2, category2=음료, category3=코코아, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=11726153888, priceDate=2025-03-17, lprice=9620.0, hprice=46900.0, avgprice=26542.86, category2=다이어트식품, category3=콜라겐, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=52575847907, priceDate=2025-03-17, lprice=2600.0, hprice=53500.0, avgprice=24587.0, category2=밀키트, category3=찌개/국, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=21787312984, priceDate=2025-03-17, lprice=6000.0, hprice=29000.0, avgprice=11828.57, category2=반찬, category3=기타반찬류, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=34794061095, priceDate=2025-03-17, lprice=10150.0, hprice=10150.0, avgprice=10150.0, category2=반찬, category3=볶음류, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=10062231804, priceDate=2025-03-17, lprice=10.0, hprice=48400.0, avgprice=12944.14, category2=음료, category3=커피, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=10175487922, priceDate=2025-03-17, lprice=3700.0, hprice=48000.0, avgprice=14003.4, category2=축산물, category3=돼지고기, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=10538139568, priceDate=2025-03-17, lprice=2100.0, hprice=82000.0, avgprice=17238.3, category2=축산물, category3=오리고기, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=12896772050, priceDate=2025-03-17, lprice=820.0, hprice=56500.0, avgprice=13754.6, category2=조미료, category3=설탕, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=36076002826, priceDate=2025-03-17, lprice=780.0, hprice=68600.0, avgprice=14110.6, category2=조미료, category3=액젓, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=14381313642, priceDate=2025-03-17, lprice=600.0, hprice=156000.0, avgprice=8833.4, category2=조미료, category3=겨자, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=10613547965, priceDate=2025-03-17, lprice=1480.0, hprice=279000.0, avgprice=25643.8, category2=냉동/간편조리식품, category3=기타냉동/간편조리식품, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=31426059890, priceDate=2025-03-17, lprice=9440.0, hprice=10730.0, avgprice=10085.0, category2=반찬, category3=장조림, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=10249949099, priceDate=2025-03-17, lprice=2700.0, hprice=56000.0, avgprice=15234.2, category2=농산물, category3=냉동과일, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=10518940867, priceDate=2025-03-17, lprice=1200.0, hprice=89000.0, avgprice=11584.2, category2=조미료, category3=후추, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=15064208279, priceDate=2025-03-17, lprice=1480.0, hprice=59000.0, avgprice=11818.2, category2=제과/제빵재료, category3=제과/제빵믹스, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=10645684827, priceDate=2025-03-17, lprice=590.0, hprice=41000.0, avgprice=18891.2, category2=유가공품, category3=마가린, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=85241896798, priceDate=2025-03-17, lprice=22840.0, hprice=54500.0, avgprice=38098.57, category2=다이어트식품, category3=기타다이어트식품, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=51929234152, priceDate=2025-03-17, lprice=21210.0, hprice=21210.0, avgprice=21210.0, category2=다이어트식품, category3=시서스, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=11051043361, priceDate=2025-03-17, lprice=11900.0, hprice=58900.0, avgprice=27770.2, category2=농산물, category3=과일, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=38274152030, priceDate=2025-03-17, lprice=980.0, hprice=104300.0, avgprice=21227.24, category2=음료, category3=주스/과즙음료, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=10899652689, priceDate=2025-03-17, lprice=2800.0, hprice=69000.0, avgprice=15487.8, category2=농산물, category3=건과류, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=12152340148, priceDate=2025-03-17, lprice=1750.0, hprice=77000.0, avgprice=23235.6, category2=축산물, category3=닭고기, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=11148207677, priceDate=2025-03-17, lprice=5000.0, hprice=40000.0, avgprice=17794.4, category2=축산물, category3=알류, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=50312510430, priceDate=2025-03-17, lprice=17410.0, hprice=449500.0, avgprice=103941.43, category2=주방용품, category3=칼/커팅기구, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=10537544536, priceDate=2025-03-17, lprice=2270.0, hprice=105000.0, avgprice=26118.6, category2=조미료, category3=물엿/올리고당, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=10717209224, priceDate=2025-03-17, lprice=530.0, hprice=12300.0, avgprice=3480.6, category2=제과/제빵재료, category3=베이킹파우더, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=46604065536, priceDate=2025-03-17, lprice=940.0, hprice=29900.0, avgprice=13527.5, category2=라면/면류, category3=면류, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=10095709765, priceDate=2025-03-17, lprice=520.0, hprice=234000.0, avgprice=20631.96, category2=축산물, category3=축산가공식품, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=10021871836, priceDate=2025-03-17, lprice=20.0, hprice=560000.0, avgprice=23946.4, category2=주방용품, category3=제과/제빵용품, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=13715116842, priceDate=2025-03-17, lprice=670.0, hprice=21300.0, avgprice=10430.57, category2=음료, category3=청량/탄산음료, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=10636479375, priceDate=2025-03-17, lprice=2200.0, hprice=530060.0, avgprice=17813.0, category2=음료, category3=우유/요거트, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=10717019679, priceDate=2025-03-17, lprice=2950.0, hprice=59500.0, avgprice=17881.63, category2=수산물, category3=수산가공식품, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=12716398079, priceDate=2025-03-17, lprice=1240.0, hprice=75000.0, avgprice=22432.8, category2=조미료, category3=소금, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=11038269829, priceDate=2025-03-17, lprice=1150.0, hprice=88790.0, avgprice=18815.2, category2=조미료, category3=식초, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=12035621247, priceDate=2025-03-17, lprice=670.0, hprice=24000.0, avgprice=4569.4, category2=조미료, category3=맛가루/후리가케, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=11359281008, priceDate=2025-03-17, lprice=1110.0, hprice=55800.0, avgprice=22953.4, category2=음료, category3=두유, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=10636352528, priceDate=2025-03-17, lprice=2700.0, hprice=98000.0, avgprice=17412.2, category2=유가공품, category3=치즈, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=26711168556, priceDate=2025-03-17, lprice=160.0, hprice=154000.0, avgprice=24353.0, category2=유가공품, category3=버터, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=10620003279, priceDate=2025-03-17, lprice=14800.0, hprice=105900.0, avgprice=44476.52, category2=다이어트식품, category3=단백질보충제, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=10658407706, priceDate=2025-03-17, lprice=5170.0, hprice=16890.0, avgprice=10986.67, category2=반찬, category3=절임류, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=10255870442, priceDate=2025-03-17, lprice=6000.0, hprice=60000.0, avgprice=19738.8, category2=수산물, category3=김/해초, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=11925385109, priceDate=2025-03-17, lprice=8700.0, hprice=139000.0, avgprice=28341.2, category2=축산물, category3=양고기, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=10651629020, priceDate=2025-03-17, lprice=4280.0, hprice=40000.0, avgprice=17735.2, category2=조미료, category3=고춧가루, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=12530525837, priceDate=2025-03-17, lprice=4540.0, hprice=40200.0, avgprice=12555.6, category2=조미료, category3=천연감미료, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=18733247051, priceDate=2025-03-17, lprice=9700.0, hprice=189700.0, avgprice=56581.48, category2=건강식품, category3=영양제, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=13943289834, priceDate=2025-03-17, lprice=2500.0, hprice=41900.0, avgprice=12266.6, category2=음료, category3=탄산수, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=10717119307, priceDate=2025-03-17, lprice=2650.0, hprice=60000.0, avgprice=9747.2, category2=유가공품, category3=생크림, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=11514282682, priceDate=2025-03-17, lprice=4000.0, hprice=48000.0, avgprice=13009.4, category2=음료, category3=파우더/스무디, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=10058251702, priceDate=2025-03-17, lprice=54100.0, hprice=54900.0, avgprice=54500.0, category2=냉동/간편조리식품, category3=도시락, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=87211744544, priceDate=2025-03-17, lprice=22900.0, hprice=22900.0, avgprice=22900.0, category2=다이어트식품, category3=식이섬유, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=6364989143, priceDate=2025-03-17, lprice=2900.0, hprice=159000.0, avgprice=38913.33, category2=반찬, category3=반찬세트, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=11188204508, priceDate=2025-03-17, lprice=4680.0, hprice=100000.0, avgprice=25250.4, category2=식용유/오일, category3=기타기름, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=10797280838, priceDate=2025-03-17, lprice=3800.0, hprice=24800.0, avgprice=10433.57, category2=농산물, category3=채소, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=10582874854, priceDate=2025-03-17, lprice=890.0, hprice=677010.0, avgprice=54446.8, category2=조미료, category3=기타조미료, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=10683643770, priceDate=2025-03-17, lprice=5900.0, hprice=62540.0, avgprice=23706.6, category2=수산물, category3=건어물, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=13543029806, priceDate=2025-03-17, lprice=10.0, hprice=99900.0, avgprice=16076.8, category2=음료, category3=생수, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=13356636552, priceDate=2025-03-17, lprice=590.0, hprice=30000.0, avgprice=10070.88, category2=과자/베이커리, category3=스낵, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=83101676458, priceDate=2025-03-17, lprice=82400.0, hprice=82400.0, avgprice=82400.0, category2=냉동/간편조리식품, category3=샐러드, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=10491659485, priceDate=2025-03-17, lprice=450.0, hprice=65600.0, avgprice=13944.8, category2=통조림/캔, category3=기타통조림/캔, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=40007393390, priceDate=2025-03-17, lprice=7900.0, hprice=24900.0, avgprice=17884.43, category2=반찬, category3=무침류, createdAt=2025-03-17 12:40:33.0), P2BEANS(productId=45843466596, priceDate=2025-03-17, lprice=860.0, hprice=37600.0, avgprice=15543.16, category2=라면/면류, category3=라면, createdAt=2025-03-17 12:40:33.0)]
		req.setAttribute("PriceChart", resultSearch); //이걸로 HTML에서 차트 표시 
		
		return "index";
		//////////////////
	}
	
	@GetMapping("/go.searchranksearch")
	public String searchresult1(@RequestParam(name = "SR_inputarea") String rresult,
			@RequestParam(name = "adesc_area", required = false) String sortN,
			@RequestParam(defaultValue = "1") int page, HttpServletRequest req) {
		mDAO.marketdtasave(rresult);

		int pageSize = 100;
		int skip = (page - 1) * pageSize;

		long totalItems = mDAO.countByQuery(rresult);
		int totalPages = (int) Math.ceil((double) totalItems / pageSize);

		page = Math.max(1, Math.min(page, totalPages));

		int windowSize = 10;
		int startPage = Math.max(1, page - (windowSize / 2));
		int endPage = Math.min(totalPages, startPage + windowSize - 1);

		if (startPage > 1) {
			endPage = Math.min(totalPages, startPage + windowSize - 1);
		}

		List<MarketMongoDBData> pageSearch = mDAO.findPaginatedData(rresult, skip, pageSize);

		req.setAttribute("query", rresult);
		req.setAttribute("ssresult", pageSearch);
		req.setAttribute("currentPage", page);
		req.setAttribute("totalPages", totalPages);
		req.setAttribute("totalItems", totalItems);
		req.setAttribute("sortN", "sim");
		req.setAttribute("startPage", startPage);
		req.setAttribute("endPage", endPage);
		
		mDAO.marketdefaultpageshow(req);
		mDAO.searchpageshow(req);
		mbDAO.isLogined(req);
		return "index";
	}

	@GetMapping("/go.adescsearch")
	public String adesctablesearch(@RequestParam(name = "adesc_input_area") String query,
			@RequestParam(name = "adesc_area", required = false) String sortN,
			@RequestParam(defaultValue = "1") int page, HttpServletRequest req, HttpSession session) {

		if (sortN == null) {
			sortN = (String) session.getAttribute("sortN");
			if (sortN == null) {
				sortN = "default";
			}
		} else {
			session.setAttribute("sortN", sortN);
		}

		mDAO.marketdtasavesort(query, sortN);

		int pageSize = 100;
		int skip = (page - 1) * pageSize;

		long totalItems = mDAO.countByQuery(query);
		int totalPages = (int) Math.ceil((double) totalItems / pageSize);

		page = Math.max(1, Math.min(page, totalPages));

		int windowSize = 10;
		int startPage = Math.max(1, page - (windowSize / 2));
		int endPage = Math.min(totalPages, startPage + windowSize - 1);

		if (startPage > 1) {
			endPage = Math.min(totalPages, startPage + windowSize - 1);
		}

		List<MarketMongoDBData> pageSearch;

		if ("dsc".equals(sortN)) {
			pageSearch = mDAO.findPaginatedDatapdsc(query, skip, pageSize);
		} else if ("asc".equals(sortN)) {
			pageSearch = mDAO.findPaginatedDatapasc(query, skip, pageSize);
		} else if ("date".equals(sortN)) {
			pageSearch = mDAO.findPaginatedDataoidesc(query, skip, pageSize);
		} else {
			pageSearch = mDAO.findPaginatedData(query, skip, pageSize);
		}

		req.setAttribute("query", query);
		req.setAttribute("ssresult", pageSearch);
		req.setAttribute("currentPage", page);
		req.setAttribute("totalPages", totalPages);
		req.setAttribute("totalItems", totalItems);
		req.setAttribute("sortN", sortN);
		req.setAttribute("startPage", startPage);
		req.setAttribute("endPage", endPage);

		mDAO.marketdefaultpageshow(req);
		mDAO.searchpageshow(req);
		mbDAO.isLogined(req);
		return "index";
	}

	@GetMapping("/marketsearchdatacate.go")
	public String catesearchresult(@RequestParam(name = "cate_searching_area") String query,
			@RequestParam(name = "adesc_area", required = false) String sortN,
			@RequestParam(defaultValue = "1") int page, HttpServletRequest req) {
		mDAO.marketdtasave(query);

		int pageSize = 100;
		int skip = (page - 1) * pageSize;

		long totalItems = mDAO.countByQuery(query);
		int totalPages = (int) Math.ceil((double) totalItems / pageSize);

		page = Math.max(1, Math.min(page, totalPages));

		int windowSize = 10;
		int startPage = Math.max(1, page - (windowSize / 2));
		int endPage = Math.min(totalPages, startPage + windowSize - 1);

		if (startPage > 1) {
			endPage = Math.min(totalPages, startPage + windowSize - 1);
		}

		List<MarketMongoDBData> pageSearch = mDAO.findPaginatedDataoicate(query, skip, pageSize);

		req.setAttribute("query", query);
		req.setAttribute("ssresult", pageSearch);
		req.setAttribute("currentPage", page);
		req.setAttribute("totalPages", totalPages);
		req.setAttribute("totalItems", totalItems);
		req.setAttribute("sortN", "sim");
		req.setAttribute("startPage", startPage);
		req.setAttribute("endPage", endPage);

		mDAO.marketdefaultpageshow(req);
		mDAO.searchpageshow(req);
		mbDAO.isLogined(req);
		return "index";
	}

	@GetMapping("/marketsearchdata.go")
	public String searchresult(@RequestParam(name = "marketsearchingarea") String query,
			@RequestParam(name = "adesc_area", required = false) String sortN,
			@RequestParam(defaultValue = "1") int page, HttpServletRequest req) {
		mDAO.marketdtasave(query);

		int pageSize = 100;
		int skip = (page - 1) * pageSize;

		long totalItems = mDAO.countByQuery(query);
		int totalPages = (int) Math.ceil((double) totalItems / pageSize);

		page = Math.max(1, Math.min(page, totalPages));

		int windowSize = 10;
		int startPage = Math.max(1, page - (windowSize / 2));
		int endPage = Math.min(totalPages, startPage + windowSize - 1);

		if (startPage > 1) {
			endPage = Math.min(totalPages, startPage + windowSize - 1);
		}

		List<MarketMongoDBData> pageSearch = mDAO.findPaginatedData(query, skip, pageSize);

		req.setAttribute("query", query);
		req.setAttribute("ssresult", pageSearch);
		req.setAttribute("currentPage", page);
		req.setAttribute("totalPages", totalPages);
		req.setAttribute("totalItems", totalItems);
		req.setAttribute("sortN", "sim");
		req.setAttribute("startPage", startPage);
		req.setAttribute("endPage", endPage);

		mDAO.marketdefaultpageshow(req);
		mDAO.searchpageshow(req);
		mbDAO.isLogined(req);
		return "index";
	}
}
