package com.cmcglobal.base;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cmcglobal.serviceImpl.JwtService;



public class BaseController {
	public BaseController() {
	}
	public boolean checkRole(HttpServletRequest request,HttpServletResponse response,JwtService jwtService) throws IOException {
		if(request!=null) {
		String url=request.getRequestURI();
		if(url.equals("/login")) return true;
		String[] urlnew=url.split("/");
		String urlReve=urlnew[1]+urlnew[2];
		Map<String, String> listPermissionAndMenuOfRole = new HashMap<>();
		String token = request.getHeader("Authorization");
		if( token==null || !jwtService.validateTokenLogin(token)) return false;
		String listRolePermissionAndMenu = request.getHeader("ListRolePermission");
		if(listRolePermissionAndMenu == null || token == null) return false;
		String role = jwtService.getRoleFromToken(token);
		String[] listRole = role.split(",");
		String[] listRolePerandMenu = listRolePermissionAndMenu.split(",");
		int listRolelength = listRole.length;
		int listRolePerAndMenulength = listRolePerandMenu.length;
		for (int index = 0; index < listRolelength; index++) {
			for (int index1 = 0; index1 <= listRolePerAndMenulength - 4; index1 += 4) {
				String old = listPermissionAndMenuOfRole.get(listRole[index]);
				if (old == null)
					old = "";
				if (listRolePerandMenu[index1].equals(listRole[index])) {
					old += listRolePerandMenu[index1 + 2] + listRolePerandMenu[index1 + 3] + ",";
				}
				listPermissionAndMenuOfRole.put(listRole[index], old);
			}
		}
		for (int index = 0; index < listRole.length; index++) {
            String actionOfRole=listPermissionAndMenuOfRole.get(listRole[index]);
            if(actionOfRole.contains(urlReve)) return true;
		   }
		}
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "The token is not valid.");
		return false;
	}
}
