package controller;

import model.RegMes;
import model.db.Book;
import model.request.BookCreation;
import org.jboss.logging.annotations.Param;
import org.springframework.web.bind.annotation.*;
import service.BookService;
import service.ResourceService;
import util.SpringIoC;

import javax.servlet.http.HttpSession;
import javax.swing.*;

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

	@RequestMapping(value = "/resources/{resource_id}/book/{isbn}", method = RequestMethod.POST)
	public RegMes bookBasic(@PathVariable int resource_id, @PathVariable String isbn, HttpSession session)
	{
		// check log
		String name = (String) session.getAttribute("name");
		if(name == null)
		{
			return FAIL("Not Logged");
		}
		// check iSBN
		if(!(isbn.length() == 13 || isbn.length() == 17))
		{
			return FAIL("Invalid ISBN");
		}
		// the called service should check the
		ResourceService rs = SpringIoC.idGetter("resourceService", ResourceService.class);
		String mes = rs.setMusicHash(resource_id, isbn, name);
		if(mes.equals("success"))
		{
			return SUCCESS(isbn);
		}
		else
		{
			return FAIL(mes);
		}
	}

	@RequestMapping(value = "/books/{book_id}", method = RequestMethod.GET)
	public Object getABook(@PathVariable int book_id, HttpSession session)
	{
		return FAIL("Not Implemented");
	}

	@RequestMapping(value = "/books/{ISBN}", method = RequestMethod.POST)
	public Object changeABook(@PathVariable String ISBN, @RequestBody BookCreation bookCreation, HttpSession session)
	{
		// check log
		String name = (String)session.getAttribute("name");
		if(name == null)
		{
			return FAIL("Not Logged");
		}

		BookService bs = SpringIoC.idGetter("bookService", BookService.class);
		String result = bs.updateIsbnUrl(ISBN, bookCreation.getUrl());
		if("success".equals(result))
		{
			return SUCCESS("success");
		}
		else
		{
			return FAIL(result);
		}
	}
}
