package controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Resource {
	@RequestMapping(value = "/resources", method = RequestMethod.GET)
	public List<Resource> getResources()
	{
		return null;
	}
	
	@RequestMapping(value = "/resources/{re_id}", method = RequestMethod.GET)
	public Resource getResource(@PathVariable("re_id")String resource_id, HttpSession session)
	{
		return null;
	}
}
