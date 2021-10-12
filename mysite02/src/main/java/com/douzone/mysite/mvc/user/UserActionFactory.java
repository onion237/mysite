package com.douzone.mysite.mvc.user;

import java.util.HashMap;
import java.util.Map;

import com.douzone.mysite.mvc.main.MainAction;
import com.douzone.web.mvc.Action;
import com.douzone.web.mvc.ActionFactory;

public class UserActionFactory extends ActionFactory{
	Map<String, Action> actionMap;

	public UserActionFactory() {
		super();
		actionMap = new HashMap<String, Action>();
		actionMap.put("joinform", new JoinFormAction());
		actionMap.put("loginform", new LoginFormAction());
		actionMap.put("updateform", new UpdateFormAction());
		actionMap.put("join", new JoinAction());
		actionMap.put("joinsuccess", new JoinSuccessAction());
		actionMap.put("login", new LoginAction());
		actionMap.put("logout", new LogoutAction());
		actionMap.put("update", new UpdateAction());
		actionMap.put("main", new MainAction());
	}


	@Override
	public Action getAction(String actionName) {
		Action action = actionMap.get(actionName);
				
		if(action == null) action = actionMap.get("main");
		
		return action;
	}
}
