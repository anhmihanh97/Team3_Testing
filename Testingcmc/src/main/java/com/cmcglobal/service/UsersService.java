package com.cmcglobal.service;

import java.util.List;

import com.cmcglobal.entity.Users;


public interface UsersService {
	List<Users> getListUser();
	
	

	Users findById(int id);

	Users findByEmail(String email);

	boolean add(Users users);

	boolean addUser(Users user);

	boolean delete(int id);

	boolean edit(Users users);

	Users loadUserByUsername(String username);
	

	List<String> getListRoleOfUser(String userName);
}
