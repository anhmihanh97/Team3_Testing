package com.cmcglobal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cmcglobal.entity.Users;


@Repository
public interface UsersRepository extends JpaRepository<Users, Integer>{
	List<Users> findByStatus(int status);
	
	Users findByEmail(@Param("email") String email);

	List<Users> findByEmailContainingOrFullnameContaining(String email, String fullname);


	
	@Query(value = "select c.name from "
			+ "(role c inner join users_role b on c.id=b.role_id) inner join users a on a.id=b.users_id"
			+ "   where a.email=:userName",nativeQuery=true)
	List<String> getListRoleOfUser(@Param("userName") String userName);
}
