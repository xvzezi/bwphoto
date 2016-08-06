package service;

import javax.servlet.http.HttpSession;

import model.SimpleUser;
import model.db.User;

/**
 * Interface LogService
 * @author Xu Zezi
 * @category Service 
 * @version
 * 		0.Initial Structure
 * @since 2016/6/28
 * @Description
 *   LogService To Check
 */
public interface LogService {
	/**
	 * 登录
	 * @param user
	 * @param session
	 * @return success
	 * username, role, date
	 */
	public String login(SimpleUser user, HttpSession session);
	
	public User userDetail(String username);
	
}
