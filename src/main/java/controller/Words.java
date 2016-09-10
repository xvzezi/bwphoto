package controller;

import model.Message;
import model.RegMes;
import model.db.Memory;
import org.springframework.web.bind.annotation.*;
import service.WordService;
import util.SpringIoC;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 回忆资源
 * @author Xu Zezi
 * @since 2016/7/5
 */
@RestController
public class Words
{
	/**
     * get the content of the memory
     * @param resource_id
     * @param session
     * @return
     */
    @RequestMapping(value = "/resources/{resource_id}/words", method = RequestMethod.GET)
    public Object getWords(@PathVariable int resource_id, HttpSession session)
    {
        //check session
        String name = (String)session.getAttribute("name");
        if(name == null)
        {
            RegMes rm = new RegMes();
            rm.setFail("User Not Logged");
            return rm;
        }
        //get the words
        WordService ws = SpringIoC.idGetter("wordService", WordService.class);
        Memory mem = ws.getMemoryByItem(resource_id);
        if(mem == null)
        {
            RegMes rm = new RegMes();
            rm.setFail("resource not exists");
            return rm;
        }
        return mem;
    }

	/**
	 * create or update the memory content
     * @param resource_id
     * @param content
     * @param session
     * @return
     */
    @RequestMapping(value = "/resources/{resource_id}/words", method = RequestMethod.POST)
    public RegMes createWords(@PathVariable int resource_id, @RequestBody Message content, HttpSession session)
    {
        //check session
        String name = (String)session.getAttribute("name");
        if(name == null)
        {
            RegMes rm = new RegMes();
            rm.setFail("User Not Logged");
            return rm;
        }
        Memory mem = new Memory();
        mem.setContent(content.getContent());
        Date date = new Date();
        mem.setTime(new Timestamp(date.getTime()));

        WordService ws = SpringIoC.idGetter("wordService", WordService.class);
        boolean result = ws.createNewMemoryOnItem(resource_id, mem);
        RegMes rm = new RegMes();
        if(result)
        {
            rm.setSuccess("create succeeded");
        }
        else
        {
            rm.setFail("failed for some reason");
        }
        return rm;
    }
}
