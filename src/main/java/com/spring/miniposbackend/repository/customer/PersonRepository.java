package com.spring.miniposbackend.repository.customer;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.spring.miniposbackend.model.customer.Person;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{

	@Query(value="select p from Person p where p.primaryPhone =?1")
	Person findByPrimaryphone(String primaryphone);

	Person findFirstByname(String name);
	
	@Query(value="select p from Person p where p.primaryPhone =?1")
	Optional<Person> findByTelephone(String primaryPhone);
	
	@Query(value="select p from Person p where p.primaryPhone =?1")
	Optional<Person> findByOnlyPrimaryphone(String primaryphone);
	
	@Query(value="select p from Person p where p.primaryPhone =?1")
	Optional<Person> findByPrimaryphones(String primaryphone);
}
