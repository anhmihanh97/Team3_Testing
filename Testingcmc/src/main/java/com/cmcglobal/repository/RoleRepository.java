package com.cmcglobal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cmcglobal.entity.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
	Role findByName(String name);
	List<Role> findByPermissions_Id(int id);
	List<Role> findByUsers_Email(String email);
	List<Object[]> getAllRolePermission();
}
