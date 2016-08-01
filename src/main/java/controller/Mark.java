package controller;

import model.RegMes;
import model.db.Item;
import model.request.MarkCreation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import service.MarkService;
import service.ResourceService;
import util.SpringIoC;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.List;

import static model.RegMes.FAIL;
import static model.RegMes.SUCCESS;

/**
 * Service Of Marks On Items Or Marks
 * @author Xu Zezi
 * @since 2016/7/29
 * @version
 *      0   basic impl.
 *      1   role control bug 2016/8/1
 */
public class Mark
{
	/************************************************资源的评论********************************************************/

	/**
	 * 拉取最新的marks
	 * @param resource_id
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/resources/{resource_id}/marks", method = RequestMethod.GET)
	public Object getMarks(@PathVariable int resource_id, HttpSession session)
	{
		// log check
		String name = (String)session.getAttribute("name");
		if(name == null)
		{
			return FAIL("Not Logged");
		}

		// return the result with the latest 10
		// if the auth is not permitted, it will be null
		MarkService ms = SpringIoC.idGetter("markService", MarkService.class);
		return ms.getMarks(resource_id, null, name, null, 10);
	}

	@RequestMapping(value = "/resources/{resource_id}/marks/{timestamp}", method = RequestMethod.GET)
	public Object getMarkByTimestamp(@PathVariable int resource_id, @PathVariable Timestamp timestamp,
	                                 HttpSession session)
	{
		// log check
		String name = (String)session.getAttribute("name");
		if(name == null)
		{
			return FAIL("Not Logged");
		}

		// return the result with 10 after the given timestamp
		// if the auth is not permitted, it will be null
		MarkService ms = SpringIoC.idGetter("markService", MarkService.class);
		return ms.getMarks(resource_id, null, name, timestamp, 10);
	}
	/**
	 * 拉取某一条marks
	 * @param resource_id
	 * @param mark_id
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/resources/{resource_id}/marks/id/{mark_id}", method = RequestMethod.GET)
	public Object getAMark(@PathVariable int resource_id, @PathVariable int mark_id, HttpSession session)
	{
		// log check
		String name = (String)session.getAttribute("name");
		if(name == null)
		{
			return FAIL("Not Logged");
		}

		// return the result with 10 after the given timestamp
		// if the auth is not permitted, it will be null
		MarkService ms = SpringIoC.idGetter("markService", MarkService.class);
		List<model.db.Mark> result = ms.getMarks(resource_id, mark_id, name, null, 0);
		if(result == null)
			return FAIL("No Such Resource Or Not Permitted");
		return result.get(0);
	}

	/**
	 * 创建一条关于resource的评论
	 * @param resource_id
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/resources/{resource_id}/marks", method = RequestMethod.POST)
	public RegMes createMark(@PathVariable int resource_id, @RequestBody MarkCreation markCreation, HttpSession session)
	{
		// log check
		String name = (String)session.getAttribute("name");
		if(name == null)
		{
			return FAIL("Not Logged");
		}

		// check the auth
		PRV prv = goCheckOwner(resource_id, name);
		if(prv == PRV.P_PRIVATE)
		{
			return FAIL("Auth Not Permitted Or Resource Not Exists");
		}

		// now store the object
		// - check the timestamp first
		// but sorry, we use the system time in server end

		// - do the storage
		MarkService ms = SpringIoC.idGetter("markService", MarkService.class);
		int id = ms.createMark(markCreation.getContent(), resource_id, null, name);
		if(id < 0)
		{
			return FAIL("Creation Failed For Some Reason");
		}
		return SUCCESS(Integer.toString(id));
	}

	/**
	 * 创建一条评论的评论
	 * @param resource_id
	 * @param mark_id
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/resources/{resource_id}/marks/{mark_id}", method = RequestMethod.POST)
	public RegMes createMarkOnMark(@PathVariable int resource_id, @PathVariable int mark_id,
	                               @RequestBody MarkCreation markCreation, HttpSession session)
	{
		// log check
		String name = (String)session.getAttribute("name");
		if(name == null)
		{
			return FAIL("Not Logged");
		}

		// check the auth
		PRV prv = goCheckOwner(resource_id, name);
		if(prv == PRV.P_PRIVATE)
		{
			return FAIL("Auth Not Permitted Or Resource Not Exists");
		}

		// now store the object
		// - check the timestamp first
		// but sorry, we use the system time in server end

		// - do the storage
		MarkService ms = SpringIoC.idGetter("markService", MarkService.class);
		int id = ms.createMark(markCreation.getContent(), resource_id, mark_id, name);
		if(id < 0)
		{
			return FAIL("Creation Failed For Some Reason");
		}
		return SUCCESS(Integer.toString(id));
	}

	/**
	 * 删除一条评论
	 * @param resource_id
	 * @param mark_id
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/resources/{resource_id}/marks/{mark_id}", method = RequestMethod.DELETE)
	public RegMes deleteMark(@PathVariable int resource_id, @PathVariable int mark_id, HttpSession session)
	{
		// logged required
		String name = (String)session.getAttribute("name");
		if(name == null)
		{
			// give the not logged message
			return FAIL("Not Logged");
		}

		// check the auth will be done inside
		MarkService ms = SpringIoC.idGetter("markService", MarkService.class);
		return SUCCESS(ms.deleteMark(resource_id, name));
	}

	/**
	 * 重置，暂时不需要
	 * @Deprecated
	 * @param resource_id
	 * @param location
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/resources/{resource_id}/marks/reset/{location}", method = RequestMethod.PUT)
	public RegMes resetToLoc(@PathVariable int resource_id, @PathVariable int location, HttpSession session)
	{
		//required logged
		String name = (String)session.getAttribute("name");
		if(name == null)
		{
			// give the not logged message
			return FAIL("Not Logged");
		}

		// short cut
		location = location / 10 * 10;  // get to the lower 10 case
		int r_id = (int)session.getAttribute("resource_id");
		if(r_id == resource_id)
		{
			session.setAttribute("resource_loc", location);
			// we success
			return SUCCESS("Old Loc Reset");
		}

		// check the auth
		PRV auth = goCheckOwner(resource_id, name);
		if(auth == PRV.P_PRIVATE)
		{
			// return the auth not permitted message
			return FAIL("Auth Not Permitted Or Resource Not Exists");
		}

		// begin to reset
		session.setAttribute("resource_id", resource_id);
		session.setAttribute("resource_loc", location);

		// return good result
		return SUCCESS("New Resource Reset");
	}

	/**
	 * Give a Definition of the right the user can get
	 */
	private enum PRV{P_OWNER, P_PUBLIC, P_PRIVATE}

	/**
	 * check the right, and return a descriptor of it
	 * @param resource_id
	 * @param username
	 * @return rights
	 */
	private PRV goCheckOwner(int resource_id, String username)
	{
		ResourceService rs = SpringIoC.idGetter("resourceService", ResourceService.class);
		List<Item> items = rs.getResources(null, resource_id);
		if(items != null)
		{
			Item item = items.get(0);
			if(item.getStatus() == 'y')
			{
				return PRV.P_PUBLIC;
			}
			return PRV.P_OWNER;
		}
		return PRV.P_PRIVATE;
	}
}
