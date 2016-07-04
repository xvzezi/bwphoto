package controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import model.RegMes;
import model.SimpleUser;
import service.LogService;
import util.Log;
import util.SpringIoC;

@Controller
public class AdminController {
	@RequestMapping("/web")
	public String adminIndex(HttpSession session, Model model)
	{
		// do log
		Log.log.log("/web").log("test").log("connected").log();

		model.addAttribute("test", "test");
		model.addAttribute("name", session.getAttribute("name"));
		return "index";
	}
	
	@RequestMapping(value = "/web/login", method = RequestMethod.GET)
	public ModelAndView adminLoginPage(HttpSession session, Model model)
	{
		//do log
		Log.log.log("/web/login").log("GET").log();

		if(session.getAttribute("role") == null)
			return new ModelAndView("login");
		else
			return new ModelAndView("redirect:/web");
	}
	
	@RequestMapping(value = "/web/login", method = RequestMethod.POST)
	@ResponseBody
	public RegMes adminLogin(@RequestBody SimpleUser user, HttpSession session, Model model)
	{
		// do log

		LogService doLog = SpringIoC.idGetter("logService", LogService.class);
		String mes = doLog.login(user, session);
		//事实上，session交由doLog做过了，之后合并代码需要将之删除
		RegMes rg = new RegMes();
		if(mes.equals("Success"))
		{
			Log.log.log("/web/login").log("POST")
					.log("username:").log(user.getUsername())
					.log("pwd:").log(user.getPwd()).log(mes).log();
			rg.setSuccess("Log Success");;
		}
		else
		{
			Log.log.log("/web/login").log("POST")
					.log("username:").log(user.getUsername())
					.log("pwd:").log(user.getPwd()).log(mes).log();
			rg.setFail("Login Failed");
		}
		return rg;
	}
	
}
