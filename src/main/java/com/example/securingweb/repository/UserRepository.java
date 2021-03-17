package com.example.securingweb.repository;

import com.example.securingweb.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

	List<User> findAll();

	User findByUsername(String username);

	<S extends User> S save(S user);
}
