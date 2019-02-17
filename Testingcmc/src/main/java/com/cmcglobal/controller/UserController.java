package com.cmcglobal.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmcglobal.base.BaseController;
import com.cmcglobal.entity.Users;
import com.cmcglobal.service.RoleService;
import com.cmcglobal.service.UsersService;
import com.cmcglobal.serviceImpl.JwtService;
import com.cmcglobal.utils.ConstantPage;




@CrossOrigin(origins = "*")
@RestController
public class UserController extends BaseController{
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private UsersService usersService;
	@Autowired
	private BCryptPasswordEncoder passEncode;
	@Autowired
	private RoleService roleService;
	
	@PostMapping(value = ConstantPage.REST_API_LOGIN_USER, produces = { MediaType.APPLICATION_PROBLEM_JSON_VALUE })
	public ResponseEntity<Map<String, String>> login(@RequestParam("user") String user) {
		Map<String, String> response = new HashMap<>();
		JSONObject jsonObject = new JSONObject(user);
		String email = jsonObject.getString("email");
		String password = jsonObject.getString("password");
		Users users = usersService.findByEmail(email);
		String listPermissionAndMenu = roleService.getAllMenuAndPermission();
		String result = "";
		String role = "";
		List<GrantedAuthority> listAuth;
		HttpStatus httpStatus = null;
		if (users != null) {
			if (passEncode.matches(password, users.getPassword()) && users.getStatus() == 1) {
				try {
					result = jwtService.generateTokenLogin(users.getEmail());
					listAuth = users.getAuthorities();
					for (GrantedAuthority grantedAuthority : listAuth) {
						role += grantedAuthority.toString() + ",";
					}
					response.put("response", result);
					response.put("roleandpermission", listPermissionAndMenu);
					response.put("email", users.getEmail());
					response.put("role", role);
					httpStatus = HttpStatus.OK;
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (users.getStatus() == 0) {
				result = "Tài khoản chưa được kích hoạt";
				httpStatus = HttpStatus.FORBIDDEN;
			} else if (users.getStatus() == 2) {
				result = "Tài khoản đang bị khóa";
				httpStatus = HttpStatus.UNAUTHORIZED;
			} else {
				result = "Tài khoản hoặc mật khẩu không hợp lệ";
				httpStatus = HttpStatus.BAD_REQUEST;
			}
		} else {
			httpStatus = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity<Map<String, String>>(response, httpStatus);
	}
}
