package com.spring.miniposbackend.repository.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.model.security.ClientAppUserIdentity;
import com.spring.miniposbackend.model.security.UserToken;

@Repository
public interface UserTokenRespository extends JpaRepository<UserToken, ClientAppUserIdentity>{
	
	Optional<UserToken> findFirstByApiToken(String token);
	@Query(value = "select ut from UserToken ut where ut.clientAppUserIdentity.user.id=?1 and ut.clientAppUserIdentity.clientApplication.name=?2 ")
	UserToken findByUserId(Integer userId, String username);

}
