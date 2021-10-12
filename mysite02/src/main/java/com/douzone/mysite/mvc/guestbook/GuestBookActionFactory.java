package com.douzone.mysite.mvc.guestbook;

import java.util.HashMap;
import java.util.Map;

import com.douzone.web.mvc.Action;
import com.douzone.web.mvc.ActionFactory;

public class GuestBookActionFactory extends ActionFactory {
	private Map<String, Action> actionMap = new HashMap<>();
	
	public GuestBookActionFactory() {
		super();
		actionMap.put("list", new ListAction());
		actionMap.put("deleteform", new DeleteFormAction());
		actionMap.put("delete", new DeleteAction());
		actionMap.put("insert", new InsertAction());
	}

	@Override
	public Action getAction(String actionName) {
		Action action = actionMap.get(actionName);
		if(action == null) action = actionMap.get("list");
		
		return action;
	}

}
