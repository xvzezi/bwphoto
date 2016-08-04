package controller;

import model.RegMes;
import org.jboss.logging.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.ResourceService;
import util.SpringIoC;

import javax.servlet.http.HttpSession;

import static model.RegMes.FAIL;
import static model.RegMes.SUCCESS;

/**
 * Service of music and book
 */
@RestController
public class MusicAndBook
{
	@RequestMapping(value = "/resources/{resource_id}/music/{music_hash}", method = RequestMethod.POST)
	public RegMes MusicHash(@PathVariable int resource_id, @PathVariable String music_hash, HttpSession session)
	{
		String name = (String)session.getAttribute("name");
		if(name == null)
		{
			return FAIL("Not Logged");
		}

		// the called service must check the ownership
		ResourceService rs = SpringIoC.idGetter("resourceService", ResourceService.class);
		String mes = rs.setMusicHash(resource_id, music_hash, name);
		if(mes.equals("success"))
		{
			return SUCCESS(music_hash);
		}
		else
		{
			return FAIL(mes);
		}
	}

	@RequestMapping(value = "/resources/{resource_id}/book", method = RequestMethod.POST)
	public RegMes bookBasic(@PathVariable int resource_id, HttpSession session)
	{
		return FAIL("Not Implemented");
	}

	@RequestMapping(value = "/books/{book_id}", method = RequestMethod.GET)
	public Object getABook(@PathVariable int book_id, HttpSession session)
	{
		return FAIL("Not Implemented");
	}

	@RequestMapping(value = "/books/{book_id}", method = RequestMethod.POST)
	public Object changeABook(@PathVariable int book_id, HttpSession session)
	{
		return FAIL("Not Implemented");
	}
}
