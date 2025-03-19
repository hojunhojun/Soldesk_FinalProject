package com.ung.recipetoyou.market.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class SearchController {
	
	@Autowired
	marketsearchDAO msDAO;
	
	@GetMapping(value = "/searchdata.get", produces = "application/json;charset=utf-8")
	public @ResponseBody marketsearchBeanslist GetSearchData(HttpServletRequest req) {
		return msDAO.get();
	}
	
	@GetMapping(value = "/searchdata.reg", produces = "application/json;charset=utf-8")
	public @ResponseBody String searchDatareg(marketsearchBeans mb) {
		return msDAO.reg(mb);
	}
}
