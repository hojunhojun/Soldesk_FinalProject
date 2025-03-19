package com.ung.recipetoyou.community;

import java.util.Collection;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityPostReplyRepository extends CrudRepository<CommunityPostReply, Integer> {
	public abstract List<CommunityPostReply> findByParNo(Integer i);
	public abstract List<CommunityPostReply> findAllByParNo(Integer no);
	public abstract Collection<? extends CommunityPostReply> findByParNoOrderByDateAsc(Integer no);

}
