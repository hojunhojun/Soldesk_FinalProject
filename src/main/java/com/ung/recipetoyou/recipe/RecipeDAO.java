package com.ung.recipetoyou.recipe;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.ung.recipetoyou.member.Member;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class RecipeDAO {
	private int recipePage;

	@Autowired
	private MainRecipeRepo mrr;
	@Autowired
	private SubRecipeRepo srr;
	@Autowired
	private MemberRecipeRepo mbrr;
	
	@Autowired
	private MongoTemplate mt;
	
	public RecipeDAO() {
		recipePage = 20;
	}
	
	public void setCategory(String category, HttpServletRequest req) {
	    req.getSession().setAttribute("category", category);
	}

	public void getCategoryRecipe(int page, HttpServletRequest req) {
	    String searchName = (String) req.getSession().getAttribute("searchName");
	    String sortBy = (String) req.getSession().getAttribute("sortBy");
	    String category = (String) req.getSession().getAttribute("category");

	    Sort sort = Sort.by(Sort.Order.asc("rcpName")); // 기본 가나다순
	    if ("rcpRec".equals(sortBy)) {
	        sort = Sort.by(Sort.Order.desc("rcpRec"), Sort.Order.asc("rcpId")); // 추천순 정렬
	    }

	    Pageable pageable = PageRequest.of(page - 1, recipePage, sort);
	    long recipeCount; 

	    if (searchName == null) {
	        searchName = "";
	    }

	    if (category == null || category.isEmpty()) {
	        // 카테고리가 없을 때 모든 카테고리 조회
	        recipeCount = mrr.countByRcpNameContainingIgnoreCase(searchName);
	        List<Recipe> recipes = mrr.findByRcpNameContainingIgnoreCase(searchName, pageable);
	        req.setAttribute("recipes", recipes);
	    } else {
	        String categoryRegex = category.replace("&", "\\&");
	        recipeCount = mrr.countByRcpNameContainingIgnoreCaseAndRcpCategoryContainingIgnoreCase(searchName, categoryRegex);
	        List<Recipe> recipes = mrr.findByRcpNameContainingIgnoreCaseAndRcpCategoryContainingIgnoreCase(searchName, categoryRegex, pageable);
	        req.setAttribute("recipes", recipes);
	    }

	    int pageCount = (int) Math.ceil((double) recipeCount / recipePage);
	    req.setAttribute("pageCount", pageCount);
	    req.setAttribute("curPage", page);
	}


	
	public void clearSearch(HttpServletRequest req) {
		req.getSession().setAttribute("searchName", null);
	}
	
	public void searchRecipe(HttpServletRequest req) {
		String searchName = req.getParameter("searchName");
		req.getSession().setAttribute("searchName", searchName);
	}
	
	public SubRecipe getSubRecipeById(Integer rcpId) {
	    List<SubRecipe> subRecipes = srr.findByRcpId(rcpId);  // rcpId로 SubRecipe 조회
	    return subRecipes.isEmpty() ? null : subRecipes.get(0);  // 첫 번째 SubRecipe를 반환, 없으면 null
	}
	
    
	// 추천수 증가 메서드 확인
	public void incrementRecCount(Integer rcpId, HttpServletRequest req) {
	    try {
	    	 // 1. 중복 요청 방지 (새로고침 방지)
	        String token = req.getParameter("token");
	        String oldSuccessToken = (String) req.getSession().getAttribute("successToken");

	        if (oldSuccessToken != null && token.equals(oldSuccessToken)) {
	            System.out.println("등록실패(새로고침)");
	            return;  // 중복 요청 방지
	        }
	        // 새로운 토큰으로 세션 업데이트
	        req.getSession().setAttribute("successToken", token);

	        // 2. 로그인한 회원 정보 확인
	        Member member = (Member) req.getSession().getAttribute("loginMember");
	        if (member == null) {
	            req.setAttribute("loginMessage", "로그인 필요");
	            return;  // 로그인 안 된 상태
	        }
	        // 3. 이미 추천한 레시피인지 확인
	        Optional<MemberRecipe> existingRecommendation = mbrr
	            .findOptionalByMemberIdAndRecipeId(member.getId(), rcpId);
	        
	        // 4. 추천수 증가 (추천하지 않은 경우만)
	        if (existingRecommendation.isPresent()) {
	            // 추천을 취소하는 로직 (추천수 -1, 기록 삭제)
	            System.out.println("추천 취소");

	            // 4. 추천수 감소 (이미 추천한 레시피인 경우)
	            // SubRecipe의 rcpRec 감소
	            Query subRecipeQuery = new Query(Criteria.where("rcpId").is(rcpId));
	            Update subRecipeUpdate = new Update().inc("rcpRec", -1);
	            mt.updateFirst(subRecipeQuery, subRecipeUpdate, SubRecipe.class);

	            // Recipe의 rcpRec 감소
	            Query recipeQuery = new Query(Criteria.where("rcpId").is(rcpId));
	            Update recipeUpdate = new Update().inc("rcpRec", -1);
	            mt.updateFirst(recipeQuery, recipeUpdate, Recipe.class);

	            // 5. 추천 기록 삭제 (회원이 추천한 레시피 기록 삭제)
	            mbrr.delete(existingRecommendation.get());

	            System.out.println("추천이 취소되었습니다.");
	        } else {
	            // 추천이 되어 있지 않은 경우, 추천을 추가하는 로직
	            System.out.println("추천");

	            // 6. 추천수 증가 (추천하지 않은 경우만)
	            // SubRecipe의 rcpRec 업데이트
	            Query subRecipeQuery = new Query(Criteria.where("rcpId").is(rcpId));
	            Update subRecipeUpdate = new Update().inc("rcpRec", 1);
	            mt.updateFirst(subRecipeQuery, subRecipeUpdate, SubRecipe.class);

	            // Recipe의 rcpRec 업데이트
	            Query recipeQuery = new Query(Criteria.where("rcpId").is(rcpId));
	            Update recipeUpdate = new Update().inc("rcpRec", 1);
	            mt.updateFirst(recipeQuery, recipeUpdate, Recipe.class);

	            // 7. 추천 기록 추가 (회원이 추천한 레시피 기록 저장)
	            MemberRecipe newRecommendation = new MemberRecipe();
	            newRecommendation.setMemberId(member.getId());
	            newRecommendation.setRecipeId(rcpId);
	            mbrr.save(newRecommendation);

	            System.out.println("추천이 완료되었습니다.");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	//추천하기 or 추천취소
	public boolean isRecipeRecommended(Integer rcpId, HttpServletRequest req) {
	    Member member = (Member) req.getSession().getAttribute("loginMember");
	    if (member == null) {
	        return false; // 로그인 안 된 상태
	    }
	    
	    Optional<MemberRecipe> existingRecommendation = mbrr.findOptionalByMemberIdAndRecipeId(member.getId(), rcpId);
	    return existingRecommendation.isPresent();
	}

//	 특정 rcpId에 대한 SubRecipe의 동적 필드 값 조회
	public Map<String, String> getRcpMulFields(Integer rcpId) {
	    List<SubRecipe> subRecipes = srr.findByRcpId(rcpId);  // rcpId를 기준으로 조회
	    Map<String, String> rcpMulFields = new HashMap<>();
	    
	    if (subRecipes != null && !subRecipes.isEmpty()) {
	        SubRecipe subRecipe = subRecipes.get(0);
	        
	        for (int i = 1; i <= 20; i++) {
	            String fieldMulName = "rcpMul" + String.format("%02d", i);
	            String fieldValue = getFieldValueByName(subRecipe, fieldMulName);
	            rcpMulFields.put(fieldMulName, fieldValue);
	        }

	        for (int i = 1; i <= 20; i++) {
	            String fieldImgName = "rcpSubimg" + String.format("%02d", i);
	            String fieldValue = getFieldValueByName(subRecipe, fieldImgName);
	            rcpMulFields.put(fieldImgName, fieldValue);
	        }
	    }
	    return rcpMulFields;
	}

	private String getFieldValueByName(SubRecipe subRecipe, String fieldName) {
	    try {
	        // 필드를 가져옵니다.
	        Field field = subRecipe.getClass().getDeclaredField(fieldName);
	        field.setAccessible(true);  // private 필드에 접근할 수 있도록 설정
	        
	        // 필드 값 가져오기 (String 타입으로 캐스팅)
	        Object fieldValue = field.get(subRecipe); // 필드의 값을 가져옴
	        if (fieldValue != null) {
	            return fieldValue.toString(); // 필드 값이 null이 아니면 String으로 반환
	        } else {
	            return ""; // null 값이 들어올 경우 빈 문자열 반환
	        }
	    } catch (NoSuchFieldException e) {
	        return "";  // 필드가 없으면 빈 문자열 반환
	    } catch (IllegalAccessException e) {
	        return "";  // 접근할 수 없을 경우 빈 문자열 반환
	    }
	}
}