package com.ung.recipetoyou.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ung.recipetoyou.DateManager;
import com.ung.recipetoyou.HomeDAO;
import com.ung.recipetoyou.community.CommunityDAO;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MemberController {

	@Autowired
	private MemberDAO mDAO;

	@Autowired
	private CommunityDAO cDAO;
	
	@Autowired
	private HomeDAO hDAO;
	
	@PostMapping("/join.do")
	public String joinDo(@RequestParam("photoTemp") MultipartFile mf, Member m, HttpServletRequest req) {
		mDAO.join(mf, m, req);
		mDAO.isLogined(req);		
		cDAO.getChallengeCommunityPost(1, req);
		req.setAttribute("contentPage", "home");
		return "index";
	}

	@PostMapping("/login.do")
	public String loginDo(Member m, HttpServletRequest req) {
		mDAO.login(m, req);
		if (mDAO.isLogined(req)) {
			mDAO.checkBadge(req);
		}
		hDAO.getHomeFreeCommuPost(req);
		hDAO.getHomeFreeCommuPost(req);
		hDAO.getHomeFoodGuidePost(req);
		hDAO.getHomeChallengeCommuPost(req);
		req.setAttribute("contentPage", "home");
		return "index";
	}

	@GetMapping("/join.go")
	public String joinGo(HttpServletRequest req) {
		DateManager.getCurYear(req);
		req.setAttribute("contentPage", "member/join");
		req.setAttribute("loginPage", "member/login");
		return "index";
	}

	@GetMapping("/logout.do")
	public String logoutDo(HttpServletRequest req) {
		mDAO.logout(req);
		mDAO.isLogined(req);
		cDAO.getChallengeCommunityPost(1, req);
		hDAO.getHomeFreeCommuPost(req);
		hDAO.getHomeFoodGuidePost(req);
		hDAO.getHomeChallengeCommuPost(req);
		req.setAttribute("contentPage", "home");
		return "index";
	}

	@GetMapping("/member.photo/{fileName}")
	public @ResponseBody Resource imageGet(@PathVariable("fileName") String n) {
		return mDAO.getImage(n);
	}
	
	@GetMapping("/member.badge/{fileName}")
	public @ResponseBody Resource badgeGet(@PathVariable("fileName") String n) {
		return mDAO.getBadgeImage(n);
	}
	
	@GetMapping("/member.info.check.go")
	public String memberInfoCheckGo(HttpServletRequest req) {
		if (mDAO.isLogined(req)) {
			req.setAttribute("contentPage", "member/infoCheck");
		} else {
			cDAO.getChallengeCommunityPost(1, req);
			hDAO.getHomeFreeCommuPost(req);
			hDAO.getHomeFoodGuidePost(req);
			hDAO.getHomeChallengeCommuPost(req);
			req.setAttribute("contentPage", "home");
		}
		return "index";
	}
	
	@PostMapping("/member.info.check.do")
	public String memberInfoCheckDo(HttpServletRequest req) {
		if (mDAO.isLogined(req)) {
			if (mDAO.infoCheck(req)) {
				mDAO.splitAddress(req);
				req.setAttribute("contentPage", "member/info");
			} else {
				cDAO.getChallengeCommunityPost(1, req);
				hDAO.getHomeFreeCommuPost(req);
				hDAO.getHomeFoodGuidePost(req);
				hDAO.getHomeChallengeCommuPost(req);
				req.setAttribute("contentPage", "home");
			}
		} else {
			cDAO.getChallengeCommunityPost(1, req);
			hDAO.getHomeFreeCommuPost(req);
			hDAO.getHomeFoodGuidePost(req);
			hDAO.getHomeChallengeCommuPost(req);
			req.setAttribute("contentPage", "home");		
		}
		return "index";
	}
	
	@PostMapping("/member.update.go")
	public String memberUpdateGo(HttpServletRequest req) {
		if (mDAO.isLogined(req)) {
			mDAO.splitAddress(req);
			req.setAttribute("contentPage", "member/infoUpdate");
		} else {
			cDAO.getChallengeCommunityPost(1, req);
			hDAO.getHomeFreeCommuPost(req);
			hDAO.getHomeFoodGuidePost(req);
			hDAO.getHomeChallengeCommuPost(req);
			req.setAttribute("contentPage", "home");			
		}
		return "index";
	}
	
	@PostMapping("/member.update")
	public String memberUpdate(@RequestParam("photo2") MultipartFile mf, Member m, HttpServletRequest req) {
		if (mDAO.isLogined(req)) {
			mDAO.update(mf, m, req);
			mDAO.splitAddress(req);
			req.setAttribute("contentPage", "member/info");
		} else {
			cDAO.getChallengeCommunityPost(1, req);
			hDAO.getHomeFreeCommuPost(req);
			hDAO.getHomeFoodGuidePost(req);
			hDAO.getHomeChallengeCommuPost(req);
			req.setAttribute("contentPage", "home");
		}
		return "index";
	}
	
	@GetMapping("/member.bye.go")
	public String memberByeGo(HttpServletRequest req) {
		if (mDAO.isLogined(req)) {
			req.setAttribute("contentPage", "member/infoDelete");
		} else {
			cDAO.getChallengeCommunityPost(1, req);
			hDAO.getHomeFreeCommuPost(req);
			hDAO.getHomeFoodGuidePost(req);
			hDAO.getHomeChallengeCommuPost(req);
			req.setAttribute("contentPage", "home");			
		}
		return "index";
	}
	
	@PostMapping("/member.bye.do")
	public String memberByeDo(HttpServletRequest req) {
		if (mDAO.isLogined(req)) {
			if (mDAO.infoCheck(req)) { 
				mDAO.bye(req);
				mDAO.logout(req);
				cDAO.getChallengeCommunityPost(1, req);
				hDAO.getHomeFreeCommuPost(req);
				hDAO.getHomeFoodGuidePost(req);
				hDAO.getHomeChallengeCommuPost(req);
				req.setAttribute("contentPage", "home");		
				mDAO.isLogined(req);
			}
		} else {
			mDAO.isLogined(req);
			cDAO.getChallengeCommunityPost(1, req);
			hDAO.getHomeFreeCommuPost(req);
			hDAO.getHomeFoodGuidePost(req);
			hDAO.getHomeChallengeCommuPost(req);
			req.setAttribute("contentPage", "home");
		}
		return "index";
	}
	
	@GetMapping(value = "/member.id.get", produces = "application/json;charset=utf-8")
	public @ResponseBody Members memberIdGet(Member m) {
		return mDAO.getMemberIdToJSON(m);
	}
	
	@GetMapping(value = "/member.nick.get", produces = "application/json;charset=utf-8")
	public @ResponseBody Members memberNickGet(Member m) {
		return mDAO.getMemberNickToJSON(m);
	}
}