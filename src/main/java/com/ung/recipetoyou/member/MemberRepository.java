package com.ung.recipetoyou.member;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends CrudRepository<Member, String> {

	Optional<Member> findByNick(String nick);

}
