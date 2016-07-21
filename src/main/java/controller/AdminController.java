package controller;

import javax.servlet.http.HttpSession;

import model.UserForm;
import model.db.Memory;
import model.db.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import model.RegMes;
import model.SimpleUser;
import service.LogService;
import service.RegisterService;
import service.WordService;
import util.DateGetAge;
import util.Log;
import util.SpringIoC;

import java.util.List;

@Controller
public class AdminController {
	@RequestMapping("/web")
	public String adminIndex(HttpSession session, Model model)
	{
		// do log
		Log.log.log("/web").log("test").log("connected").log();
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

	@RequestMapping(value = "/web/register", method = RequestMethod.GET)
	public ModelAndView adminRegisterPage(HttpSession session, Model model)
	{
		Log.log.log("/web/register").log("GET").log();

		if(session.getAttribute("role") == null)
			return new ModelAndView("registration");
		else
			return new ModelAndView("redirect:/web/login");
	}

	@RequestMapping(value = "/web/register", method = RequestMethod.POST)
	@ResponseBody
	public RegMes adminRegister(@RequestBody UserForm user, HttpSession session, Model model)
	{
		// do log
		RegisterService doReg = SpringIoC.idGetter("regService", RegisterService.class);
		DateGetAge getA=new DateGetAge();
		User newuser=new User();
		newuser.setRole(1);
		newuser.setBirth(user.getBirth());
		newuser.setEmail(user.getEmail());
		newuser.setName(user.getName());
		newuser.setPwd(user.getPwd());
		newuser.setAge(getA.getAge(user.getBirth()));
		String mes = doReg.regUser(newuser, session);
		RegMes rg = new RegMes();
		if(mes.equals("SUCCESS"))
		{
			Log.log.log("/web/register").log("POST")
					.log("username:").log(user.getName())
					.log("pwd:").log(user.getPwd()).log(mes).log();
			rg.setSuccess("Register Success");;
		}
		else
		{
			Log.log.log("/web/login").log("POST")
					.log("username:").log(user.getName())
					.log("pwd:").log(user.getPwd()).log(mes).log();
			rg.setFail("Register Failed");
		}
		return rg;
	}

	@RequestMapping("/web/music")
	public String musicResource(HttpSession session, Model model)
	{
		// do log
		Log.log.log("/web/music").log("test").log("connected").log();

		model.addAttribute("test", "test");
		model.addAttribute("name", session.getAttribute("name"));
		return "music";
	}

	@RequestMapping("/web/book")
	public String bookResource(HttpSession session, Model model)
	{
		// do log
		Log.log.log("/web/book").log("test").log("connected").log();

		model.addAttribute("test", "test");
		model.addAttribute("name", session.getAttribute("name"));
		return "book";
	}

	@RequestMapping("/web/words")
	public String wordsResource(HttpSession session, Model model)
	{
		// do log
		Log.log.log("/web/words").log("test").log("connected").log();

		WordService getWords=SpringIoC.idGetter("wordService", WordService.class);
		List<Memory> list= getWords.getWords();
		session.setAttribute("list",list );

		Log.log.log("/web/words GET").log();



		model.addAttribute("test", "test");
		model.addAttribute("name", session.getAttribute("words"));
		model.addAttribute("list",session.getAttribute("list"));
		model.addAttribute("count",session.getAttribute("count"));

		return "words";
	}


}
