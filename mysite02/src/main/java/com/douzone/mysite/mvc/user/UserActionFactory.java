package com.douzone.mysite.mvc.user;

import java.util.HashMap;
import java.util.Map;

import com.douzone.dao.UserDao;
import com.douzone.mysite.mvc.main.MainAction;
import com.douzone.util.SimpleConnectionProvider;
import com.douzone.web.mvc.Action;
import com.douzone.web.mvc.ActionFactory;

public class UserActionFactory extends ActionFactory{
	private Map<String, Action> actionMap;
	private UserDao dao = new UserDao(new SimpleConnectionProvider());
	public UserActionFactory() {
		super();
		actionMap = new HashMap<String, Action>();
		actionMap.put("joinform", new JoinFormAction());
		actionMap.put("loginform", new LoginFormAction());
		actionMap.put("updateform", new UpdateFormAction(dao));
		actionMap.put("join", new JoinAction(dao));
		actionMap.put("joinsuccess", new JoinSuccessAction());
		actionMap.put("login", new LoginAction(dao));
		actionMap.put("logout", new LogoutAction());
		actionMap.put("update", new UpdateAction(dao));
		actionMap.put("main", new MainAction());
	}


	@Override
	public Action getAction(String actionName) {
		Action action = actionMap.get(actionName);
				
		if(action == null) action = actionMap.get("main");
		
		return action;
	}
}
