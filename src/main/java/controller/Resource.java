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

	/**
	 * 根据时间戳获取资源，第一个直接获取最新的
	 * @param session
	 * @return
	 */
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
	 * @deprecated
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
		String name = (String)session.getAttribute("name");
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

	/*************************************************friend's resources***********************************************/
	/**
	 * 获取朋友的最近的资源
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/resources/friends", method = RequestMethod.GET)
	public Object getFriendRecentResource(HttpSession session)
	{
		// check the name
		String name = (String)session.getAttribute("name");
		if(name == null)
		{
			return FAIL("Not Logged");
		}

		// get it right
		ResourceService rs = SpringIoC.idGetter("resourceService", ResourceService.class);

		return rs.getFriendAndLatest(name, null, 10);
	}

	/**
	 * 获取朋友最近的资源——配套
	 * @param timestamp
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/resources/friends/{timestamp}", method = RequestMethod.GET)
	public Object getFriendResourceByTimeStamp(@PathVariable Timestamp timestamp, HttpSession session)
	{
		// check the name
		String name = (String)session.getAttribute("name");
		if(name == null)
		{
			return FAIL("Not Logged");
		}

		// get it right
		ResourceService rs = SpringIoC.idGetter("resourceService", ResourceService.class);
		return rs.getFriendAndLatest(name, timestamp, 10);
	}

	/**
	 * 获取自己资源
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/resources/self", method = RequestMethod.GET)
	public Object getSelfResource(HttpSession session)
	{
		// check the name
		String name = (String)session.getAttribute("name");
		if(name == null)
		{
			return FAIL("Not Logged");
		}

		// get it right
		ResourceService rs = SpringIoC.idGetter("resourceService", ResourceService.class);
		return rs.getPersonalResource(name, name, null, 10);
	}

	/**
	 * 获取自己的资源——配套
	 * @param timestamp
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/resources/self/{timestamp}", method = RequestMethod.GET)
	public Object getSelfResourceByTime(@PathVariable Timestamp timestamp, HttpSession session)
	{
		// check the name
		String name = (String)session.getAttribute("name");
		if(name == null)
		{
			return FAIL("Not Logged");
		}

		// get it right
		ResourceService rs = SpringIoC.idGetter("resourceService", ResourceService.class);
		return rs.getPersonalResource(name, name, timestamp, 10);
	}
}
