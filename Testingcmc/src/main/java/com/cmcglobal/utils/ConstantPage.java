package com.cmcglobal.utils;

public class ConstantPage {
	// BASE URL
		public static final String BASE_URL_CLIENT = "http://localhost:4200";
		// Role
		public static final String REST_API_GET_ALL_ROLE = "/role/list";
		public static final String REST_API_DELETE_ROLE_BY_ID = "/role/delete/{id}";
		public static final String REST_API_UPDATE_ROLE = "/role/update";
		public static final String REST_API_INSERT_ROLE = "/role/insert";
		public static final String REST_API_SORT_ROLE = "/role/sort";

		public static final String REST_API_SEARCH_KEY_ROLE = "role/search";
		public static final String REST_API_SORT_ROLE_KEY = "/role/sort/{key}";
		public static final String REST_API_ROLE_PERMISSION = "/role/addRolePermission";

	//  user
		public static final String REST_API_ACTIVE_USER = "/active";
		public static final String REST_API_ACTIVE_FORGET_PASSWORD = "/activeforgotpass";
		public static final String REST_API_GET_USER_BY_ID = "/users/{userId}";
		public static final String REST_API_PROFILE_USER = "/profileusers";
		public static final String REST_API_ROLE_USER = "/users/addUserRole";
		public static final String REST_API_ALL_ROLE_USER = "/users/getAllUserRole";
		public static final String REST_API_REMOVE_ROLE_USER = "/users/removeUserRole";
		public static final String REST_API_LIST_EXAM_OF_USER = "/users/listexamofuser";
		public static final String REST_API_DETAIL_USER = "/userdetail";
		public static final String REST_API_LIST_EXAM_COMPLETE = "/users/listExamUserCompleted/{userId}";
		public static final String REST_API_LIST_PRACTICE_COMPLETE = "/users/listPracticeUserCompleted/{userId}";
		public static final String REST_API_LIST_PRACTICE_BY_USER = "/users/listpracticeofuser";
		public static final String REST_API_ACTIVE_ACCOUNT = "/active_account";
		public static final String REST_API_LOGIN_USER = "/login";
		public static final String REST_API_COMPLETE_LIST_USER = "/users/listUserComplete";
		public static final String REST_API_INCOMPLETE_LIST_USER = "/users/listUserInComplete";
		public static final String REST_API_SEARCH_USER_ROLE = "/user/search";

		public static final String REST_API_GET_ALL_USERS = "/users/list";
		public static final String REST_API_DELETE_USERS_BY_ID = "/users/delete/{id}";
		public static final String REST_API_SEARCH_USERS = "/users/search";
		public static final String REST_API_SORT_USERS = "/users/sort";
		public static final String REST_API_INSERT_USERS = "/users/insert";
		public static final String REST_API_UPDATE_USERS = "/users/update";
		public static final String REST_API_UPDATE_USERS_ACTIVE = "/users/update/status";
		
}
