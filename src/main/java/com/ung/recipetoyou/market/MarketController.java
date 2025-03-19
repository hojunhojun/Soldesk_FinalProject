package com.ung.recipetoyou.market;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ung.recipetoyou.HomeDAO;
import com.ung.recipetoyou.market.search.marketsearchBeans;
import com.ung.recipetoyou.market.search.marketsearchBeanslist;
import com.ung.recipetoyou.market.search.marketsearchDAO;
import com.ung.recipetoyou.member.MemberDAO;
import com.ung.recipetoyou.recipe.MainPageRecipes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class MarketController {
	@Autowired
	private MemberDAO mbDAO;
	
	@Autowired
	private MarketDAO mDAO;
	
	@Autowired
	private MemberDAO meDAO;
	
	@Autowired
	private marketsearchDAO msDAO;
	
	@Autowired
	private HomeDAO hDAO;
	
	@GetMapping("/market.go")
	public String markethome(HttpServletRequest req, marketsearchBeans mB, marketsearchBeanslist mbl) { 
		meDAO.isLogined(req);
		mDAO.getChallengeCommunityPost(req);
		mDAO.markethomecate(req);
		msDAO.searchdataselect(req);
		mDAO.marketdefaultpageshow(req);
		mbDAO.isLogined(req);
	    return "index";
	}
	
	@GetMapping(value = "/marketpage.recipe.get", produces = "application/json;charset=utf-8")
	public @ResponseBody MainPageRecipes recipePostGet(HttpServletRequest req, HttpServletResponse res) {
		 MainPageRecipes recipes = hDAO.getmarketRecipePost(req);
		 return recipes;
	}
}