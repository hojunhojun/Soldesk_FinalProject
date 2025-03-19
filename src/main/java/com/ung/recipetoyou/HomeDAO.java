package com.ung.recipetoyou;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ung.recipetoyou.community.CommunityPostRepository;
import com.ung.recipetoyou.recipe.MainPageRecipes;
import com.ung.recipetoyou.recipe.MainRecipeRepo;
import com.ung.recipetoyou.recipe.Recipe;
import com.ung.recipetoyou.recipe.SubRecipe;
import com.ung.recipetoyou.recipe.SubRecipeRepo;
import com.ung.recipetoyou.recipe.TodayRecipe;
import com.ung.recipetoyou.recipe.TodayRecipeRepository;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class HomeDAO {
    @Autowired
    private CommunityPostRepository cr;

    @Autowired
    private MainRecipeRepo mrr;

    @Autowired
	private TodayRecipeRepository trr;
    
    @Autowired
    private SubRecipeRepo srr;
    
    private List<Recipe> dailyRecipes;
    private Random r;

    public HomeDAO() {
        r = new Random();
        dailyRecipes = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        updateDailyRecipes();
    }
    
    @Scheduled(cron = "00 00 00 * * *")
    public void updateDailyRecipes() {
        List<Recipe> recipeList = mrr.findAll();
        HashSet<Integer> recipeNum = new HashSet<>();

        while (recipeNum.size() < 5 && recipeList.size() > 0) {
            recipeNum.add(r.nextInt(recipeList.size()));
        }

        dailyRecipes.clear();
        for (int index : recipeNum) {
            dailyRecipes.add(recipeList.get(index));
        }
    }
    
    public MainPageRecipes getHomeRecipePost(HttpServletRequest req) {
    	return new MainPageRecipes(dailyRecipes);
    }

    public void getHomeFoodGuidePost(HttpServletRequest req) {
        req.setAttribute("MainFoodGuideposts", cr.findTop3ByTypeOrderByNoDesc(3));
    }

    public void getHomeFreeCommuPost(HttpServletRequest req) {
        req.setAttribute("MainFreePosts", cr.findTop14ByTypeOrderByNoDesc(1));
    }
    
    public void getHomeChallengeCommuPost(HttpServletRequest req) {
		TodayRecipe existingRecipe = trr.findFirstByOrderByNameAsc();
        Recipe today = mrr.findByRcpId(existingRecipe.getRno());
        req.setAttribute("todayRcp", today);
        req.setAttribute("MainTodayChallengePosts",  
        		cr.findTop3ByTypeAndRnoOrderByNoDesc(2, today.getRcpId()));
	}
    
    public MainPageRecipes getmarketRecipePost(HttpServletRequest req) {
        List<Recipe> recipesWithSub = new ArrayList<>();
        for (Recipe recipe : dailyRecipes) { //dailyrecipe 가지고 foreach하여 subrecipeRepo에서 데이터 추출 
            List<SubRecipe> subRecipes = srr.findByRcpId(recipe.getRcpId());
            if (subRecipes != null && !subRecipes.isEmpty()) {
                recipe.setSubRecipes(subRecipes);
            } else {
                recipe.setSubRecipes(new ArrayList<>()); //데이터 없으면 빈리스트 추출시킴
            }
            recipesWithSub.add(recipe);
        }
        return new MainPageRecipes(recipesWithSub);
    }
}
