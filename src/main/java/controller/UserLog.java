package controller;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import model.DetailUser;
import model.RegMes;
import model.SimpleUser;
import model.db.User;
import service.LogService;
import util.Log;
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
	@ResponseBody
	public RegMes logIn(@RequestBody SimpleUser user, HttpSession session)
	{
		String oldUser = (String)session.getAttribute("name");
		LogService doLog = SpringIoC.idGetter("logService", LogService.class);
		String mes = doLog.login(user,  session);
		RegMes rm = new RegMes();
		if(mes.equals("Success"))
		{
			if(oldUser == null) oldUser = "";
			rm.setSuccess("Login Success with old user " + oldUser);
			Log.log.log("/login POST").log("success").log("username:").log(user.getUsername()).log();
		}
		else
		{
			session.invalidate();
			rm.setFail("User Not Exist Or Password Not Match");
			Log.log.log("/login POST").log("failed").log("username:").log(user.getUsername()).log();
		}
		return rm;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	@ResponseBody
	public RegMes logOut(HttpSession session)
	{
		String message;
		RegMes rm = new RegMes();
		if(session.getAttribute("name") == null)
		{
			rm.setSuccess("No User Logged");
		}
		else
		{
			session.invalidate();
			rm.setSuccess("Log out success");
		}
		return rm;
	}
	/**
	 * 用户信息
	 * @param session
	 * @return detailed user info
	 */
	@RequestMapping(value = "/identity/detail", method = RequestMethod.GET)
	@ResponseBody
	public User getDetail(HttpSession session)
	{
		String username = (String)session.getAttribute("name");
		if(username == null)
		{
			Log.log.log("/identity/detail GET").log("user not found").log();
			return new User();
		}
		Log.log.log("/identity/detail GET").log("success").log("username:").log(username).log();
		LogService doLog = SpringIoC.idGetter("logService", LogService.class);
		return doLog.userDetail(username);
	}
}
