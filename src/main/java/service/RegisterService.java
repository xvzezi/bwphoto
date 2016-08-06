package service;

import javax.servlet.http.HttpSession;

import model.db.User;

/**
 * 注册独立业务
 * @author Zhou TQ
 */
public interface RegisterService{
	public String regUser(User user, HttpSession Session);
	
}
