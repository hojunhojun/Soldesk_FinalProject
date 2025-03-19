package com.ung.recipetoyou.community;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ung.recipetoyou.member.Member;

@Repository
public interface CommunityPostRepository extends CrudRepository<CommunityPost, Integer> {

	public abstract long countByTitleContainingOrWriterIdContaining(String search, String id);
	public abstract List<CommunityPost> findByTitleContainingOrWriterIdContaining(String search, String id, Pageable pg);
	public abstract CommunityPost findByNo(Integer no);
	
	public abstract long countByTypeAndTitleContainingOrTypeAndWriterIdContaining(int type, String search, int type2, String search2);
	public abstract List<CommunityPost> findByTypeAndTitleContainingOrTypeAndWriterIdContaining(int type, String search, int type2, String search2, Pageable pg);
	public abstract List<CommunityPost> findByTypeAndTitleContainingOrTypeAndWriterIdContaining(int type, String search, int type2, String search2);
	
	public abstract List<CommunityPost> findTop3ByTypeOrderByNoDesc(int type);
	public abstract List<CommunityPost> findTop14ByTypeOrderByNoDesc(int type);
	
	public abstract long countByType(Integer i);
	public abstract long countByTypeAndRnoAndTitleContainingOrTypeAndRnoAndWriterIdContaining(int i, int int1,
			String search, int j, int int2, String search2);
	public abstract List<CommunityPost> findByTypeAndRnoAndTitleContainingOrTypeAndRnoAndWriterIdContaining(int i,
			int int1, String search, int j, int int2, String search2, Pageable pg);
	
	public abstract List<CommunityPost> findTop3ByTypeAndRnoOrderByNoDesc(int i, int ii);
	
	public abstract long countByCateAndTypeAndTitleContainingOrCateAndTypeAndWriterIdContaining(String cate, int type, String search, String cate2, int type2, String search2);
	public abstract List<CommunityPost> findByCateAndTypeAndTitleContainingOrCateAndTypeAndWriterIdContaining(String cate, int type, String search, String cate2, int type2, String search2, Pageable pg);
	public abstract long countByWriter(Member m);
}
