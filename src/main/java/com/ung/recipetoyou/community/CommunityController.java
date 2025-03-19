package com.ung.recipetoyou.community;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ung.recipetoyou.HomeDAO;
import com.ung.recipetoyou.LeeTokenGenerator;
import com.ung.recipetoyou.member.MemberDAO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Controller
public class CommunityController {
	
	@Autowired
	private CommunityDAO cDAO;
	
	@Autowired
	private MemberDAO mDAO;
	
	@Autowired
	private LeeTokenGenerator ltg;
	
	@Autowired
	private HomeDAO hDAO;
	
	@GetMapping("/community.photo/{fileName}")
	public @ResponseBody Resource imageGet(@PathVariable("fileName") String n) {
		return cDAO.getImage(n);
	}
	
	@GetMapping("/challenge.community.go")
	public String challengeCommunityGo(HttpServletRequest req) {
		ltg.generate(req);
		cDAO.searchClear(req);
		mDAO.isLogined(req);
		cDAO.getChallengeCommunityPost(1, req);
		req.setAttribute("contentPage", "community/challengeCommu");
		return "index";
	}
	
	@PostMapping(value="/challenge.community.post.write", produces= "application/json;charset=utf-8")
	public @ResponseBody String challengeCommunityPostWrite(@RequestParam("imgTemp2") MultipartFile mf1, @RequestParam("imgTemp3") MultipartFile mf2,
			@RequestParam("imgTemp4") MultipartFile mf3, @RequestParam("imgTemp5") MultipartFile mf4,
			@RequestParam("imgTemp6") MultipartFile mf5, CommunityPost cp, HttpServletRequest req) {
		return cDAO.writeCommuPost(mf1, mf2, mf3, mf4, mf5, cp, req);
	}
	
	@GetMapping("/challenge.community.post.detail")
	public String challengeCommunityPostDetail(CommunityPost cp, HttpServletRequest req) {
		ltg.generate(req);
		mDAO.isLogined(req);
		cDAO.getChallengeCommunityPostDetail(Integer.parseInt(req.getParameter("post")), req);
		cDAO.checkLike(req);
		req.setAttribute("contentPage", "community/challengeCommuDetail");
		return "index";
	}
	
	@GetMapping("/challenge.community.page.change")
	public String challengePageChange(int page, HttpServletRequest req) {
		ltg.generate(req);
		cDAO.getChallengeCommunityPost(page, req);
		mDAO.isLogined(req);
		req.setAttribute("contentPage", "community/challengeCommu");
		return "index";
	}
	
	@GetMapping("/challenge.community.search")
	public String challengeCommunitySearch(HttpServletRequest request) {
		ltg.generate(request);
		cDAO.searchPost(request);
		cDAO.getChallengeCommunityPost(1, request);
		mDAO.isLogined(request);
		request.setAttribute("contentPage", "community/challengeCommu");
		return "index";
	}
	
	@GetMapping("/challenge.community.update.go")
	public String challengeCommunityUpdateGo(CommunityPost cp, HttpServletRequest req) {
		if(mDAO.isLogined(req)) {
			ltg.generate(req);
			cDAO.getChallengeCommunityPostDetail(Integer.parseInt(req.getParameter("post")), req);
			req.setAttribute("contentPage", "community/challengeCommuUpdate");
		} else {
			hDAO.getHomeFreeCommuPost(req);
			hDAO.getHomeFoodGuidePost(req);
			hDAO.getHomeChallengeCommuPost(req);
			req.setAttribute("contentPage", "home");
		}
		return "index";
	}
	
	@PostMapping(value="/challenge.community.update.do", produces= "application/json;charset=utf-8")
	public @ResponseBody String challengeCommunityUpdateDo(@RequestParam("imgTemp2") MultipartFile mf1, @RequestParam("imgTemp3") MultipartFile mf2,
			@RequestParam("imgTemp4") MultipartFile mf3, @RequestParam("imgTemp5") MultipartFile mf4,
			@RequestParam("imgTemp6") MultipartFile mf5, CommunityPost cp, HttpServletRequest req) {
		return cDAO.updateCommunityPost(mf1, mf2, mf3, mf4, mf5, cp, req);
	}
	
	@GetMapping("/challenge.community.post.delete")
	public String challengeCommunityPostDelete(CommunityPost cp, HttpServletRequest req) {
		if (mDAO.isLogined(req)) {
			ltg.generate(req);
			cDAO.deletePost(cp, req);
			cDAO.searchClear(req);
			cDAO.getChallengeCommunityPost(1 , req);
			mDAO.checkBadge(req);
			req.setAttribute("contentPage", "community/challengeCommu");
		} else {
			hDAO.getHomeFreeCommuPost(req);
			hDAO.getHomeFoodGuidePost(req);
			hDAO.getHomeChallengeCommuPost(req);
			req.setAttribute("contentPage", "home");			
		}
		return "index";
	}
	
	@GetMapping("/challenge.community.comment.write")
	public String challengeCommunityCommentWrite(CommunityPostComment cpc, HttpServletRequest req) {
		if (mDAO.isLogined(req)) {
			ltg.generate(req);
			cDAO.searchClear(req);
			cDAO.writeComment(cpc, req);
			cDAO.checkLike(req);
			cDAO.getChallengeCommunityPostDetail(Integer.parseInt(req.getParameter("post")) , req);
			req.setAttribute("contentPage", "community/challengeCommuDetail");
		} else {
			hDAO.getHomeFreeCommuPost(req);
			hDAO.getHomeFoodGuidePost(req);
			hDAO.getHomeChallengeCommuPost(req);
			req.setAttribute("contentPage", "home");			
		}
		return "index";
	}
	
	@GetMapping("/challenge.community.comment.delete")
	public String challengeCommunityCommentdelete(CommunityPostComment cpc, HttpServletRequest req) {
		if (mDAO.isLogined(req)) {
			ltg.generate(req);
			cDAO.searchClear(req);
			cDAO.deleteComment(cpc, req);
			cDAO.checkLike(req);
			cDAO.getChallengeCommunityPostDetail(Integer.parseInt(req.getParameter("post")) , req);
			req.setAttribute("contentPage", "community/challengeCommuDetail");
		} else {
			hDAO.getHomeFreeCommuPost(req);
			hDAO.getHomeFoodGuidePost(req);
			hDAO.getHomeChallengeCommuPost(req);
			req.setAttribute("contentPage", "home");			
		}
		return "index";
	}
	
	@GetMapping("/challenge.community.reply.write")
	public String challengeCommunityReplyWrite(CommunityPostReply cpr, HttpServletRequest req) {
		if (mDAO.isLogined(req)) {
			ltg.generate(req);
			cDAO.searchClear(req);
			cDAO.writereply(cpr, req);
			cDAO.checkLike(req);
			cDAO.getChallengeCommunityPostDetail(Integer.parseInt(req.getParameter("post")) , req);
			req.setAttribute("contentPage", "community/challengeCommuDetail");
		} else {
			hDAO.getHomeFreeCommuPost(req);
			hDAO.getHomeFoodGuidePost(req);
			hDAO.getHomeChallengeCommuPost(req);
			req.setAttribute("contentPage", "home");			
		}
		return "index";
	}
	
	@GetMapping("/challenge.community.reply.delete")
	public String challengeCommunityReplyDelete(CommunityPostReply cpr, HttpServletRequest req) {
		if (mDAO.isLogined(req)) {
			ltg.generate(req);
			cDAO.searchClear(req);
			cDAO.deleteReply(cpr, req);
			cDAO.checkLike(req);
			cDAO.getChallengeCommunityPostDetail(Integer.parseInt(req.getParameter("post")) , req);
			req.setAttribute("contentPage", "community/challengeCommuDetail");
		} else {
			hDAO.getHomeFreeCommuPost(req);
			hDAO.getHomeFoodGuidePost(req);
			hDAO.getHomeChallengeCommuPost(req);
			req.setAttribute("contentPage", "home");			
		}
		return "index";
	}
	
	@GetMapping("/challenge.community.post.like")
	public String challengeCommunityPostLike(CommunityPostLike cpl, HttpServletRequest req) {
		if (mDAO.isLogined(req)) {
			ltg.generate(req);
			cDAO.postLike(cpl, req);
			cDAO.checkLike(req);
			cDAO.getChallengeCommunityPostDetail(Integer.parseInt(req.getParameter("post")), req);
			req.setAttribute("contentPage", "community/challengeCommuDetail");
		} else {
			hDAO.getHomeFreeCommuPost(req);
			hDAO.getHomeFoodGuidePost(req);
			hDAO.getHomeChallengeCommuPost(req);
			req.setAttribute("contentPage", "home");
		}
		return "index";
	}
		
	@GetMapping("/challenge.community.post.unlike")
	public String challengeCommunityPostUnLike(CommunityPostLike cpl, HttpServletRequest req) {
		if (mDAO.isLogined(req)) {
			ltg.generate(req);
			cDAO.postUnLike(req);
			cDAO.checkLike(req);
			cDAO.getChallengeCommunityPostDetail(Integer.parseInt(req.getParameter("post")), req);
			req.setAttribute("contentPage", "community/challengeCommuDetail");
		} else {
			hDAO.getHomeFreeCommuPost(req);
			hDAO.getHomeFoodGuidePost(req);
			hDAO.getHomeChallengeCommuPost(req);
			req.setAttribute("contentPage", "home");
		}
		return "index";
	}
	
	@GetMapping("/foodguide.community.go")
	public String foodGuideCommunityHome(HttpServletRequest req) {
		mDAO.isLogined(req);
		req.setAttribute("contentPage", "community/foodGuideCommu");
		return "index";
	}
	
	@GetMapping(value = "/foodguide.community.post.get", produces = "application/json;charset=utf-8")
	public @ResponseBody FoodGuideCommuPosts foodGuideCommunityPostGet(@RequestParam(defaultValue = "1") int page, HttpServletRequest req, HttpServletResponse res) {
		res.setHeader("Access-Control-Allow-Origin", "*");
		cDAO.searchClear(req);
	    return cDAO.postsToJson(page, req);
	}
	
	@GetMapping(value = "/foodguide.community.search", produces="application/json;charset=utf-8")
	public @ResponseBody FoodGuideCommuPosts FoodPostSearchGet(@RequestParam(defaultValue = "1") int page, HttpServletRequest req, HttpServletResponse res) {
		res.setHeader("Access-Control-Allow-Origin", "*");
		mDAO.isLogined(req);
		cDAO.searchPost(req);
		return cDAO.postsToJson(page, req);
	}
	
	@PostMapping(value = "/foodguide.community.post.write", produces= "application/json;charset=utf-8")
	public @ResponseBody String foodGuideCommunityWrite(@RequestParam("file1") MultipartFile mf1, @RequestParam("file2") MultipartFile mf2,
			@RequestParam("file3") MultipartFile mf3, @RequestParam("file4") MultipartFile mf4,
			@RequestParam("file5") MultipartFile mf5, CommunityPost cp, HttpServletRequest req) {
		return cDAO.regFoodGuideCommuPost(mf1, mf2, mf3, mf4, mf5, cp, req);
	}
	
	@GetMapping("/foodguide.community.post.detail")
	public String foodguideCommunityPostDetail(HttpServletRequest req) {
		mDAO.isLogined(req);
		cDAO.getFoodGuideCommuPostDetail(req);
		req.setAttribute("contentPage", "community/foodGuideComuPostDetail");
		return "index";
	}
	
	@PostMapping(value = "/foodguide.community.update", produces= "application/json;charset=utf-8")
	public @ResponseBody String foodGuideCommunityUpdate(@RequestParam("file1") MultipartFile mf1, @RequestParam("file2") MultipartFile mf2,
			@RequestParam("file3") MultipartFile mf3, @RequestParam("file4") MultipartFile mf4,
			@RequestParam("file5") MultipartFile mf5, CommunityPost cp, HttpServletRequest req) {
		return cDAO.updateFoodGuideCommuPost(mf1, mf2, mf3, mf4, mf5, cp, req);
	}
	
	@GetMapping(value = "/foodguide.community.post.delete", produces = "application/json;charset=utf-8")
	public @ResponseBody String foodGuideCommunityPostDelete(CommunityPost cp, HttpServletRequest req) {
		return cDAO.foodGuideCommuPostDelete(req);
	}
	@GetMapping(value = "/f.community.post.reply.delete", produces = "application/json;charset=utf-8")
	public @ResponseBody String fCommunityReplyDelete(HttpServletRequest req) {
		return cDAO.fCommuPostReplyDelete(req);
	}
	@GetMapping(value = "/f.community.post.comment.delete", produces = "application/json;charset=utf-8")
	public @ResponseBody String fCommunityCommentDelete(HttpServletRequest req) {
		return cDAO.fCommuPostCommentDelete(req);
	}
	
	@PostMapping(value="/free.community.post.write", produces = "application/json;charset=utf-8")
	public @ResponseBody String freeCommunityPostWrite(@RequestParam("file1") MultipartFile mf1, @RequestParam("file2") MultipartFile mf2,
			@RequestParam("file3") MultipartFile mf3, @RequestParam("file4") MultipartFile mf4,
			@RequestParam("file5") MultipartFile mf5, CommunityPost fp, HttpServletRequest req) {
		return cDAO.writeCommuPost(mf1, mf2, mf3, mf4, mf5, fp, req);
	}
	
	@GetMapping("/free.community.page.change")
	public String freeCommunityPostPageChange(int page, HttpServletRequest req) {
		ltg.generate(req);
		cDAO.getFreeCommuPost(page, req);
		mDAO.isLogined(req);
		req.setAttribute("contentPage", "community/freeCommunity");
		return "index";
	}
	
	@GetMapping("/free.community.post.detail")
	public String freeCommunityPostDetail(HttpServletRequest req) {
		ltg.generate(req);
		mDAO.isLogined(req);
		cDAO.getFreeCommuPostDetail(req);
		req.setAttribute("contentPage", "community/freeCommuPostDetail");
		return "index";
	}
	
	@GetMapping("/free.community.post.like")
	public @ResponseBody String freeCommunityPostLike(HttpServletRequest req) {
		return cDAO.freeCommuPostLike(req);
	}
	
	@GetMapping(value = "/f.community.comment.write", produces = "application/json;charset=utf-8")
	public @ResponseBody String fReplyWrite(HttpServletRequest req) {
		return cDAO.regCommuPostComment(req);
	}
	@GetMapping(value = "/f.community.reply.write", produces = "application/json;charset=utf-8")
	public @ResponseBody String fRReplyWrite(HttpServletRequest req) {
		return cDAO.regCommuPostReply(req);
	}
	
	@GetMapping("/free.community.search")
	public String freeCommunityPostSearch(HttpServletRequest req) {
		ltg.generate(req);
		cDAO.searchPost(req);
		cDAO.getFreeCommuPost(1, req);
		mDAO.isLogined(req);
		req.setAttribute("contentPage", "community/freeCommunity");
		return "index";
	}
	
	@GetMapping("/free.community.go")
	public String freeCommunityHome(HttpServletRequest req) {
		ltg.generate(req);
		cDAO.searchClear(req);
		cDAO.cateClear(req);
		cDAO.getFreeCommuPost(1, req);
		mDAO.isLogined(req);
		req.setAttribute("contentPage", "community/freeCommunity");
		return "index";
	}
	
	@GetMapping("/free.community.post.category")
	public String freeCommunityCateGo(HttpServletRequest req) {
		ltg.generate(req);
		cDAO.searchClear(req);
		cDAO.catePost(req);
		cDAO.getFreeCommuPost(1, req);
		mDAO.isLogined(req);
		req.setAttribute("contentPage", "community/freeCommunity");
		return "index";
	}
	
	@PostMapping(value="/free.community.update", produces= "application/json;charset=utf-8")
	public @ResponseBody String freeCommunityUpdate(@RequestParam("file1") MultipartFile mf1, @RequestParam("file2") MultipartFile mf2,
			@RequestParam("file3") MultipartFile mf3, @RequestParam("file4") MultipartFile mf4,
			@RequestParam("file5") MultipartFile mf5, CommunityPost cp, HttpServletRequest req) {
		return cDAO.updateCommunityPost(mf1, mf2, mf3, mf4, mf5, cp, req);
	}
	
	@GetMapping("/free.community.post.delete")
	public String freeCommunityPostDelete(HttpServletRequest req) {
		if (mDAO.isLogined(req)) {
			ltg.generate(req);
			cDAO.freeCommuPostDelete(req);
			cDAO.searchClear(req);
			cDAO.cateClear(req);
			cDAO.getFreeCommuPost(1, req);
			req.setAttribute("contentPage", "community/freeCommunity");
		} else {
			hDAO.getHomeFreeCommuPost(req);
			hDAO.getHomeFoodGuidePost(req);
			hDAO.getHomeChallengeCommuPost(req);
			req.setAttribute("contentPage", "home");			
		}
		return "index";
	}
	
}
