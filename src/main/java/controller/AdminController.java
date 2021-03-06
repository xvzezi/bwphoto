package controller;

import javax.servlet.http.HttpSession;

import model.UserForm;
import model.db.Memory;
import model.db.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import model.response.RegMes;
import model.SimpleUser;
import service.LogService;
import service.RegisterService;
import service.WordService;
import service.UserService;
import util.DateGetAge;
import util.Log;
import util.SpringIoC;

import java.util.List;

/**
 * Admin Pg To Control
 * @since some time
 * @author Xu Zezi & Zhou TQ
 */
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

	/*
	 * @func：编辑回忆函数
	 * @     如果有id则修改，没有则添加
	 */
	@RequestMapping(value = "/web/words/edit", method = RequestMethod.POST)
	@ResponseBody
	public RegMes editWords(@RequestBody Memory memory, HttpSession session, Model model)
	{
		// do edit
		WordService doedit = SpringIoC.idGetter("wordService", WordService.class);

		String mes = doedit.updateMemory(memory, session);
		RegMes rg = new RegMes();
		if(mes.equals("SUCCESS"))
		{
			Log.log.log("/web/words/edit").log("POST")
					.log("content:").log(memory.getContent())
					.log(mes).log();
			rg.setSuccess("EDIT Success");
		}
		else
		{
			Log.log.log("/web/login").log("POST")
					.log("content:").log(memory.getContent())
					.log(mes).log();
			rg.setFail("EDIT Failed");
		}
		return rg;
	}


	@RequestMapping(value = "/web/words/delete", method = RequestMethod.POST)
	@ResponseBody
	public RegMes deleteWords(@RequestBody Memory memory, HttpSession session, Model model)
	{
		// do delete
		WordService dodel = SpringIoC.idGetter("wordService", WordService.class);

		String mes = dodel.deleteMemory(memory, session);
		RegMes rg = new RegMes();
		if(mes.equals("SUCCESS"))
		{
			Log.log.log("/web/words/delete").log("POST")
					.log("content:").log(memory.getContent())
					.log(mes).log();
			rg.setSuccess("DELETE Success");
		}
		else
		{
			Log.log.log("/web/words/delete").log("POST")
					.log("content:").log(memory.getContent())
					.log(mes).log();
			rg.setFail("DELETE Failed");
		}
		return rg;
	}

	@RequestMapping("/web/admin")
	public String adminResource(HttpSession session, Model model)
	{
		// do log
		Log.log.log("/web/admin").log("test").log("connected").log();

		UserService getAdmin=SpringIoC.idGetter("userService", UserService.class);
		List<User>list= getAdmin.getAdmin();
		session.setAttribute("list",list );

		Log.log.log("/web/admin GET").log();



		model.addAttribute("test", "test");
		model.addAttribute("list",session.getAttribute("list"));

		return "admin";
	}

	@RequestMapping(value = "/web/admin/edit", method = RequestMethod.POST)
	@ResponseBody
	public RegMes editAdmin(@RequestBody User user, HttpSession session, Model model)
	{
		// do edit
		UserService doedit = SpringIoC.idGetter("userService", UserService.class);
		user.setRole(1);
		String mes = doedit.updateUser(user, session);
		RegMes rg = new RegMes();
		if(mes.equals("SUCCESS"))
		{
			Log.log.log("/web/admin/edit").log("POST")
					.log("user:").log(user.getName())
					.log(mes).log();
			rg.setSuccess("EDIT Success");
		}
		else
		{
			Log.log.log("/web/admin/edit").log("POST")
					.log("user:").log(user.getName())
					.log(mes).log();
			rg.setFail("EDIT Failed");
		}
		return rg;
	}


	@RequestMapping(value = "/web/admin/delete", method = RequestMethod.POST)
	@ResponseBody
	public RegMes deleteAdmin(@RequestBody User user, HttpSession session, Model model)
	{
		// do delete
		UserService dodel = SpringIoC.idGetter("userService", UserService.class);

		String mes = dodel.deleteUser(user, session);
		RegMes rg = new RegMes();
		if(mes.equals("SUCCESS"))
		{
			Log.log.log("/web/admin/delete").log("POST")
					.log("user:").log(user.getName())
					.log(mes).log();
			rg.setSuccess("DELETE Success");
		}
		else
		{
			Log.log.log("/web/admin/delete").log("POST")
					.log("user:").log(user.getName())
					.log(mes).log();
			rg.setFail("DELETE Failed");
		}
		return rg;
	}

	@RequestMapping("/web/generaluser")
	public String generalUserResource(HttpSession session, Model model)
	{
		// do log
		Log.log.log("/web/generaluser").log("test").log("connected").log();

		UserService getGen=SpringIoC.idGetter("userService", UserService.class);
		List<User>list= getGen.getGeneral();
		session.setAttribute("list",list );

		Log.log.log("/web/generaluser GET").log();



		model.addAttribute("test", "test");
		model.addAttribute("list",session.getAttribute("list"));

		return "generaluser";
	}

	@RequestMapping(value = "/web/generaluser/edit", method = RequestMethod.POST)
	@ResponseBody
	public RegMes editGeneral(@RequestBody User user, HttpSession session, Model model)
	{
		// do edit
		UserService doedit = SpringIoC.idGetter("userService", UserService.class);
		String mes = doedit.updateUser(user, session);
		RegMes rg = new RegMes();
		if(mes.equals("SUCCESS"))
		{
			Log.log.log("/web/generaluser/edit").log("POST")
					.log("user:").log(user.getName())
					.log(mes).log();
			rg.setSuccess("EDIT Success");
		}
		else
		{
			Log.log.log("/web/generaluser/edit").log("POST")
					.log("user:").log(user.getName())
					.log(mes).log();
			rg.setFail("EDIT Failed");
		}
		return rg;
	}


	@RequestMapping(value = "/web/generaluser/delete", method = RequestMethod.POST)
	@ResponseBody
	public RegMes deleteUser(@RequestBody User user, HttpSession session, Model model)
	{
		// do delete
		UserService dodel = SpringIoC.idGetter("userService", UserService.class);

		String mes = dodel.deleteUser(user, session);
		RegMes rg = new RegMes();
		if(mes.equals("SUCCESS"))
		{
			Log.log.log("/web/generaluser/delete").log("POST")
					.log("user").log(user.getName())
					.log(mes).log();
			rg.setSuccess("DELETE Success");
		}
		else
		{
			Log.log.log("/web/generaluser/delete").log("POST")
					.log("user:").log(user.getName())
					.log(mes).log();
			rg.setFail("DELETE Failed");
		}
		return rg;
	}
}
