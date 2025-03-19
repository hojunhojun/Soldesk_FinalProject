package com.ung.recipetoyou;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ung.recipetoyou.member.MemberDAO;
import com.ung.recipetoyou.recipe.MainPageRecipes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class HomeController {
	
	@Autowired
	private MemberDAO mDAO;
	@Autowired
	private HomeDAO hDAO;
	
	@GetMapping("/")
	public String home(HttpServletRequest req) {
		req.setAttribute("contentPage", "home");
		hDAO.getHomeFreeCommuPost(req);
		hDAO.getHomeFoodGuidePost(req);
		hDAO.getHomeChallengeCommuPost(req);
		mDAO.isLogined(req);
		return "index";
	}

	@GetMapping("/index.do")
	public String index(HttpServletRequest req) {
		req.setAttribute("contentPage", "home");
		hDAO.getHomeFreeCommuPost(req);
		hDAO.getHomeFoodGuidePost(req);
		hDAO.getHomeChallengeCommuPost(req);
		if (mDAO.isLogined(req)) {
			mDAO.checkBadge(req);
		}
		return "index";
	}
	
	@GetMapping(value = "/mainpage.recipe.get", produces = "application/json;charset=utf-8")
	public @ResponseBody MainPageRecipes recipePostGet(HttpServletRequest req, HttpServletResponse res) {
		res.setHeader("Access-Control-Allow-Origin", "*");
		return hDAO.getHomeRecipePost(req);
	}
	
}
