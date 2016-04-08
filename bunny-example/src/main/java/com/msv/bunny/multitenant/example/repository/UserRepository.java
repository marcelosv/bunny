package com.msv.bunny.multitenant.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.msv.bunny.multitenant.example.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("from User user where user.email = ?1")
	User findByEmail(String email);

}
