package com.ung.recipetoyou.community;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityPostCommentRepository extends CrudRepository<CommunityPostComment, Integer> {
	public abstract List<CommunityPostComment> findByPostNoAndPostType(Integer no, Integer type);
	public abstract List<CommunityPostComment> findAllByPostNoAndPostType(Integer PostNo, Integer type);
	public abstract List<CommunityPostComment> findAllByPostNoAndPostTypeOrderByDateAsc(Integer no, Integer i);
}
