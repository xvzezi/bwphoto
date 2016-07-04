package controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import model.SimpleUser;
import service.LogService;
import util.SpringIoC;

@Controller
public class AdminController {
	@RequestMapping("/web")
	public String adminIndex(HttpSession session, Model model)
	{
		model.addAttribute("test", "test");
		model.addAttribute("name", session.getAttribute("name"));
		return "index";
	}
	
	@RequestMapping(value = "/web/login", method = RequestMethod.GET)
	public ModelAndView adminLoginPage(HttpSession session, Model model)
	{
		if(session.getAttribute("role") == null)
			return new ModelAndView("login").addObject("user", new SimpleUser(null, null));
		else
			return new ModelAndView("redirect:/web");
	}
	
	@RequestMapping(value = "/web/login", method = RequestMethod.POST)
	public ModelAndView adminLogin(SimpleUser user, HttpSession session, Model model)
	{
		LogService doLog = SpringIoC.idGetter("logService", LogService.class);
		String mes = doLog.login(user, session);
		//事实上，session交由doLog做过了，之后合并代码需要将之删除
		if(mes.equals(user.getUsername()))
		{
			session.setAttribute("name", user.getUsername());
			session.setAttribute("role", 1);
		}
		return new ModelAndView("redirect:/web", model.asMap());
	}
	
}
