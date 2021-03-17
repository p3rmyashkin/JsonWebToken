package com.example.securingweb.repository;

import com.example.securingweb.entites.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

	List<Role> findAll();

	Role findByRoleName(String roleName);

	<S extends Role> S save(S role);
}
