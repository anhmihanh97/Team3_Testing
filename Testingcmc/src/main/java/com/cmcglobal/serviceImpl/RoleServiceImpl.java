package com.cmcglobal.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmcglobal.repository.RoleRepository;
import com.cmcglobal.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{
	@Autowired
	private RoleRepository roleRepository;
	@Override
	public String getAllMenuAndPermission() {
		String result = "";
		try {
			List<Object[]> listPermission = roleRepository.getAllRolePermission();
			int length = listPermission.size();
			length = listPermission.size();
			for (int index = 0; index < length; index++) {
				Object[] list = listPermission.get(index);
				result +=  list[0] + "," + list[1]+","+list[2]+","+list[3];
				if (index != length - 1) {
					result += ",";
				}
			}

			return result;
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			return null;
		}
	
	}

}
