package com.ung.recipetoyou.arcade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ung.recipetoyou.member.MemberDAO;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ArcadeController {
	@Autowired
	private MemberDAO mDAO;
	@Autowired
	private QuizDAO qDAO;
	
	@GetMapping("/arcade.go")
	public String arcadeHome(HttpServletRequest req) {
		req.setAttribute("contentPage", "arcade/arcade");
		mDAO.isLogined(req);
		return "index";
	}
	
	@GetMapping("/arcade.roulette.go")
	public String rouletteGo(HttpServletRequest req) {
		req.setAttribute("contentPage", "arcade/arcade");
		mDAO.isLogined(req);
		return "index";
	}
	@GetMapping("/arcade.quiz.go")
	public String arcadeQuizHome(HttpServletRequest req) {
		req.setAttribute("contentPage", "arcade/quizHome");
		mDAO.isLogined(req);
		return "index";
	}
	
	@GetMapping("/arcade.commonsense.quiz.do")
	public String commonsenseQuizDo(HttpServletRequest req) {
		qDAO.getQuiz(req, "1");
		req.setAttribute("contentPage", "arcade/CSQuiz");
		mDAO.isLogined(req);
		return "index";
	}
	
	@PostMapping(value = "/arcade.commonsense.quiz.reg", produces = "application/json;charset=utf-8")
	public @ResponseBody String commonsenseQuizReg(HttpServletRequest req) {
		return qDAO.regCSQuiz(req);
	}
	@GetMapping("/arcade.food.quiz.do")
	public String foodQuizDo(HttpServletRequest req) {
		qDAO.getQuiz(req, "2");
		req.setAttribute("contentPage", "arcade/foodQuiz");
		mDAO.isLogined(req);
		return "index";
	}
	
	@PostMapping(value = "/arcade.food.quiz.reg", produces = "application/json;charset=utf-8")
	public @ResponseBody String foodQuizReg(@RequestParam("file2") MultipartFile mf, Quiz q, HttpServletRequest req) {
		return qDAO.regfoodQuiz(mf, q, req);
	}
	
	@GetMapping("/rty.quiz.image/{fileName}")
	public @ResponseBody Resource imgFolder(@PathVariable("fileName") String f, HttpServletRequest req) {
		return qDAO.getQuizImg(f);
	}
	
	@GetMapping("/arcade.quiz.check.go")
	public String quizCheckGo(HttpServletRequest req) {
		qDAO.getAllQuiz(req);
		req.setAttribute("contentPage", "arcade/quizCheck");
		mDAO.isLogined(req);
		return "index";
	}
	@GetMapping(value = "/arcade.quiz.delete", produces = "application/json;charset=utf-8")
	public @ResponseBody String quizDelete(HttpServletRequest req) {
		return qDAO.quizDelete(req);
	}
}
