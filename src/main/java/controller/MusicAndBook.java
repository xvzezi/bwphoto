package controller;

import model.RegMes;
import org.jboss.logging.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import static model.RegMes.FAIL;

/**
 * Service of music and book
 */
@RestController
public class MusicAndBook
{
	@RequestMapping(value = "/resources/{resource_id}/music/{music_hash}", method = RequestMethod.POST)
	public RegMes MusicHash(@PathVariable int resource_id, @PathVariable String music_hash, HttpSession session)
	{
		return FAIL("Not Implemented");
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
