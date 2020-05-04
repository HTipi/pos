package com.spring.miniposbackend.repository.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.spring.miniposbackend.model.admin.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
//    @Query(value = "select u from User u where u.enable=true")
//    List<User> findAllActive();
	Optional<User> findFirstByUsername(String username);
	Optional<User> findFirstByApiToken(String token);

//    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM User c WHERE c.user_name = :user_name and c.user_visible=:user_visible")
//    boolean existsByUser_name(@Param("user_name") String user_name, @Param("user_visible") boolean user_visible);
//
//    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM User c WHERE c.user_tel = :user_tel and c.user_visible=:user_visible")
//    boolean existsByUser_tel(@Param("user_tel") String user_tel, @Param("user_visible") boolean user_visible);
}
