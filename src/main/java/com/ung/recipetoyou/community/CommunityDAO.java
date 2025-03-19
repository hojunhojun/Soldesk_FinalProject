package com.ung.recipetoyou.community;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ung.recipetoyou.LeeFileNameGenerator;
import com.ung.recipetoyou.member.Member;
import com.ung.recipetoyou.member.MemberDAO;
import com.ung.recipetoyou.recipe.MainRecipeRepo;
import com.ung.recipetoyou.recipe.Recipe;
import com.ung.recipetoyou.recipe.RecipeDAO;
import com.ung.recipetoyou.recipe.SubRecipe;
import com.ung.recipetoyou.recipe.SubRecipeRepo;
import com.ung.recipetoyou.recipe.TodayRecipe;
import com.ung.recipetoyou.recipe.TodayRecipeRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class CommunityDAO {

	@Value("${rty.community.photo}")
	private String fileFolder;

	@Value("${member.photo.size}")
	private int photoSize;

	private int postPerPage;
	private int PagesPerGroup;
	
	public CommunityDAO() {
		postPerPage = 10;
		PagesPerGroup = 5;
	}
	
	@Autowired
	private RecipeDAO rDAO;
	
	@Autowired
	private MemberDAO mDAO;
	
	@Autowired
	private CommunityPostRepository cpr;

	@Autowired
	private CommunityPostCommentRepository cpcr;

	@Autowired
	private CommunityPostReplyRepository cprr;

	@Autowired
	private CommunityPostLikeRepository cplr;

	@Autowired
	private MainRecipeRepo mrr;
	
	@Autowired
	private SubRecipeRepo srr;
	
	@Autowired
	private TodayRecipeRepository trr;

	
	public String writeCommuPost(MultipartFile mf1, MultipartFile mf2, MultipartFile mf3,
			MultipartFile mf4, MultipartFile mf5, CommunityPost cp, HttpServletRequest req) {
		MultipartFile[] files = { mf1, mf2, mf3, mf4, mf5 };
		String[] fileNames = new String[5];
		Member m = (Member) req.getSession().getAttribute("loginMember");
		if (m == null) {
			return "{\"result\":\"로그인 후 이용해주세요\"}";
		}
		try {
			for (int i = 0; i < files.length; i++) {
				if (files[i] != null && files[i].getSize() > photoSize) {
					throw new Exception();
				}
				if (files[i] != null && !files[i].isEmpty()) {
					fileNames[i] = LeeFileNameGenerator.generateFileName(files[i]);
					files[i].transferTo(new File(fileFolder + "/" + fileNames[i]));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"등록실패(파일크기)\"}";
		}
		try {
			String token = req.getParameter("token");
			String oldSuccessToken = (String) req.getSession().getAttribute("successToken");
			if (oldSuccessToken != null && token.equals(oldSuccessToken)) {
				return "{\"result\":\"등록실패(새로고침)\"}";
			}
			cp.setWriter(m);
			cp.setText(cp.getText().replace("\r\n", "<br>"));
			cp.setImg1(fileNames[0]);
			cp.setImg2(fileNames[1]);
			cp.setImg3(fileNames[2]);
			cp.setImg4(fileNames[3]);
			cp.setImg5(fileNames[4]);
			cp.setLike(0);
			if (req.getParameter("rcpno") != null) { // 자유게시판의 경우는 rcpno가 없으니 체크
				cp.setRno(Integer.parseInt(req.getParameter("rcpno")));				
			}
			cpr.save(cp);
			req.getSession().setAttribute("successToken", token);
			req.setAttribute("post", cp.getNo());
			mDAO.checkBadge(req);
			return "{\"result\":\"글쓰기 성공\"}";
		} catch (Exception e) {
			e.printStackTrace();
			for (String photo : fileNames) {
				if (photo != null) {
					new File(fileFolder + "/" + photo).delete();
				}
			}
			return "{\"result\":\"글쓰기 실패\"}";

		}
	}
	public void getChallengeCommunityPost(int page, HttpServletRequest req) {
		try {
	        TodayRecipe existingRecipe = trr.findFirstByOrderByNameAsc();
	        Recipe today = mrr.findByRcpId(existingRecipe.getRno());
	        req.setAttribute("todayRcp", today);
	        List<SubRecipe> sc = srr.findByRcpId(today.getRcpId());
	        req.setAttribute("stRcp", sc);
	        Map<String, String> rcpMulFields = rDAO.getRcpMulFields(today.getRcpId());
		    req.setAttribute("rcpMulFields", rcpMulFields);
	        
	        long allPostCount = cpr.countByType(1);
	        String search = (String) req.getSession().getAttribute("search");
	        if (search == null) {
	            search = "";
	        } else {
	            allPostCount = cpr.countByTypeAndRnoAndTitleContainingOrTypeAndRnoAndWriterIdContaining
	            		(2, today.getRcpId(),search, 2, today.getRcpId(), search); // 검색정책 : 작성자 or 제목
	        }
			
	        int pageCount = (int) Math.ceil(allPostCount / (double) postPerPage);
	        int curPageGroup = (page - 1) / PagesPerGroup + 1;
	        req.setAttribute("pageCount", pageCount);
	        req.setAttribute("curPage", page);
	        
	        Sort s = Sort.by(Sort.Order.desc("date"));
	        Pageable pg = PageRequest.of(page-1, postPerPage, s);
	        
	        List<CommunityPost> posts = cpr.findByTypeAndRnoAndTitleContainingOrTypeAndRnoAndWriterIdContaining
	        		(2, today.getRcpId(), search, 2, today.getRcpId(), search, pg);
	        req.setAttribute("curPageGroup", curPageGroup);
			req.setAttribute("rcpno", req.getParameter("rcpno"));
			req.setAttribute("posts", posts);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void searchClear(HttpServletRequest request) {
		request.getSession().setAttribute("search", "");
	}

	public Resource getImage(String n) {
		try {
			return new UrlResource("file:" + fileFolder + "/" + n);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void getChallengeCommunityPostDetail(int no, HttpServletRequest req) {
		req.setAttribute("post", cpr.findByNo(no));
		List<CommunityPostComment> c = cpcr.findAllByPostNoAndPostTypeOrderByDateAsc(no, 2);
		List<CommunityPostReply> r = new ArrayList<>();
		for (CommunityPostComment communityPostReply : c) {
			r.addAll(cprr.findByParNoOrderByDateAsc(communityPostReply.getNo()));
		}
		if (req.getParameter("curPage") != null) {
			req.setAttribute("curPage", Integer.parseInt(req.getParameter("curPage")));
		}
		req.setAttribute("postComment", c);
		req.setAttribute("postReply", r);
	}
	
	public void searchPost(HttpServletRequest req) {
		try {
			req.getSession().setAttribute("search", req.getParameter("search"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String updateCommunityPost(MultipartFile mf1, MultipartFile mf2, MultipartFile mf3, MultipartFile mf4,
			MultipartFile mf5, CommunityPost cp, HttpServletRequest req) {
		MultipartFile[] files = { mf1, mf2, mf3, mf4, mf5 };
		String[] newPhotos = new String[5];
		try {
			for (int i = 0; i < files.length; i++) {
				if (files[i] != null && files[i].getSize() > photoSize) {
					return "{\"result\":\"수정실패(파일용량)\"}";
				}
				if (files[i] != null && !files[i].isEmpty()) {
					newPhotos[i] = LeeFileNameGenerator.generateFileName(files[i]);
					files[i].transferTo(new File(fileFolder + "/" + newPhotos[i]));
				} else {
					newPhotos[i] = null; // 파일이 없으면 null로 처리
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"수정실패(파일처리)\"}";
		}

		try {
			String token = req.getParameter("token");
			String oldSuccessToken = (String) req.getSession().getAttribute("successToken");
			if (oldSuccessToken != null && token.equals(oldSuccessToken)) {
				return "{\"result\":\"등록실패(새로고침)\"}";
			}
			int no = Integer.parseInt(req.getParameter("post"));
			CommunityPost dbcp = cpr.findByNo(no);
			
			dbcp.setTitle(cp.getTitle());
			dbcp.setText(cp.getText().replace("\r\n", "<br>"));
			
			// 기존 이미지 파일 처리
			String[] oldPhotos = { dbcp.getImg1(), dbcp.getImg2(), dbcp.getImg3(), dbcp.getImg4(), dbcp.getImg5() };
			for (int i = 0; i < oldPhotos.length; i++) {
				// 새 파일이 없으면 기존 이미지를 그대로 두고, 새 파일이 있으면 기존 파일 삭제
				if (newPhotos[i] == null) {
					// 새 파일이 없으면 기존 파일을 삭제
					if (oldPhotos[i] != null) {
						new File(fileFolder + "/" + oldPhotos[i]).delete(); // 기존 파일 삭제
					}
				} else {
					// 새 파일이 있으면 기존 파일을 삭제하고 새 파일을 저장
					if (oldPhotos[i] != null) {
						new File(fileFolder + "/" + oldPhotos[i]).delete(); // 기존 파일 삭제
					}
				}
			}
			
			// 새 이미지를 DB에 저장
			dbcp.setImg1(newPhotos[0]);
			dbcp.setImg2(newPhotos[1]);
			dbcp.setImg3(newPhotos[2]);
			dbcp.setImg4(newPhotos[3]);
			dbcp.setImg5(newPhotos[4]);

			// 게시물 저장
			cpr.save(dbcp);
			req.getSession().setAttribute("successToken", token);
			return "{\"result\":\"수정성공\"}";
		} catch (Exception e) {
			e.printStackTrace();
			// 실패 시 업로드된 파일 삭제
			for (String photo : newPhotos) {
				if (photo != null) {
					new File(fileFolder + "/" + photo).delete();
				}
			}
			return "{\"result\":\"수정실패\"}";
		}
	}




	public void deletePost(CommunityPost cp, HttpServletRequest request) {
		new File(fileFolder + "/" + cp.getImg1()).delete();
		new File(fileFolder + "/" + cp.getImg2()).delete();
		new File(fileFolder + "/" + cp.getImg3()).delete();
		new File(fileFolder + "/" + cp.getImg4()).delete();
		new File(fileFolder + "/" + cp.getImg5()).delete();
		cpr.delete(cp);
	}
	
	public void freeCommuPostDelete(HttpServletRequest req) {
		try {
			int no = Integer.parseInt(req.getParameter("no"));
			CommunityPost cp = cpr.findByNo(no);
			new File(fileFolder + "/" + cp.getImg1()).delete();
			new File(fileFolder + "/" + cp.getImg2()).delete();
			new File(fileFolder + "/" + cp.getImg3()).delete();
			new File(fileFolder + "/" + cp.getImg4()).delete();
			new File(fileFolder + "/" + cp.getImg5()).delete();
			cpr.deleteById(no);
			System.out.println("삭제성공");
		} catch (Exception e) {
			System.out.println("삭제실패");
		}
	}
	
	public void writeComment(CommunityPostComment cpr, HttpServletRequest req) {
		try {
			String token = req.getParameter("token");
			String oldSuccessToken = (String) req.getSession().getAttribute("successToken");
			if (oldSuccessToken != null && token.equals(oldSuccessToken)) {
				System.out.println("등록실패(새로고침)");
				return;
			}
			System.out.println("여기까지 옴2");
			Member m = (Member) req.getSession().getAttribute("loginMember");
			cpr.setWriter(m.getId());
			cpr.setPostNo(Integer.parseInt(req.getParameter("post")));
			cpr.setPostType(2);
			cpcr.save(cpr);
			System.out.println("댓글쓰기 성공");
			req.getSession().setAttribute("successToken", token);
		} catch (Exception e) {
			System.out.println("댓글쓰기 실패");
		}
	}
	
	public void deleteComment(CommunityPostComment cpc, HttpServletRequest req) {
		try {
			cpcr.delete(cpc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void writereply(CommunityPostReply cpr, HttpServletRequest req) {
		try {
			String token = req.getParameter("token");
			String oldSuccessToken = (String) req.getSession().getAttribute("successToken");
			if (oldSuccessToken != null && token.equals(oldSuccessToken)) {
				System.out.println("등록실패(새로고침)");
				return;
			}
			Member m = (Member) req.getSession().getAttribute("loginMember");
			cpr.setWriter(m.getId());
			cprr.save(cpr);
			System.out.println("댓글쓰기 성공");
			req.getSession().setAttribute("successToken", token);
		} catch (Exception e) {
			System.out.println("댓글쓰기 실패");
		}
	}
	
	public void postLike(CommunityPostLike cpl, HttpServletRequest req) {
		try {
			String token = req.getParameter("token");
			String oldSuccessToken = (String) req.getSession().getAttribute("successToken");

			if (oldSuccessToken != null && token.equals(oldSuccessToken)) {
				System.out.println("등록실패(새로고침)");
				return;
			}

			CommunityPost dbcp = cpr.findById(Integer.parseInt(req.getParameter("post"))).get();
			Member m = (Member) req.getSession().getAttribute("loginMember");

			cpl.setMemId(m.getId());
			cpl.setPostNo(dbcp.getNo());
			cpl.setPostType(2);
			dbcp.setLike(dbcp.getLike() + 1);
			cplr.save(cpl);
			cpr.save(dbcp);
			req.getSession().setAttribute("successToken", token);
			System.out.println("좋아요 성공");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("좋아요 실패");
		}
	}
	
	public void checkLike(HttpServletRequest req) {
		boolean isLike;
		Member m = (Member) req.getSession().getAttribute("loginMember");
		if (m == null) {
			isLike = false;
		} else {
			isLike = cplr.existsByPostNoAndMemIdAndPostType(Integer.parseInt(req.getParameter("post")), m.getId(), 2);			
		}
		req.setAttribute("isLike", isLike);
	}
	
	public void postUnLike(HttpServletRequest req) {
		try {
			String token = req.getParameter("token");
			String oldSuccessToken = (String) req.getSession().getAttribute("successToken");
			
			if (oldSuccessToken != null && token.equals(oldSuccessToken)) {
				System.out.println("등록실패(새로고침)");
				return;
			}
			CommunityPost dbcp = cpr.findById(Integer.parseInt(req.getParameter("post"))).get();
			Member m = (Member) req.getSession().getAttribute("loginMember");
			dbcp.setLike(dbcp.getLike() - 1);
			CommunityPostLike existingLike = cplr.findByMemIdAndPostNo(m.getId(), dbcp.getNo());
			cplr.delete(existingLike);
			cpr.save(dbcp);
			req.getSession().setAttribute("successToken", token);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Scheduled(cron = "0 00 00 * * *")
	public void updateDailyRecipe() { 
		List<Recipe> recipes = mrr.findAll();
		if (!recipes.isEmpty()) {
		    Random random = new Random();
		    Recipe currentRecipe = recipes.get(random.nextInt(recipes.size()));
		    String todayRcp = currentRecipe.getRcpName();  // 선택된 레시피의 이름
		    int todayRcpNo = currentRecipe.getRcpId();
		    TodayRecipe existingRecipe = trr.findFirstByOrderByNameAsc();
		    if (existingRecipe != null) {
		        existingRecipe.setName(todayRcp);  // 기존 레시피의 이름 업데이트
		        existingRecipe.setRno(todayRcpNo);
		        trr.save(existingRecipe);
		    }
		}
	}
	
	public String regFoodGuideCommuPost(MultipartFile mf1, MultipartFile mf2, MultipartFile mf3, 
			MultipartFile mf4, MultipartFile mf5, CommunityPost cm, HttpServletRequest req) {
		MultipartFile[] files = { mf1, mf2, mf3, mf4, mf5 };
		String[] fileNames = new String[5];
		Member m = (Member) req.getSession().getAttribute("loginMember");
		if (m == null) {
			return "{\"result\":\"로그인 후 이용해주세요\"}";
		}
		try {
			for (int i = 0; i < files.length; i++) {
				if (files[i] != null && files[i].getSize() > photoSize) {
					return "{\"result\":\"등록실패(파일용량)\"}";
				}
				if (files[i] != null && !files[i].isEmpty()) {
					fileNames[i] = LeeFileNameGenerator.generateFileName(files[i]);
					files[i].transferTo(new File(fileFolder + "/" + fileNames[i]));
				}
			}
		} catch (Exception e) {
			return "{\"result\":\"등록실패(파일처리)\"}";
		}
		try {
			cm.setWriter(m);
			cm.setText(cm.getText().replace("\r\n", "<br>"));
			cm.setImg1(fileNames[0]);
			cm.setImg2(fileNames[1]);
			cm.setImg3(fileNames[2]);
			cm.setImg4(fileNames[3]);
			cm.setImg5(fileNames[4]);
			cm.setLike(0);
			cpr.save(cm);
			return "{\"result\":\"등록성공\"}";
		} catch (Exception e) {
			e.printStackTrace();
			new File(fileFolder + "/" + fileNames[0]).delete();
			new File(fileFolder + "/" + fileNames[1]).delete();
			new File(fileFolder + "/" + fileNames[2]).delete();
			new File(fileFolder + "/" + fileNames[3]).delete();
			new File(fileFolder + "/" + fileNames[4]).delete();
			return "{\"result\":\"등록실패\"}";
		}
	}
	
	public FoodGuideCommuPosts postsToJson(int page, HttpServletRequest req) {
	    try {
	        String search = (String) req.getSession().getAttribute("search");
	        long allPostCount = cpr.countByType(3);
	        if (search == null) {
	            search = "";
	        }else {
	            allPostCount = cpr.countByTypeAndTitleContainingOrTypeAndWriterIdContaining(3, search, 3, search);
	        }
	        int pageCount = (int) Math.ceil(allPostCount / (double) 6);
	        req.setAttribute("pageCount", pageCount);

	        Sort sort = Sort.by(Sort.Order.desc("no"));
	        Pageable pageable = PageRequest.of(page - 1, 6, sort);
	        List<CommunityPost> posts = cpr .findByTypeAndTitleContainingOrTypeAndWriterIdContaining(3, search, 3, search, pageable);

	        return new FoodGuideCommuPosts(posts, pageCount);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new FoodGuideCommuPosts(new ArrayList<>(), 0);
	    }
	}
	
	public void getFoodGuideCommuPostDetail(HttpServletRequest req) {
		CommunityPost cp = cpr.findByNo(Integer.parseInt(req.getParameter("post")));
		req.setAttribute("foodGuideCommuPostDetail", cp);
		req.setAttribute("foodGuideCommuPostDetailUpdateText", cp.getText().replace("<br>", "\r\n"));
		List<CommunityPostComment> c = cpcr.findByPostNoAndPostType(Integer.parseInt(req.getParameter("post")), 3);
		List<CommunityPostReply> r = new ArrayList<>();
		for (CommunityPostComment communityPostReply : c) {
			r.addAll(cprr.findByParNo(communityPostReply.getNo()));
		}
		req.setAttribute("foodGuideCommuPostComment", c);
		req.setAttribute("foodGuideCommuPostReply", r);
	}

	public String updateFoodGuideCommuPost(MultipartFile mf1, MultipartFile mf2, MultipartFile mf3, MultipartFile mf4,
			MultipartFile mf5, CommunityPost cm, HttpServletRequest req) {
		MultipartFile[] files = { mf1, mf2, mf3, mf4, mf5 };
		String[] newPhotos = new String[5];
		try {
			for (int i = 0; i < files.length; i++) {
				if (files[i] != null && files[i].getSize() > photoSize) {
					return "{\"result\":\"수정실패(파일용량)\"}";
				}
				if (files[i] != null && !files[i].isEmpty()) {
					newPhotos[i] = LeeFileNameGenerator.generateFileName(files[i]);
					files[i].transferTo(new File(fileFolder + "/" + newPhotos[i]));
				}
			}
		} catch (Exception e) {
			return "{\"result\":\"수정실패(파일처리)\"}";
		}
		try {
			int no = Integer.parseInt(req.getParameter("no"));
			CommunityPost dbcp = cpr.findById(no).get();

			dbcp.setTitle(cm.getTitle());
			dbcp.setText(cm.getText().replace("\r\n", "<br>"));

			String[] oldPhotos = { dbcp.getImg1(), dbcp.getImg2(), dbcp.getImg3(), dbcp.getImg4(), dbcp.getImg5() };
			for (int i = 0; i < oldPhotos.length; i++) {
				if (newPhotos[i] == null) {
					newPhotos[i] = oldPhotos[i];
				} else if (oldPhotos[i] != null) {
					new File(fileFolder + "/" + oldPhotos[i]).delete();
				}
			}
			dbcp.setImg1(newPhotos[0]);
			dbcp.setImg2(newPhotos[1]);
			dbcp.setImg3(newPhotos[2]);
			dbcp.setImg4(newPhotos[3]);
			dbcp.setImg5(newPhotos[4]);

			dbcp.setLike(0);
			cpr.save(dbcp);

			return "{\"result\":\"수정성공\"}";
		} catch (Exception e) {
			e.printStackTrace();
			for (String photo : newPhotos) {
				if (photo != null) {
					new File(fileFolder + "/" + photo).delete();
				}
			}
			return "{\"result\":\"수정실패\"}";
		}
	}
	
	public String foodGuideCommuPostDelete(HttpServletRequest req) {
		try {
			int no = Integer.parseInt(req.getParameter("no"));
			cpr.deleteById(no);
			return "{\"result\":\"삭제성공\"}";
		} catch (Exception e) {
			return "{\"result\":\"삭제실패\"}";
		}
	}
	
	public void deleteReply(CommunityPostReply cpr, HttpServletRequest req) {
		try {
			cprr.delete(cpr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String fCommuPostReplyDelete(HttpServletRequest req) {
		try {
			int no = Integer.parseInt(req.getParameter("no"));
			cprr.deleteById(no);
			return "{\"result\":\"삭제성공\"}";
		} catch (Exception e) {
			return "{\"result\":\"삭제실패\"}";
		}
	}
	
	public String fCommuPostCommentDelete(HttpServletRequest req) {
		try {
			int no = Integer.parseInt(req.getParameter("no"));
			cpcr.deleteById(no);
			return "{\"result\":\"삭제성공\"}";
		} catch (Exception e) {
			return "{\"result\":\"삭제실패\"}";
		}
	}
	
	public String freeCommuPostLike(HttpServletRequest req) {
		try {
			Member m = (Member) req.getSession().getAttribute("loginMember");
			if (m == null) {
				return "{\"result\":\"로그인 후 이용해주세요\"}";
			}
			
			String token = req.getParameter("token");
			String oldSuccessToken = (String) req.getSession().getAttribute("successToken");
			System.out.println(oldSuccessToken);
			if (oldSuccessToken != null && token.equals(oldSuccessToken)) {
				return "{\"result\":\"등록실패(새로고침)\"}";
			}
			CommunityPost dbcp = cpr.findById(Integer.parseInt(req.getParameter("post"))).get();
			CommunityPostLike existingLike = cplr.findByMemIdAndPostNo(m.getId(), dbcp.getNo());
			
			if (existingLike == null) {
				CommunityPostLike newCpl = new CommunityPostLike();
				newCpl.setMemId(m.getId());
				newCpl.setPostNo(dbcp.getNo());
				newCpl.setPostType(Integer.parseInt(req.getParameter("postType")));
				cplr.save(newCpl);
				dbcp.setLike(dbcp.getLike() + 1);
				req.getSession().setAttribute("successToken", token);
				cpr.save(dbcp);
				return "{\"result\":\"좋아요 추가 성공\"}";
			} else {
				cplr.delete(existingLike);
				dbcp.setLike(dbcp.getLike() - 1);
				req.getSession().setAttribute("successToken", token);
				cpr.save(dbcp);
				return "{\"result\":\"좋아요 삭제 성공\"}";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"좋아요 실패\"}";
		}
	}
	
	public String regCommuPostComment(HttpServletRequest req) {
		try {
			Member m = (Member) req.getSession().getAttribute("loginMember");
			if (m == null) {
				return "{\"result\":\"로그인 후 이용해주세요\"}";
			}
			String token = req.getParameter("token");
			String oldSuccessToken = (String) req.getSession().getAttribute("successToken");
			if (oldSuccessToken != null && token.equals(oldSuccessToken)) {
				return "{\"result\":\"댓글등록실패(새로고침)\"}";
			}
			CommunityPostComment c = new CommunityPostComment();
			c.setWriter(m.getId());
			c.setText(req.getParameter("text").replace("\r\n", "<br>"));
			c.setPostType(Integer.parseInt(req.getParameter("postType")));
			c.setPostNo(Integer.parseInt(req.getParameter("post")));
			cpcr.save(c);
			return "{\"result\":\"댓글등록성공\"}";
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"댓글등록실패\"}";
		}
	}
	
	public String regCommuPostReply(HttpServletRequest req) {
		try {
			Member m = (Member) req.getSession().getAttribute("loginMember");
			if (m == null) {
				return "{\"result\":\"로그인 후 이용해주세요\"}";
			}
			String token = req.getParameter("token");
			String oldSuccessToken = (String) req.getSession().getAttribute("successToken");
			if (oldSuccessToken != null && token.equals(oldSuccessToken)) {
				return "{\"result\":\"대댓글등록실패(새로고침)\"}";
			}
			CommunityPostReply r = new CommunityPostReply();
			r.setWriter(m.getId());
			r.setText(req.getParameter("text").replace("\r\n", "<br>"));
			r.setParNo(Integer.parseInt(req.getParameter("parNo")));
			cprr.save(r);
			return "{\"result\":\"대댓글등록성공\"}";
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"대댓글등록실패\"}";
		}
	}
	
	public void cateClear(HttpServletRequest request) {
		request.getSession().setAttribute("cate", "");
	}

	public void catePost(HttpServletRequest req) {
		try {
			req.getSession().setAttribute("cate", req.getParameter("cate"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getFreeCommuPost(int page, HttpServletRequest req) {
		try {
			String search = (String) req.getSession().getAttribute("search");
			String cate = (String) req.getSession().getAttribute("cate");
			long allPostCount = cpr.count();
			Sort s = Sort.by(Sort.Order.desc("date"));
			Pageable pg = PageRequest.of(page - 1, postPerPage, s);
			List<CommunityPost> posts = new ArrayList<>();
			if (cate != null && !cate.isEmpty()) {
	            allPostCount = cpr.countByCateAndTypeAndTitleContainingOrCateAndTypeAndWriterIdContaining(cate, 1, search, cate, 1, search);
	            posts = cpr.findByCateAndTypeAndTitleContainingOrCateAndTypeAndWriterIdContaining(cate, 1, search, cate, 1, search, pg);
	        } else {  
	            allPostCount = cpr.countByTypeAndTitleContainingOrTypeAndWriterIdContaining(1, search, 1, search);
	            posts = cpr.findByTypeAndTitleContainingOrTypeAndWriterIdContaining(1, search, 1, search, pg);
	        }
			int pageCount = (int) Math.ceil(allPostCount / (double) postPerPage);
			int curPageGroup = (page - 1) / PagesPerGroup + 1;
			req.setAttribute("pageCount", pageCount);
			req.setAttribute("curPage", page);
			req.setAttribute("curPageGroup", curPageGroup);
	        req.setAttribute("freeCommuPosts", posts);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getFreeCommuPostDetail(HttpServletRequest req) {
		Member m = (Member) req.getSession().getAttribute("loginMember");
		if (m != null) {
			CommunityPostLike existingLike = cplr.findByMemIdAndPostNo(m.getId(), Integer.parseInt(req.getParameter("post")));
			if (existingLike == null) {
				req.setAttribute("isLiked", 0);
			} else {
				req.setAttribute("isLiked", 1);
			}
		} else {
			req.setAttribute("isLiked", 0);
		}
		req.setAttribute("curPage", Integer.parseInt(req.getParameter("curPage")));
		CommunityPost cp =  cpr.findByNo(Integer.parseInt(req.getParameter("post")));
		req.setAttribute("freeCommuPostDetail", cp);
		req.setAttribute("freeCommuPostDetailUpdateText", cp.getText().replace("<br>", "\r\n"));
		
		List<CommunityPostComment> c = cpcr.findByPostNoAndPostType(Integer.parseInt(req.getParameter("post")), 1);
		List<CommunityPostReply> r = new ArrayList<>();
		for (CommunityPostComment communityPostReply : c) {
			r.addAll(cprr.findByParNo(communityPostReply.getNo()));
		}
		req.setAttribute("freeCommuPostComment", c);
		req.setAttribute("freeCommuPostReply", r);
	}
}