package controller;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import model.DetailUser;
import model.RegMes;
import model.SimpleUser;
import model.db.User;
import service.LogService;
import util.SpringIoC;

/**
 * UserLog
 * @author Xu Zezi
 * @category controller
 * @version
 * 		0 service controller for user login, test
 * @since 2016/6/29
 * @Description
 *   Simple Sample
 */
@RestController
public class UserLog {
	/**
	 * 登录
	 * @param user
	 * @param session
	 * @return login message
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public RegMes logIn(@RequestBody SimpleUser user, HttpSession session)
	{
		LogService doLog = SpringIoC.idGetter("logService", LogService.class);
		String mes = doLog.login(user,  session);
		RegMes rm = new RegMes();
		if(mes.equals("Success"))
		{
			rm.setSuccess("Login Success");
		}
		else
		{
			rm.setFail("User Not Exist Or Password Not Match");
		}
		return rm;
	}

	/**
	 * 用户信息
	 * @param session
	 * @return detailed user info
	 */
	@RequestMapping(value = "/identity/detail", method = RequestMethod.GET)
	public User getDetail(HttpSession session)
	{
		String username = (String)session.getAttribute("name");
		if(username == null)
		{
			return new User();
		}
		LogService doLog = SpringIoC.idGetter("logService", LogService.class);
		return doLog.userDetail(username);
	}
}
