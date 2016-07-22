package controller;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.swing.*;

import DAO.ItemDao;
import model.BasicInfo;
import model.RegMes;
import model.db.Item;
import model.db.Itemtag;
import model.db.Mark;
import model.db.Tag;
import model.request.MarkCreation;
import org.springframework.web.bind.annotation.*;
import service.MarkService;
import service.ResourceService;
import service.TagService;
import util.Log;
import util.SpringIoC;

import static model.RegMes.FAIL;
import static model.RegMes.SUCCESS;

@RestController
public class Resource
{
	/***************************************************资源***********************************************************/
	/**
	 * 获取资源
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/resources", method = RequestMethod.GET)
	public Object getResources(HttpSession session)
	{
		//check session
		String name = (String)session.getAttribute("name");
		if(name == null)
		{
			return FAIL("Not Logged");
		}

		// Get the resource content
		ResourceService rs = SpringIoC.idGetter("resourceService", ResourceService.class);
		List<Item> result = rs.getResources(name, -1);
		Log.log.log("/resources GET").log("logged by").log(name).log();
		return result;
	}

	@RequestMapping(value = "/resources/latest", method = RequestMethod.GET)
	public Object getNewResource(HttpSession session)
	{
		List<Item> items = SpringIoC.idGetter("resourceService", ResourceService.class).getLatestResource(null, 10);
		return items;
	}

	@RequestMapping(value = "/resources/latest/{timestamp}", method = RequestMethod.GET)
	public Object getAfterResource(@PathVariable Timestamp timestamp, HttpSession session)
	{
		List<Item> items = SpringIoC.idGetter("resourceService", ResourceService.class).getLatestResource(timestamp, 10);
		return items;
	}
	/**
	 * 获取单个资源
	 * @param resource_id
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/resources/{re_id}", method = RequestMethod.GET)
	@ResponseBody
	public Object getResource(@PathVariable("re_id")int resource_id, HttpSession session)
	{
		//check session
		String name = (String)session.getAttribute("name");
		if(name == null)
		{
			return FAIL("Not Logged");
		}

		// Get the resource content
		ResourceService rs = SpringIoC.idGetter("resourceService", ResourceService.class);
		List<Item> result = rs.getResources(name, resource_id);
		if(result == null)
		{
			return FAIL("Resource Not Exists");
		}
		Log.log.log("/resources/{re_id} GET").log("logged by").log(name).log();
		return result.get(0);
	}

	/**
	 * 创建资源
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/resources", method = RequestMethod.POST)
	public Object createResource(HttpSession session)
	{
		//check session
		String name = (String)session.getAttribute("name");
		if(name == null)
		{
			return SUCCESS("Not Logged");
		}

		//create the resource
		ResourceService rs = SpringIoC.idGetter("resourceService", ResourceService.class);
		Item result = rs.createResource(name, new BasicInfo());
		if(result != null)
		{
			Log.log.log("/resources POST").log("logged by").log(name).log("success").log();
			return result;
		}
		else
		{
			return FAIL("Creation Failed");
		}
	}
	/**
	 * 将资源的种类改换为public或者private
	 * @param resource_id
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/resources/{resource_id}/public", method=RequestMethod.PUT)
	public RegMes makePublic(@PathVariable int resource_id, HttpSession session)
	{
		// log check
		String name = (String)session.getAttribute("name");
		if(name == null)
		{
			return FAIL("Not Logged");
		}

		// check auth
		PRV prv = goCheckOwner(resource_id, name);
		if(prv != PRV.P_OWNER)
		{
			return FAIL("No Auth");
		}
		// do the change
		ResourceService rs = SpringIoC.idGetter("resourceService", ResourceService.class);
		char old = rs.changeToPublic(name, resource_id);
		return SUCCESS(""+old);
	}

	@RequestMapping(value = "/resources/{resource_id}/private", method=RequestMethod.PUT)
	public RegMes makePrivate(@PathVariable int resource_id, HttpSession session)
	{
		// log check
		String name = (String)session.getAttribute("name");
		if(name == null)
		{
			return FAIL("Not Logged");
		}

		// check auth
		PRV prv = goCheckOwner(resource_id, name);
		if(prv != PRV.P_OWNER)
		{
			return FAIL("No Auth");
		}

		// do the change
		ResourceService rs = SpringIoC.idGetter("resourceService", ResourceService.class);
		char old = rs.changeToPrivate(name, resource_id);
		return SUCCESS(""+old);
	}

	/**
	 * 删除一个resource
	 * @param resource_id
	 * @param session
	 * @return 进度信息
	 */
	@RequestMapping(value = "/resources/{resource_id}", method = RequestMethod.DELETE)
	public RegMes deleteResource(@PathVariable int resource_id, HttpSession session)
	{
		// log checked
		String name = (String)session.getAttribute("username");
		if(name == null)
		{
			return FAIL("Not Logged");
		}

		// do the deletion
		ResourceService rs = SpringIoC.idGetter("resourceService", ResourceService.class);
		boolean result = rs.deleteResource(name, resource_id);
		if(result)
		{
			return SUCCESS("Deletion Succeeded");
		}
		return FAIL("Resource Not Exists Or Not Permitted");
	}

	/************************************************资源的Tag*********************************************************/
	/**
	 * 获取resource的tag
	 * @param resource_id
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/resources/{resourec_id}/tags", method = RequestMethod.GET)
	public List<Tag> getTagsById(@PathVariable int resource_id, HttpSession session)
	{
		//check session
		String name = (String)session.getAttribute("name");
		if(name == null)
		{
			return null;
		}

		//
		TagService tagService = SpringIoC.idGetter("tagService", TagService.class);
		List<Itemtag> itemtags = tagService.getTags(name, resource_id);
		if(itemtags == null)
		{
			return null;
		}

		//
		List<Tag> tags = new ArrayList<Tag>();
		Tag tmp = new Tag();
		for(Itemtag tag : itemtags)
		{
			tmp.setName(tag.getTagName());
			tags.add(tmp);
		}
		return tags;
	}

	/**
	 * 创建tag
	 * @param resource_id
	 * @param tag_name
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/resources/{resource_id}/tags/{tag_name}", method = RequestMethod.POST)
	public RegMes createTagOn(@PathVariable int resource_id, @PathVariable String tag_name, HttpSession session)
	{
		//check session
		RegMes rm = new RegMes();
		String name = (String)session.getAttribute("name");
		if(name == null)
		{
			rm.setFail("Not Logged");
			return rm;
		}

		//
		TagService tagService = SpringIoC.idGetter("tagService", TagService.class);
		Itemtag itemtag = new Itemtag();
		itemtag.setTagName(tag_name);
		itemtag.setItemId(resource_id);
		boolean result = tagService.saveObject(name, itemtag);

		if(result)
		{
			rm.setSuccess("succeeded");
		}
		else
		{
			rm.setFail("failed");
		}
		return rm;
	}

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
		String name = (String)session.getAttribute("username");
		if(name == null)
		{
			return FAIL("Not Logged");
		}

		// return the result with the latest 10
		// if the auth is not permitted, it will be null
		MarkService ms = SpringIoC.idGetter("markService", MarkService.class);
		return ms.getMarks(resource_id, null, name, null, 10);
	}

	@RequestMapping(value = "/resources/{resource_id}/{timestamp}/marks", method = RequestMethod.GET)
	public Object getMarkByTimestamp(@PathVariable int resource_id, @PathVariable Timestamp timestamp,
	                                 HttpSession session)
	{
		// log check
		String name = (String)session.getAttribute("username");
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
	@RequestMapping(value = "/resources/{resource_id}/marks/{mark_id}", method = RequestMethod.GET)
	public Object getAMark(@PathVariable int resource_id, @PathVariable int mark_id, HttpSession session)
	{
		// log check
		String name = (String)session.getAttribute("username");
		if(name == null)
		{
			return FAIL("Not Logged");
		}

		// return the result with 10 after the given timestamp
		// if the auth is not permitted, it will be null
		MarkService ms = SpringIoC.idGetter("markService", MarkService.class);
		List<Mark> result = ms.getMarks(resource_id, mark_id, name, null, 0);
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
		String name = (String)session.getAttribute("username");
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
		String name = (String)session.getAttribute("username");
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
		String name = (String)session.getAttribute("username");
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
		String name = (String)session.getAttribute("username");
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
