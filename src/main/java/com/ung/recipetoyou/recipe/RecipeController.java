package com.ung.recipetoyou.recipe;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.ung.recipetoyou.market.search.marketsearchDAO;
import com.ung.recipetoyou.member.MemberDAO;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class RecipeController {
	@Autowired
	private RecipeDAO rDAO;
	@Autowired
	private MainRecipeRepo mrr;
	@Autowired
	private MemberDAO mDAO;
	
	@GetMapping("/recipe.go")
	public String recipeHome(@RequestParam(name = "category", required = false) String category, HttpServletRequest req) {
		if (category != null) {
	        try {
	            category = java.net.URLDecoder.decode(category, "UTF-8");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        rDAO.setCategory(category, req);
	    }
	    rDAO.searchRecipe(req);
	    rDAO.getCategoryRecipe(1, req); // 카테고리+정렬+검색 유지
	    req.setAttribute("contentPage", "recipe/recipe");
	    mDAO.isLogined(req);
	    return "index";
	}

	@GetMapping("/recipe.go.change")
	public String recipePageChange(@RequestParam(name = "page") int page, HttpServletRequest req) {
	    rDAO.getCategoryRecipe(page, req); // 페이지 이동 시 카테고리 유지
	    req.setAttribute("contentPage", "recipe/recipe");
	    mDAO.isLogined(req);
	    return "index";
	}

	@GetMapping("/recipe.go.sort")
	public String recipeSort(@RequestParam(name = "sortBy", defaultValue = "rcpName") String sortBy, HttpServletRequest req) {
	    req.getSession().setAttribute("sortBy", sortBy);
	    return "redirect:/recipe.go?page=1"; // 정렬 변경 시 카테고리 유지
	}

	@GetMapping("/recipe.go.{rcpId}")
	public String getRecipeById(@PathVariable("rcpId") Integer rcpId, HttpServletRequest req) {
	    // rcpMulFields 조회
	    Map<String, String> rcpMulFields = rDAO.getRcpMulFields(rcpId);
	    req.setAttribute("rcpMulFields", rcpMulFields);
	    // SubRecipe 조회
	    SubRecipe subRecipe = rDAO.getSubRecipeById(rcpId); // rcpId로 SubRecipe 조회
	    // 조회된 recipe, subRecipe를 request에 담기
	    req.setAttribute("subRecipe", subRecipe); // SubRecipe 정보
	    // contentPage 설정 (템플릿 이름)
	    req.setAttribute("contentPage", "recipe/subrecipe");
	    // 로그인 페이지 설정
	    mDAO.isLogined(req);
	    // 최종적으로 index 페이지로 이동
	    return "index";
	} 
	
	@GetMapping("/recipe.rec.{rcpId}")
	public String getRec(@PathVariable("rcpId") Integer rcpId, HttpServletRequest req) {
	    // 추천수 +1 또는 -1 하는 메서드 호출
	    rDAO.incrementRecCount(rcpId, req);

	    // 추천 상태 확인
	    boolean isRecommended = rDAO.isRecipeRecommended(rcpId, req);
	    req.setAttribute("isRecommended", isRecommended);

	    // SubRecipe 정보 조회
	    SubRecipe subRecipe = rDAO.getSubRecipeById(rcpId);
	    req.setAttribute("subRecipe", subRecipe);

	    // rcpMulFields 조회
	    Map<String, String> rcpMulFields = rDAO.getRcpMulFields(rcpId);
	    req.setAttribute("rcpMulFields", rcpMulFields);

	    // 페이지 템플릿 설정
	    req.setAttribute("contentPage", "recipe/subrecipe");

	    // 로그인 여부 체크
	    mDAO.isLogined(req);

	    // index 페이지로 이동
	    return "index";
	}
	
}
