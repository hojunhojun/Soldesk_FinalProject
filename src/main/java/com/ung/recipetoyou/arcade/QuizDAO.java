package com.ung.recipetoyou.arcade;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ung.recipetoyou.LeeFileNameGenerator;
import com.ung.recipetoyou.member.Member;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class QuizDAO {
	@Autowired
	private QuizRepository qr;
	
	@Value("${rty.quiz.image}")
	private String photoFolder;
	
	@Value("${quiz.photo.size}")
	private int photoSize;
	
	private Random r ;
	public QuizDAO() {
		r = new Random();
	}
	
	public void getQuiz(HttpServletRequest req, String type) {
		List<Quiz> q = qr.findByType(type);
		HashSet<Integer> quizNum = new HashSet<>();
		while (quizNum.size() != 5) {
			quizNum.add(r.nextInt(q.size()));
		}
		ArrayList<Integer> al = new ArrayList<>(quizNum);
		ArrayList<Quiz> pickQuiz = new ArrayList<>();
		for (int j = 0; j < al.size(); j++) {
			pickQuiz.add(q.get(al.get(j)));
		}
		req.setAttribute("pickQuiz", pickQuiz);
	}
	
	public Resource getQuizImg(String f) {
		try {
			return new UrlResource("file:" + photoFolder + "/" + f);
		} catch (Exception e) {
			return null;
		}
	}
	
	public String regCSQuiz(HttpServletRequest req) {
		try {
			Member m = (Member) req.getSession().getAttribute("loginMember");
			if (m == null) {
				return "{\"result\":\"로그인 후 이용해주세요\"}";
			}
			Quiz q = new Quiz();
			q.setCor(req.getParameter("cor"));
			q.setQuiz(req.getParameter("quiz").replace("/", " ").replace(",", " "));
			q.setType(req.getParameter("type"));
			String quiz = q.getQuiz();
			quiz = String.format("%s/%s,%s,%s,%s", quiz, 
					req.getParameter("choice1"), req.getParameter("choice2"),
					req.getParameter("choice3"), req.getParameter("choice4"));
			if(!quiz.contains(q.getCor())) {
				return "{\"result\":\"정답이 보기에 없습니다.\"}";
			}
			q.setQuiz(quiz);
			qr.save(q);
			return "{\"result\":\"등록성공\"}";
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"등록실패(DB)\"}";
		}
	}
	public String regfoodQuiz(MultipartFile mf, Quiz q, HttpServletRequest req) {
		String photoFile = null;
		Member m = (Member) req.getSession().getAttribute("loginMember");
		if (m == null) {
			return "{\"result\":\"로그인 후 이용해주세요\"}";
		}
		try {
			if (mf.getSize() > photoSize) {
				throw new Exception();
			}
			photoFile = LeeFileNameGenerator.generateFileName(mf);
			mf.transferTo(new File(photoFolder + "/" + photoFile));
		} catch (Exception e) {
			return "{\"result\":\"등록실패(파일크기)\"}";
		}
		try {
			q.setQuiz(photoFile);
			qr.save(q);
			return "{\"result\":\"등록성공\"}";
		} catch (Exception e) {
			new File(photoFolder + "/" + photoFile).delete();
			return "{\"result\":\"등록실패(DB)\"}";
		}
	}
	public void getAllQuiz(HttpServletRequest req) {
		try {
			req.setAttribute("CSQuiz", qr.findByType("1"));
			req.setAttribute("foodQuiz", qr.findByType("2"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String quizDelete(HttpServletRequest req) {
		try {
			qr.deleteById(Integer.parseInt(req.getParameter("no")));
			return "{\"result\":\"삭제 성공\"}";
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"삭제 실패\"}";
		}
	}
}
