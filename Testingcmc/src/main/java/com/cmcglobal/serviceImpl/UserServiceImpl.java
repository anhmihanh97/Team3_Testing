package com.cmcglobal.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cmcglobal.entity.Users;
import com.cmcglobal.repository.UsersRepository;
import com.cmcglobal.service.UsersService;

@Service
public class UserServiceImpl implements UsersService{
	@Autowired
	private BCryptPasswordEncoder passEncode;
	@Autowired
	private UsersRepository usersRepository;
	@Override
	public List<Users> getListUser() {
		return usersRepository.findAll();
	}


	@Override
	public Users findById(int id) {
		return usersRepository.findById(id).get();
	}

	@Override
	public Users findByEmail(String email) {
		return usersRepository.findByEmail(email);
	}

	@Override
	public boolean add(Users users) {
		usersRepository.save(users);
		return true;
	}

	@Override
	public boolean addUser(Users user) {
		try {
			usersRepository.save(user);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	@Override
	public boolean delete(int id) {
		boolean isSuccess = false;
		Users users = usersRepository.getOne(id);
		try {
			usersRepository.delete(users);
			isSuccess = true;
		} catch (Exception e) {
			isSuccess = false;
		}
		return isSuccess;
	}

	@Override
	public boolean edit(Users users) {
		boolean isSuccess = false;
		Users updateUsers = usersRepository.save(users);
		if (updateUsers != null) {
			isSuccess = true;
		} else {
			isSuccess = false;
		}
		return isSuccess;
	}

	@Override
	public Users loadUserByUsername(String username) {
		for (Users users : usersRepository.findAll()) {
			if (users.getFullname().equals(username)) {
				return users;
			}
		}
		return null;
	
	}


	@Override
	public List<String> getListRoleOfUser(String userName) {
		try {
			List<String> listRoleOfUser = new ArrayList<String>();
			listRoleOfUser = usersRepository.getListRoleOfUser(userName);
			return listRoleOfUser;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

}
