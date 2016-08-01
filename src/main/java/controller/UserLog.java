package controller;

import javax.servlet.http.HttpSession;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import model.DetailUser;
import model.RegMes;
import model.SimpleUser;
import model.db.User;
import service.LogService;
import service.ProfileService;
import util.Log;
import util.SpringIoC;
import util.StatisticUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * UserLog
 * @author Xu Zezi
 * @category controller
 * @version
 * 		0 service controller for user login, test
 * 		1 service controller for logout			2016/7/1
 * 		2 service controller for user detail	2016/7/2
 * 		3 service controller for user profile	2016/7/7
 * 	    4 service controller for make friends   2016/7/11 DONE IN java.controller.Friend
 * 	    5 user profile improved                 2016/7/11
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
			StatisticUtil.visitorCounter += 1;
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

	/**
	 * 登出
	 * @param session
	 * @return 成功或者失败信息
     */
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
	@RequestMapping(value = "/identity/detail/{username}", method = RequestMethod.GET)
	@ResponseBody
	public User getDetail(@PathVariable String username, HttpSession session)
	{
		String name = (String)session.getAttribute("name");
		Log.log.log("/identity/detail GET").log("success").log("username:").log(username).log();
		LogService doLog = SpringIoC.idGetter("logService", LogService.class);
		User detailT = doLog.userDetail(username);
		detailT.setPwd(null);
		detailT.setRole(null);
		// process
		if(username.equals(name))
		{
			return detailT;
		}
		else
		{
			User user = new User();
			user.setName(detailT.getName());
			user.setAge(detailT.getAge());
			user.setContent(detailT.getContent());
			return user;
		}
	}

	/**
	 * 获取头像
	 * @param session
	 * @return 图片
     */
	@RequestMapping(value="/identity/profile/{username}", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> downloadProfile(@PathVariable String username, HttpSession session)
	{
		Log.log.log("get in here").log();
		//check
		//get what we get
		ProfileService ps = SpringIoC.idGetter("profileService", ProfileService.class);
		InputStream is = ps.getProfile(username);
		if(is == null)
		{
			return defaultProfile("/profile/default.png");
		}
		return ResponseEntity.ok()
				.headers(new HttpHeaders())
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(new InputStreamResource(is));
	}
	//helper for get default profile file
	private ResponseEntity<InputStreamResource> defaultProfile(String path)
	{
		//not logged get the default png
		ClassPathResource cpr = new ClassPathResource(path);
		InputStream is = null;
		try{
			is = cpr.getInputStream();
		}catch (IOException e){
			//file not found
			Log.log.log("default image open error:").log(e.getMessage()).log();
			return ResponseEntity.badRequest().body(null);
		}
		//should return default one
		return ResponseEntity.ok()
				.headers(new HttpHeaders())
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(new InputStreamResource(is));
	}
}
