package controller;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import model.RegMes;
import model.db.User;
import service.RegisterService;
import util.Log;
import util.SpringIoC;

/**
 * 注册
 * @author Xu Zezi
 * @since 2016/7/1
 * @version
 *      0 basic register function
 */
@RestController
public class UserReg {
	/**
	 * 注册	/identity/reg
	 * @param user
	 * @param session
     * @return RegMes
	 * @version
	 * 		0	basic
     */
	@RequestMapping(value = "/identity/reg", method = RequestMethod.POST)
	public RegMes register(@RequestBody User user, HttpSession session)
	{
		RegisterService rs = SpringIoC.idGetter("regService", RegisterService.class);
		String mes = rs.regUser(user, session);
		RegMes rm = new RegMes();
		if(mes.equals("SUCCESS"))
		{
			//log
			Log.log.log("/identity/reg POST").log("success").log("username:").log(user.getName()).log();

			//form message
			rm.setSuccess("Register Success!");
		}
		else
		{
			//Log
			Log.log.log("/identity/reg POST").log("failed").log("username:").log(user.getName()).log(mes).log();

			//form message
			rm.setFail(mes);
		}
		return rm;
	}
}
