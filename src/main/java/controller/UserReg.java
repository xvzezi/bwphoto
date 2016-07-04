package controller;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import model.RegMes;
import model.db.User;

@RestController
public class UserReg {
	@RequestMapping(value = "/identity/reg", method = RequestMethod.POST)
	public RegMes register(@RequestBody User user, HttpSession session)
	{
		RegMes rm = new RegMes();
		rm.setFail("Not Implemented!");
		return rm;
	}
}
