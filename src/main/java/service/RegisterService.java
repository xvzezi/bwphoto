package service;

import javax.servlet.http.HttpSession;

import model.db.User;

public interface RegisterService{
	public String regUser(User user, HttpSession Session);
	
}
