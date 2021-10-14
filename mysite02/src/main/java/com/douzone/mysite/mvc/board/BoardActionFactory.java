package com.douzone.mysite.mvc.board;

import java.util.HashMap;
import java.util.Map;

import com.douzone.dao.BoardDao;
import com.douzone.util.SimpleConnectionProvider;
import com.douzone.web.mvc.Action;
import com.douzone.web.mvc.ActionFactory;

public class BoardActionFactory extends ActionFactory{
	private final BoardDao dao = new BoardDao(new SimpleConnectionProvider());
	private Map<String, Action> map = new HashMap<>();
	
	
	
	
	public BoardActionFactory() {
		super();
		map.put("list", new ListAction(dao));
		map.put("detail", new DetailAction(dao));
		map.put("writeform", new WriteFormAction(dao));
		map.put("write", new WriteAction(dao));
		map.put("modifyform", new ModyfiFormAction(dao));
		map.put("modify", new ModifyAction(dao));
		map.put("delete", new DeleteAction(dao));
	}




	@Override
	public Action getAction(String actionName) {
		Action action = map.get(actionName);
		if(action == null) action = map.get("list");
		
		return action;
	}

}
